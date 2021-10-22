const mongoose = require('mongoose');

const sampleShema = new mongoose.Schema({
  idIntent: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Intent',
  },
  content: { type: String },
  create_at: { type: Number },
  update_at: { type: Number },
});

const Sample = mongoose.model('Sample', sampleShema, 'sample');

module.exports = Sample;
