import 'package:json_annotation/json_annotation.dart';

part 'shop.model.g.dart';

@JsonSerializable()
class ShopModel {
  @JsonKey(name: '_id')
  final String id;
  final String idUser;
  final String name;
  final String description;
  final String city;
  final String district;
  final String subDistrict;
  final String address;
  final String media;
  @JsonKey(name: 'create_at')
  final int createAt;
  @JsonKey(name: 'update_at')
  final int updateAt;
  ShopModel(
      {required this.id,
      required this.idUser,
      required this.name,
      required this.description,
      required this.city,
      required this.district,
      required this.subDistrict,
      required this.address,
      required this.media,
      required this.createAt,
      required this.updateAt});
  factory ShopModel.fromJson(Map<String, dynamic> json) =>
      _$ShopModelFromJson(json);
  Map<String, dynamic> toJson() => _$ShopModelToJson(this);
}
