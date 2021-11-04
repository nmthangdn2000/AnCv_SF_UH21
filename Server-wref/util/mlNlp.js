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

  async intentScript(intent, message, entity, oldIntent, repeat, entityData) {
    const getIntent = await Intent.findOne({ intent: oldIntent }).populate('script.entity').lean();
    if (!getIntent) {
      return {
        type: 'text',
        message: 'Xin lỗi, tôi không thể hiểu ý của bạn!',
      };
    }
    let index = getIntent.script.findIndex((s) => {
      console.log(s.entity._id);
      return entity === s.entity._id.toString();
    });
    const param = getEntities(message, getIntent.script[index].entity.name, entityData);

    // đúng
    if (param[0]) {
      params[getIntent.script[index].entity.name] = param[0];
      index++;
      if (index < getIntent.script.length) {
        return {
          type: 'text',
          message: getIntent.script[index].feedback,
          entity: getIntent.script[index].entity,
          oldIntent: oldIntent,
          repeat: 0,
        };
      }
      return resultData(getIntent, oldIntent);
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
      entity: entity,
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
    entity: message.script[0].entity,
    oldIntent: intent,
    repeat: 0,
  };
}

function getEntities(message, entity, dataEntity) {
  let key = '';
  dataEntity[entity].forEach((e) => {
    key += `${e}|`;
  });
  const regex = new RegExp(`(${key})`, 'g');
  return message.match(regex);
}

async function resultData(getIntent, oldIntent) {
  let data;
  if (getIntent.intent === 'weather') data = await weatherService.getWeatherCity(params.city);
  let feedback = getIntent.feedback;

  const keys = feedback.match(/{\w+}/g);
  const keyClear = keys.map((k) => k.match(/\w+/));
  keys.forEach((k, index) => {
    const regex = new RegExp(k, 'g');
    feedback = feedback.replace(regex, params[keyClear[index]]);
  });
  return {
    type: getIntent.type,
    message: feedback,
    data: data,
    oldIntent: oldIntent,
    repeat: 0,
  };
}
