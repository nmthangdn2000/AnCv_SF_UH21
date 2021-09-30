import 'dart:async';

import 'package:flutter/material.dart';
import 'package:retail_agent_werf/apis/auth.api.dart';
import 'package:retail_agent_werf/components/commom/common.dart';
import 'package:retail_agent_werf/screen/mainPage.dart';
import 'package:retail_agent_werf/screen/purchaser/mainPage.purchaser.dart';
import 'package:retail_agent_werf/screen/signIn.dart';
import 'package:retail_agent_werf/utils/user_shared_preferences.dart';

class SplashScreen extends StatefulWidget {
  const SplashScreen({Key? key}) : super(key: key);

  @override
  _SplashScreenState createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> {
  _auth() async {
    AuthApi authApi = new AuthApi();
    bool auth = await authApi.auth();
    if (!auth) {
      return CommomComponents.pushContextFalse(context, SignIn());
    }
    int type = UserSharedPreferences.getUserType();
    if (type == 1)
      return CommomComponents.pushContextFalse(context, MainPage());
    return CommomComponents.pushContextFalse(context, MainPagePurchaser());
  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    Timer(Duration(seconds: 3), () {
      _auth();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          Center(
            child: Container(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  Image.asset("assets/images/logo.png"),
                  SizedBox(
                    height: 20,
                  ),
                  Text(
                    "WREF",
                    style: TextStyle(
                      color: Colors.black,
                      fontSize: 50,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  SizedBox(
                    height: 20,
                  ),
                  Text(
                    "ứng dụng dành cho đại lý bán lẻ".toUpperCase(),
                    style: TextStyle(
                      color: Colors.black,
                      fontSize: 14,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ],
              ),
            ),
          ),
          Positioned(
            child: Container(),
          ),
        ],
      ),
    );
  }
}
