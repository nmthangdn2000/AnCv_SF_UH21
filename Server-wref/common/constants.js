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
    // product 141-150
    CanNotGetProduct: 141,
    CanNotCreateProduct: 142,
    CanNotDeleteProduct: 143,
    CanNotUpdateProduct: 144,
    // category 151-160
    CanNotGetCategory: 151,
    CanNotCreateCategory: 152,
    CanNotDeleteCategory: 153,
    CanNotUpdateCategory: 154,
    // order 161-170
    CanNotGetOrder: 161,
    CanNotCreateOrder: 162,
    CanNotDeleteOrder: 163,
    CanNotUpdateOrder: 164,
    // PlantProtection 171-180
    CanNotGetPlantProtection: 171,
    CanNotCreatePlantProtection: 172,
    CanNotDeletePlantProtection: 173,
    CanNotUpdatePlantProtection: 174,
    // Stopword 181-190
    CanNotGetStopword: 181,
    CanNotCreateStopword: 182,
    CanNotDeleteStopword: 183,
    CanNotUpdateStopword: 184,
    // Intent 191-200
    CanNotGetIntent: 191,
    CanNotCreateIntent: 192,
    CanNotDeleteIntent: 193,
    CanNotUpdateIntent: 194,
    // Samples 200-210
    CanNotGetSamples: 201,
    CanNotCreateSamples: 202,
    CanNotDeleteSamples: 203,
    CanNotUpdateSamples: 204,
    // Reply Intent 211-220
    CanNotGetReplyIntent: 211,
    CanNotCreateReplyIntent: 212,
    CanNotDeleteReplyIntent: 213,
    CanNotUpdateReplyIntent: 214,
  },
  RESPONSE: {
    SUCCESS: 'success',
    SIGNUPSUCCESS: 'Sign Up Success',
    SIGNINSUCCESS: 'Sign In Success',
    UPDATECOMMENTSUCCESS: 'Update Comment Success',
    DELETECOMMENTSUCCESS: 'Delete Comment Success',
  },
  STATUS: {
    SHIP: {
      PENDDING: 0, // đang chờ shop xác nhận
      CONFIM: 1, // xác nhận
      TRANSPORT: 2, // vận chuyển
      DONE: 3, // giao hàng
    },
    ORDER: {
      PENDDING: 0, // đang chờ shop xác nhận
      CONFIM: 1, // xác nhận
      CANCEL: 2, // hủy
      DONE: 3, // đơn hàng thành công
    },
  },
  TYPE_INTENT: {
    TEXT: 'text',
    DATA: 'data',
    PRODUCT: 'product',
  },
};

module.exports = constants;
