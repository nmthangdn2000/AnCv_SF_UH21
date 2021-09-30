import 'dart:async';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:retail_agent_werf/apis/auth.api.dart';
import 'package:retail_agent_werf/components/commom/common.dart';
import 'package:retail_agent_werf/components/loader/loading.dart';
import 'package:retail_agent_werf/screen/signIn.dart';
import 'package:retail_agent_werf/utils/constants.dart';

class SignUp2 extends StatefulWidget {
  const SignUp2({
    Key? key,
    required this.userName,
    required this.address,
    required this.phone,
    required this.password,
  }) : super(key: key);
  final String userName;
  final String address;
  final String phone;
  final String password;
  @override
  _SignUp2State createState() => _SignUp2State();
}

class _SignUp2State extends State<SignUp2> {
  final item = [
    'Nhà bán lẻ thuốc BTTV và các dịch vụ',
    'Nhà thu mua các sản phẩm nông nghiệp'
  ];
  String valueChoose = "Nhà bán lẻ thuốc BTTV và các dịch vụ";
  int userType = 1;
  String title1 = "Nhà bán lẻ";
  String title2 = "Nhà thu mua";
  String subTitle1 = "thuốc BVTV và các dịch vụ";
  String subTitle2 = "các sản phẩm nông nghiệp";
  String content1 =
      "Bạn có thể thu mua các sản phẩm nông nghiệp tốt từ các người nông dân nhỏ lẻ của chúng tôi. \nNhận thông báo về tình hình giá cả thị trường của một số loại nông sản phổ biến. \nKết nối trực tiếp với người nông dân. \nGhi cap mới vào đây";
  String content2 =
      "Bạn có thể thu mua các sản phẩm nông nghiệp tốt từ các người nông dân nhỏ lẻ của chúng tôi. \nNhận thông báo về tình hình giá cả thị trường của một số loại nông sản phổ biến. \nKết nối trực tiếp với người nông dân. \nĐặt hàng sớm trước mùa vụ";
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Scaffold(
      body: SafeArea(
        child: Stack(
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
            _bodySignIn(size),
          ],
        ),
      ),
    );
  }

  Widget _bodySignIn(Size size) {
    return Container(
      height: size.height,
      padding: const EdgeInsets.symmetric(horizontal: 20.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text(
            "WREF - An toàn Thịnh vượng",
            style: TextStyle(color: Colors.grey, fontSize: 12),
          ),
          SizedBox(
            height: 2,
          ),
          Text(
            "Đăng Ký - Bước 2",
            style: TextStyle(
                color: signInColor, fontSize: 26, fontWeight: FontWeight.bold),
          ),
          SizedBox(
            height: 30,
          ),
          _dropdownButton(),
          SizedBox(
            height: 20,
          ),
          _checkUserType(),
          SizedBox(
            height: 30,
          ),
          Row(
            children: [
              Expanded(
                flex: 1,
                child: _btnSignIn(),
              ),
              SizedBox(
                width: 10,
              ),
              Expanded(
                flex: 1,
                child: _btnSignUp(),
              )
            ],
          )
        ],
      ),
    );
  }

  _checkUserType() {
    if (userType == AGENT)
      return _description(title1, subTitle1, content1);
    else
      return _description(title2, subTitle2, content2);
  }

  _dropdownButton() {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 4),
      decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(4),
          border: Border.all(color: signInColor, width: 2)),
      child: DropdownButton(
        underline: SizedBox(),
        items: item.map(biuldMenuItem).toList(),
        value: valueChoose,
        onChanged: (value) => setState(() {
          if (value == item[0])
            userType = 1;
          else
            userType = 2;
          valueChoose = value.toString();
        }),
        isExpanded: true,
        icon: Icon(
          FontAwesomeIcons.sortDown,
          color: signInColor,
          size: 36,
        ),
      ),
    );
  }

  _description(String title, String subTitle, String content) {
    return Container(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            title,
            style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
          ),
          SizedBox(
            height: 10,
          ),
          Text(
            subTitle,
            style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
          ),
          SizedBox(
            height: 10,
          ),
          SizedBox(
            height: 4,
            width: 100,
            child: Divider(
              color: Colors.black,
            ),
          ),
          SizedBox(
            height: 10,
          ),
          Text(
            content,
            style: TextStyle(
                fontSize: 14,
                fontWeight: FontWeight.bold,
                color: Color(0xFF818181)),
          ),
        ],
      ),
    );
  }

  DropdownMenuItem<String> biuldMenuItem(String item) => DropdownMenuItem(
        value: item,
        child: Text(
          item,
          style: TextStyle(
            fontSize: 14,
          ),
        ),
      );

  ElevatedButton _btnSignIn() {
    return ElevatedButton(
      onPressed: () {
        _signIn();
      },
      style: ElevatedButton.styleFrom(
        padding: const EdgeInsets.symmetric(horizontal: 0, vertical: 20),
        alignment: Alignment.center,
        primary: signInColor,
        shadowColor: Colors.transparent,
      ),
      child: const Text(
        "Hoàn thành",
        textAlign: TextAlign.center,
        style: TextStyle(fontWeight: FontWeight.bold),
      ),
    );
  }

  ElevatedButton _btnSignUp() {
    return ElevatedButton(
      onPressed: () {
        Navigator.of(context).pushAndRemoveUntil(
            MaterialPageRoute(builder: (BuildContext context) => SignIn()),
            (route) => false);
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
        "Đăng nhập",
        textAlign: TextAlign.center,
        style: TextStyle(fontWeight: FontWeight.bold, color: signInColor),
      ),
    );
  }

  _showDialog() {
    return showDialog(
      context: context,
      builder: (BuildContext context) {
        return Expanded(
          child: Loading(),
        );
      },
    );
  }

  _signIn() async {
    _showDialog();
    AuthApi api = new AuthApi();
    bool signIn = await api.signUp(widget.userName, widget.address,
        widget.phone, widget.password, userType);
    print(signIn);
    if (!signIn)
      return await Future.delayed(const Duration(seconds: 2), () {
        Navigator.pop(context);
        CommomComponents.showToast("Đăng ký thất bại");
      });
    await Future.delayed(const Duration(seconds: 2), () {
      Navigator.pop(context);
      CommomComponents.showToast("Đăng ký thành công");
    });

    return Navigator.of(context).pushAndRemoveUntil(
        MaterialPageRoute(builder: (BuildContext context) => SignIn()),
        (route) => false);
  }
}
