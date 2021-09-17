// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'order.model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

OrderModel _$OrderModelFromJson(Map<String, dynamic> json) {
  return OrderModel(
    id: json['_id'] as String,
    idProduct: ProductModel.fromJson(json['idProduct'] as Map<String, dynamic>),
    idUser: UserModel.fromJson(json['idUser'] as Map<String, dynamic>),
    amount: json['amount'] as int,
    totalPrice: json['totalPrice'] as int,
    status: json['status'] as int,
    deliveryTo: json['deliveryTo'] as String,
    statusShip: json['statusShip'] as int,
    createAt: json['create_at'] as int,
  );
}

Map<String, dynamic> _$OrderModelToJson(OrderModel instance) =>
    <String, dynamic>{
      '_id': instance.id,
      'amount': instance.amount,
      'idProduct': instance.idProduct,
      'idUser': instance.idUser,
      'totalPrice': instance.totalPrice,
      'status': instance.status,
      'statusShip': instance.statusShip,
      'deliveryTo': instance.deliveryTo,
      'create_at': instance.createAt,
    };
