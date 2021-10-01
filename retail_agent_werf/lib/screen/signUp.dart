import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:retail_agent_werf/apis/auth.api.dart';
import 'package:retail_agent_werf/components/commom/common.dart';
import 'package:retail_agent_werf/screen/signIn.dart';
import 'package:retail_agent_werf/screen/signUp2.dart';
import 'package:retail_agent_werf/utils/Constants.dart';

class SignUp extends StatefulWidget {
  const SignUp({Key? key}) : super(key: key);

  @override
  _SignUpState createState() => _SignUpState();
}

class _SignUpState extends State<SignUp> {
  bool _isHiddenPassword = true;
  TextEditingController phoneController = new TextEditingController();
  TextEditingController passwordController = new TextEditingController();
  TextEditingController userController = new TextEditingController();
  TextEditingController addressController = new TextEditingController();
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
            "Đăng Ký - Bước 1",
            style: TextStyle(
                color: signInColor, fontSize: 26, fontWeight: FontWeight.bold),
          ),
          SizedBox(
            height: 30,
          ),
          _inputUserName(),
          SizedBox(
            height: 10,
          ),
          _inputAddress(),
          SizedBox(
            height: 10,
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

  TextField _inputUserName() {
    return TextField(
      controller: userController,
      keyboardType: TextInputType.text,
      decoration: InputDecoration(
          errorText: _validatePhone ? 'Tên không thể trống' : null,
          enabledBorder: OutlineInputBorder(
            borderSide: BorderSide(color: signInColor),
          ),
          focusedBorder: OutlineInputBorder(
            borderSide: BorderSide(color: signInColor),
          ),
          prefixIcon: Icon(
            FontAwesomeIcons.solidUser,
            color: signInColor,
          ),
          hintText: 'Nhập tên của bạn',
          labelText: 'Họ & tên',
          labelStyle: TextStyle(color: signInColor)),
      obscureText: false,
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
          labelText: 'Số điện thoại',
          labelStyle: TextStyle(color: signInColor)),
      obscureText: false,
    );
  }

  TextField _inputAddress() {
    return TextField(
      controller: addressController,
      keyboardType: TextInputType.text,
      decoration: InputDecoration(
          errorText: _validatePhone ? 'Địa chỉ không thể trống' : null,
          enabledBorder: OutlineInputBorder(
            borderSide: BorderSide(color: signInColor),
          ),
          focusedBorder: OutlineInputBorder(
            borderSide: BorderSide(color: signInColor),
          ),
          prefixIcon: Icon(
            FontAwesomeIcons.mapMarkerAlt,
            color: signInColor,
          ),
          hintText: 'Nhập địa chỉ',
          labelText: 'Địa chỉ',
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
          labelText: 'Mật khẩu',
          labelStyle: TextStyle(color: signInColor)),
      obscureText: _isHiddenPassword,
    );
  }

  ElevatedButton _btnSignIn() {
    return ElevatedButton(
      onPressed: () {
        _validateSignIn(userController.text, phoneController.text,
            addressController.text, passwordController.text);
      },
      style: ElevatedButton.styleFrom(
        padding: const EdgeInsets.symmetric(horizontal: 0, vertical: 20),
        alignment: Alignment.center,
        primary: signInColor,
        shadowColor: Colors.transparent,
      ),
      child: const Text(
        "Tiếp theo",
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
        ));
  }

  _validateSignIn(
      String username, String phone, String address, String password) {
    if (username.isNotEmpty &&
        phone.isNotEmpty &&
        address.isNotEmpty &&
        password.isNotEmpty) {
      return Navigator.of(context).pushAndRemoveUntil(
          MaterialPageRoute(
              builder: (BuildContext context) => SignUp2(
                    userName: username,
                    address: address,
                    phone: phone,
                    password: password,
                  )),
          (route) => false);
    }
    return CommomComponents.showToast("Bạn phải nhập đủ thông tin");
  }
}
