const fs = require("fs");

const express = require("express");
const router = express.Router();

router.get("/", (req, res) => {
  res.render("pages/home.ejs");
});

// Business Page
router.get("/Business", (req, res) => {
  res.render("pages/business.ejs", {
    datatypes: [
      "Năng suất cây trồng",
      "Bệnh thực vật",
      "Giá nông sản",
      "Thuốc BVTV",
      "Thời tiết",
      "Phân bố nông nghiệp",
    ],
    advertisement: {
      public: {
        customerApproachTypes: [
          "0-99 người (29$/tháng)",
          "100-299 người (59$/tháng)",
          "300-499 người (69$/tháng)",
          "500-1000 người (89$/tháng)",
        ],
      },
    },
  });
});

// About Us Page
router.get("/AboutUs", (req, res) => {
  res.render("pages/aboutUs.ejs");
});

// Tools Page
router.get("/Tools", (req, res) => {
  res.render("pages/tools.ejs");
});

module.exports = router;
