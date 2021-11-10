const Shop = require('../models/shops.model');
const { ERROR } = require('../common/constants');
const fs = require('fs');

class ShopService {
  async getAll() {
    const data = await Shop.find();
    if (!data) throw Error(ERROR.CanNotGetShop);
    return data;
  }

  async getByUserId(id) {
    const data = await Shop.find({ idUser: id });
    if (!data) throw Error(ERROR.CanNotGetShop);
    return data;
  }

  async getById(id) {
    const data = await Shop.findById(id);
    if (!data) throw Error(ERROR.CanNotGetShop);
    return data;
  }

  async createShop(shop) {
    const newShop = await new Shop({
      ...shop,
      create_at: new Date(),
      update_at: new Date(),
    });
    const data = await newShop.save();
    if (!data) throw Error(ERROR.CanNotGetShop);
  }

  async updateShop(id, shop) {
    const data = await Shop.updateOne({ _id: id }, shop);
    if (!data || data.n == 0) throw Error(ERROR.CanNotUpdateShop);
  }

  async deleteShop(id) {
    const data = await Shop.findByIdAndDelete(id);
    if (!data) throw Error(ERROR.CanNotDeleteShop);
    const path = `./public/uploads/${data.media}`;
    try {
      fs.unlinkSync(path);
    } catch (error) {
      console.log(' ' + error);
    }
  }
}

module.exports = new ShopService();
// idUser, name, media, city, district, subDistrict, address
