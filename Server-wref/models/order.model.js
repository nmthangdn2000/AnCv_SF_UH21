const mongoose = require('mongoose');

const orderShema = new mongoose.Schema({
  idUser: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
  },
  idProduct: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Product',
  },
  amount: { type: Number },
  totalPrice: { type: Number },
  deliveryTo: { type: String },
  status: { type: Number },
  statusShip: { type: Number },
  create_at: { type: Number },
  update_at: { type: Number },
});

const Order = mongoose.model('Order', orderShema, 'order');

module.exports = Order;
