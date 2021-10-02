const mongoose = require('mongoose');

const plantProtection = new mongoose.Schema({
  idShop: { type: mongoose.Schema.Types.ObjectId, ref: 'Shop' },
  name: { type: String },
  image: { type: String },
  detail: { type: String },
  qrcode: { type: String },
  create_at: { type: Number },
  update_at: { type: Number },
});

const PlantProtection = mongoose.model('PlantProtection', plantProtection, 'plantProtection');

module.exports = PlantProtection;
