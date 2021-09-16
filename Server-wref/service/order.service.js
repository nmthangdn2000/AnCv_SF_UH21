const Order = require('../models/order.model');
const Product = require('../models/product.model');
const { ERROR, STATUS } = require('../common/constants');

class OrderService {
  async getByIdUser(id) {
    const data = await Order.find({ idUser: id }).populate('idProduct', 'name media price').populate('idUser', 'name');
    if (!data) throw Error(ERROR.NoData);
    return data;
  }

  async getById(id) {
    const data = await Order.findById(id).populate('idProduct', 'name media price').populate('idUser', 'name');
    if (!data) throw Error(ERROR.NoData);
    return data;
  }

  async create(data) {
    const unitPrice = await Product.findById(data.idProduct).select('price');
    const newOrder = await new Order({
      ...data,
      totalPrice: unitPrice.price * data.amount,
      status: STATUS.ORDER.PENDDING,
      statusShip: STATUS.SHIP.PENDDING,
      create_at: new Date(),
      update_at: new Date(),
    });
    const order = await newOrder.save();
    if (!order) throw Error(ERROR.CanNotCreateOrder);
  }

  async updateStatus(id, status) {
    const data = await Order.updateOne({ _id: id }, { status });
    if (!data || data.n < 1) throw Error(ERROR.CanNotUpdateOrder);
  }

  async updateShip(id, statusShip) {
    const data = await Order.updateOne({ _id: id }, { statusShip });
    if (!data || data.n < 1) throw Error(ERROR.CanNotUpdateOrder);
  }

  async delete(id) {
    await Order.deleteOne({ _id: id });
  }
}

module.exports = new OrderService();
