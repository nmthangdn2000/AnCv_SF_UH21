const express = require('express');
const router = express.Router();
const path = require('path');
const controller = require('../../controllers/inforAgri.controller');

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

router.get('/inforAgri/theme/all', controller.getAllInfoAgri);

router.get('/inforAgri/theme/:id/all', controller.getAllDataForTheme);

router.get('/inforAgri/detail/:id', controller.getDetailAgri);

router.post('/inforAgri/theme', controller.postThemeAgri);

router.post('/inforAgri/theme/item', upload.single('image'), controller.postItemAgri);

module.exports = router;
