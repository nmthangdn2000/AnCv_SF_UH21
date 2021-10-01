import 'dart:async';
import 'dart:developer';

import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:retail_agent_werf/apis/auth.api.dart';
import 'package:retail_agent_werf/components/commom/common.dart';
import 'package:retail_agent_werf/screen/mainPage.dart';
import 'package:retail_agent_werf/screen/purchaser/mainPage.purchaser.dart';
import 'package:retail_agent_werf/screen/signUp.dart';
import 'package:retail_agent_werf/utils/Constants.dart';
import 'package:retail_agent_werf/utils/user_shared_preferences.dart';

class SignIn extends StatefulWidget {
  const SignIn({Key? key}) : super(key: key);

  @override
  _SignInState createState() => _SignInState();
}

class _SignInState extends State<SignIn> {
  bool _isHiddenPassword = true;
  TextEditingController phoneController = new TextEditingController();
  TextEditingController passwordController = new TextEditingController();
  bool _validatePassword = false, _validatePhone = false;
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Scaffold(
      body: Stack(
        children: [
          Positioned(
            child: Image.asset(
              'assets/images/image-login1.jpg',
              width: size.width,
              fit: BoxFit.cover,
            ),
            bottom: 0,
            left: 0,
            right: 0,
          ),
          _bodySignIn(),
        ],
      ),
    );
  }

  Widget _bodySignIn() {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 20.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text(
            "WREF - R Ứng dụng di động nông nghiệp dành cho nhà bán lẻ",
            style: TextStyle(color: Colors.grey, fontSize: 12),
          ),
          SizedBox(
            height: 2,
          ),
          Text(
            "Đăng nhập",
            style: TextStyle(
                color: signInColor, fontSize: 26, fontWeight: FontWeight.bold),
          ),
          SizedBox(
            height: 10,
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Container(
                padding: const EdgeInsets.symmetric(
                  vertical: 8,
                  horizontal: 20,
                ),
                decoration: BoxDecoration(
                  border: Border.all(color: signInColor),
                  borderRadius: BorderRadius.circular(50),
                ),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    FaIcon(
                      FontAwesomeIcons.google,
                      color: signInColor,
                      size: 16,
                    ),
                    SizedBox(
                      width: 6,
                    ),
                    Text(
                      "Đăng nhập bằng Google",
                      style: TextStyle(color: signInColor, fontSize: 14),
                    )
                  ],
                ),
              ),
            ],
          ),
          SizedBox(
            height: 30,
          ),
          _inputEmail(),
          SizedBox(
            height: 10,
          ),
          _inputPassword(),
          SizedBox(
            height: 30,
          ),
          Row(
            children: [
              Expanded(
                child: _btnSignIn(),
                flex: 1,
              ),
              SizedBox(
                width: 10,
              ),
              Expanded(
                child: _btnSignUp(),
                flex: 1,
              ),
            ],
          )
        ],
      ),
    );
  }

  TextField _inputEmail() {
    return TextField(
      controller: phoneController,
      keyboardType: TextInputType.phone,
      decoration: InputDecoration(
          errorText: _validatePhone ? 'Số điệ thoại không thể trống' : null,
          enabledBorder: OutlineInputBorder(
            borderSide: BorderSide(color: signInColor),
          ),
          focusedBorder: OutlineInputBorder(
            borderSide: BorderSide(color: signInColor),
          ),
          prefixIcon: Icon(
            FontAwesomeIcons.phone,
            color: signInColor,
          ),
          hintText: 'Nhập số điện thoại',
          labelText: 'Phone',
          labelStyle: TextStyle(color: signInColor)),
      obscureText: false,
    );
  }

  TextField _inputPassword() {
    return TextField(
      controller: passwordController,
      keyboardType: TextInputType.emailAddress,
      decoration: InputDecoration(
          errorText: _validatePassword ? 'Mật khẩu không thể trống' : null,
          enabledBorder: OutlineInputBorder(
            borderSide: BorderSide(color: signInColor),
          ),
          focusedBorder: OutlineInputBorder(
            borderSide: BorderSide(color: signInColor),
          ),
          prefixIcon: Icon(
            FontAwesomeIcons.lock,
            color: signInColor,
          ),
          suffixIcon: IconButton(
            onPressed: () {
              setState(() {
                _isHiddenPassword = !_isHiddenPassword;
              });
            },
            icon: Icon(
              _isHiddenPassword
                  ? FontAwesomeIcons.eyeSlash
                  : FontAwesomeIcons.eye,
              color: signInColor,
            ),
          ),
          hintText: 'Nhập mật khẩu',
          labelText: 'Password',
          labelStyle: TextStyle(color: signInColor)),
      obscureText: _isHiddenPassword,
    );
  }

  ElevatedButton _btnSignIn() {
    return ElevatedButton(
      onPressed: () {
        CommomComponents.showDialogLoading(context);
        _validateSignIn(phoneController.text, passwordController.text);
      },
      style: ElevatedButton.styleFrom(
        padding: const EdgeInsets.symmetric(horizontal: 0, vertical: 20),
        alignment: Alignment.center,
        primary: signInColor,
        shadowColor: Colors.transparent,
      ),
      child: const Text(
        "Đăng nhập",
        textAlign: TextAlign.center,
        style: TextStyle(fontWeight: FontWeight.bold),
      ),
    );
  }

  ElevatedButton _btnSignUp() {
    return ElevatedButton(
        onPressed: () {
          CommomComponents.pushContextTrue(context, SignUp());
        },
        style: ElevatedButton.styleFrom(
          padding: const EdgeInsets.symmetric(horizontal: 0, vertical: 20),
          alignment: Alignment.center,
          primary: Colors.white,
          onPrimary: Colors.blue,
          shadowColor: Colors.transparent,
          shape: RoundedRectangleBorder(
            side: BorderSide(color: signInColor),
            borderRadius: BorderRadius.circular(4),
          ),
        ),
        child: const Text(
          "Đăng ký",
          textAlign: TextAlign.center,
          style: TextStyle(fontWeight: FontWeight.bold, color: signInColor),
        ));
  }

  _validateSignIn(String phone, String password) async {
    if (phone.isEmpty) {
      return setState(() {
        _validatePhone = true;
      });
    }
    if (password.isEmpty) {
      return setState(() {
        _validatePassword = true;
      });
    }
    try {
      AuthApi api = new AuthApi();
      bool signIn = await api.signIn(phone, password).then((value) => value);
      if (signIn) {
        int type = UserSharedPreferences.getUserType();
        Timer(Duration(seconds: 2), () {
          if (type == 1)
            return CommomComponents.pushContextFalse(context, MainPage());
          return CommomComponents.pushContextFalse(
              context, MainPagePurchaser());
        });
      } else {
        print("sai");
      }
    } catch (e) {
      print(e);
    }
  }
}
