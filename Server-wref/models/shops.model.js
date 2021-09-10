const mongoose = require('mongoose');

const shopShema = new mongoose.Schema({
  idUser: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
  },
  name: {
    type: String,
  },
  media: {
    type: String,
  },
  city: {
    type: String,
  },
  district: {
    type: String,
  },
  subDistrict: {
    type: String,
  },
  address: {
    type: String,
  },
  create_at: {
    type: Number,
  },
  update_at: {
    type: Number,
  },
});

const Shop = mongoose.model('Shop', shopShema, 'shop');

module.exports = Shop;
