const Posts = require('../models/posts.model');
const fs = require('fs');

const getPosts = async (req, res) => {
  await Posts.find()
    .populate('idUser', 'userName avata')
    .populate('idLocation')
    // .populate("Like.iduserLike", "userName avata")
    .then((data) => res.send(data))
    .catch((err) =>
      res.json({
        success: false,
        msg: 'get Posts failed',
      })
    );
};
const addNewPosts = async (req, res) => {
  let arrMedia = [];
  req.files.forEach((element) => {
    arrMedia.push(element.filename);
  });
  let newPost = await new Posts({
    idUser: req.user._id,
    idLocation: req.body.idlocation,
    content: req.body.content,
    media: arrMedia,
    Like: [],
    Comment: 12,
    create_at: new Date(),
    update_at: new Date(),
  });
  await newPost
    .save()
    .then((data) => {
      res.json({
        success: true,
        msg: 'Posts new success',
      });
    })
    .catch((err) =>
      res.json({
        success: false,
        msg: 'Posts new failed',
      })
    );
};
const editPost = async (req, res) => {
  await Posts.updateOne(
    { _id: req.params.id },
    {
      $set: {
        content: req.body.content,
      },
    }
  )
    .then((data) => {
      res.json({
        success: true,
        msg: 'Posts update success',
      });
    })
    .catch((err) =>
      res.json({
        success: false,
        msg: 'Posts update failed',
      })
    );
};

const deletePosts = async (req, res) => {
  await Posts.findOne({ _id: req.params.id })
    .then((data) => {
      console.log(typeof data.media);
      if (data.media.length > 0)
        data.media.forEach((element) => {
          const path = './public/uploads/' + element;
          try {
            fs.unlinkSync(path);
          } catch (error) {
            console.log(' ' + error);
          }
        });
    })
    .catch((err) => console.log(' ' + err));
  await Posts.deleteOne({ _id: req.params.id })
    .then((data) =>
      res.json({
        success: true,
        msg: 'Delete posts success',
      })
    )
    .catch((err) => {
      console.log('' + err);
      res.json({
        success: false,
        msg: 'Delete posts failed',
      });
    });
};

module.exports = {
  // lấy tất cả các bài viết
  getPosts: getPosts,
  // thêm bài viết mới
  addNewPosts: addNewPosts,
  // editPosts
  editPost: editPost,
  //deletePosts
  deletePosts: deletePosts,
};
