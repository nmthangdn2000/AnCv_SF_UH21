import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:retail_agent_werf/components/commom/common.dart';
import 'package:retail_agent_werf/screen/purchaser/quickPurchase2.dart';
import 'package:retail_agent_werf/utils/constants.dart';

class QuickPurchase extends StatefulWidget {
  const QuickPurchase({Key? key}) : super(key: key);

  @override
  State<QuickPurchase> createState() => _QuickPurchaseState();
}

class _QuickPurchaseState extends State<QuickPurchase> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: SingleChildScrollView(
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
                Text(
                  "Đây là chức năng giúp bạn nhanh chóng mua một số lượng nông sản với một vài thao tác đơn giản.",
                  style: TextStyle(
                    fontSize: 14,
                    color: Color(0xFF818181),
                  ),
                ),
                SizedBox(
                  height: 10,
                ),
                Text(
                  "Ứng dụng sẽ tìm và thu thập các loại nông sản theo các tiêu chí mà bạn đề ra. Sau đó liệt kê thành một danh sách để bạn lựa chọn.",
                  style: TextStyle(
                    fontSize: 14,
                    color: Color(0xFF818181),
                  ),
                ),
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
        CommomComponents.pushContextTrue(context, QuickPurchase2());
      },
      style: ElevatedButton.styleFrom(
        padding: const EdgeInsets.symmetric(horizontal: 40, vertical: 20),
        alignment: Alignment.center,
        primary: signInColor,
        shadowColor: Colors.transparent,
      ),
      child: const Text(
        "Bắt đầu",
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
              color: signInColor,
            ),
            child: Center(
              child: Text(
                "1",
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
}
