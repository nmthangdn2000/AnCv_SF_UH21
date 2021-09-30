const express = require('express');
const router = express.Router();
const controller = require('../../controllers/fakeData.controller');

router.get('/', controller.fakeData);

router.get('/export', controller.exportFakeData);

module.exports = router;
