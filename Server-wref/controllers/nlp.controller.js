const nlpService = require('../service/nlp.service');
const intentService = require('../service/intent.service');
const sampleService = require('../service/sample.service');
const entityService = require('../service/entity.service');
const { RESPONSE, ERROR } = require('../common/constants');
const { responseError, responseSuccess, responseSuccessWithData } = require('./base.controller');

class NlpController {
  async intent(req, res) {
    try {
      const entity = await entityService.getEntity();
      const data = await intentService.getIntent();
      return res.render('nlp/main.ejs', { data, entity, page: 'intent' });
    } catch (error) {
      return responseError(res, error.message);
    }
  }

  async createIntent(req, res) {
    try {
      const { intent, sample, feedback, type, script_entity, script_repeat, script_feedback } = req.body;
      console.log(req.body);
      const idIntent = await intentService.createIntent(
        intent,
        feedback,
        type,
        script_entity,
        script_repeat,
        script_feedback
      );
      if (Array.isArray(sample)) await sampleService.createMultipleSample(idIntent, sample);
      else sampleService.createSample(idIntent, sample);
      return res.redirect('/nlp');
    } catch (error) {
      console.log(error);
      return responseError(res, error.message);
    }
  }

  async deleteIntent(req, res) {
    try {
      const idIntent = await intentService.deleteIntent(req.params.id);
      await sampleService.deleteMutipleSample(idIntent);
      return res.redirect('/nlp');
    } catch (error) {
      console.log(error);
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
      const { intent, sample } = req.body;
      if (Array.isArray(sample)) await sampleService.createMultipleSample(intent, sample);
      else await sampleService.createSample(intent, sample);
      return res.redirect('/nlp/samples');
    } catch (error) {
      return responseError(res, error.message);
    }
  }

  async updateSample(req, res) {
    try {
      await sampleService.updateSample(req.params.id);
      return res.redirect('/nlp/samples');
    } catch (error) {
      return responseError(res, error.message);
    }
  }

  async deleteSample(req, res) {
    try {
      await sampleService.deleteSample(req.params.id);
      return res.redirect('/nlp/samples');
    } catch (error) {
      return responseError(res, error.message);
    }
  }

  async entity(req, res) {
    try {
      const data = await entityService.getEntity();
      return res.render('nlp/entity.ejs', { data, page: 'entity' });
    } catch (error) {
      console.log(error);
      return responseError(res, error.message);
    }
  }

  async createEntity(req, res) {
    try {
      await entityService.createEntity(req.body);
      return res.redirect('/nlp/entity');
    } catch (error) {
      console.log(error);
      return responseError(res, error.message);
    }
  }

  async deleteEntity(req, res) {
    try {
      await entityService.deleteById(req.params.id);
      return res.redirect('/nlp/entity');
    } catch (error) {
      console.log(error);
      return responseError(res, error.message);
    }
  }

  async stopword(req, res) {
    res.render('nlp/main.ejs');
  }

  async chatbot(req, res) {
    res.render('nlp/chatbot.ejs', { page: 'chatbot' });
  }

  async chatbot(req, res) {
    res.render('nlp/voicebot.ejs', { page: 'voicebot' });
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
