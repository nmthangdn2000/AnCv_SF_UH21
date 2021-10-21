const express = require('express');
const router = express.Router();
const controller = require('../../controllers/bot.controller');

router.get('/bot/train', controller.train);

router.get('/bot/message/:message', controller.handle);

module.exports = router;
