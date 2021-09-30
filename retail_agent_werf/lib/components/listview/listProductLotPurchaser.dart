import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:retail_agent_werf/components/commom/common.dart';
import 'package:retail_agent_werf/models/product.purchaser/productLot.purchaser.model.dart';
import 'package:retail_agent_werf/screen/purchaser/categroyProduct.purchaser.dart';

class ListProductsLotPurchaser extends StatefulWidget {
  const ListProductsLotPurchaser({Key? key}) : super(key: key);

  @override
  _ListProductsLotPurchaserState createState() =>
      _ListProductsLotPurchaserState();
}

class _ListProductsLotPurchaserState extends State<ListProductsLotPurchaser> {
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
            List<ProductLotModelPurchaser> items =
                data.data as List<ProductLotModelPurchaser>;
            return ListView.builder(
                scrollDirection: Axis.horizontal,
                physics: BouncingScrollPhysics(),
                itemCount: items.length,
                itemBuilder: (context, index) {
                  return GestureDetector(
                      onTap: () {
                        CommomComponents.pushContextTrue(
                          context,
                          CategoryProductPurchaser(),
                        );
                      },
                      child: _slideTop(items[index]));
                });
          } else
            return Center(
              child: CircularProgressIndicator(),
            );
        },
      ),
    );
  }

  Future<List<ProductLotModelPurchaser>> _readJson() async {
    final String pathJson = 'assets/jsonfile/productBuyALot.json';
    final String response = await rootBundle.loadString(pathJson);
    final data = await json.decode(response) as List<dynamic>;
    return data.map((p) => ProductLotModelPurchaser.fromJson(p)).toList();
  }

  Container _slideTop(ProductLotModelPurchaser model) {
    return Container(
      margin: const EdgeInsets.only(right: 10),
      decoration: BoxDecoration(
          color: Color(0xFFF6F9FB), borderRadius: BorderRadius.circular(16)),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.start,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          ClipRRect(
            borderRadius: BorderRadius.circular(16),
            child: Image.asset("assets/images/${model.img}"),
          ),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  "${model.name}",
                  style: TextStyle(
                      color: Colors.black, fontWeight: FontWeight.bold),
                ),
                SizedBox(
                  height: 5,
                ),
                Text("${model.note}"),
              ],
            ),
          )
        ],
      ),
    );
  }
}
