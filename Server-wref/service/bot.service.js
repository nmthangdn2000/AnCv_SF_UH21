const vntk = require('vntk');
const classifiers = new vntk.BayesClassifier();
const { dataInput } = require('../util/nlp');
const mlNlp = require('../util/mlNlp');
const intentService = require('../service/intent.service');
const sampleService = require('../service/sample.service');
const entityService = require('../service/entity.service');
const entityData = {};

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
      return { sample, intent };
    });
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
    // let vocabulary = [];
    // dataInput.forEach((d) => {
    //   const sentenceClean = pretreatment(d.sample);
    //   const words = sentenceClean.split(' ');
    //   vocabulary = arrayUnique(vocabulary.concat(words));
    // });
    // console.log(vocabulary);
    // let vector = [];
    // dataInput.forEach((d) => {
    //   const sentenceClean = pretreatment(d.sample);
    //   const words = sentenceClean.split(' ');
    //   const bagOfWord = bagOfWords(vocabulary, words);
    //   vector.push(bagOfWord);
    // });
    // console.log(vector);
  }

  async handle(message, entity, oldIntent, repeat) {
    const text = mlNlp.pretreatment(message);
    const intent = await classifiers.classify(text);
    let data;
    if (entity) data = await mlNlp.intentScript(intent, message, entity, oldIntent, repeat, entityData);
    else data = await mlNlp.intentToMess(intent);
    return data;
  }
}

module.exports = new BotService();
