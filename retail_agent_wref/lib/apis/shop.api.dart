import 'dart:convert';

import 'package:retail_agent_werf/models/base_response.model/base_list_response.model.dart';
import 'package:retail_agent_werf/models/shop.model/shop.model.dart';
import 'package:retail_agent_werf/utils/base_url.dart';
import 'package:retail_agent_werf/utils/user_shared_preferences.dart';
import 'package:http/http.dart' as http;

class ShopApi {
  Future<List<ShopModel>> getShopByIdUser() async {
    try {
      await UserSharedPreferences.init();
      String token = UserSharedPreferences.getToken();
      String idUser = UserSharedPreferences.getId();
      var url = Uri.parse("$BASE_URL/$idUser/shop");
      var res = await http.get(url, headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': 'Bearer $token'
      });
      if (res.statusCode == 200) {
        final BaseListResponseModel baseListResponseModel =
            BaseListResponseModel<ShopModel>.fromJson(jsonDecode(res.body),
                (data) => data.map((e) => ShopModel.fromJson(e)).toList());
        List<ShopModel> shops = baseListResponseModel.data as List<ShopModel>;
        return shops;
      } else {
        return [];
      }
    } catch (e) {
      print("lỗi: $e");
      return [];
    }
  }
}
