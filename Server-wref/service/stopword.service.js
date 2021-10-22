const Stopwords = require('../models/stopword.model');
const { ERROR } = require('../common/constants');

class StopwordService {
  async getStopWords() {
    const stopword = await Stopwords.find();
    if (!stopword) throw ERROR.CanNotGetStopword;
    return stopword;
  }

  async createStopWords(data) {
    const newStopword = await new Stopwords({
      ...data,
      create_at: new Date(),
      update_at: new Date(),
    });
    const stopword = await newStopword.save();
    if (!stopword) throw ERROR.CanNotCreateStopword;
  }

  async updateStopWords(data) {
    const updateIntent = await Stopwords.updateOne({ _id: data.id }, { content: data.content, update_at: new Date() });
    if (!updateIntent || updateIntent.n < 1) throw ERROR.CanNotUpdateIntent;
  }

  async deleteStopWords(data) {
    const deleteStopword = await Stopwords.deleteOne({ _id: data });
    if (deleteStopword.deletedCount < 1) throw ERROR.CanNotDeleteStopword;
  }
}

module.exports = new StopwordService();
