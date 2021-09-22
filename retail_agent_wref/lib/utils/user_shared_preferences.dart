import 'package:retail_agent_werf/models/user.model/user.model.dart';
import 'package:shared_preferences/shared_preferences.dart';

class UserSharedPreferences {
  static SharedPreferences? _sharedPreferences;
  static const String ID = "id";
  static const String USER_NAME = "username";
  static const String EMAIL = "email";
  static const String AVATA = "avata";
  static const String TOKEN = "token";

  static Future init() async {
    _sharedPreferences = await SharedPreferences.getInstance();
  }

  static Future saveData(UserModel userModel) async {
    print(userModel);
    await _sharedPreferences?.setString(ID, userModel.id);
    await _sharedPreferences?.setString(USER_NAME, userModel.userName);
    await _sharedPreferences?.setString(EMAIL, "${userModel.email}");
    await _sharedPreferences?.setString(AVATA, "${userModel.avata}");
    await _sharedPreferences?.setString(TOKEN, "${userModel.token}");
  }

  static String getId() {
    return _sharedPreferences?.getString(ID) ?? "";
  }

  static String getUserName() {
    return _sharedPreferences?.getString(USER_NAME) ?? "";
  }

  static String getEmail() {
    return _sharedPreferences?.getString(EMAIL) ?? "";
  }

  static String getAvata() {
    return _sharedPreferences?.getString(AVATA) ?? "";
  }

  static String getToken() {
    return _sharedPreferences?.getString(TOKEN) ?? "";
  }

  static clear() {
    return _sharedPreferences?.clear();
  }
}
