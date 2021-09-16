import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:retail_agent_werf/components/item/itemOrder.dart';
import 'package:retail_agent_werf/models/order.model/order.model.dart';

class ListViewOrder extends StatefulWidget {
  const ListViewOrder({Key? key}) : super(key: key);

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
        future: readJson(),
        builder: (context, data) {
          if (data.hasError)
            return Text("No data ${data.error}");
          else if (data.hasData) {
            List<OrderModel> items = data.data as List<OrderModel>;
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
          } else
            return Center(
              child: CircularProgressIndicator(),
            );
        },
      ),
    );
  }

  Future<List<OrderModel>> readJson() async {
    final String response =
        await rootBundle.loadString('assets/jsonfile/order.json');
    final data = await json.decode(response) as List<dynamic>;
    return data.map((e) => OrderModel.fromJson(e)).toList();
  }
}
