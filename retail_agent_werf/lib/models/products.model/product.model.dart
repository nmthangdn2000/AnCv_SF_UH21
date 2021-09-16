import 'package:json_annotation/json_annotation.dart';

part 'product.model.g.dart';

@JsonSerializable()
class ProductModel {
  final String id;
  final String name;
  final String price;
  final String company;
  final String saleOff;
  final String img;
  final bool type;
  final String ingredient;
  final String effect;
  final String userManual;
  final String note;
  ProductModel(
      {required this.id,
      required this.name,
      required this.price,
      required this.company,
      required this.saleOff,
      required this.img,
      required this.type,
       required this.ingredient,
      required this.effect,
      required this.userManual,
      required this.note});
  factory ProductModel.fromJson(Map<String, dynamic> json) =>
      _$ProductModelFromJson(json);
  Map<String, dynamic> toJson() => _$ProductModelToJson(this);
}
