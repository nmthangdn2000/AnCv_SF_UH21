const Comment = require('../models/comment.model');
const { ERROR } = require('../common/constants');
const fs = require('fs');

class CommentService {
  async getComment(id) {
    if (!id) throw Error(ERROR.CanNotGetComment);
    const data = await Comment.find({ idPosts: id }).populate('idUser', 'userName avata').lean();
    if (!data) throw Error(ERROR.CanNotGetComment);
    return data;
  }

  async createComment(idUser, idPosts, content, media) {
    const newCmt = await new Comment({
      idUser,
      idPosts,
      content,
      media,
      Like: [],
      create_at: new Date(),
      update_at: new Date(),
    });
    const data = await newCmt.save();
    if (!data) throw Error(ERROR.CanNotCreateComment);
  }

  async updateComment(id, content) {
    const data = await Comment.findByIdAndUpdate(id, {
      $set: {
        content,
        update_at: new Date(),
      },
    });
    if (!data) throw Error(ERROR.CanNotUpdateComment);
  }

  async createRefComment(idUser, idPosts, idComment, content) {
    const newCmt = await new Comment({
      idUser,
      idPosts,
      idComment,
      content,
      // media: req.file.filename,
      Like: [],
      create_at: new Date(),
      update_at: new Date(),
    });
    const data = await newCmt.save();
    if (!data) throw Error(ERROR.CanNotCreateRefComment);
  }

  async getRefComment(idComment) {
    await Comment.findOne({ idComment });
  }

  async deleteComment(id) {
    // delete file
    await Comment.findOne({ _id: id })
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
    await Comment.deleteOne({ _id: id });
  }
}

module.exports = new CommentService();
