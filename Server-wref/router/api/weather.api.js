const express = require('express');
const router = express.Router();
const controller = require('../../controllers/weather.controller');

router.get('/weather', controller.getWeather);

router.post('/detailweather', controller.getDetailWeather);

router.post('/getweather24h', controller.getWeather24h);

module.exports = router;
