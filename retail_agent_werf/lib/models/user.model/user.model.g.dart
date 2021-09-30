// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'user.model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

UserModel _$UserModelFromJson(Map<String, dynamic> json) {
  return UserModel(
    id: json['_id'] as String,
    userName: json['userName'] as String,
    email: json['email'] as String?,
    userType: json['userType'] as int?,
    address: json['address'] as String?,
    avata: json['avata'] as String?,
    firstName: json['firstName'] as String?,
    lastName: json['lastName'] as String?,
    token: json['token'] as String?,
  );
}

Map<String, dynamic> _$UserModelToJson(UserModel instance) => <String, dynamic>{
      '_id': instance.id,
      'userName': instance.userName,
      'address': instance.address,
      'userType': instance.userType,
      'email': instance.email,
      'avata': instance.avata,
      'firstName': instance.firstName,
      'lastName': instance.lastName,
      'token': instance.token,
    };
