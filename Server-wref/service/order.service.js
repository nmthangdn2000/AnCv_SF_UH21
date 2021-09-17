const Order = require('../models/order.model');
const Product = require('../models/product.model');
const Shop = require('../models/shops.model');
const { ERROR, STATUS } = require('../common/constants');

class OrderService {
  async getByIdUser(idCustomer, idBoss) {
    if (idCustomer) return await getDataByIdCustomer(idCustomer);
    if (idBoss) return await getDataByIdBoss(idBoss);
    throw Error(ERROR.NoData);
  }

  async getById(id) {
    const data = await Order.findById(id).populate('idProduct', 'name media price').populate('idUser', 'name');
    if (!data) throw Error(ERROR.NoData);
    return data;
  }

  async create(data) {
    const product = await Product.findById(data.idProduct).select('price idShop');
    const newOrder = await new Order({
      ...data,
      idShop: product.idShop,
      totalPrice: product.price * data.amount,
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

async function getDataByIdCustomer(idUser) {
  const data = await Order.find({ idUser }).populate('idProduct', 'name media price').populate('idUser', 'userName');
  if (!data) throw Error(ERROR.NoData);
  return data;
}

async function getDataByIdBoss(idUser) {
  const shops = await Shop.find({ idUser }).select('_id');
  const orders = [];
  shops.forEach((idShop) => {
    orders.push(Order.find({ idShop }).populate('idProduct', 'name media price').populate('idUser', 'userName'));
  });
  const arr = await Promise.all(orders);
  const data = [].concat.apply([], arr);
  console.log(data);
  if (!data) throw Error(ERROR.NoData);
  return data;
}
