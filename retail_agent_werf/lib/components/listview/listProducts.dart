import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:retail_agent_werf/components/item/itemGroupProduct.dart';
import 'package:retail_agent_werf/components/item/itemUnitProduct.dart';
import 'package:retail_agent_werf/models/products.model/product.model.dart';

class ListProduct extends StatefulWidget {
  final Color color;
  final int indeList;
  const ListProduct({Key? key, required this.color, required this.indeList})
      : super(key: key);

  @override
  _ListProductState createState() => _ListProductState();
}

class _ListProductState extends State<ListProduct> {
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
            List<ProductModel> items = data.data as List<ProductModel>;
            return ListView.builder(
                scrollDirection: Axis.horizontal,
                physics: BouncingScrollPhysics(),
                itemCount: items.length,
                itemBuilder: (context, index) {
                  if (items[index].type)
                    return ItemUnitProduct(
                      productModel: items[index],
                      color: widget.color,
                    );
                  else
                    return ItemGroupProduct(
                      productModel: items[index],
                      color: widget.color,
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

  Future<List<ProductModel>> _readJson() async {
    final String pathJson = widget.indeList == 0
        ? 'assets/jsonfile/product.json'
        : widget.indeList == 1
            ? 'assets/jsonfile/product_dexuat.json'
            : 'assets/jsonfile/product_1.json';
    final String response = await rootBundle.loadString(pathJson);
    final data = await json.decode(response) as List<dynamic>;
    return data.map((p) => ProductModel.fromJson(p)).toList();
  }
}
