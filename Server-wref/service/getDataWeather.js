require('dotenv').config();
const rp = require('request-promise');
const Weather = require('../models/weather.model');

async function potsWeather(arr, id) {
  await Weather.findOne({ idLocation: id }, async (err, data) => {
    if (!data) {
      const newWeather = await new Weather({
        idLocation: id,
        infor: arr,
      });
      await newWeather
        .save()
        .then((data) => console.log('oki'))
        .catch((err) => console.log('', err));
      return;
    } else {
      await Weather.updateOne(
        { idLocation: id },
        {
          $set: {
            infor: arr,
          },
        }
      )
        .then((data) => {
          console.log('cập nhật');
        })
        .catch((err) => console.log('', err));
      return;
    }
  });
}

module.exports.restApi = function restApi(latiude, longitude, id) {
  const URL =
    'https://api.openweathermap.org/data/2.5/onecall?lat=' +
    latiude +
    '&lon=' +
    longitude +
    '&exclude=hourly&appid=' +
    process.env.KEY_API;
  rp(URL)
    .then(async (data) => {
      const api = JSON.parse(data);
      const array = [];
      array.push(api.current);
      api.daily.forEach((element) => {
        array.push(element);
      });
      console.log(array.length);
      await potsWeather(array, id);
    })
    .catch((err) => console.log('', err));
};

module.exports.getWeatherWithLocation = function getWeatherWithLocation(latiude, longitude, res) {
  const URL =
    'https://api.openweathermap.org/data/2.5/onecall?lat=' +
    latiude +
    '&lon=' +
    longitude +
    '&exclude=hourly&appid=' +
    process.env.KEY_API;
  rp(URL)
    .then(async (data) => {
      const api = JSON.parse(data);
      res.send(api);
    })
    .catch((err) => console.log('lỗi'));
};

module.exports.getWeather24h = function getWeather24h(latiude, longitude, res) {
  const URL =
    'https://api.openweathermap.org/data/2.5/onecall?lat=' +
    latiude +
    '&lon=' +
    longitude +
    '&exclude=minutely,daily&appid=' +
    process.env.KEY_API;
  rp(URL)
    .then(async (data) => {
      const api = JSON.parse(data);
      res.send(api);
    })
    .catch((err) => console.log('lỗi'));
};

module.exports.getWeatherLocation = function getWeatherLocation(latiude, longitude, res, media) {
  const URL =
    'https://api.openweathermap.org/data/2.5/onecall?lat=' +
    latiude +
    '&lon=' +
    longitude +
    '&exclude=hourly&appid=' +
    process.env.KEY_API;
  rp(URL)
    .then(async (data) => {
      const weather = JSON.parse(data);
      res.json({
        weather,
        media,
      });
    })
    .catch((err) => console.log('lỗi'));
};

module.exports.getWeatherCity = function getWeatherCity(city, res) {
  const URL =
    'https://api.openweathermap.org/data/2.5/weather?q=' + city + '&lang=vi&units=metric&appid=' + process.env.KEY_API;
  return rp(URL)
    .then(async (data) => {
      const weather = JSON.parse(data);
      return {
        'Mô tả': weather.weather[0].description,
        'Nhiệt độ trung bình': `${weather.main.temp} °C`,
        'Nhiệt độ cao nhất': `${weather.main.temp_max} °C`,
        'Nhiệt độ thấp nhất': `${weather.main.temp_min} °C`,
        'Tốc độ gió': `${weather.wind.speed} m/s`,
      };
    })
    .catch((err) => console.log('lỗi', err));
};

function getKeyCity(city) {
  switch (city) {
    case 'Đà Nẵng':
      return 'danang';
    default:
      break;
  }
}
