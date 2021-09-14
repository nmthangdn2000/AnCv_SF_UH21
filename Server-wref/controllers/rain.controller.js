const { responseSuccessWithData } = require('./base.controller');
const data5Day = require('../util/5day');
const data10Day = require('../util/10day');

class RainController {
  getData(req, res) {
    const data = getRain(20, 100);
    return responseSuccessWithData(res, data);
  }

  getData5Day(req, res) {
    let day = req.params.day - 1;
    console.log(day);
    const data = [];
    for (let i = 0; i < 5; i++) {
      let d = day--;
      if (d < 0) d = 365 + d;
      data.push(data5Day[d]);
    }
    return responseSuccessWithData(res, data);
  }

  getData10Day(req, res) {
    let day = req.params.day - 1;
    console.log(day);
    const data = [];
    for (let i = 0; i < 10; i++) {
      let d = day--;
      if (d < 0) d = 365 + d;
      data.push(data5Day[d]);
    }
    return responseSuccessWithData(res, data);
  }
}

module.exports = new RainController();

function getRain(min, max) {
  return Math.round(Math.random() * (max - min) + min);
}
