import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:retail_agent_werf/components/listview/listViewShop.dart';
import 'package:retail_agent_werf/utils/constants.dart';
import 'package:dotted_border/dotted_border.dart';

class ShopTabView extends StatelessWidget {
  const ShopTabView({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 10),
      child: SingleChildScrollView(
        physics: NeverScrollableScrollPhysics(),
        child: Column(
          children: [
            _addNewProduct(size),
            SizedBox(
              height: 20,
            ),
            ListViewShop(),
          ],
        ),
      ),
    );
  }

  Widget _addNewProduct(size) {
    return GestureDetector(
      onTap: () {
        print("click thêm sản phẩm");
      },
      child: _buildDashedBorder(
        child: Container(
          margin: const EdgeInsets.symmetric(vertical: 10),
          padding: const EdgeInsets.symmetric(vertical: 10),
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(4),
          ),
          width: size.width,
          child: Row(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              FaIcon(
                FontAwesomeIcons.plus,
                size: 16,
                color: signInColor,
              ),
              SizedBox(
                width: 10,
              ),
              Text(
                "Thêm gian hàng",
                style: TextStyle(
                    fontSize: 16,
                    color: signInColor,
                    fontWeight: FontWeight.w500),
              )
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildDashedBorder({required Widget child}) => DottedBorder(
        child: child,
        color: Colors.red,
      );
}
