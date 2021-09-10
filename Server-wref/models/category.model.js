const mongoose = require('mongoose');

const categoryShema = new mongoose.Schema({
  name: { type: String },
  slug: { type: String },
  create_at: {
    type: Number,
  },
  update_at: {
    type: Number,
  },
});

const Category = mongoose.model('Category', categoryShema, 'category');

module.exports = Category;
