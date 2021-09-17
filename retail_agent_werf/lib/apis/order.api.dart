import 'dart:convert';

import 'package:retail_agent_werf/models/base_response.model/base_list_response.model.dart';
import 'package:retail_agent_werf/models/order.model/order.model.dart';
import 'package:retail_agent_werf/utils/base_url.dart';
import 'package:retail_agent_werf/utils/user_shared_preferences.dart';
import 'package:http/http.dart' as http;

class OrderApi {
  Future<List<OrderModel>> getOrderByIdUserBoss(int status) async {
    try {
      await UserSharedPreferences.init();
      String token = UserSharedPreferences.getToken();
      String idUser = UserSharedPreferences.getId();
      var url = Uri.parse("$BASE_URL/order?idBoss=$idUser&status=$status");
      var res = await http.get(url, headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': 'Bearer $token'
      });
      if (res.statusCode == 200) {
        final BaseListResponseModel baseListResponseModel =
            BaseListResponseModel<OrderModel>.fromJson(jsonDecode(res.body),
                (data) => data.map((e) => OrderModel.fromJson(e)).toList());
        List<OrderModel> orders =
            baseListResponseModel.data as List<OrderModel>;
        return orders;
      } else {
        return [];
      }
    } catch (e) {
      print("lỗi: $e");
      return [];
    }
  }
}
