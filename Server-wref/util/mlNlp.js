const vntk = require('vntk');
const tokenizer = vntk.wordTokenizer();
const { stopWords } = require('../util/nlp');
const Intent = require('../models/intent.model');
const weatherService = require('../service/getDataWeather');

const params = [];
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
    if (message.script.length > 0) {
      return await withScript(intent);
      // const data = await weatherService.getWeatherCity('danang');
      // return {
      //   type: 'weather',
      //   data,
      // };
    }
    return {
      type: 'text',
      message: message.feedback,
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

  async intentScript(intent, message, scriptIntent, oldIntent, repeat) {
    const getIntent = await Intent.findOne({ intent: oldIntent }).populate('script.intent').lean();
    if (!getIntent) {
      return {
        type: 'text',
        message: 'Xin lỗi, tôi không thể hiểu ý của bạn!',
      };
    }
    let index = getIntent.script.findIndex((s) => {
      console.log(s.intent._id);
      return scriptIntent === s.intent._id.toString();
    });
    // đúng
    if (intent === getIntent.script[index].intent.intent) {
      const param = getEntities(message);
      params.push(param);
      index++;
      if (index < getIntent.script.length) {
        return {
          type: 'text',
          message: getIntent.script[index].feedback,
          scriptIntent: getIntent.script[index].intent,
          oldIntent: oldIntent,
          repeat: 0,
        };
      }
      const data = await weatherService.getWeatherCity('danang');
      return {
        type: getIntent.type,
        message: `${getIntent.feedback} ${param} như sau:`,
        data: data,
        oldIntent: oldIntent,
        repeat: 0,
      };
    }
    //sai thì lặp lại câu hỏi
    repeat = Number(repeat) + 1;
    if (repeat >= getIntent.script[index].repeat) {
      return {
        type: 'text',
        message: 'Xin lỗi, tôi không thể hiểu ý của bạn!',
      };
    }
    return {
      type: 'text',
      message: getIntent.script[index].feedback,
      scriptIntent: scriptIntent,
      oldIntent: oldIntent,
      repeat: repeat,
    };
  }
}

module.exports = new MlNlp();

async function withScript(intent) {
  const message = await Intent.findOne({ intent }).lean();
  return {
    type: 'text',
    message: message.script[0].feedback,
    scriptIntent: message.script[0].intent,
    oldIntent: intent,
    repeat: 0,
  };
}

function getEntities(message) {
  return 'Đà Nẵng';
}
