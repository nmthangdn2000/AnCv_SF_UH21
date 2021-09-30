import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:retail_agent_werf/components/item/itemOrderPurchaser.dart';
import 'package:retail_agent_werf/models/order.purchaser/order.purchaser.model.dart';
import 'package:retail_agent_werf/utils/constants.dart';

class OrderPagePurchaser extends StatefulWidget {
  const OrderPagePurchaser({Key? key}) : super(key: key);

  @override
  _OrderPagePurchaserState createState() => _OrderPagePurchaserState();
}

class _OrderPagePurchaserState extends State<OrderPagePurchaser> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Color(0xFFFAFAFA),
        elevation: 0,
        centerTitle: true,
        title: Text(
          "Thống kê",
          style: TextStyle(
              fontSize: 20, fontWeight: FontWeight.bold, color: Colors.black),
        ),
      ),
      body: SafeArea(
        child: SingleChildScrollView(
          child: Column(
            children: [
              _list1(),
              SizedBox(
                height: 15,
              ),
              _list2(),
            ],
          ),
        ),
      ),
    );
  }

  _list1() {
    return Column(
      children: [
        Padding(
          padding: const EdgeInsets.all(20.0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text(
                "Hôm nay",
                style: TextStyle(
                  color: Colors.black,
                  fontSize: 18,
                ),
              ),
              Text(
                "----------------------------------------",
                style: TextStyle(
                  color: Colors.grey,
                  fontSize: 18,
                ),
              ),
            ],
          ),
        ),
        SizedBox(
          height: 20,
        ),
        Text(
          "Mua lẻ",
          style: TextStyle(
            color: Color(0xFF818181),
            fontSize: 14,
          ),
        ),
        SizedBox(
          height: 15,
        ),
        ItemOrderPurchaser(
          orderModel: new OrderModelPurchaser(
              id: "1",
              name: "Cà chua",
              amount: 3,
              totalPrice: "96.000 VNĐ",
              status: TRANSPORT,
              date: "29/09/2021",
              img: "avata1.jpg"),
        ),
        SizedBox(
          height: 10,
        ),
        ItemOrderPurchaser(
          orderModel: new OrderModelPurchaser(
              id: "1",
              name: "Lúa",
              amount: 10,
              totalPrice: "240.000 VNĐ",
              status: DONE,
              date: "29/09/2021",
              img: "avata1.jpg"),
        ),
        SizedBox(
          height: 10,
        ),
        ItemOrderPurchaser(
          orderModel: new OrderModelPurchaser(
              id: "1",
              name: "Lúa",
              amount: 15,
              totalPrice: "320.000 VNĐ",
              status: PENDDING,
              date: "29/09/2021",
              img: "avata1.jpg"),
        ),
        SizedBox(
          height: 10,
        ),
        Text(
          "Đặt trước",
          style: TextStyle(
            color: Color(0xFF818181),
            fontSize: 14,
          ),
        ),
        SizedBox(
          height: 10,
        ),
        _item(
          false,
          "Đang thu thập",
          "Lúa",
          "40KG",
          "26.000 VND/KG",
          "29/09/2021",
          "80%",
          "avata1.jpg",
          "32KG",
        ),
        _item(
          false,
          "Đang thu thập",
          "Cà chua",
          "32KG",
          "40.000 VND/KG",
          "29/09/2021",
          "12%",
          "avata1.jpg",
          "32KG",
        ),
      ],
    );
  }

  _list2() {
    return Column(
      children: [
        Padding(
          padding: const EdgeInsets.all(20.0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text(
                "30/09/2021",
                style: TextStyle(
                  color: Colors.black,
                  fontSize: 18,
                ),
              ),
              Text(
                "----------------------------------------",
                style: TextStyle(
                  color: Colors.grey,
                  fontSize: 18,
                ),
              ),
            ],
          ),
        ),
        SizedBox(
          height: 20,
        ),
        Text(
          "Mua lẻ",
          style: TextStyle(
            color: Color(0xFF818181),
            fontSize: 14,
          ),
        ),
        SizedBox(
          height: 15,
        ),
        ItemOrderPurchaser(
          orderModel: new OrderModelPurchaser(
              id: "1",
              name: "Cà chua",
              amount: 6,
              totalPrice: "300.000 VNĐ",
              status: CANCEL,
              date: "30/09/2021",
              img: "avata1.jpg"),
        ),
        SizedBox(
          height: 10,
        ),
        ItemOrderPurchaser(
          orderModel: new OrderModelPurchaser(
              id: "1",
              name: "Cam",
              amount: 10,
              totalPrice: "240.000 VNĐ",
              status: DONE,
              date: "30/09/2021",
              img: "avata1.jpg"),
        ),
        SizedBox(
          height: 10,
        ),
        Text(
          "Đặt trước",
          style: TextStyle(
            color: Color(0xFF818181),
            fontSize: 14,
          ),
        ),
        SizedBox(
          height: 10,
        ),
        _item(
          true,
          "Hoàn thành",
          "Lúa",
          "40KG",
          "26.000 VND/KG",
          "30/09/2021",
          "80%",
          "avata1.jpg",
          "40KG",
        ),
        _item(
          false,
          "Đang thu thập",
          "Dứa",
          "32KG",
          "40.000 VND/KG",
          "30/09/2021",
          "12%",
          "avata1.jpg",
          "32KG",
        ),
      ],
    );
  }

  _item(bool status, String statusName, String name, String amount,
      String unitPrice, String date, String rate, String img, String have) {
    return Container(
      height: 111,
      padding: const EdgeInsets.symmetric(vertical: 8, horizontal: 20),
      margin: EdgeInsets.only(
        top: 6,
        left: 20,
        right: 20,
        bottom: 6,
      ),
      decoration: BoxDecoration(
        border: Border.all(color: status ? Colors.green : signInColor),
        borderRadius: BorderRadius.circular(8),
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Expanded(
            flex: 2,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  name,
                  style: TextStyle(
                    color: Colors.black,
                    fontWeight: FontWeight.bold,
                    fontSize: 16,
                  ),
                ),
                Text(
                  "Số lượng: $amount",
                  style: TextStyle(
                    fontSize: 12,
                    color: Color(0xFF818181),
                  ),
                ),
                Text(
                  "Đơn giá: $unitPrice ₫",
                  style: TextStyle(
                    fontSize: 12,
                    color: Color(0xFF818181),
                  ),
                ),
                Text(
                  "$date",
                  style: TextStyle(
                    color: status ? Colors.green : signInColor,
                    fontSize: 10,
                  ),
                ),
              ],
            ),
          ),
          Expanded(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              children: [
                Text(
                  statusName,
                  style: TextStyle(
                    fontSize: 16,
                    color: status ? Colors.green : signInColor,
                  ),
                ),
                SizedBox(
                  height: 10,
                ),
                _a(status, rate),
                SizedBox(
                  height: 10,
                ),
                Text(
                  have,
                  style: TextStyle(
                    fontSize: 16,
                    color: status ? Colors.green : signInColor,
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  _a(bool status, String? rate) {
    if (status)
      return Icon(
        FontAwesomeIcons.check,
        size: 28,
        color: Colors.green,
      );
    else
      return Text(
        "$rate",
        style: TextStyle(
          fontSize: 28,
          color: status ? Colors.green : signInColor,
        ),
      );
  }
}
