const ReplyIntent = require('../models/intent.model');
const { ERROR } = require('../common/constants');

class ReplyIntentService {
  async getReplyIntent() {
    const replyIntent = await ReplyIntent.find();
    if (!replyIntent) throw ERROR.CanNotGetReplyIntent;
    return replyIntent;
  }

  async createReplyIntent(data) {
    const newReplyIntent = await new ReplyIntent({
      ...data,
      create_at: new Date(),
      update_at: new Date(),
    });
    const replyIntent = await newReplyIntent.save();
    if (!replyIntent) throw ERROR.CanNotCreateReplyIntent;
  }

  async updateReplyIntent(data) {
    const updateReplyIntent = await ReplyIntent.updateOne(
      { _id: data.id },
      { content: data.content, update_at: new Date() }
    );
    if (!updateReplyIntent || updateReplyIntent.n < 1) throw ERROR.CanNotUpdateReplyIntent;
  }

  async deleteReplyIntent(data) {
    const deleteReplyIntent = await ReplyIntent.deleteOne({ _id: data });
    if (!deleteReplyIntent || deleteReplyIntent.deletedCount < 1) throw ERROR.CanNotDeleteReplyIntent;
  }
}

module.exports = new ReplyIntentService();
