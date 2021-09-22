import 'package:json_annotation/json_annotation.dart';

part 'product.model.g.dart';

@JsonSerializable()
class ProductModel {
  @JsonKey(name: '_id')
  final String id;
  final String name;
  final int price;
  final String media;
  final String? company;
  final String? saleOff;
  final bool? type;
  final String? ingredient;
  final String? effect;
  final String? userManual;
  final String? note;
  ProductModel(
      {required this.id,
      required this.name,
      required this.price,
      required this.media,
      this.company,
      this.saleOff,
      required this.type,
      this.ingredient,
      this.effect,
      this.userManual,
      this.note});
  factory ProductModel.fromJson(Map<String, dynamic> json) =>
      _$ProductModelFromJson(json);
  Map<String, dynamic> toJson() => _$ProductModelToJson(this);
}
