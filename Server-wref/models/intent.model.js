const mongoose = require('mongoose');

const intentShema = new mongoose.Schema({
  content: { type: String },
  create_at: { type: Number },
  update_at: { type: Number },
});

const Intent = mongoose.model('Intent', intentShema, 'Intent');

module.exports = Intent;
