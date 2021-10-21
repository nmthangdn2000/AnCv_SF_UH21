const vntk = require('vntk');
const classifiers = new vntk.BayesClassifier();
const { dataInput, stopWords } = require('../util/nlp');
const tokenizer = vntk.wordTokenizer();
const tfidf = new vntk.TfIdf();

class BotService {
  async train() {
    dataInput.forEach((data) => {
      const sample = pretreatment(data.sample);
      classifiers.addDocument(sample, data.intent);
    });
    console.log('Đang train');
    classifiers.train();
    console.log('Train hoàn tất');
  }

  async handle(message) {
    const text = pretreatment(message);
    const intent = await classifiers.classify(text);
    const mess = intentToMess(intent);
    return mess;
  }
}

module.exports = new BotService();

function pretreatment(text) {
  const textStopWord = stopWord(text);
  const textClean = cleanText(textStopWord);
  return textClean;
}

function stopWord(text) {
  // tách từ
  const words = tokenizer.tag(text);
  // xóa stopword
  const removeStopWords = words.filter(function (w) {
    return stopWords.indexOf(w) < 0;
  });
  // nối lại thành câu
  return removeStopWords.join(' ').toLowerCase();
}

function cleanText(text) {
  return text.replace(/[&\/\\#,+()$~%.'":*?!<>{}]/g, '');
}

function vocabulary(input) {}

function arrayUnique(array) {
  var a = array.concat();
  for (var i = 0; i < a.length; ++i) {
    for (var j = i + 1; j < a.length; ++j) {
      if (a[i] === a[j]) a.splice(j--, 1);
    }
  }
  return a;
}

function bagOfWords(vocabulary, text) {
  const parse = (t) => vocabulary.map((w, i) => t.reduce((a, b) => (b === w ? ++a : a), 0));
  return parse(text);
}

function intentToMess(intent) {
  switch (intent) {
    case 'greeting':
      return 'Xin chào, Tôi là TBot \n Tôi có thể giúp gì cho bạn';
    default:
      return 'Xin lỗi, tôi không thể hiểu ý của bạn!';
  }
}
