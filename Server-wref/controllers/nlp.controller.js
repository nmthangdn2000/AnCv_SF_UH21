const nlpService = require('../service/nlp.service');
const { RESPONSE, ERROR } = require('../common/constants');
const { responseError, responseSuccess, responseSuccessWithData } = require('./base.controller');

class NlpController {
  async nlp(req, res) {
    res.render('nlp/main.ejs');
  }

  async chatbot(req, res) {
    res.render('nlp/chatbot.ejs');
  }
}

module.exports = new NlpController();
// try {
//     await nlpService.train();
//     return responseSuccess(res, 'train thành công');
//   } catch (error) {
//     console.log(error);
//     return responseError(res, error.message);
//   }
