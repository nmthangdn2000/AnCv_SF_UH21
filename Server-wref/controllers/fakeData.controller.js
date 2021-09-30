const Product = require('../models/product.model');
const dataProduct = require('../util/product');

const ingredient = 'Phân bón hỗn hợp chứa ít nhất 2 thành phần dinh dưỡng trong 3 thành N, P, K trở lên';
const effect =
  'Khi cây trồng được cung cấp đủ dinh dưỡng, quá trình sinh trưởng và phát triển diễn ra thuận lợi. Điều này cũng có nghĩa là các quá trình sinh hóa của cây trồng diễn ra thuận lợi, hiệu quả, hạn chế được cấp mầm mống gây hại, gia tăng sức chịu đựng và giảm thiểu tác động không tốt từ môi trường bên ngoài.';
const userManual =
  'Liều lượng ảnh hưởng rất nhiều đến hiệu quả sử dụng phân bón. Nếu sử dụng không đủ liều lượng, hiệu quả của phân bón không được đảm bảo, cây trồng không được cung cấp đủ dưỡng chất cần thiết. Tuy nhiên sử dụng quá liều lưỡng cũng sẽ gây những ảnh hưởng tiêu cực đến quá trình tăng trưởng của cây trồng và môi trường xung quanh. Bạn không nên áp dụng một công thức bón phân cố định mà nên quan sát tình trạng thực tế của cây trồng để điều chỉnh lượng phân bón cho phù hợp và hiệu quả.';
const note =
  'Cần trang bị đủ khẩu trang, găng tay, trang phục bảo hộ và các dụng cụ phù hợp để đảm bảo an toàn cho cơ thể. Nên tuân thủ các chỉ định và hướng dẫn đi kèm để đạt được hiệu quả tốt nhất. Trong trường hợp bạn chưa có nhiều kinh nghiệm với một số giống cây trồng, hãy hỏi thêm thông tin từ chuyên gia hoặc những người có kinh nghiệm để lựa chọn và sử dụng phân bón NPK 1 cách phù hợp.';

class FakeDataController {
  async fakeData(req, res) {
    const list = [];
    dataProduct.forEach((p) => {
      p.pesticide.forEach((pc) => {
        var number = Number(pc.price.replace(/[^0-9.-]+/g, ''));
        list.push({ name: pc.name, price: number * 1000, media: pc.image });
      });
    });
    const data = list.filter((v, i, a) => a.findIndex((t) => t.name === v.name) === i);

    data.forEach(async (d) => {
      const newProduct = await new Product({
        idShop: '61421c24347b5b0dccad76c6',
        idCategory: '613c17d2b3b52f0a60443215',
        name: d.name,
        price: d.price,
        saleOff: 15,
        type: false,
        media: d.media,
        ingredient,
        effect,
        userManual,
        note,
        create_at: new Date(),
        update_at: new Date(),
      });

      await newProduct.save();
    });
    res.send(list);
  }

  async exportFakeData(req, res) {
    const list = await Promise.all(
      dataProduct.map(async (d) => {
        const a = await Promise.all(
          d.pesticide.map(async (p) => {
            const product = await Product.findOne({ name: p.name }).select('_id');
            return { ...p, id: product._id };
          })
        );
        return { ...d, pesticide: a };
      })
    );

    res.send(list);
  }
}

module.exports = new FakeDataController();
