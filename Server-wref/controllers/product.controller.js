const { RESPONSE, ERROR } = require('../common/constants');
const { responseError, responseSuccess, responseSuccessWithData } = require('./base.controller');
const productService = require('../service/product.service');

class ProductController {
  async getProduct(req, res) {
    try {
      const { page, limit, ...query } = req.query;
      const data = await productService.getProduct(query, page, limit);
      if (!data) return responseError(res, ERROR.NoData, data);
      return responseSuccessWithData(res, data);
    } catch (error) {
      console.log(error);
      return responseError(res, error.message);
    }
  }

  async getFull(req, res) {
    try {
      const data = await productService.getFullByCategory();
      if (!data) return responseError(res, ERROR.NoData, data);
      return responseSuccessWithData(res, data);
    } catch (error) {
      console.log(error);
      return responseError(res, error.message);
    }
  }

  async getById(req, res) {
    try {
      const data = await productService.getById(req.params.id);
      if (!data) return responseError(res, ERROR.NoData, data);
      return responseSuccessWithData(res, data);
    } catch (error) {
      console.log(error);
      return responseError(res, error.message);
    }
  }

  //   async getByIdCategory(req, res) {}

  async create(req, res) {
    try {
      const media = req.file ? req.file.filename : null;
      const data = { ...req.body, media };
      const qrcode = await productService.create(data);
      return responseSuccessWithData(res, qrcode);
    } catch (error) {
      return responseError(res, error.message);
    }
  }

  async update(req, res) {}

  async delete(req, res) {
    try {
      await productService.delete(req.params.id);
      return responseSuccess(res);
    } catch (error) {
      return responseError(res, error.message);
    }
  }
}

module.exports = new ProductController();
