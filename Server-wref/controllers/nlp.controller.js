const nlpService = require('../service/nlp.service');
const intentService = require('../service/intent.service');
const sampleService = require('../service/sample.service');
const { RESPONSE, ERROR } = require('../common/constants');
const { responseError, responseSuccess, responseSuccessWithData } = require('./base.controller');

class NlpController {
  async intent(req, res) {
    try {
      const data = await intentService.getIntent();
      console.log(data);
      return res.render('nlp/main.ejs', { data });
    } catch (error) {
      return responseError(res, error.message);
    }
  }

  async createIntent(req, res) {
    try {
      console.log(req.body);
      const { intent, sample } = req.body;
      const idIntent = await intentService.createIntent(intent);
      await sampleService.createMultipleSample(idIntent, sample);
      return res.redirect('/nlp');
    } catch (error) {
      return responseError(res, error.message);
    }
  }

  async sample(req, res) {
    try {
      const data = await sampleService.getSample();
      return res.render('nlp/sample.ejs', { data });
    } catch (error) {
      return responseError(res, error.message);
    }
  }
  async stopword(req, res) {
    res.render('nlp/main.ejs');
  }
  async replyIntent(req, res) {
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
