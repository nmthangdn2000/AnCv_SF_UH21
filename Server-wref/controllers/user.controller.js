const { responseError, responseSuccess, responseSuccessWithData } = require('./base.controller');
const authService = require('../service/auth.service');
const { ERROR, RESPONSE } = require('../common/constants');

class AuthController {
  async postSignUp(req, res) {
    try {
      const { userName, email, passWord, address, userType } = req.body;
      console.log(userName, email, passWord, address, userType);
      await authService.postSignUp(userName, email, passWord, address, userType);
      return responseSuccess(res, RESPONSE.SIGNUPSUCCESS);
    } catch (error) {
      return responseError(res, error.message);
    }
  }

  async postSignIn(req, res) {
    const data = authService.postSignIn(req.user);
    return responseSuccessWithData(res, data);
  }

  async checkLogin(req, res) {
    try {
      const data = await authService.checkLogin(req.user._id);
      return responseSuccessWithData(res, data);
    } catch (error) {
      return responseError(res, error.message);
    }
  }
}

function getInfUser(req, res) {}
function editUser(req, res) {}
function deleteUser(req, res) {}

module.exports = new AuthController();
