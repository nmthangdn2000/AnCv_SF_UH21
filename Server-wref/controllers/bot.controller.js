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
    try {
      const { message, entity, oldIntent, repeat } = req.body;
      const data = await botService.handle(message, entity, oldIntent, repeat ? repeat : 0);
      return responseSuccessWithData(res, data);
    } catch (error) {
      console.log(error);
      return responseSuccessWithData(res, {
        type: 'text',
        message: 'Xin lỗi, tôi không thể hiểu ý của bạn!',
      });
    }
  }
}

module.exports = new BotController();
