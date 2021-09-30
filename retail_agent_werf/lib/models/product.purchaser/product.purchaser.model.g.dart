// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'product.purchaser.model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ProductModelPurchaser _$ProductModelPurchaserFromJson(
    Map<String, dynamic> json) {
  return ProductModelPurchaser(
    id: json['id'] as String,
    unitPrice: json['unitPrice'] as String,
    residual: json['residual'] as String,
    note: json['note'] as String?,
    img: json['img'] as String,
  );
}

Map<String, dynamic> _$ProductModelPurchaserToJson(
        ProductModelPurchaser instance) =>
    <String, dynamic>{
      'id': instance.id,
      'unitPrice': instance.unitPrice,
      'residual': instance.residual,
      'note': instance.note,
      'img': instance.img,
    };
