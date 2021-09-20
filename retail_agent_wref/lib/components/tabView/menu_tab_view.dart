import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:retail_agent_werf/components/item/item_menu.dart';

class MenuTabView extends StatelessWidget {
  const MenuTabView({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GridView.count(
      physics: NeverScrollableScrollPhysics(),
      shrinkWrap: true,
      primary: false,
      padding: const EdgeInsets.all(20),
      crossAxisSpacing: 10,
      mainAxisSpacing: 10,
      crossAxisCount: 2,
      children: [
        ItemMenu(
          icon: FontAwesomeIcons.tachometerAlt,
          name: "Thống kê",
        ),
        ItemMenu(
          icon: FontAwesomeIcons.users,
          name: "Khách hàng",
        ),
        ItemMenu(
          icon: FontAwesomeIcons.boxes,
          name: "Sản phẩm",
        ),
        ItemMenu(
          icon: FontAwesomeIcons.truck,
          name: "Nhập hàng",
        ),
        ItemMenu(
          icon: FontAwesomeIcons.cog,
          name: "Cài đặt",
        ),
        ItemMenu(
          icon: FontAwesomeIcons.headset,
          name: "Liên hệ",
        ),
      ],
    );
  }
}
