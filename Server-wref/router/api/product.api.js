const express = require('express');
const router = express.Router();
const controller = require('../../controllers/product.controller');
const path = require('path');
var multer = require('multer');
const storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, './public/uploads/');
  },
  filename: function (req, file, cb) {
    console.log(file);
    cb(
      null,
      file.fieldname + '-' + Date.now() + '-' + Math.round(Math.random() * 1e9) + path.extname(file.originalname)
    );
  },
});
const upload = multer({ storage: storage });

router.get('/product/home', controller.getFull);
// idShop, idCategory, page, limit
router.get('/product', controller.getProduct);

// id product
router.get('/product/:id', controller.getById);

router.post('/product', upload.single('media'), controller.create);

// // id shop
// router.put('/shop/:id', upload.single('media'), controller.update);

// id shop
router.delete('/product/:id', controller.delete);

module.exports = router;
