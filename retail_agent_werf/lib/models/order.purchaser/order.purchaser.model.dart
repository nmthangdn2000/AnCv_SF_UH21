import 'package:json_annotation/json_annotation.dart';

part 'order.purchaser.model.g.dart';

@JsonSerializable()
class OrderModelPurchaser {
  final String id;
  final String name;
  final int amount;
  final String totalPrice;
  final int status; //0 hủy, 1 đang chờ, 2 đang chuyển, 3 đã nhận
  final String date;
  final String img;
  OrderModelPurchaser({
    required this.id,
    required this.name,
    required this.amount,
    required this.totalPrice,
    required this.status,
    required this.date,
    required this.img,
  });
  factory OrderModelPurchaser.fromJson(Map<String, dynamic> json) =>
      _$OrderModelPurchaserFromJson(json);
  Map<String, dynamic> toJson() => _$OrderModelPurchaserToJson(this);
}
