import 'package:curved_navigation_bar/curved_navigation_bar.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:retail_agent_werf/screen/goodsPage.dart';
import 'package:retail_agent_werf/screen/homePage.dart';
import 'package:retail_agent_werf/screen/orderPage.dart';
import 'package:retail_agent_werf/utils/constants.dart';

class MainPage extends StatefulWidget {
  const MainPage({Key? key}) : super(key: key);

  @override
  _MainPageState createState() => _MainPageState();
}

class _MainPageState extends State<MainPage> {
  int _currentPage = 0;
  HomePage _homePage = new HomePage();
  OrderPage _orderPage = new OrderPage();
  GoodsPage _goodsPage = new GoodsPage();
  Widget _showPage = new HomePage();
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
              icon: FaIcon(FontAwesomeIcons.home), label: "Nhà"),
          BottomNavigationBarItem(
              icon: FaIcon(FontAwesomeIcons.solidClipboard), label: "Đơn hàng"),
          BottomNavigationBarItem(
              icon: FaIcon(FontAwesomeIcons.truck), label: "Nhập hàng"),
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
        return _homePage;
      case 1:
        return _orderPage;
      case 2:
        return _goodsPage;
      default:
        return Container(
          child: Text("jadnsakdjn"),
        );
    }
  }
}
