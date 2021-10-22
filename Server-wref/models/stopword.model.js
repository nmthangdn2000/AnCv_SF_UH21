const mongoose = require('mongoose');

const stopwordShema = new mongoose.Schema({
  content: { type: String },
  create_at: { type: Number },
  update_at: { type: Number },
});

const Stopword = mongoose.model('Stopword', stopwordShema, 'Stopword');

module.exports = Stopword;
