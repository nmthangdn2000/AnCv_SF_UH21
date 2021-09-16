const Location = require('../models/location.model');
const Posts = require('../models/posts.model');
const Weather = require('../models/weather.model');
const getDataWeather = require('../service/getDataWeather');
const GetData = require('../service/getDataWeather');

const getAllDatalocation = async (req, res) => {
  await Location.find()
    .then((data) => {
      res.send(data);
    })
    .catch((err) => {
      console.log('', err);
      logErr(res, 'get all data Location failed');
    });
};
//
const getDataLocation = async (req, res) => {
  console.log(req.body);
  const img = [];
  const dataL = await Location.findOne({ name: req.body.name });
  if (dataL) {
    await Posts.find({ idLocation: dataL._id }).then((dataP) => {
      const data_img = dataP.map((element) => {
        element.media.forEach((value) => {
          img.push(value);
        });
        return element.media;
      });
    });
  }
  if (img.length > 0) GetData.getWeatherLocation(dataL.latiude, dataL.longitude, res, img);
  else GetData.getWeatherLocation(req.body.lati, req.body.longitude, res, img);
};
//
const postLocation = async (req, res) => {
  const newLocation = await new Location({
    longitude: req.body.long,
    latiude: req.body.lati,
    name: req.body.name,
  });
  await newLocation
    .save()
    .then(async (data) => {
      await getDataWeather.restApi(data.latiude, data.longitude, data._id);
      res.json({
        success: true,
        msg: 'new Location success',
      });
    })
    .catch((err) => {
      console.log('', err);
      logErr(res, 'new Location failed');
    });
};
function logErr(res, msg) {
  res.json({
    success: false,
    msg: msg,
  });
}
module.exports = {
  getAllDatalocation,
  getDataLocation,
  postLocation,
};
