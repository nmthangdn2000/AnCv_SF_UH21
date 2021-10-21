const express = require('express');
const router = express.Router();
const controller = require('../../controllers/nlp.controller');

router.get('/nlp', controller.nlp);

router.get('/nlp/chatbot', controller.chatbot);

module.exports = router;
