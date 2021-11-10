const shopService = require('../service/shop.service');
const { RESPONSE, ERROR } = require('../common/constants');
const { responseError, responseSuccess, responseSuccessWithData } = require('./base.controller');

class ShopController {
  async getAll(req, res) {
    try {
      const data = await shopService.getAll();
      if (!data) return responseError(res, ERROR.NoData, data);
      return responseSuccessWithData(res, data);
    } catch (error) {
      return responseError(res, error.message);
    }
  }

  async getByUserId(req, res) {
    try {
      const data = await shopService.getByUserId(req.params.id);
      if (!data) return responseError(res, ERROR.NoData, data);
      return responseSuccessWithData(res, data);
    } catch (error) {
      return responseError(res, error.message);
    }
  }

  async getById(req, res) {
    try {
      const data = await shopService.getById(req.params.id);
      if (!data) return responseError(res, ERROR.NoData, data);
      return responseSuccessWithData(res, data);
    } catch (error) {
      return responseError(res, error.message);
    }
  }

  async createShop(req, res) {
    try {
      const media = req.file ? req.file.filename : null;
      const data = { ...req.body, media };
      await shopService.createShop(data);
      return responseSuccess(res);
    } catch (error) {
      return responseError(res, error.message);
    }
  }

  async updateShop(req, res) {
    try {
      const media = req.file ? req.file.filename : null;
      let data = {};
      if (media) data = { ...req.body, media };
      else data = req.body;
      await shopService.updateShop(req.params.id, data);
      return responseSuccess(res);
    } catch (error) {
      return responseError(res, error.message);
    }
  }

  async deleteShop(req, res) {
    try {
      await shopService.deleteShop(req.params.id);
      return responseSuccess(res);
    } catch (error) {
      return responseError(res, error.message);
    }
  }
}

module.exports = new ShopController();
