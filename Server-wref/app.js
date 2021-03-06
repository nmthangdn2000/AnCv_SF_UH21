require('dotenv').config();

const express = require('express');
const app = express();
const bodyParser = require('body-parser');
var path = require('path');
const cors = require('cors');

// require("./service/crawlData");
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.static(path.join(__dirname, 'public')));
app.use(cors());
// passport
const passport = require('passport');
const passportConfig = require('./middlewares/passport');
// api
const apiPosts = require('./router/api/posts.api');
const apiUser = require('./router/api/user.api');
const apiLogin = require('./router/api/login.api');
const apiComment = require('./router/api/comment.api');
const apiLocation = require('./router/api/location.api');
const apiStories = require('./router/api/stories.api');
const apiWeather = require('./router/api/weather.api');
const apiInforAgri = require('./router/api/inforAgri.api');
const apiHarvesthelper = require('./router/api/harvesthelper.api');
const apiShop = require('./router/api/shop.api');
const apiProduct = require('./router/api/product.api');
const apiCategory = require('./router/api/category.api');
const apiOrder = require('./router/api/order.api');
const apiRain = require('./router/api/rain.api');
// page
const pages = require("./router/page/page");

// api path
app.use('/api', apiLogin);
app.use('/api', apiInforAgri);
app.use('/api', passport.authenticate('jwt', { session: false }), apiPosts);
app.use('/api', passport.authenticate('jwt', { session: false }), apiUser);
app.use('/api', passport.authenticate('jwt', { session: false }), apiComment);
app.use('/api', passport.authenticate('jwt', { session: false }), apiLocation);
app.use('/api', passport.authenticate('jwt', { session: false }), apiStories);
app.use('/api', passport.authenticate('jwt', { session: false }), apiWeather);
app.use('/api', passport.authenticate('jwt', { session: false }), apiHarvesthelper);
app.use('/api', passport.authenticate('jwt', { session: false }), apiShop);
app.use('/api', passport.authenticate('jwt', { session: false }), apiProduct);
app.use('/api', passport.authenticate('jwt', { session: false }), apiCategory);
app.use('/api', passport.authenticate('jwt', { session: false }), apiOrder);
app.use('/api', passport.authenticate('jwt', { session: false }), apiRain);

// page path
app.use("/", pages);

app.use('/document', (req, res) => {
  res.render('document.ejs');
});

module.exports = app;
