import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:retail_agent_werf/apis/order.api.dart';
import 'package:retail_agent_werf/components/item/itemOrder.dart';
import 'package:retail_agent_werf/models/order.model/order.model.dart';

class ListViewOrder extends StatefulWidget {
  const ListViewOrder({Key? key, required this.status}) : super(key: key);
  final int status;
  @override
  _ListViewOrderState createState() => _ListViewOrderState();
}

class _ListViewOrderState extends State<ListViewOrder> {
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Container(
      height: size.height - 140,
      child: FutureBuilder(
        future: getOrders(),
        builder: (context, data) {
          if (data.hasError)
            return Text("Không có đơn hàng nào");
          else if (data.hasData) {
            List<OrderModel> items = data.data as List<OrderModel>;
            if (items.length > 0)
              return ListView.builder(
                  key: PageStorageKey('orderPage'),
                  physics: BouncingScrollPhysics(),
                  itemCount: items.length,
                  itemBuilder: (context, index) {
                    return ItemOrder(
                      orderModel: items[index],
                      isLastItem: (items.length - 1) == index ? true : false,
                    );
                  });
            else
              return Center(
                child: Text("Không có đơn hàng nào"),
              );
          } else
            return Center(
              child: CircularProgressIndicator(),
            );
        },
      ),
    );
  }

  Future<List<OrderModel>> getOrders() async {
    OrderApi api = new OrderApi();
    return await api.getOrderByIdUserBoss(widget.status);
  }
}
