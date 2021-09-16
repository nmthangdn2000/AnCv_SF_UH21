import '../utils/constants.dart';
import 'package:flutter/material.dart';

class AppTheme {
  static final darkTheme = ThemeData(
      textTheme: TextTheme(bodyText1: TextStyle(fontFamily: 'RobotoMono')),
      scaffoldBackgroundColor: Colors.grey.shade900,
      colorScheme: ColorScheme.dark());
  static final lightTheme = ThemeData(
      textTheme: TextTheme(bodyText1: TextStyle(fontFamily: 'RobotoMono')),
      primaryColor: kPrimaryColor,
      scaffoldBackgroundColor: Colors.white,
      colorScheme: ColorScheme.light(),
      primarySwatch: Colors.orange);
}
