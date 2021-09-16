const express = require('express');
const router = express.Router();
const controller = require('../../controllers/rain.controller');

router.get('/rain', controller.getData);

router.get('/rain/5-day/:day', controller.getData5Day);

router.get('/flow/10-day/:day', controller.getData10Day);

module.exports = router;
