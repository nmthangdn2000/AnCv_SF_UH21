const mongoose = require("mongoose");

const themeAgriShema = new mongoose.Schema({
  name: {
    type: String,
    required: true,
  },
  slug: {
    type: String,
    required: true,
  },
});

const ThemeAgri = mongoose.model("ThemeAgri", themeAgriShema, "themeAgri");

module.exports = ThemeAgri;
