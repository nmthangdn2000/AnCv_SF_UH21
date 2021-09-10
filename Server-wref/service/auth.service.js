const User = require('../models/user.model');
const jwt = require('jsonwebtoken');
const { JWT_SECRET } = require('../config/appconfig');
const { ERROR } = require('../common/constants');

class AuthService {
  async postSignUp(userName, email, passWord) {
    const newUser = new User({
      userName,
      email,
      passWord,
      create_at: new Date(),
      update_at: new Date(),
    });
    const data = await newUser.save();
    if (!data) throw Error(ERROR.CanNotSignUp);
  }

  postSignIn(user) {
    const inforUser = {
      id: user._id,
      userName: user.userName,
    };
    const token = endCodeToken(inforUser);
    const data = {
      _id: user._id,
      userName: user.userName,
      email: user.email,
      avata: user.avata,
      token: token,
    };
    return data;
  }

  async checkLogin(id, res) {
    const data = await User.findById(id).select('-passWord');
    if (!data) throw Error(ERROR.UserDoesNotExist);
    return data;
  }
}

module.exports = new AuthService();

function endCodeToken(user) {
  return jwt.sign(user, JWT_SECRET, { expiresIn: '30d' });
}
