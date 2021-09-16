const mongoose = require('mongoose');

const cartShema = new mongoose.Schema({
  idUser: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
  },
  idProduct: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Product',
  },
  create_at: {
    type: Number,
  },
  update_at: {
    type: Number,
  },
});

const Cart = mongoose.model('Cart', cartShema, 'cart');

module.exports = Cart;
