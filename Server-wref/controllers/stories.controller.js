const Stories = require('../models/stories.model');
const fs = require('fs');

const getStories = async (req, res) => {
  await Stories.find()
    .populate('idUser', 'userName avata')
    .then((data) => {
      res.send(data);
    })
    .catch((err) => {
      console.log('', err);
      res.json({
        success: false,
        msg: 'get Stories failed',
      });
    });
};
const postStories = async (req, res) => {
  const arrMedia = [];
  await req.files.forEach((element) => {
    arrMedia.push(element.filename);
  });
  const findStories = await Stories.findOne({ idUser: req.body.iduser });
  if (findStories) {
    await Stories.findByIdAndUpdate(findStories._id, {
      $addToSet: {
        media: arrMedia,
      },
    })
      .then((data) => {
        res.json({
          success: true,
          msg: 'Stories new success',
        });
      })
      .catch((err) =>
        res.json({
          success: false,
          msg: 'Stories new failed',
        })
      );
    return;
  } else {
    const newStories = await new Stories({
      idUser: req.body.iduser,
      media: arrMedia,
      like: [],
      create_at: new Date(),
    });
    await newStories
      .save()
      .then((data) => {
        res.json({
          success: true,
          msg: 'Stories new success',
        });
      })
      .catch((err) =>
        res.json({
          success: false,
          msg: 'Stories new failed',
        })
      );
    return;
  }
};

module.exports = {
  getStories,
  postStories,
};
