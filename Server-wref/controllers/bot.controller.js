const { RESPONSE, ERROR } = require('../common/constants');
const { responseError, responseSuccess, responseSuccessWithData } = require('./base.controller');
const botService = require('../service/bot.service');

class BotController {
  async train(req, res) {
    try {
      await botService.train();
      return responseSuccess(res, 'train thành công');
    } catch (error) {
      console.log(error);
      return responseError(res, error.message);
    }
  }

  async handle(req, res) {
    return responseSuccessWithData(res, await botService.handle(req.params.message));
  }
}

module.exports = new BotController();
