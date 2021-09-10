const commentService = require('../service/comment.service');
const { responseError, responseSuccess, responseSuccessWithData } = require('./base.controller');
const { RESPONSE } = require('../common/constants');
class CommentController {
  async getComment(req, res) {
    try {
      const data = await commentService.getComment(req.params.id);
      return responseSuccessWithData(res, data);
    } catch (error) {
      responseError(res, error.message);
    }
  }

  async createComment(req, res) {
    try {
      const idUser = req.user._id;
      const idPosts = req.params.id;
      const content = req.body.content;
      const media = req.file ? req.file.filename : null;
      const data = await commentService.createComment(idUser, idPosts, content, media);
      return responseSuccessWithData(res, data);
    } catch (error) {
      responseError(res, error.message);
    }
  }

  async updateComment(req, res) {
    try {
      await commentService.updateComment(req.params.id, req.body.content);
      return responseSuccess(res, RESPONSE.UPDATECOMMENTSUCCESS);
    } catch (error) {
      responseError(res, error.message);
    }
  }

  async createRefComment(req, res) {
    try {
      const idUser = req.user._id;
      const idPosts = req.params.id;
      const content = req.body.content;
      const media = req.file ? req.file.filename : null;
      const data = await commentService.createRefCommentComment(idUser, idPosts, content, media);
      return responseSuccessWithData(res, data);
    } catch (error) {
      responseError(res, error.message);
    }
  }

  async getRefComment(req, res) {
    try {
      const data = await commentService.getRefComment(req.params.id);
      return responseSuccessWithData(res, data);
    } catch (error) {
      responseError(res, error.message);
    }
  }

  async deleteComment(req, res) {
    try {
      const data = await commentService.deleteComment(req.params.id);
      return responseSuccess(res, RESPONSE.DELETECOMMENTSUCCESS);
    } catch (error) {
      responseError(res, error.message);
    }
  }
}

module.exports = new CommentController();
