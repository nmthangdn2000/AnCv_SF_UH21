const { ERROR } = require('../common/constants');
const { responseError, responseSuccess, responseSuccessWithData } = require('./base.controller');
const orderService = require('../service/order.service');

class OrderController {
  async getByIdUser(req, res) {
    try {
      const { idCustomer, idBoss } = req.query;
      const data = await orderService.getByIdUser(idCustomer, idBoss);
      if (!data) return responseError(res, ERROR.NoData, data);
      return responseSuccessWithData(res, data);
    } catch (error) {
      return responseError(res, error.message);
    }
  }

  async getById(req, res) {
    try {
      const data = await orderService.getById(req.params.id);
      if (!data) return responseError(res, ERROR.NoData, data);
      return responseSuccessWithData(res, data);
    } catch (error) {
      return responseError(res, error.message);
    }
  }
  async create(req, res) {
    try {
      await orderService.create(req.body);
      return responseSuccess(res);
    } catch (error) {
      console.log(error);
      return responseError(res, ERROR.CanNotCreateOrder);
    }
  }

  async updateStatus(req, res) {
    try {
      await orderService.updateStatus(req.params.id, req.body.status);
      return responseSuccess(res);
    } catch (error) {
      return responseError(res, ERROR.CanNotUpdateOrder);
    }
  }

  async updateShip(req, res) {
    try {
      await orderService.updateShip(req.params.id, req.body.statusShip);
      return responseSuccess(res);
    } catch (error) {
      return responseError(res, ERROR.CanNotUpdateOrder);
    }
  }

  async delete(req, res) {
    try {
      await orderService.delete(req.params.id);
      return responseSuccess(res);
    } catch (error) {
      return responseError(res, ERROR.CanNotDeleteOrder);
    }
  }
}

module.exports = new OrderController();
