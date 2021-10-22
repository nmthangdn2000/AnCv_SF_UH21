const vntk = require('vntk');
const classifiers = new vntk.BayesClassifier();
const { dataInput } = require('../util/nlp');
// const tfidf = new vntk.TfIdf();
const mlNlp = require('../util/mlNlp');

class BotService {
  async train() {
    // Bayes Classifier
    try {
      dataInput.forEach((data) => {
        const sample = mlNlp.pretreatment(data.sample);
        classifiers.addDocument(sample, data.intent);
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

  async handle(message) {
    const text = mlNlp.pretreatment(message);
    const intent = await classifiers.classify(text);
    const mess = mlNlp.intentToMess(intent);
    return mess;
  }
}

module.exports = new BotService();
