const vntk = require('vntk');
const tf = require('@tensorflow/tfjs');
const classifiers = new vntk.BayesClassifier();
const { dataInput } = require('../util/nlp');
const mlNlp = require('../util/mlNlp');
const intentService = require('../service/intent.service');
const sampleService = require('../service/sample.service');
const entityService = require('../service/entity.service');
const entityData = {};
let model;
let labelList = [];
let vocabulary = [];
class BotService {
  async train() {
    const samples = await sampleService.getSample();
    entityService
      .getEntity()
      .then((data) => {
        data.forEach((d) => {
          entityData[d.name] = d.sampleEntity;
        });
        console.log(entityData);
      })
      .catch((err) => console.log(err));

    const data = samples.map((s) => {
      const sample = s.content;
      const intent = s.idIntent.intent;
      labelList.push(intent);
      return { sample, intent };
    });
    console.log(data);
    labelList = [...new Set(labelList)];
    console.log(labelList);
    // Bayes Classifier
    try {
      data.forEach((d) => {
        const sample = mlNlp.pretreatment(d.sample);
        classifiers.addDocument(sample, d.intent);
      });
      console.log('Đang train');
      classifiers.train();
      console.log('Train hoàn tất');
    } catch (error) {
      console.log(error);
    }

    // deep learning --------------------------------------------------------
    let labels = [];
    data.forEach((d) => {
      const sentenceClean = mlNlp.pretreatment(d.sample);
      const words = sentenceClean.split(' ');
      vocabulary = vocabulary.concat(words);
      labels.push(labelList.indexOf(d.intent));
    });
    vocabulary = mlNlp.arrayUnique(vocabulary);
    const index = vocabulary.indexOf('');
    if (index > -1) {
      vocabulary.splice(index, 1);
    }
    console.log(vocabulary);
    let vector = [];
    data.forEach((d) => {
      const sentenceClean = mlNlp.pretreatment(d.sample);
      const words = sentenceClean.split(' ');
      const bagOfWord = mlNlp.bagOfWords(vocabulary, words);
      vector.push(bagOfWord);
    });
    console.log(vector[vector.length - 1]);

    model = tf.sequential();

    model.add(tf.layers.dense({ inputShape: [vocabulary.length], units: 16, activation: 'sigmoid' }));
    model.add(tf.layers.dense({ units: labelList.length, activation: 'softmax' }));

    model.compile({
      optimizer: tf.train.adam(),
      loss: 'categoricalCrossentropy',
      metrics: ['accuracy'],
    });

    const xs = tf.tensor2d(vector);
    const labelTensor = tf.tensor1d(labels, 'int32');
    const ys = tf.oneHot(labelTensor, labelList.length);
    labelTensor.dispose();

    const options = {
      epochs: 400,
      validationSplit: 0.1,
      shuffle: true,
    };

    await model.fit(xs, ys, options).then((data) => {
      console.log('loss: ', data.history.loss[data.history.loss.length - 1]);
      console.log('accuracy: ', data.history.acc[data.history.acc.length - 1]);
    });
  }

  async handle(message, entity, oldIntent, repeat) {
    // const text = mlNlp.pretreatment(message);
    // const intent = await classifiers.classify(text);
    const intent = predictIntent(message);
    let data;
    if (entity) data = await mlNlp.intentScript(intent, message, entity, oldIntent, repeat, entityData);
    else data = await mlNlp.intentToMess(intent);
    return data;
  }
}

module.exports = new BotService();

function predictIntent(message) {
  const sentenceClean = mlNlp.pretreatment(message);
  const words = sentenceClean.split(' ');
  const bagOfWord = mlNlp.bagOfWords(vocabulary, words);
  const results = model.predict(tf.tensor2d([bagOfWord]));
  results.print();
  const index = results.argMax(1).dataSync();
  console.log(labelList[index]);
  return labelList[index];
}
