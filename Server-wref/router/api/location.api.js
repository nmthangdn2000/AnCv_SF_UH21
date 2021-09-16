const express = require('express');
const router = express.Router();
const controller = require('../../controllers/location.controller')

router.get('/getalllocation', controller.getAllDatalocation)

router.post('/location', controller.getDataLocation)

router.post('/location', controller.postLocation)

module.exports = router