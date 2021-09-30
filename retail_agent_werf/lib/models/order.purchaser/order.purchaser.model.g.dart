// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'order.purchaser.model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

OrderModelPurchaser _$OrderModelPurchaserFromJson(Map<String, dynamic> json) {
  return OrderModelPurchaser(
    id: json['id'] as String,
    name: json['name'] as String,
    amount: json['amount'] as int,
    totalPrice: json['totalPrice'] as String,
    status: json['status'] as int,
    date: json['date'] as String,
    img: json['img'] as String,
  );
}

Map<String, dynamic> _$OrderModelPurchaserToJson(
        OrderModelPurchaser instance) =>
    <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'amount': instance.amount,
      'totalPrice': instance.totalPrice,
      'status': instance.status,
      'date': instance.date,
      'img': instance.img,
    };
