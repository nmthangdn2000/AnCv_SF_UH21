const constants = {
  ERROR: {
    Default: 100,
    InternalServerError: 101,
    NoData: 102,
    //account 111 - 120
    CanNotSignUp: 111,
    UserDoesNotExist: 112,
    // comment 121 - 130
    CanNotGetComment: 121,
    CanNotCreateComment: 122,
    CanNotUpdateComment: 123,
    CanNotCreateRefComment: 124,
    // shop 131-140
    CanNotGetShop: 131,
    CanNotCreateShop: 132,
    CanNotDeleteShop: 133,
    CanNotUpdateShop: 134,
  },
  RESPONSE: {
    SUCCESS: 'success',
    SIGNUPSUCCESS: 'Sign Up Success',
    SIGNINSUCCESS: 'Sign In Success',
    UPDATECOMMENTSUCCESS: 'Update Comment Success',
    DELETECOMMENTSUCCESS: 'Delete Comment Success',
  },
};

module.exports = constants;
