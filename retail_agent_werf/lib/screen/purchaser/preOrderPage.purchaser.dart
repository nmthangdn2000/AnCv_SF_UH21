import 'dart:async';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/painting.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:retail_agent_werf/components/commom/common.dart';
import 'package:retail_agent_werf/screen/purchaser/mainPage.purchaser.dart';
import 'package:retail_agent_werf/utils/constants.dart';

class PreOrderPagePurchaser extends StatefulWidget {
  const PreOrderPagePurchaser({Key? key}) : super(key: key);

  @override
  _PreOrderPagePurchaserState createState() => _PreOrderPagePurchaserState();
}

class _PreOrderPagePurchaserState extends State<PreOrderPagePurchaser> {
  final List<String> itemProduct = [
    "Cà chua",
    "Ngô",
    "Lúa",
    "Thanh long",
    "Sắn",
    "Dưa leo"
  ];
  String tree = "dat_truoc";
  final List<String> itemUnit = ["kg", "yến", "tạ", "tấn"];
  String valueProduct = "Cà chua";
  String valueUnit = "kg";
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Scaffold(
      body: SafeArea(
        child: SingleChildScrollView(
          child: Container(
            padding: const EdgeInsets.all(20),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Container(
                  width: size.width,
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Text(
                        "Đặt trước",
                        style: TextStyle(
                            color: Colors.black,
                            fontSize: 26,
                            fontWeight: FontWeight.bold),
                      ),
                      SizedBox(
                        height: 5,
                      ),
                      Text(
                        "NÔNG SẢN",
                        style:
                            TextStyle(color: Color(0xFF818181), fontSize: 16),
                      ),
                    ],
                  ),
                ),
                _rowTop(size),
                SizedBox(
                  height: 10,
                ),
                _inputTextField("Nhập số lượng", "Số lượng"),
                SizedBox(
                  height: 10,
                ),
                _inputTextFieldArea("Thêm mô tả", "Mô tả"),
                SizedBox(
                  height: 20,
                ),
                Row(
                  children: [
                    Expanded(
                      child: _btnReset(),
                    ),
                    SizedBox(
                      width: 20,
                    ),
                    Expanded(
                      child: _btnSuccess(),
                    ),
                  ],
                )
              ],
            ),
          ),
        ),
      ),
    );
  }

  _tree(String name) {
    switch (name) {
      case "Cà chua":
        tree = "dat_truoc";
        break;
      case "Ngô":
        tree = "bap1";
        break;
      case "Lúa":
        tree = "lua1";
        break;
      case "Thanh long":
        tree = "thanh_long1";
        break;
      case "Sắn":
        tree = "san1";
        break;
      case "Dưa leo":
        tree = "dua_leo1";
        break;
      default:
        tree = "ca_chua1";
        break;
    }
  }

  _rowTop(Size size) {
    return Container(
      width: size.width,
      child: Row(
        children: [
          Expanded(
            flex: 8,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text("Loại cây"),
                SizedBox(
                  height: 5,
                ),
                _dropdownButton(itemProduct, valueProduct, true),
                SizedBox(
                  height: 10,
                ),
                Text("Đơn giá"),
                SizedBox(
                  height: 5,
                ),
                Row(
                  children: [
                    Expanded(
                      child: _inputTextField("Nhập đơn giá", "Đơn giá (VNĐ)"),
                      flex: 6,
                    ),
                    SizedBox(
                      width: 10,
                    ),
                    Expanded(
                      child: _dropdownButton(itemUnit, valueUnit, false),
                      flex: 4,
                    ),
                  ],
                )
              ],
            ),
          ),
          Expanded(
            child: Padding(
              padding: const EdgeInsets.only(
                left: 10,
              ),
              child: Image.asset(
                "assets/images/$tree.png",
                fit: BoxFit.contain,
              ),
            ),
            flex: 4,
          )
        ],
      ),
    );
  }

  _dropdownButton(List<String> item, String valueChoose, bool type) {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 4),
      decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(4),
          border: Border.all(color: signInColor.withOpacity(0.6), width: 2)),
      child: DropdownButton(
        underline: SizedBox(),
        items: item.map(biuldMenuItem).toList(),
        value: valueChoose,
        onChanged: (value) => setState(() {
          _tree(value.toString());
          if (type)
            valueProduct = value.toString();
          else
            valueUnit = value.toString();
        }),
        isExpanded: true,
        iconEnabledColor: signInColor.withOpacity(0.8),
      ),
    );
  }

  DropdownMenuItem<String> biuldMenuItem(String item) => DropdownMenuItem(
        value: item,
        child: Text(
          item,
          style: TextStyle(
            fontSize: 14,
          ),
        ),
      );

  TextField _inputTextField(String hintText, String label) {
    return TextField(
      // controller: addressController,
      keyboardType: TextInputType.text,
      decoration: InputDecoration(
          // errorText: _validatePhone ? 'Địa chỉ không thể trống' : null,
          enabledBorder: OutlineInputBorder(
            borderSide: BorderSide(color: signInColor),
          ),
          focusedBorder: OutlineInputBorder(
            borderSide: BorderSide(color: signInColor),
          ),
          hintText: hintText,
          labelText: label,
          labelStyle: TextStyle(color: signInColor)),
      obscureText: false,
    );
  }

  TextField _inputTextFieldArea(String hintText, String label) {
    return TextField(
      maxLines: 8,
      // controller: addressController,
      keyboardType: TextInputType.multiline,
      textAlign: TextAlign.start,
      decoration: InputDecoration(
          // errorText: _validatePhone ? 'Địa chỉ không thể trống' : null,
          enabledBorder: OutlineInputBorder(
            borderSide: BorderSide(color: signInColor),
          ),
          focusedBorder: OutlineInputBorder(
            borderSide: BorderSide(color: signInColor),
          ),
          hintText: hintText,
          labelText: label,
          alignLabelWithHint: true,
          labelStyle: TextStyle(color: signInColor)),
      obscureText: false,
    );
  }

  ElevatedButton _btnSuccess() {
    return ElevatedButton(
      onPressed: () {
        CommomComponents.showDialogLoading(context);
        Timer(Duration(seconds: 2), () {
          Navigator.pop(context);
          _showBottomSheet(context);
        });
      },
      style: ElevatedButton.styleFrom(
        padding: const EdgeInsets.symmetric(horizontal: 0, vertical: 20),
        alignment: Alignment.center,
        primary: signInColor,
        shadowColor: Colors.transparent,
      ),
      child: const Text(
        "Hoàn thành",
        textAlign: TextAlign.center,
        style: TextStyle(fontWeight: FontWeight.bold),
      ),
    );
  }

  ElevatedButton _btnReset() {
    return ElevatedButton(
      onPressed: () {},
      style: ElevatedButton.styleFrom(
        padding: const EdgeInsets.symmetric(horizontal: 0, vertical: 20),
        alignment: Alignment.center,
        primary: Colors.white,
        onPrimary: Colors.blue,
        shadowColor: Colors.transparent,
        shape: RoundedRectangleBorder(
          side: BorderSide(color: signInColor),
          borderRadius: BorderRadius.circular(4),
        ),
      ),
      child: const Text(
        "Đặt lại",
        textAlign: TextAlign.center,
        style: TextStyle(fontWeight: FontWeight.bold, color: signInColor),
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
                    "Đơn đặt trước của bạn đã được thêm.",
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
}
