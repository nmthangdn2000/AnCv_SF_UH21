const mongoose = require('mongoose');
const { TYPE_INTENT } = require('../common/constants');

const intentShema = new mongoose.Schema({
  intent: { type: String, unique: true },
  type: { type: String, enum: TYPE_INTENT, default: TYPE_INTENT.TEXT },
  feedback: { type: String },
  script: [
    {
      entity: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Entity',
      },
      repeat: Number,
      feedback: String,
    },
  ],
  scriptCode: {
    api: String,
    code: String,
    option: String,
  },
  create_at: { type: Number },
  update_at: { type: Number },
});

const Intent = mongoose.model('Intent', intentShema, 'Intent');

module.exports = Intent;
