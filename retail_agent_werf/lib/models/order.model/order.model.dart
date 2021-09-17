import 'package:json_annotation/json_annotation.dart';
import 'package:retail_agent_werf/models/products.model/product.model.dart';
import 'package:retail_agent_werf/models/user.model/user.model.dart';

part 'order.model.g.dart';

@JsonSerializable()
class OrderModel {
  @JsonKey(name: '_id')
  final String id;
  final int amount;
  final ProductModel idProduct;
  final UserModel idUser;
  final int totalPrice;
  final int status; //0 hủy, 1 đang chờ, 2 đang chuyển, 3 đã nhận
  final int statusShip;
  final String deliveryTo;
  @JsonKey(name: 'create_at')
  final int createAt;
  OrderModel({
    required this.id,
    required this.idProduct,
    required this.idUser,
    required this.amount,
    required this.totalPrice,
    required this.status,
    required this.deliveryTo,
    required this.statusShip,
    required this.createAt,
  });
  factory OrderModel.fromJson(Map<String, dynamic> json) =>
      _$OrderModelFromJson(json);
  Map<String, dynamic> toJson() => _$OrderModelToJson(this);
}
