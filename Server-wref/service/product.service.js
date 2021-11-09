const Product = require('../models/product.model');
const { ERROR } = require('../common/constants');
const fs = require('fs');
const QRCode = require('qrcode');
const categoryService = require('../service/category.service');
class ProductService {
  async getProduct(query, page = 1, limit = 12) {
    const product = Product.find(query)
      .populate('idShop', 'name')
      .populate('idCategory', 'name')
      // .select('idCategory name price saleOff media type')
      .select('-ingredient -effect -userManual -note')
      .skip(page * limit - limit)
      .limit(Number(limit))
      .lean();
    const countProducts = Product.find(query).countDocuments();
    const [data, totalPage] = await Promise.all([product, countProducts]);
    if (!data) throw Error(ERROR.CanNotGetProduct);
    return {
      data,
      page,
      totalPage,
    };
  }

  async getFullByCategory(page = 1, limit = 12) {
    const category = await categoryService.getAll();
    const arr = [];
    category.forEach((c) => {
      arr.push(
        Product.find({ idCategory: c._id })
          .populate('idShop', 'name')
          .populate('idCategory', 'name')
          // .select('idCategory name price saleOff media type')
          .select('-ingredient -effect -userManual -note')
          .skip(page * limit - limit)
          .limit(Number(limit))
          .lean()
      );
    });
    const products = await Promise.all(arr);
    if (!products) throw Error(ERROR.CanNotGetProduct);
    const data = products.map((p) => {
      console.log(p.length);
      const data = {};
      if (p.length < 1) return p.pop();
      data.title = p[0].idCategory.name;
      data.products = p;
      return data;
    });
    return data.filter(function (el) {
      return el != null;
    });
  }

  async getById(id) {
    const data = await Product.findById(id).populate('idShop', 'name').populate('idCategory', 'name').lean();
    if (!data) throw Error(ERROR.CanNotGetProduct);
    return data;
  }

  async create(product) {
    console.log(product);
    const newProduct = await new Product({
      ...product,
      create_at: new Date(),
      update_at: new Date(),
    });
    const data = await newProduct.save();
    if (!data) throw Error(ERROR.CanNotCreateProduct);
    const id = data._id;
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
      await Product.deleteOne({ _id: id });
      throw Error(ERROR.CanNotCreateProduct);
    }
    const addQRCode = await Product.updateOne({ _id: id }, { qrcode: `${id}.png` });
    if (addQRCode.n < 1) throw Error(ERROR.CanNotCreateProduct);
    return { QRcode: `${id}.png` };
  }

  async update(req, res) {}

  async delete(id) {
    const data = await Product.findByIdAndDelete(id);
    if (!data) throw Error(ERROR.CanNotDeleteProduct);
    const path = `./public/uploads/${data.media}`;
    try {
      fs.unlinkSync(path);
    } catch (error) {
      console.log(' ' + error);
    }
  }
}

module.exports = new ProductService();
