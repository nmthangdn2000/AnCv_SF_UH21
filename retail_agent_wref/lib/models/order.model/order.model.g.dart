// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'order.model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

OrderModel _$OrderModelFromJson(Map<String, dynamic> json) {
  return OrderModel(
    id: json['id'] as String,
    name: json['name'] as String,
    amount: json['amount'] as int,
    totalPrice: json['totalPrice'] as String,
    date: json['date'] as String,
    status: json['status'] as int,
    statusShip: json['statusShip'] as int,
    avata: json['avata'] as String,
  );
}

Map<String, dynamic> _$OrderModelToJson(OrderModel instance) =>
    <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'amount': instance.amount,
      'totalPrice': instance.totalPrice,
      'date': instance.date,
      'status': instance.status,
      'statusShip': instance.statusShip,
      'avata': instance.avata,
    };
