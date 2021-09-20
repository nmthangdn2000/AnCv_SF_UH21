// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'shop.model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ShopModel _$ShopModelFromJson(Map<String, dynamic> json) {
  return ShopModel(
    id: json['_id'] as String,
    idUser: json['idUser'] as String,
    name: json['name'] as String,
    description: json['description'] as String,
    city: json['city'] as String,
    district: json['district'] as String,
    subDistrict: json['subDistrict'] as String,
    address: json['address'] as String,
    media: json['media'] as String,
    createAt: json['create_at'] as int,
    updateAt: json['update_at'] as int,
  );
}

Map<String, dynamic> _$ShopModelToJson(ShopModel instance) => <String, dynamic>{
      '_id': instance.id,
      'idUser': instance.idUser,
      'name': instance.name,
      'description': instance.description,
      'city': instance.city,
      'district': instance.district,
      'subDistrict': instance.subDistrict,
      'address': instance.address,
      'media': instance.media,
      'create_at': instance.createAt,
      'update_at': instance.updateAt,
    };
