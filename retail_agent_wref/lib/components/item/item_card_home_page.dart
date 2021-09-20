import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';

class ItemCardHomePage extends StatelessWidget {
  const ItemCardHomePage({
    Key? key,
    required this.title,
    this.number,
    this.price,
    required this.rate,
    required this.color,
    required this.icon,
    required this.type,
  }) : super(key: key);
  final String title;
  final int? number;
  final String? price;
  final int rate;
  final Color color;
  final IconData icon;
  final bool type;
  @override
  Widget build(BuildContext context) {
    final header = number ?? price;
    return Container(
      padding: const EdgeInsets.all(30),
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(20),
        gradient: LinearGradient(
          colors: [
            color,
            color.withOpacity(.5),
          ],
        ),
      ),
      child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Column(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Column(
                  mainAxisAlignment: MainAxisAlignment.start,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    _number(header.toString()),
                    _title(title),
                  ],
                ),
                _rate(rate, type),
              ],
            ),
            Container(
              child: Center(
                child: Icon(
                  icon,
                  size: 60,
                  color: Colors.white,
                ),
              ),
            ),
          ]),
    );
  }

  Widget _number(String s) {
    return Text(
      s,
      style: TextStyle(
        fontSize: 25,
        fontWeight: FontWeight.bold,
        color: Colors.white,
      ),
      maxLines: 1,
      overflow: TextOverflow.ellipsis,
    );
  }

  Widget _title(String s) {
    return Text(
      s,
      style: TextStyle(
        fontSize: 18,
        color: Colors.white,
      ),
      maxLines: 1,
      overflow: TextOverflow.ellipsis,
    );
  }

  Widget _rate(int rate, bool type) {
    return Row(
      children: [
        Icon(
          type ? FontAwesomeIcons.levelUpAlt : FontAwesomeIcons.edit,
          color: Colors.white,
        ),
        Text(
          type ? "$rate %" : "",
          style: TextStyle(
            fontSize: 14,
            color: Colors.white,
          ),
          maxLines: 1,
          overflow: TextOverflow.ellipsis,
        )
      ],
    );
  }
}
