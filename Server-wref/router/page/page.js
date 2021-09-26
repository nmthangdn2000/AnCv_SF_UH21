const express = require("express");
const router = express.Router();

router.get("/", (req, res) => {
  res.render("pages/home.ejs");
});

router.get("/Business", (req, res) => {
  res.render("pages/business.ejs");
});

module.exports = router;