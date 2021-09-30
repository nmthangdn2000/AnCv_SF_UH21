import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:retail_agent_werf/components/listview/listProductLotPurchaser.dart';
import 'package:retail_agent_werf/components/listview/listProductsPurchaser.dart';
import 'package:retail_agent_werf/utils/constants.dart';

class GoodsPagePurchaser extends StatefulWidget {
  const GoodsPagePurchaser({Key? key}) : super(key: key);

  @override
  _GoodsPagePurchaserState createState() => _GoodsPagePurchaserState();
}

class _GoodsPagePurchaserState extends State<GoodsPagePurchaser> {
  final List<String> _list_1 = ["Tất cả", "Cà chua", "Cam", "Dưa leo"];
  final List<String> _list_2 = ["Ngô", "Đậu nành", "Thanh long", "Ổi"];
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Scaffold(
      key: PageStorageKey('goodsPage'),
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
                _searchBar(size),
                SizedBox(
                  height: 10,
                ),
                _listViewText(size, _list_1),
                SizedBox(
                  height: 10,
                ),
                _listViewText(size, _list_2),
                SizedBox(
                  height: 10,
                ),
                _listViewProductLot("Được thu mua nhiều"),
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

  Row _searchBar(size) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Container(
          width: size.width - 120,
          child: TextField(
            decoration: InputDecoration(
              fillColor: Color(0xFFE7F6FF),
              filled: true,
              hintText: "Tìm kiếm ...",
              prefixIcon: Icon(
                FontAwesomeIcons.search,
                color: Colors.black,
              ),
              border: OutlineInputBorder(
                borderSide: BorderSide.none,
                borderRadius: BorderRadius.circular(8),
              ),
            ),
          ),
        ),
        Container(
          width: 60,
          height: 60,
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(100),
            color: Color(0xFFE7F6FF),
          ),
          child: Icon(FontAwesomeIcons.bell),
        ),
      ],
    );
  }

  Container _listViewText(size, myListData) {
    return Container(
      width: size.width,
      height: 30,
      child: ListView.builder(
        scrollDirection: Axis.horizontal,
        itemCount: myListData.length,
        itemBuilder: (context, i) {
          return Container(
            margin: const EdgeInsets.only(right: 20),
            child: TextButton(
              style: TextButton.styleFrom(
                padding: const EdgeInsets.symmetric(
                  vertical: 2,
                  horizontal: 20,
                ),
                backgroundColor: i == 0 ? signInColor : Colors.white,
                side: BorderSide(
                  color: i == 0 ? signInColor : Colors.grey,
                ),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(100),
                ),
              ),
              onPressed: () {},
              child: Text(
                myListData[i],
                style: TextStyle(
                    color: i == 0 ? Colors.white : Colors.black, fontSize: 12),
              ),
            ),
          );
        },
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

  Widget _listViewProductLot(title) {
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
        ListProductsLotPurchaser(),
      ],
    );
  }
}
