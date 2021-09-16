const express = require('express');
const router = express.Router();
const path = require('path');
const controller = require('../../controllers/comment.controller');

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

router.get('/posts/:id/comment', controller.getComment);

router.post('/posts/:id/comment', upload.single('image'), controller.createComment);

router.get('/posts/refcomment=:id', controller.getRefComment);

router.post('/posts/:idposts/comment=:idcmt', controller.createRefComment);

router.put('/posts/comment=:id', controller.updateComment);

router.delete('/posts/comment=:id', controller.deleteComment);

module.exports = router;
