const { ERROR, RESPONSE } = require('../common/constants');

class BaseController {
  responseSuccess(res, message = RESPONSE.SUCCESS) {
    return res.status(200).json({
      success: true,
      message,
    });
  }

  responseSuccessWithData(res, data, message = RESPONSE.SUCCESS) {
    return res.status(200).json({
      success: true,
      message,
      data,
    });
  }

  responseError(res, errorCode = ERROR.InternalServerError, data = {}) {
    let code = Number(errorCode);
    let message = '';
    if (Number.isInteger(code)) message = getErrorMessage(code);
    else {
      message = errorCode;
      code = ERROR.Default;
    }
    return res.status(200).json({
      success: false,
      message,
      errorCode: code,
      data,
    });
  }
}

module.exports = new BaseController();

function getErrorMessage(code) {
  const message = getKeyByValue(ERROR, code);
  console.log(message, typeof code);
  return message ? message.replace(/([A-Z])/g, ' $1').trim() : `${code}`;
}

function getKeyByValue(object, value) {
  return Object.keys(object).find((key) => object[key] === value);
}
