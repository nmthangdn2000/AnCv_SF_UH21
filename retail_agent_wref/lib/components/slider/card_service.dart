import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:retail_agent_werf/components/item/item_card_home_page.dart';

class CardService extends StatelessWidget {
  const CardService({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return CarouselSlider(
        options: CarouselOptions(
          height: 220.0,
          enlargeCenterPage: true,
          autoPlay: true,
          viewportFraction: 0.8,
        ),
        items: [
          Padding(
            padding: const EdgeInsets.symmetric(
              horizontal: 10,
              vertical: 20,
            ),
            child: ItemCardHomePage(
              color: Colors.orange,
              rate: 25,
              title: "Tổng doanh thu",
              price: "500.000 ₫",
              icon: FontAwesomeIcons.dollarSign,
              type: true,
            ),
          ),
          Padding(
            padding: const EdgeInsets.symmetric(
              horizontal: 10,
              vertical: 20,
            ),
            child: ItemCardHomePage(
              color: Colors.red,
              rate: 25,
              title: "Đơn hàng tháng này",
              price: "17",
              icon: FontAwesomeIcons.addressBook,
              type: true,
            ),
          ),
          Padding(
            padding: const EdgeInsets.symmetric(
              horizontal: 10,
              vertical: 20,
            ),
            child: ItemCardHomePage(
              color: Colors.green,
              rate: 25,
              title: "Tổng sản phẩm",
              price: "89",
              icon: FontAwesomeIcons.boxOpen,
              type: false,
            ),
          ),
          Padding(
            padding: const EdgeInsets.symmetric(
              horizontal: 10,
              vertical: 20,
            ),
            child: ItemCardHomePage(
              color: Colors.blue,
              rate: 25,
              title: "Khách hàng",
              price: "5",
              icon: FontAwesomeIcons.users,
              type: false,
            ),
          ),
        ]);
  }
}
