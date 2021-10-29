const vntk = require('vntk');
const tokenizer = vntk.wordTokenizer();
const { stopWords } = require('../util/nlp');
const Intent = require('../models/intent.model');
const weatherService = require('../service/getDataWeather');

class MlNlp {
  pretreatment(text) {
    const textStopWord = this.stopWord(text);
    const textClean = this.cleanText(textStopWord);
    return textClean;
  }

  stopWord(text) {
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

  cleanText(text) {
    const textClean = text.replace(/[&\/\\#,+()$~%.'":*?!<>{}]/g, '');
    return this.stringToSlug(textClean);
  }

  arrayUnique(array) {
    var a = array.concat();
    for (var i = 0; i < a.length; ++i) {
      for (var j = i + 1; j < a.length; ++j) {
        if (a[i] === a[j]) a.splice(j--, 1);
      }
    }
    return a;
  }

  bagOfWords(vocabulary, text) {
    const parse = (t) => vocabulary.map((w, i) => t.reduce((a, b) => (b === w ? ++a : a), 0));
    return parse(text);
  }

  async intentToMess(intent) {
    const message = await Intent.findOne({ intent }).lean();
    if (!message) return 'Xin lỗi, tôi không thể hiểu ý của bạn!';
    if (intent == 'watch.weather') {
      const data = await weatherService.getWeatherCity('danang');
      return {
        type: 'weather',
        data,
      };
    }
    return {
      type: 'text',
      data: message.feedback,
    };
  }

  stringToSlug(str) {
    // remove accents
    var from = 'àáãảạăằắẳẵặâầấẩẫậèéẻẽẹêềếểễệđùúủũụưừứửữựòóỏõọôồốổỗộơờớởỡợìíỉĩịäëïîöüûñçýỳỹỵỷ',
      to = 'aaaaaaaaaaaaaaaaaeeeeeeeeeeeduuuuuuuuuuuoooooooooooooooooiiiiiaeiiouuncyyyyy';
    for (var i = 0, l = from.length; i < l; i++) {
      str = str.replace(RegExp(from[i], 'gi'), to[i]);
    }

    str = str.toLowerCase().trim();

    return str;
  }
}

module.exports = new MlNlp();
