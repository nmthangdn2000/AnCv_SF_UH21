const app = require("./app");
const http = require("http").Server(app);
const port = process.env.PORT;
const db = require("./db/connectDB");
const rp = require("./service/apiOpenWeather");
var io = require("./service/socket");
io.attach(http);

http.listen(port, () => console.log(`Active on ${port} port`));
