const express = require('express');
const router = express.Router();
const controller = require('../../controllers/nlp.controller');

// intent
router.get('/nlp', controller.intent);
router.post('/nlp', controller.createIntent);
router.put('/nlp', controller.intent);
router.get('/nlp/delete/:id', controller.deleteIntent);
// sample
router.get('/nlp/samples', controller.sample);
router.post('/nlp/samples', controller.createSample);
router.put('/nlp/samples', controller.intent);
router.get('/nlp/samples/delete/:id', controller.deleteSample);
// stopword
router.get('/nlp/stopword', controller.stopword);
router.post('/nlp/stopword', controller.intent);
router.put('/nlp/stopword', controller.intent);
router.delete('/nlp/stopword', controller.intent);
// entity
router.get('/nlp/entity', controller.entity);
router.post('/nlp/entity', controller.createEntity);
router.put('/nlp/entity', controller.intent);
router.get('/nlp/entity/delete/:id', controller.deleteEntity);
// chatbot
router.get('/nlp/chatbot', controller.chatbot);
// voicebot
router.get('/nlp/voicebot', controller.voicebot);

module.exports = router;
