class BaseResponseModel<T> {
  bool success;
  String message;
  T? data;

  BaseResponseModel({
    required this.success,
    required this.message,
    this.data,
  });

  factory BaseResponseModel.fromJson(
      Map<String, dynamic> json, Function(Map<String, dynamic>) fromJson) {
    return BaseResponseModel<T>(
      success: json["success"],
      message: json["message"],
      data: fromJson(json["data"]),
    );
  }

  factory BaseResponseModel.fromJsonNotData(Map<String, dynamic> json) {
    return BaseResponseModel<T>(
      success: json["success"],
      message: json["message"],
    );
  }
}
