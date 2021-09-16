class BaseListResponseModel<T> {
  bool success;
  String message;
  List<T>? data;

  BaseListResponseModel({
    required this.success,
    required this.message,
    required this.data,
  });

  factory BaseListResponseModel.fromJson(
      Map<String, dynamic> json, Function(List<dynamic>) fromJson) {
    return BaseListResponseModel<T>(
        success: json["success"],
        message: json["message"],
        data: fromJson(json["data"]));
  }
}
