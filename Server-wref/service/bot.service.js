const vntk = require('vntk');
const classifiers = new vntk.BayesClassifier();
const { dataInput, stopWords } = require('../util/nlp');
const tokenizer = vntk.wordTokenizer();
const tfidf = new vntk.TfIdf();

class BotService {
  async train() {
    // Bayes Classifier
    dataInput.forEach((data) => {
      const sample = pretreatment(data.sample);
      classifiers.addDocument(sample, data.intent);
    });
    console.log('Đang train');
    classifiers.train();
    console.log('Train hoàn tất');

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
  let removeStopWords = words.filter(function (w) {
    return stopWords.indexOf(w) < 0;
  });
  removeStopWords = removeStopWords.map((w) => w.replace(' ', '_'));
  // nối lại thành câu
  return removeStopWords.join(' ').toLowerCase();
}

function cleanText(text) {
  const textClean = text.replace(/[&\/\\#,+()$~%.'":*?!<>{}]/g, '');
  return stringToSlug(textClean);
}

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

function stringToSlug(str) {
  // remove accents
  var from = 'àáãảạăằắẳẵặâầấẩẫậèéẻẽẹêềếểễệđùúủũụưừứửữựòóỏõọôồốổỗộơờớởỡợìíỉĩịäëïîöüûñçýỳỹỵỷ',
    to = 'aaaaaaaaaaaaaaaaaeeeeeeeeeeeduuuuuuuuuuuoooooooooooooooooiiiiiaeiiouuncyyyyy';
  for (var i = 0, l = from.length; i < l; i++) {
    str = str.replace(RegExp(from[i], 'gi'), to[i]);
  }

  str = str.toLowerCase().trim();

  return str;
}
