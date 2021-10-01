import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:retail_agent_werf/components/loader/loading.dart';

class CommomComponents {
  static showToast(String mess) {
    return Fluttertoast.showToast(
      msg: mess,
      toastLength: Toast.LENGTH_SHORT,
      gravity: ToastGravity.BOTTOM,
      timeInSecForIosWeb: 1,
      backgroundColor: Colors.black.withOpacity(0.5),
      textColor: Colors.white,
      fontSize: 16.0,
    );
  }

  static pushContextFalse(context, page) {
    return Navigator.of(context).pushAndRemoveUntil(
        MaterialPageRoute(builder: (BuildContext context) => page),
        (route) => false);
  }

  static pushContextTrue(context, page) {
    return Navigator.of(context).pushAndRemoveUntil(
        MaterialPageRoute(builder: (BuildContext context) => page),
        (route) => true);
  }

  static showDialogLoading(context) {
    return showDialog(
      context: context,
      builder: (BuildContext context) {
        return Container(
          child: Loading(),
        );
      },
    );
  }

  static showBottomSheet(
      BuildContext context, String mess, VoidCallback callback) {
    return showModalBottomSheet(
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(10.0),
      ),
      context: context,
      builder: (BuildContext context) {
        return Wrap(
          children: [
            Container(
              width: MediaQuery.of(context).size.width,
              padding: const EdgeInsets.all(30),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  Text(
                    "Thành công",
                    style: TextStyle(
                      fontSize: 26,
                      color: Color(0xFF22E079),
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  SizedBox(
                    height: 10,
                  ),
                  Text(
                    "$mess",
                    style: TextStyle(
                      fontSize: 14,
                      color: Color(0xFF818181),
                    ),
                  ),
                  SizedBox(
                    height: 30,
                  ),
                  Icon(
                    FontAwesomeIcons.checkCircle,
                    color: Color(0xFF22E079),
                    size: 100,
                  ),
                ],
              ),
            ),
          ],
        );
      },
    ).whenComplete(() => callback());
  }
}
