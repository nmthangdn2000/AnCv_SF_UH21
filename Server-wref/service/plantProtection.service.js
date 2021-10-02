const PlantProtection = require('../models/plantProtection.model');
const { ERROR } = require('../common/constants');
const QRCode = require('qrcode');

class PlantProtectionService {
  async getByQuery(page, limit, query) {
    const plantProtections = await PlantProtection.find(query)
      .populate('idShop')
      .select('name qrcode')
      .limit(limit)
      .skip(page * limit - limit)
      .limit(Number(limit))
      .lean();
    const countPlantProtections = PlantProtection.find(query).countDocuments();
    const [data, totalPage] = await Promise.all([plantProtections, countPlantProtections]);
    if (!data) throw Error(ERROR.CanNotGetProduct);
    return {
      data,
      page,
      totalPage,
    };
  }

  async getById(id) {
    const plantProtections = await PlantProtection.findById(id);
    if (!plantProtections) throw Error(ERROR.CanNotGetPlantProtection);
    return plantProtections;
  }

  async create(data) {
    const newPlantProtection = await new PlantProtection({
      ...data,
      create_at: new Date(),
      update_at: new Date(),
    });
    const plantProtection = await newPlantProtection.save();
    if (!plantProtection) throw Error(ERROR.CanNotCreateOrder);
    const id = plantProtection._id;
    const qr = await QRCode.toFile(`./public/qrCode/${id}.png`, id.toString())
      .then((result) => {
        return true;
      })
      .catch((err) => {
        console.log(err);
        return false;
      });
    // tạo qr ko đc thì xóa sản phẩm đó khỏi database
    if (!qr) {
      await PlantProtection.deleteOne({ _id: id });
      throw Error(ERROR.CanNotCreateOrder);
    }
    const addQRCode = await PlantProtection.updateOne({ _id: id }, { qrcode: `${id}.png` });
    if (addQRCode.n < 1) throw Error(ERROR.CanNotCreateOrder);
  }

  async delete() {}
}

module.exports = new PlantProtectionService();
