const stopwords = require('../models/stopword.model');
const intent = require('../models/intent.model');
const sample = require('../models/sample.model');
const replyIntent = require('../models/replyIntent.model');
const { ERROR } = require('../common/constants');
class NlpService {
  async getStopWords() {
    const stopword = await stopwords.find();
    if (!stopword) throw ERROR.CanNotGetStopword;
    return stopword;
  }

  async createStopWords(data) {
    const newStopword = await new stopwords({
      ...data,
      create_at: new Date(),
      update_at: new Date(),
    });
    const stopword = await newStopword.save();
    if (!stopword) throw ERROR.CanNotCreateStopword;
  }

  async samples() {}

  async intents() {}

  async reply() {}
}

module.exports = new NlpService();
