const crops = require("../models/crops");

const getAll = async (req, res) => {
  await crops
    .find()
    .limit(15)
    .then((data) => res.send(data))
    .catch((err) => console.log(err));
};

const getByID = async (req, res) => {
  console.log(req.params.id);
  await crops
    .findById(req.params.id)
    .then((data) => res.send(data))
    .catch((err) => console.log(err));
};

module.exports = {
  getAll,
  getByID,
};
