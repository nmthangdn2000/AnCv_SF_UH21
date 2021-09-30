import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:retail_agent_werf/screen/homePage.dart';
import 'package:retail_agent_werf/screen/purchaser/goodsPage.purchaser.dart';
import 'package:retail_agent_werf/screen/purchaser/orderPage.purchaser.dart';
import 'package:retail_agent_werf/screen/purchaser/preOrderPage.purchaser.dart';
import 'package:retail_agent_werf/utils/constants.dart';

class MainPagePurchaser extends StatefulWidget {
  const MainPagePurchaser({Key? key}) : super(key: key);

  @override
  _MainPagePurchaserState createState() => _MainPagePurchaserState();
}

class _MainPagePurchaserState extends State<MainPagePurchaser> {
  int _currentPage = 0;
  PreOrderPagePurchaser _homePage = new PreOrderPagePurchaser();
  OrderPagePurchaser _orderPage = new OrderPagePurchaser();
  GoodsPagePurchaser _goodsPage = new GoodsPagePurchaser();
  Widget _showPage = new OrderPagePurchaser();
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        bottom: false,
        child: _showPage,
      ),
      bottomNavigationBar: BottomNavigationBar(
        items: [
          BottomNavigationBarItem(
              icon: FaIcon(FontAwesomeIcons.solidClipboard), label: "Thống kê"),
          BottomNavigationBarItem(
              icon: FaIcon(FontAwesomeIcons.shoppingBasket),
              label: "Nhập hàng"),
          BottomNavigationBarItem(
              icon: FaIcon(FontAwesomeIcons.cartArrowDown), label: "Đặt trước"),
        ],
        currentIndex: _currentPage,
        selectedItemColor: signInColor,
        onTap: (index) {
          setState(() {
            _currentPage = index;
            _showPage = _getBody();
          });
        },
      ),
    );
  }

  Widget _getBody() {
    switch (_currentPage) {
      case 0:
        return _orderPage;
      case 1:
        return _goodsPage;
      case 2:
        return _homePage;
      default:
        return Container(
          child: Text("jadnsakdjn"),
        );
    }
  }
}
