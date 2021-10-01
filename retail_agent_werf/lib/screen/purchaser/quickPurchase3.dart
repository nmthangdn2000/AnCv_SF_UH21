import 'dart:async';
import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:retail_agent_werf/components/commom/common.dart';
import 'package:retail_agent_werf/models/productCheckBox/checkBox.model.dart';
import 'package:retail_agent_werf/screen/purchaser/mainPage.purchaser.dart';
import 'package:retail_agent_werf/utils/constants.dart';

class QuickPurchase3 extends StatefulWidget {
  const QuickPurchase3({Key? key}) : super(key: key);

  @override
  State<QuickPurchase3> createState() => _QuickPurchase3State();
}

class _QuickPurchase3State extends State<QuickPurchase3> {
  bool valueCheckBox = false;
  int totalAmount = 0;
  int totalPrice = 0;
  final products = [
    ProductCheckBox(
      img: "ca_chua1.png",
      amount: 3,
      totalPrice: 90,
      unitPrice: "30k VND/KG",
      description: "Vừa mới thu hoặc \nChất lượng cao",
    ),
    ProductCheckBox(
      img: "ca_chua2.png",
      amount: 7,
      totalPrice: 140,
      unitPrice: "20k VND/KG",
      description: "Vừa mới thu hoặc \nChất lượng cao",
    ),
    ProductCheckBox(
      img: "ca_chua3.png",
      amount: 5,
      totalPrice: 125,
      unitPrice: "25k VND/KG",
      description: "Vừa mới thu hoặc \nChất lượng cao",
    ),
    ProductCheckBox(
      img: "ca_chua4.png",
      amount: 10,
      totalPrice: 300,
      unitPrice: "30k VND/KG",
      description: "Vừa mới thu hoặc \nChất lượng cao",
    ),
  ];
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: SingleChildScrollView(
          physics: BouncingScrollPhysics(),
          child: Container(
            padding: const EdgeInsets.all(20),
            child: Column(
              children: [
                _bannerMain(),
                SizedBox(
                  height: 50,
                ),
                Text(
                  "Mua nhanh",
                  style: TextStyle(
                    fontSize: 26,
                    color: Colors.black,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                SizedBox(
                  height: 20,
                ),
                _tableProduct(),
                SizedBox(
                  height: 30,
                ),
                Row(
                  children: [
                    Expanded(
                      child: _btnStart(true),
                      flex: 1,
                    ),
                    SizedBox(
                      width: 20,
                    ),
                    Expanded(
                      child: _btnStart(false),
                      flex: 1,
                    ),
                  ],
                ),
                SizedBox(
                  height: 30,
                ),
                _bottom(),
              ],
            ),
          ),
        ),
      ),
    );
  }

  Container _bannerMain() {
    return Container(
      width: MediaQuery.of(context).size.width,
      child: Stack(
        children: [
          ClipRRect(
            borderRadius: BorderRadius.circular(16),
            child: Image.asset(
              "assets/images/main.jpg",
            ),
          ),
          Positioned(
            bottom: 0,
            left: 0,
            right: 0,
            child: ClipRRect(
              borderRadius: BorderRadius.only(bottomLeft: Radius.circular(16)),
              child: Image.asset(
                "assets/images/ellipse3.png",
                width: MediaQuery.of(context).size.width,
                fit: BoxFit.contain,
              ),
            ),
          ),
          Positioned(
            top: 0,
            left: 0,
            child: Container(
              margin: const EdgeInsets.all(20),
              child: ClipRRect(
                borderRadius: BorderRadius.circular(40.0),
                child: InkWell(
                  onTap: () {
                    Navigator.pop(context);
                  },
                  child: BackdropFilter(
                    filter: ImageFilter.blur(sigmaX: 2.0, sigmaY: 5.0),
                    child: Container(
                      color: Colors.black.withOpacity(0.2),
                      padding: const EdgeInsets.all(8),
                      child: Icon(
                        FontAwesomeIcons.chevronLeft,
                        color: Colors.white,
                      ),
                    ),
                  ),
                ),
              ),
            ),
          ),
          Positioned(
            bottom: 0,
            left: 0,
            right: 0,
            child: Padding(
              padding: const EdgeInsets.all(20.0),
              child: Text(
                "Cà chua",
                style: TextStyle(
                  fontSize: 26,
                  color: Colors.white,
                  fontWeight: FontWeight.bold,
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  ElevatedButton _btnStart(bool type) {
    return ElevatedButton(
      onPressed: () {
        if (type) {
          CommomComponents.showDialogLoading(context);
          Timer(Duration(seconds: 2), () {
            Navigator.pop(context);
            _showBottomSheet(context);
          });
        } else {
          Timer(Duration(milliseconds: 500), () {
            CommomComponents.pushContextFalse(context, MainPagePurchaser());
          });
        }
      },
      style: ElevatedButton.styleFrom(
        padding: const EdgeInsets.symmetric(horizontal: 40, vertical: 20),
        alignment: Alignment.center,
        primary: type ? signInColor : Colors.red,
        shadowColor: Colors.transparent,
      ),
      child: Text(
        "${type ? "Mua ngay" : "Thoát"}",
        textAlign: TextAlign.center,
        style: TextStyle(fontWeight: FontWeight.bold),
      ),
    );
  }

  _showBottomSheet(context) {
    return showModalBottomSheet(
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(10.0),
      ),
      context: context,
      builder: (BuildContext context) {
        return Wrap(
          children: [
            Container(
              width: MediaQuery.of(context).size.width,
              padding: const EdgeInsets.all(30),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  Text(
                    "Thành công",
                    style: TextStyle(
                      fontSize: 26,
                      color: Color(0xFF22E079),
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  SizedBox(
                    height: 10,
                  ),
                  Text(
                    "Đơn đặt trước của bạn sẽ sớm được vận chuyển.",
                    style: TextStyle(
                      fontSize: 14,
                      color: Color(0xFF818181),
                    ),
                  ),
                  SizedBox(
                    height: 30,
                  ),
                  Icon(
                    FontAwesomeIcons.checkCircle,
                    color: Color(0xFF22E079),
                    size: 100,
                  ),
                ],
              ),
            ),
          ],
        );
      },
    ).whenComplete(() {
      Timer(Duration(milliseconds: 500), () {
        CommomComponents.pushContextFalse(context, MainPagePurchaser());
      });
    });
  }

  _tableProduct() {
    return Column(
      children: [
        Row(
          children: [
            Expanded(
              child: Text(""),
              flex: 1,
            ),
            Expanded(
              child: Text("Hình ảnh"),
              flex: 3,
            ),
            Expanded(
              child: Text("Số lượng"),
              flex: 3,
            ),
            Expanded(
              child: Text("Tổng tiền"),
              flex: 3,
            ),
          ],
        ),
        SizedBox(
          height: 20,
          child: Divider(),
        ),
        ListView.separated(
          physics: NeverScrollableScrollPhysics(),
          shrinkWrap: true,
          itemBuilder: (context, index) =>
              _buildSingleCheckBox(products[index]),
          separatorBuilder: (context, index) => const SizedBox(
            height: 10,
          ),
          itemCount: products.length,
        ),
        SizedBox(
          height: 20,
          child: Divider(),
        ),
        Row(
          children: [
            Expanded(
              child: Text("Tổng cộng"),
              flex: 4,
            ),
            Expanded(
              child: Text(
                "${totalAmount}KG",
                style: TextStyle(color: Colors.red, fontSize: 16),
              ),
              flex: 3,
            ),
            Expanded(
              child: Text(
                "${totalPrice}k VNĐ",
                style: TextStyle(color: Colors.red, fontSize: 16),
              ),
              flex: 3,
            ),
          ],
        ),
      ],
    );
  }

  _buildSingleCheckBox(ProductCheckBox productCheckBox) {
    return InkWell(
      onTap: () {
        setState(() {
          final newValue = !productCheckBox.value;
          productCheckBox.value = newValue;
          if (newValue) {
            totalAmount = totalAmount + productCheckBox.amount;
            totalPrice = totalPrice + productCheckBox.totalPrice;
          } else {
            totalAmount = totalAmount - productCheckBox.amount;
            totalPrice = totalPrice - productCheckBox.totalPrice;
          }
        });
      },
      child: Column(
        children: [
          Row(
            children: [
              Expanded(
                flex: 1,
                child: _buildCheckBox(
                  productCheckBox: productCheckBox,
                  onClick: () {
                    setState(() {
                      final newValue = !productCheckBox.value;
                      productCheckBox.value = newValue;
                      if (newValue) {
                        totalAmount = totalAmount + productCheckBox.amount;
                        totalPrice = totalPrice + productCheckBox.totalPrice;
                      } else {
                        totalAmount = totalAmount - productCheckBox.amount;
                        totalPrice = totalPrice - productCheckBox.totalPrice;
                      }
                    });
                  },
                ),
              ),
              Expanded(
                child: GestureDetector(
                  onTap: () {
                    setState(() {
                      final newValue = !productCheckBox.showDescription;
                      productCheckBox.showDescription = newValue;
                    });
                  },
                  child: Container(
                    width: 50,
                    height: 50,
                    child: ClipRRect(
                      borderRadius: BorderRadius.circular(4),
                      child:
                          Image.asset("assets/images/${productCheckBox.img}"),
                    ),
                  ),
                ),
                flex: 3,
              ),
              Expanded(
                child: Text(
                  "${productCheckBox.amount} KG",
                  style: TextStyle(
                      color:
                          productCheckBox.value ? signInColor : Colors.black),
                ),
                flex: 3,
              ),
              Expanded(
                child: Text(
                  "${productCheckBox.totalPrice}K VNĐ",
                  style: TextStyle(
                      color:
                          productCheckBox.value ? signInColor : Colors.black),
                ),
                flex: 3,
              ),
            ],
          ),
          _more(productCheckBox),
        ],
      ),
    );
  }

  _more(ProductCheckBox productCheckBox) {
    if (productCheckBox.showDescription)
      return Column(
        mainAxisAlignment: MainAxisAlignment.end,
        crossAxisAlignment: CrossAxisAlignment.end,
        children: [
          SizedBox(
            height: 5,
          ),
          Row(
            children: [
              Expanded(
                child: Text(
                  "Đơn giá",
                  textAlign: TextAlign.end,
                ),
                flex: 5,
              ),
              Expanded(
                child: Text(
                  "${productCheckBox.unitPrice}",
                  textAlign: TextAlign.end,
                ),
                flex: 5,
              ),
            ],
          ),
          SizedBox(
            height: 5,
          ),
          Row(
            children: [
              Expanded(
                child: Text(
                  "Đặc điểm",
                  textAlign: TextAlign.end,
                ),
                flex: 5,
              ),
              Expanded(
                child: Text(
                  "${productCheckBox.description}",
                  textAlign: TextAlign.end,
                ),
                flex: 5,
              ),
            ],
          ),
        ],
      );
    else
      return SizedBox();
  }

  Widget _buildCheckBox(
      {required ProductCheckBox productCheckBox,
      required VoidCallback onClick}) {
    return Checkbox(
        value: productCheckBox.value, onChanged: (value) => onClick());
  }

  _bottom() {
    return Container(
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Container(
            width: 40,
            height: 40,
            decoration: BoxDecoration(
              border: Border.all(color: signInColor),
              borderRadius: BorderRadius.circular(40),
              color: Colors.white,
            ),
            child: Center(
              child: Text(
                "1",
                style: TextStyle(
                  fontSize: 20,
                  color: signInColor,
                ),
              ),
            ),
          ),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 10),
            child: SizedBox(
              child: Divider(
                color: Colors.black,
              ),
              width: 10,
              height: 4,
            ),
          ),
          Container(
            width: 40,
            height: 40,
            decoration: BoxDecoration(
                border: Border.all(color: signInColor),
                borderRadius: BorderRadius.circular(40),
                color: Colors.white),
            child: Center(
              child: Text(
                "2",
                style: TextStyle(
                  fontSize: 20,
                  color: signInColor,
                ),
              ),
            ),
          ),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 10),
            child: SizedBox(
              child: Divider(
                color: Colors.black,
              ),
              width: 10,
              height: 4,
            ),
          ),
          Container(
            width: 40,
            height: 40,
            decoration: BoxDecoration(
                border: Border.all(color: signInColor),
                borderRadius: BorderRadius.circular(40),
                color: signInColor),
            child: Center(
              child: Text(
                "3",
                style: TextStyle(
                  fontSize: 20,
                  color: Colors.white,
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
