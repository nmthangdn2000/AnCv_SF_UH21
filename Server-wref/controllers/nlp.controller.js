const nlpService = require('../service/nlp.service');
const intentService = require('../service/intent.service');
const sampleService = require('../service/sample.service');
const { RESPONSE, ERROR } = require('../common/constants');
const { responseError, responseSuccess, responseSuccessWithData } = require('./base.controller');

class NlpController {
  async intent(req, res) {
    try {
      const data = await intentService.getIntent();
      return res.render('nlp/main.ejs', { data, page: 'intent' });
    } catch (error) {
      return responseError(res, error.message);
    }
  }

  async createIntent(req, res) {
    try {
      const { intent, sample, feedback } = req.body;
      const idIntent = await intentService.createIntent(intent, feedback);
      await sampleService.createMultipleSample(idIntent, sample);
      return res.redirect('/nlp');
    } catch (error) {
      return responseError(res, error.message);
    }
  }

  async deleteIntent(req, res) {
    try {
      const idIntent = await intentService.deleteIntent(req.params.id);

      return res.redirect('/nlp');
    } catch (error) {
      return responseError(res, error.message);
    }
  }

  async sample(req, res) {
    try {
      const data = await sampleService.getSample();
      const intents = await intentService.getIntent();
      return res.render('nlp/samples.ejs', { data, intents, page: 'samples' });
    } catch (error) {
      return responseError(res, error.message);
    }
  }

  async createSample(req, res) {
    try {
      const data = await sampleService.createSample();
      return res.redirect('/nlp/samples');
    } catch (error) {
      return responseError(res, error.message);
    }
  }

  async stopword(req, res) {
    res.render('nlp/main.ejs');
  }

  async chatbot(req, res) {
    res.render('nlp/chatbot.ejs', { page: 'chatbot' });
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
