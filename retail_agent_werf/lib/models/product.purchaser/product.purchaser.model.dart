import 'package:json_annotation/json_annotation.dart';

part 'product.purchaser.model.g.dart';

@JsonSerializable()
class ProductModelPurchaser {
  final String id;
  final String unitPrice;
  final String residual;
  final String? note; //0 hủy, 1 đang chờ, 2 đang chuyển, 3 đã nhận
  final String img;
  ProductModelPurchaser({
    required this.id,
    required this.unitPrice,
    required this.residual,
    this.note,
    required this.img,
  });
  factory ProductModelPurchaser.fromJson(Map<String, dynamic> json) =>
      _$ProductModelPurchaserFromJson(json);
  Map<String, dynamic> toJson() => _$ProductModelPurchaserToJson(this);
}
