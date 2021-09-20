import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:retail_agent_werf/components/listview/listProducts.dart';
import 'package:retail_agent_werf/utils/constants.dart';

class GoodsPage extends StatefulWidget {
  const GoodsPage({Key? key}) : super(key: key);

  @override
  _GoodsPageState createState() => _GoodsPageState();
}

class _GoodsPageState extends State<GoodsPage> {
  final List<String> _list_1 = [
    "Tất cả",
    "Phân bón",
    "Dụng cụ",
    "Thuốc trừ sâu"
  ];
  final List<String> _list_2 = [
    "Thuốc trị nấm",
    "Thuốc trừ bệnh đậu ôn",
    "Thuốc trị thối rễ"
  ];
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
                _imageMain(size),
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
                _listViewProduct("Giảm giá nhiều", Color(0xFF22E079), 0),
                SizedBox(
                  height: 10,
                ),
                _listViewProduct("Đề xuất", Color(0xFF62AEFB), 1),
                SizedBox(
                  height: 10,
                ),
                _listViewProduct("Bán chạy", Color(0xFFB2E2FE), 2),
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

  Stack _imageMain(size) {
    return Stack(
      children: [
        Container(
          width: size.width,
          height: 180,
          child: ClipRRect(
            borderRadius: BorderRadius.circular(8),
            child: Image.asset(
              "assets/images/image_cty.jpg",
              fit: BoxFit.cover,
            ),
          ),
        ),
        Positioned(
          top: 0,
          left: 0,
          right: 0,
          child: Container(
            padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 20),
            height: 180,
            decoration: BoxDecoration(
              borderRadius: BorderRadius.only(topLeft: Radius.circular(8)),
              image: DecorationImage(
                alignment: Alignment.topLeft,
                image: AssetImage("assets/images/ellipse.png"),
              ),
            ),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  "Khai trương",
                  style: TextStyle(fontSize: 10, color: Colors.white),
                ),
                SizedBox(
                  height: 20,
                ),
                Text(
                  "Giảm 50%",
                  style: TextStyle(
                      fontSize: 20,
                      color: Colors.white,
                      fontWeight: FontWeight.bold),
                ),
                SizedBox(
                  height: 20,
                ),
                Text(
                  "Cho mọi loại mặt hàng",
                  style: TextStyle(
                      fontSize: 12,
                      color: Colors.white,
                      fontWeight: FontWeight.bold),
                ),
              ],
            ),
          ),
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

  Widget _listViewProduct(title, color, indeList) {
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
        ListProduct(color: color, indeList: indeList),
      ],
    );
  }
}
