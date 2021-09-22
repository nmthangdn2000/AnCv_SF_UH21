import 'dart:convert';

import 'package:retail_agent_werf/apis/base.api.dart';
import 'package:retail_agent_werf/models/base_response.model/base_list_response.model.dart';
import 'package:retail_agent_werf/models/base_response.model/base_response.model.dart';
import 'package:retail_agent_werf/models/order.model/order.model.dart';
import 'package:retail_agent_werf/utils/base_url.dart';
import 'package:retail_agent_werf/utils/constants.dart';
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

  Future<OrderModel> getOrderById(id) async {
    await UserSharedPreferences.init();
    String token = UserSharedPreferences.getToken();
    var url = Uri.parse("$BASE_URL/order/$id");
    var res = await http.get(url, headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': 'Bearer $token'
    });
    if (res.statusCode == 200) {
      print(res.body);
      final BaseResponseModel baseResponseModel =
          BaseResponseModel<OrderModel>.fromJson(
              jsonDecode(res.body), (data) => OrderModel.fromJson(data));
      print(baseResponseModel.data);
      return baseResponseModel.data;
    } else {
      return throw Exception(CanNotGetData);
    }
  }

  Future<bool> updateOrderStatus(String id, int status) async {
    await UserSharedPreferences.init();
    String token = UserSharedPreferences.getToken();
    var url = Uri.parse("$BASE_URL/order/$id/status");
    Map body = {"status": status.toString()};
    BaseApi api = new BaseApi();
    var res = await api.httpPutHelperWithToken(url, token, body);
    if (res.statusCode == 200) {
      final BaseResponseModel baseResponseModel =
          BaseResponseModel.fromJsonNotData(jsonDecode(res.body));
      if (baseResponseModel.success)
        return true;
      else
        return false;
    } else {
      return false;
    }
  }
}
