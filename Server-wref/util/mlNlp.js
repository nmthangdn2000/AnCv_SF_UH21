const vntk = require('vntk');
const tokenizer = vntk.wordTokenizer();
const { stopWords } = require('../util/nlp');
const Intent = require('../models/intent.model');
const weatherService = require('../service/getDataWeather');

const params = {};
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
      return resultMess('text', message.script[0].feedback, message.script[0].entity, intent, 0);
    }
    return resultMess('text', message.feedback);
    // return {
    //   type: 'text',
    //   message: message.feedback,
    // };
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

  async intentScript(intent, message, entity, oldIntent, repeat, entityData) {
    const getIntent = await Intent.findOne({ intent: oldIntent }).populate('script.entity').lean();
    if (!getIntent) {
      return {
        type: 'text',
        message: 'Xin lỗi, tôi không thể hiểu ý của bạn!',
      };
    }
    let index = getIntent.script.findIndex((s) => {
      return entity === s.entity._id.toString();
    });
    if (index < 0) return defaultMess();
    const param = getEntities(message, getIntent.script[index].entity.name, entityData);

    // đúng
    if (param[0]) {
      params[getIntent.script[index].entity.name] = param[0];
      index++;
      if (index < getIntent.script.length) {
        return resultMess('text', getIntent.script[index].feedback, getIntent.script[index].entity, oldIntent, 0);
      }
      return resultMessageData(getIntent, oldIntent);
    }
    //sai thì lặp lại câu hỏi
    repeat = Number(repeat) + 1;
    if (repeat >= getIntent.script[index].repeat) {
      return defaultMess();
    }
    return resultMess('text', getIntent.script[index].feedback, entity, oldIntent, repeat);
  }
}

module.exports = new MlNlp();

async function withScript(intent) {}

function getEntities(message, entity, dataEntity) {
  let key = '';
  dataEntity[entity].forEach((e) => {
    key += `${e}|`;
  });
  const regex = new RegExp(`(${key})`, 'g');
  return message.match(regex);
}

async function resultMessageData(getIntent, oldIntent) {
  let data;
  if (getIntent.intent === 'weather') data = await weatherService.getWeatherCity(new MlNlp().stringToSlug(params.city));
  let feedback = getIntent.feedback;

  const keys = feedback.match(/{\w+}/g);
  const keyClear = keys.map((k) => k.match(/\w+/));
  keys.forEach((k, index) => {
    const regex = new RegExp(k, 'g');
    feedback = feedback.replace(regex, params[keyClear[index]]);
  });
  return resultMessWithData(getIntent.type, feedback, data, oldIntent, 0);
}

function resultMessWithData(type, message, data, oldIntent, repeat) {
  return {
    type,
    message,
    data,
    oldIntent,
    repeat,
  };
}

function resultMess(type, message, entity, oldIntent, repeat) {
  return {
    type,
    message,
    entity,
    oldIntent,
    repeat,
  };
}

function defaultMess() {
  return {
    type: 'text',
    message: 'Xin lỗi, tôi không thể hiểu ý của bạn!',
  };
}
