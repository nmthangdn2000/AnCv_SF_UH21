const express = require('express');
const router = express.Router();
const controller = require('../../controllers/nlp.controller');

// intent
router.get('/nlp', controller.intent);
router.post('/nlp', controller.createIntent);
router.put('/nlp', controller.intent);
router.delete('/nlp/delete/:id', controller.intent);
// sample
router.get('/nlp/samples', controller.sample);
router.post('/nlp/samples', controller.intent);
router.put('/nlp/samples', controller.intent);
router.delete('/nlp/samples', controller.intent);
// reply
router.get('/nlp/stopword', controller.stopword);
router.post('/nlp/stopword', controller.intent);
router.put('/nlp/stopword', controller.intent);
router.delete('/nlp/stopword', controller.intent);
// chatbot
router.get('/nlp/chatbot', controller.chatbot);

module.exports = router;
