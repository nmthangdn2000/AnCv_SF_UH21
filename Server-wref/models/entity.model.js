const mongoose = require('mongoose');

const entityShema = new mongoose.Schema({
  name: String,
  sampleEntity: [String],
  create_at: { type: Number },
  update_at: { type: Number },
});

const Entity = mongoose.model('Entity', entityShema, 'entity');

module.exports = Entity;
