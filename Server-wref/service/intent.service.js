const Intent = require('../models/intent.model');
const { ERROR } = require('../common/constants');

class IntentService {
  async getIntent() {
    const intent = await Intent.find();
    if (!intent) throw ERROR.CanNotGetIntent;
    return intent;
  }

  async createIntent(data) {
    const newIntent = await new Intent({
      content: data,
      create_at: new Date(),
      update_at: new Date(),
    });
    const intent = await newIntent.save();
    if (!intent) throw ERROR.CanNotCreateIntent;
    return intent._id;
  }

  async updateIntent(data) {
    const updateIntent = await Intent.updateOne({ _id: data.id }, { content: data.content, update_at: new Date() });
    if (!updateIntent || updateIntent.n < 1) throw ERROR.CanNotUpdateIntent;
  }

  async deleteStopWords(data) {
    const deleteStopword = await Intent.deleteOne({ _id: data });
    if (!deleteStopword || deleteStopword.deletedCount < 1) throw ERROR.CanNotDeleteStopword;
  }
}

module.exports = new IntentService();
