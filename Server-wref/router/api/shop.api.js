const express = require('express');
const router = express.Router();
const controller = require('../../controllers/shop.controller');
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

router.get('/shops', controller.getAll);
// id user
router.get('/:id/shop', controller.getByUserId);

// id shop
router.get('/shop/:id', controller.getById);

router.post('/shop', upload.single('media'), controller.createShop);

// id shop
router.put('/shop/:id', upload.single('media'), controller.updateShop);

// id shop
router.delete('/shop/:id', controller.deleteShop);

module.exports = router;
