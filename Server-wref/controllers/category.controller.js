const { RESPONSE, ERROR } = require('../common/constants');
const { responseError, responseSuccess, responseSuccessWithData } = require('./base.controller');
const categoryService = require('../service/category.service');

class CategoryController {
  async getAll(req, res) {
    try {
      const data = await categoryService.getAll();
      if (!data) return responseError(res, ERROR.NoData, data);
      return responseSuccessWithData(res, data);
    } catch (error) {
      return responseError(res, error.message);
    }
  }

  //   async getByIdShop(req, res) {}

  async getById(req, res) {}

  //   async getByIdCategory(req, res) {}

  async create(req, res) {
    try {
      await categoryService.create(req.body.name);
      return responseSuccess(res);
    } catch (error) {
      return responseError(res, error.message);
    }
  }

  async update(req, res) {}

  async delete(req, res) {}
}

module.exports = new CategoryController();
