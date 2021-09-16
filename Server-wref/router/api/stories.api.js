const express = require('express');
const router = express.Router();
const path = require('path');
const controller = require('../../controllers/stories.controller')

var multer  = require('multer');
const storage = multer.diskStorage({
    destination: function (req, file, cb) {
      cb(null, './public/uploads/');
    },
    filename: function (req, file, cb) {
      console.log(file);
      cb(null, file.fieldname + '-' + Date.now() + '-' + Math.round(Math.random() * 1E9)+path.extname(file.originalname));
    }
  });
const upload = multer({ storage: storage });

router.get('/stories', controller.getStories)

router.post('/stories', upload.array('image', 5), controller.postStories)

// router.delete('/posts/delete/:id', controller.deletePosts)

// router.put('/posts/put/:id', controller.editPost)

module.exports = router