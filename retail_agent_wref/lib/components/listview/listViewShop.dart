import 'package:flutter/material.dart';
import 'package:retail_agent_werf/apis/shop.api.dart';
import 'package:retail_agent_werf/components/item/item_shop.dart';
import 'package:retail_agent_werf/models/shop.model/shop.model.dart';

class ListViewShop extends StatefulWidget {
  const ListViewShop({Key? key}) : super(key: key);

  @override
  _ListViewShopState createState() => _ListViewShopState();
}

class _ListViewShopState extends State<ListViewShop> {
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Container(
      child: FutureBuilder(
        future: readData(),
        builder: (context, data) {
          if (data.hasError)
            return Text("No data ${data.error}");
          else if (data.hasData) {
            List<ShopModel> items = data.data as List<ShopModel>;
            return ListView.builder(
                key: PageStorageKey('orderPage'),
                physics: NeverScrollableScrollPhysics(),
                shrinkWrap: true,
                itemCount: items.length,
                itemBuilder: (context, index) {
                  return ItemShop(
                    key: Key("item-shop-$index"),
                    address: items[index].address,
                    city: items[index].city,
                    description: items[index].description,
                    media: items[index].media,
                    name: items[index].name,
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

  Future<List<ShopModel>> readData() async {
    ShopApi api = new ShopApi();
    List<ShopModel> shops = await api.getShopByIdUser();
    return shops;
  }
}
