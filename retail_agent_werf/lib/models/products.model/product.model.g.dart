// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'product.model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ProductModel _$ProductModelFromJson(Map<String, dynamic> json) {
  return ProductModel(
    id: json['_id'] as String,
    name: json['name'] as String,
    price: json['price'] as int,
    media: json['media'] as String,
    company: json['company'] as String?,
    saleOff: json['saleOff'] as String?,
    type: json['type'] as bool?,
    ingredient: json['ingredient'] as String?,
    effect: json['effect'] as String?,
    userManual: json['userManual'] as String?,
    note: json['note'] as String?,
  );
}

Map<String, dynamic> _$ProductModelToJson(ProductModel instance) =>
    <String, dynamic>{
      '_id': instance.id,
      'name': instance.name,
      'price': instance.price,
      'media': instance.media,
      'company': instance.company,
      'saleOff': instance.saleOff,
      'type': instance.type,
      'ingredient': instance.ingredient,
      'effect': instance.effect,
      'userManual': instance.userManual,
      'note': instance.note,
    };
