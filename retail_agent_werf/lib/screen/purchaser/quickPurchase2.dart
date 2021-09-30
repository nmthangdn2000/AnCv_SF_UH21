import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:retail_agent_werf/components/commom/common.dart';
import 'package:retail_agent_werf/screen/purchaser/quickPurchase3.dart';
import 'package:retail_agent_werf/utils/constants.dart';

class QuickPurchase2 extends StatefulWidget {
  const QuickPurchase2({Key? key}) : super(key: key);

  @override
  State<QuickPurchase2> createState() => _QuickPurchase2State();
}

class _QuickPurchase2State extends State<QuickPurchase2> {
  final List<String> itemUnit = ["kg", "yến", "tạ", "tấn"];
  final List<String> itemSize = ["Nhỏ hơn", "Lớn hơn"];
  String valueUnit = "kg";
  String valueSize = "Nhỏ hơn";
  final List<String> note = [""];
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
                  height: 10,
                ),
                Text(
                  "Tiêu chí",
                  style: TextStyle(
                    fontSize: 16,
                    color: Color(0xFF818181),
                    fontWeight: FontWeight.bold,
                  ),
                ),
                SizedBox(
                  height: 20,
                ),
                _content1(),
                SizedBox(
                  height: 10,
                ),
                _content2(),
                SizedBox(
                  height: 10,
                ),
                _addTextField(),
                SizedBox(
                  height: 30,
                ),
                _btnStart(),
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

  ElevatedButton _btnStart() {
    return ElevatedButton(
      onPressed: () {
        CommomComponents.pushContextTrue(context, QuickPurchase3());
      },
      style: ElevatedButton.styleFrom(
        padding: const EdgeInsets.symmetric(horizontal: 40, vertical: 20),
        alignment: Alignment.center,
        primary: signInColor,
        shadowColor: Colors.transparent,
      ),
      child: const Text(
        "Tiếp theo",
        textAlign: TextAlign.center,
        style: TextStyle(fontWeight: FontWeight.bold),
      ),
    );
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
              color: signInColor,
            ),
            child: Center(
              child: Text(
                "2",
                style: TextStyle(
                  fontSize: 20,
                  color: Colors.white,
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
                "3",
                style: TextStyle(
                  fontSize: 20,
                  color: signInColor,
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  _content1() {
    return Container(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            "Mục tiêu",
            style: TextStyle(
              fontSize: 12,
              color: Color(0xFF818181),
            ),
          ),
          SizedBox(
            height: 10,
          ),
          Row(
            children: [
              Expanded(
                flex: 7,
                child: _inputTextField("Nhập Số lượng", "Số lượng"),
              ),
              SizedBox(
                width: 10,
              ),
              Expanded(
                flex: 3,
                child: _dropdownButton(itemUnit, valueUnit, true),
              )
            ],
          ),
          SizedBox(
            height: 10,
          ),
          Row(
            children: [
              Expanded(
                flex: 4,
                child: _dropdownButton(itemSize, valueSize, false),
              ),
              SizedBox(
                width: 10,
              ),
              Expanded(
                flex: 6,
                child: _inputTextField(
                  "Nhập giá",
                  "Giá tiền (VNĐ)",
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }

  _content2() {
    return Container(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            "Đặt điểm nông sản",
            style: TextStyle(
              fontSize: 12,
              color: Color(0xFF818181),
            ),
          ),
          SizedBox(
            height: 10,
          ),
          ListView.separated(
            physics: NeverScrollableScrollPhysics(),
            shrinkWrap: true,
            itemBuilder: (context, index) {
              return _noteTextField(index);
            },
            separatorBuilder: (context, index) => const SizedBox(
              height: 10,
            ),
            itemCount: note.length,
          ),
        ],
      ),
    );
  }

  Widget _noteTextField(index) {
    return Padding(
      padding: const EdgeInsets.all(0),
      child: Row(
        children: [
          Expanded(flex: 9, child: _inputTextField("Nhập giá", "")),
          Visibility(
            visible: note.length > 1,
            child: Expanded(
              flex: 1,
              child: IconButton(
                onPressed: () {
                  setState(() {
                    note.removeAt(index);
                  });
                },
                icon: Icon(
                  FontAwesomeIcons.minusCircle,
                  color: Colors.red,
                ),
              ),
            ),
          )
        ],
      ),
    );
  }

  ElevatedButton _addTextField() {
    return ElevatedButton(
      onPressed: () {
        setState(() {
          note.add("");
        });
      },
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
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Icon(
            FontAwesomeIcons.plus,
            color: signInColor,
          ),
          const Text(
            "Thêm đặc điểm",
            textAlign: TextAlign.center,
            style: TextStyle(fontWeight: FontWeight.bold, color: signInColor),
          ),
        ],
      ),
    );
  }

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
          if (type)
            valueUnit = value.toString();
          else
            valueSize = value.toString();
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
            color: signInColor,
          ),
        ),
      );
}
