const express = require('express');
const router = express.Router();
const controller = require('../../controllers/nlp.controller');

// intent
router.get('/nlp', controller.intent);
router.post('/nlp', controller.createIntent);
router.put('/nlp', controller.intent);
router.delete('/nlp', controller.intent);
// sample
router.get('/nlp/sample', controller.sample);
router.post('/nlp/sample', controller.intent);
router.put('/nlp/sample', controller.intent);
router.delete('/nlp/sample', controller.intent);
// reply
router.get('/nlp/stopword', controller.stopword);
router.post('/nlp/stopword', controller.intent);
router.put('/nlp/stopword', controller.intent);
router.delete('/nlp/stopword', controller.intent);
// stopword
router.get('/nlp/reply-intent', controller.replyIntent);
router.post('/nlp/reply-intent', controller.intent);
router.put('/nlp/reply-intent', controller.intent);
router.delete('/nlp/reply-intent', controller.intent);
// chatbot
router.get('/nlp/chatbot', controller.chatbot);

module.exports = router;
