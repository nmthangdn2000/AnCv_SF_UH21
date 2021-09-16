const mongoose = require('mongoose');

const productShema = new mongoose.Schema({
  idShop: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Shop',
  },
  idCategory: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Category',
  },
  name: { type: String },
  media: { type: String },
  price: { type: Number },
  saleOff: { type: Number },
  type: { type: Boolean },
  ingredient: { type: String },
  effect: { type: String },
  userManual: { type: String },
  note: { type: String },
  create_at: {
    type: Number,
  },
  update_at: {
    type: Number,
  },
});

const Product = mongoose.model('Product', productShema, 'product');

module.exports = Product;
