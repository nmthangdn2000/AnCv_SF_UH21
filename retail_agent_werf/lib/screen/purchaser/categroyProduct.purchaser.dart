import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/painting.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:retail_agent_werf/components/commom/common.dart';
import 'package:retail_agent_werf/components/listview/listProductsPurchaser.dart';
import 'package:retail_agent_werf/screen/purchaser/quickPurchase1.dart';
import 'package:retail_agent_werf/utils/constants.dart';

class CategoryProductPurchaser extends StatefulWidget {
  const CategoryProductPurchaser({Key? key}) : super(key: key);

  @override
  _CategoryProductPurchaserState createState() =>
      _CategoryProductPurchaserState();
}

class _CategoryProductPurchaserState extends State<CategoryProductPurchaser> {
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Scaffold(
      key: PageStorageKey('goodsPage'),
      floatingActionButton: RawMaterialButton(
        onPressed: () {
          CommomComponents.pushContextTrue(context, QuickPurchase());
        },
        fillColor: Color(0xFF22E079),
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(4)),
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 5),
          child: Text(
            "Mua nhanh",
            style: TextStyle(fontWeight: FontWeight.bold, color: Colors.white),
          ),
        ),
      ),
      body: SafeArea(
        top: true,
        child: SingleChildScrollView(
          physics: BouncingScrollPhysics(),
          child: Padding(
            padding: const EdgeInsets.symmetric(
              vertical: 8.0,
              horizontal: 20,
            ),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                _bannerMain(),
                SizedBox(
                  height: 10,
                ),
                _listViewProduct(
                    "Mới rao bán", Color(0xFF62AEFB), "moi_rao_ban.json"),
                SizedBox(
                  height: 10,
                ),
                _listViewProduct(
                    "Đang bán rẻ", Color(0xFFB2E2FE), "dang_ban_re.json"),
                SizedBox(
                  height: 10,
                ),
                _listViewProduct("Nông sản chất lượng cao", Color(0xFFB2E2FE),
                    "nong_san_chat_luong_cao.json"),
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
            child: Image.asset(
              "assets/images/ellipse3.png",
              width: MediaQuery.of(context).size.width,
              fit: BoxFit.contain,
            ),
          ),
          Positioned(
            top: 0,
            left: 0,
            child: Container(
              margin: const EdgeInsets.all(20),
              child: ClipRRect(
                borderRadius: BorderRadius.circular(40.0),
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

  Widget _listViewProduct(title, color, filename) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.start,
      children: [
        SizedBox(
          height: 20,
        ),
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Text(
              title,
              style: TextStyle(
                fontSize: 16,
                fontWeight: FontWeight.bold,
              ),
            ),
            Text(
              "Xem thêm",
              style: TextStyle(
                fontSize: 10,
                color: signInColor,
              ),
            ),
          ],
        ),
        SizedBox(
          height: 20,
        ),
        ListProductsPurchaser(color: color, fileName: filename),
      ],
    );
  }
}
