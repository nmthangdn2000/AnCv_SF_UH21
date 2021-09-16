const mongoose = require("mongoose");

const cropsSchema = new mongoose.Schema({
  name: { type: String },
  description: { type: String },
  optimal_sun: { type: String },
  optimal_soil: { type: String },
  planting_considerations: { type: String },
  when_to_plant: { type: String },
  growing_from_seed: { type: String },
  transplanting: { type: String },
  spacing: { type: String },
  watering: { type: String },
  feeding: { type: String },
  other_care: { type: String },
  diseases: { type: String },
  pests: { type: String },
  harvesting: { type: String },
  storage_use: { type: String },
  image_url: { type: String },
});

const crops = mongoose.model("crops", cropsSchema, "crops");

module.exports = crops;
