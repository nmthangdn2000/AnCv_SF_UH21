import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:retail_agent_werf/components/item/itemProductPurchaser.dart';
import 'package:retail_agent_werf/models/product.purchaser/product.purchaser.model.dart';

class ListProductsPurchaser extends StatefulWidget {
  final Color color;
  final String fileName;
  const ListProductsPurchaser(
      {Key? key, required this.color, required this.fileName})
      : super(key: key);

  @override
  _ListProductsPurchaserState createState() => _ListProductsPurchaserState();
}

class _ListProductsPurchaserState extends State<ListProductsPurchaser> {
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Container(
      width: size.width,
      height: 160,
      child: FutureBuilder(
        future: _readJson(),
        builder: (context, data) {
          if (data.hasError)
            return Text("No data ${data.error}");
          else if (data.hasData) {
            List<ProductModelPurchaser> items =
                data.data as List<ProductModelPurchaser>;
            return ListView.builder(
                scrollDirection: Axis.horizontal,
                physics: BouncingScrollPhysics(),
                itemCount: items.length,
                itemBuilder: (context, index) {
                  return ItemProductPurchaser(
                    model: items[index],
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

  Future<List<ProductModelPurchaser>> _readJson() async {
    final String pathJson = 'assets/jsonfile/${widget.fileName}';
    final String response = await rootBundle.loadString(pathJson);
    final data = await json.decode(response) as List<dynamic>;
    return data.map((p) => ProductModelPurchaser.fromJson(p)).toList();
  }
}
