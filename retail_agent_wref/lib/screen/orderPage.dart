import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:retail_agent_werf/apis/order.api.dart';
import 'package:retail_agent_werf/components/listview/listViewOrder.dart';
import 'package:retail_agent_werf/models/order.model/order.model.dart';
import 'package:retail_agent_werf/utils/constants.dart';

class OrderPage extends StatefulWidget {
  const OrderPage({Key? key}) : super(key: key);

  @override
  State<OrderPage> createState() => _OrderPageState();
}

class _OrderPageState extends State<OrderPage>
    with SingleTickerProviderStateMixin {
  late TabController _tabController;
  String title = "Chờ xác nhận";
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    _tabController = TabController(length: 5, vsync: this);
    _tabController.addListener(() {
      setState(() {
        switch (_tabController.index) {
          case 0:
            title = "Đơn Chờ xác nhận";
            break;
          case 1:
            title = "Đơn đã Xác nhận";
            break;
          case 2:
            title = "Đang giao hàng";
            break;
          case 3:
            title = "Đơn hoàn tất";
            break;
          default:
            title = "Đơn đã hủy";
            break;
        }
      });
    });
  }

  @override
  void dispose() {
    // TODO: implement dispose
    super.dispose();
    _tabController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Color(0xFFFAFAFA),
        elevation: 0,
        centerTitle: true,
        title: Text(
          title,
          style: TextStyle(
              fontSize: 20, fontWeight: FontWeight.bold, color: Colors.black),
        ),
        bottom: TabBar(
          controller: _tabController,
          unselectedLabelColor: Colors.black.withOpacity(.5),
          indicatorColor: signInColor,
          labelColor: signInColor,
          tabs: [
            Tab(
              child: Text(
                "Chờ xác nhận",
                style: TextStyle(fontSize: 10),
              ),
              icon: Icon(FontAwesomeIcons.wallet),
            ),
            Tab(
              child: Text(
                "xác nhận",
                style: TextStyle(fontSize: 10),
              ),
              icon: Icon(FontAwesomeIcons.boxOpen),
            ),
            Tab(
              child: Text(
                "Đang giao",
                style: TextStyle(fontSize: 10),
              ),
              icon: Icon(FontAwesomeIcons.truck),
            ),
            Tab(
              child: Text(
                "Hoàn tất",
                style: TextStyle(fontSize: 10),
              ),
              icon: Icon(FontAwesomeIcons.calendarCheck),
            ),
            Tab(
              child: Text(
                "Hủy đơn",
                style: TextStyle(fontSize: 10),
              ),
              icon: Icon(FontAwesomeIcons.calendarTimes),
            ),
          ],
        ),
      ),
      body: TabBarView(
        controller: _tabController,
        children: [
          Container(
            color: Color(0xFFFAFAFA),
            child: SingleChildScrollView(
              child: ListViewOrder(
                status: PENDDING,
              ),
            ),
          ),
          Container(
            color: Color(0xFFFAFAFA),
            child: SingleChildScrollView(
              child: ListViewOrder(
                status: CONFIM,
              ),
            ),
          ),
          Container(
            color: Color(0xFFFAFAFA),
            child: SingleChildScrollView(
              child: ListViewOrder(
                status: TRANSPORT,
              ),
            ),
          ),
          Container(
            color: Color(0xFFFAFAFA),
            child: SingleChildScrollView(
              child: ListViewOrder(
                status: DONE,
              ),
            ),
          ),
          Container(
            color: Color(0xFFFAFAFA),
            child: SingleChildScrollView(
              child: ListViewOrder(
                status: CANCEL,
              ),
            ),
          ),
        ],
      ),
    );
  }
}
