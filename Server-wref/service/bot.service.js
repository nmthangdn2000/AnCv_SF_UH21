const vntk = require('vntk');
const classifiers = new vntk.BayesClassifier();
const { dataInput } = require('../util/nlp');
const mlNlp = require('../util/mlNlp');
const intentService = require('../service/intent.service');
const sampleService = require('../service/sample.service');

class BotService {
  async train() {
    const samples = await sampleService.getSample();
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

  async handle(message, scriptIntent, oldIntent, repeat) {
    const text = mlNlp.pretreatment(message);
    const intent = await classifiers.classify(text);
    let data;
    if (scriptIntent) data = await mlNlp.intentScript(intent, message, scriptIntent, oldIntent, repeat);
    else data = await mlNlp.intentToMess(intent);
    return data;
  }
}

module.exports = new BotService();
