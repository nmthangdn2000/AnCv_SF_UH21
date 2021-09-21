import 'dart:convert';

import 'package:http/http.dart' as http;

class BaseApi {
  Future httpGetHelper(Uri url, String token) async {
    var res = await http.get(url, headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': 'Bearer $token'
    });
    return res;
  }

  Future<http.Response> httpPostHelperWithToken(
      Uri url, String token, Map body) async {
    var res = await http.post(url,
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
          'Accept': 'application/json',
          'Authorization': 'Bearer $token'
        },
        body: jsonEncode(body));
    return res;
  }

  Future<http.Response> httpPutHelperWithToken(
      Uri url, String token, Map body) async {
    var res = await http.put(url,
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
          'Accept': 'application/json',
          'Authorization': 'Bearer $token'
        },
        body: body);
    return res;
  }
}
