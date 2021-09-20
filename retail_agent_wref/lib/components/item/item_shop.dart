import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:retail_agent_werf/utils/base_url.dart';

class ItemShop extends StatelessWidget {
  const ItemShop({
    Key? key,
    required this.media,
    required this.name,
    required this.description,
    required this.city,
    required this.address,
  }) : super(key: key);
  final String media;
  final String name;
  final String description;
  final String city;
  final String address;
  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(10),
      margin: const EdgeInsets.symmetric(vertical: 10),
      decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(4),
          border: Border.all(color: Colors.blueAccent),),
      child: Row(
        children: [
          Expanded(
            flex: 2,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Image.network(
                  "$BASE_URL_MEDIA/uploads/$media",
                  width: 60,
                  height: 60,
                  fit: BoxFit.cover,
                ),
              ],
            ),
          ),
          Expanded(
            flex: 6,
            child: Padding(
              padding: const EdgeInsets.only(right: 8.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    name,
                    style: TextStyle(fontWeight: FontWeight.bold),
                  ),
                  Text(
                    description,
                    maxLines: 2,
                    overflow: TextOverflow.ellipsis,
                  ),
                  SizedBox(
                    height: 10,
                  ),
                  Row(
                    children: [
                      Flexible(
                          child: RichText(
                        text: TextSpan(
                          children: [
                            WidgetSpan(
                              child: FaIcon(
                                FontAwesomeIcons.mapMarkerAlt,
                                size: 16,
                                color: Colors.red,
                              ),
                            ),
                            WidgetSpan(
                              child: SizedBox(
                                width: 5,
                              ),
                            ),
                            TextSpan(
                              text: "$city - $address",
                              style:
                                  TextStyle(fontSize: 12, color: Colors.black),
                            ),
                          ],
                        ),
                      )),
                    ],
                  )
                ],
              ),
            ),
          ),
          Expanded(
            flex: 1,
            child: Column(
              children: [
                FaIcon(
                  FontAwesomeIcons.edit,
                  size: 16,
                ),
                SizedBox(height: 20),
                FaIcon(
                  FontAwesomeIcons.trashAlt,
                  size: 16,
                ),
              ],
            ),
          )
        ],
      ),
    );
  }
}
