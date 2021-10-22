const mongoose = require('mongoose');

const replyIntentShema = new mongoose.Schema({
  idIntent: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Intent',
  },
  content: { type: String },
  create_at: { type: Number },
  update_at: { type: Number },
});

const ReplyIntent = mongoose.model('ReplyIntent', replyIntentShema, 'replyIntent');

module.exports = ReplyIntent;
