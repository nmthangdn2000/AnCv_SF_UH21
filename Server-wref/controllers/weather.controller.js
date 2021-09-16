const Weather = require('../models/weather.model');
const GetData = require('../service/getDataWeather');

const getWeather = async (req, res) => {
  await Weather.findOne({ idLocation: req.res })
    .then((data) => res.send(data))
    .catch((err) =>
      res.json({
        success: false,
        msg: 'getWeather failed',
      })
    );
};

const getDetailWeather = async (req, res) => {
  const data = GetData.getWeatherWithLocation(req.body.lat, req.body.long, res);
};
const getWeather24h = async (req, res) => {
  const data = GetData.getWeather24h(req.body.lat, req.body.long, res);
};
module.exports = {
  getWeather,
  getDetailWeather,
  getWeather24h,
};
