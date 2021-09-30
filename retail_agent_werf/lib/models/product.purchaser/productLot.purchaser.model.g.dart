// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'productLot.purchaser.model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ProductLotModelPurchaser _$ProductLotModelPurchaserFromJson(
    Map<String, dynamic> json) {
  return ProductLotModelPurchaser(
    id: json['id'] as String,
    name: json['name'] as String,
    note: json['note'] as String,
    img: json['img'] as String,
  );
}

Map<String, dynamic> _$ProductLotModelPurchaserToJson(
        ProductLotModelPurchaser instance) =>
    <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'note': instance.note,
      'img': instance.img,
    };
