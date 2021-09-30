import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';

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
}
