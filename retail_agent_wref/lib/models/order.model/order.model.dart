import 'package:json_annotation/json_annotation.dart';

part 'order.model.g.dart';

@JsonSerializable()
class OrderModel {
  final String id;
  final String name;
  final int amount;
  final String totalPrice;
  final String date;
  final int status; //0 hủy, 1 đang chờ, 2 đang chuyển, 3 đã nhận
  final int statusShip;
  final String avata;
  OrderModel(
      {required this.id,
      required this.name,
      required this.amount,
      required this.totalPrice,
      required this.date,
      required this.status,
      required this.statusShip,
      required this.avata});
  factory OrderModel.fromJson(Map<String, dynamic> json) =>
      _$OrderModelFromJson(json);
  Map<String, dynamic> toJson() => _$OrderModelToJson(this);
}
