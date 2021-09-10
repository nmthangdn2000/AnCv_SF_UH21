const mongoose = require("mongoose");

const inforAgriShema = new mongoose.Schema({
  idTheme: {
    type: mongoose.Schema.Types.ObjectId,
    require: true,
    ref: "ThemeAgri",
  },
  title: {
    type: String,
    required: true,
  },
  url: {
    type: String,
    required: true,
  },
  image: {
    type: String,
    required: true,
  },
  created_at: {
    type: Number,
    required: true,
  },
});

const InforAgri = mongoose.model("InforAgri", inforAgriShema, "inforAgri");

module.exports = InforAgri;
