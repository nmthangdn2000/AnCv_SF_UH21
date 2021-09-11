const Product = require('../models/product.model');
const { ERROR } = require('../common/constants');
const fs = require('fs');

class ProductService {
  async getProduct(query, page, limit) {
    const product = Product.find(query)
      //   .populate('idShop', 'name')
      //   .populate('idCategory', 'name')
      .select('name price saleOff media type')
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

  async getById(id) {
    const data = await Product.findById(id).populate('idShop', 'name').populate('idCategory', 'name').lean();
    if (!data) throw Error(ERROR.CanNotGetProduct);
    return data;
  }

  async create(product) {
    const newProduct = await new Product({
      ...product,
      create_at: new Date(),
      update_at: new Date(),
    });
    const data = await newProduct.save();
    if (!data) throw Error(ERROR.CanNotCreateProduct);
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
