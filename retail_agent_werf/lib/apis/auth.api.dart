import 'dart:convert';

import 'package:retail_agent_werf/models/base_response.model/base_response.model.dart';
import 'package:retail_agent_werf/models/user.model/user.model.dart';
import 'package:retail_agent_werf/utils/base_url.dart';
import 'package:http/http.dart' as http;
import 'package:retail_agent_werf/utils/user_shared_preferences.dart';

class AuthApi {
  Future<bool> signIn(String email, password) async {
    try {
      Map body = {"email": email, "password": password};
      print(body);
      var url = Uri.parse("$BASE_URL/signin");
      var res = await http.post(url, body: body);
      if (res.statusCode == 200) {
        print(res.body);
        final BaseResponseModel baseRessponseModel =
            BaseResponseModel<UserModel>.fromJson(
                jsonDecode(res.body), (data) => UserModel.fromJson(data));
        await UserSharedPreferences.init();
        await UserSharedPreferences.saveData(baseRessponseModel.data);
        return true;
      } else {
        return false;
      }
    } catch (e) {
      print(e);
      return false;
    }
  }

  Future<bool> signUp(String username, email, password) async {
    try {
      Map body = {"username": username, "email": email, "password": password};
      var url = Uri.parse("$BASE_URL/signup");
      var res = await http.post(url, body: body);
      if (res.statusCode == 200) {
        return true;
      } else {
        return false;
      }
    } catch (e) {
      print(e);
      return false;
    }
  }

  Future<bool> auth() async {
    try {
      await UserSharedPreferences.init();
      String token = UserSharedPreferences.getToken();
      var url = Uri.parse("$BASE_URL/signup");
      var res = await http.get(url, headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': 'Bearer $token'
      });
      if (res.statusCode == 200) {
        return true;
      } else {
        return false;
      }
    } catch (e) {
      print(e);
      return false;
    }
  }
}
