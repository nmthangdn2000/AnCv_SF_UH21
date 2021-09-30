import 'package:json_annotation/json_annotation.dart';

part 'productLot.purchaser.model.g.dart';

@JsonSerializable()
class ProductLotModelPurchaser {
  final String id;
  final String name;
  final String note;
  final String img;
  ProductLotModelPurchaser({
    required this.id,
    required this.name,
    required this.note,
    required this.img,
  });
  factory ProductLotModelPurchaser.fromJson(Map<String, dynamic> json) =>
      _$ProductLotModelPurchaserFromJson(json);
  Map<String, dynamic> toJson() => _$ProductLotModelPurchaserToJson(this);
}
