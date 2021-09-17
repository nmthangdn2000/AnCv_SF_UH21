import 'package:json_annotation/json_annotation.dart';

part 'user.model.g.dart';

@JsonSerializable()
class UserModel {
  @JsonKey(name: '_id')
  final String id;
  final String userName;
  final String? email;
  final String? avata;
  final String? firstName;
  final String? lastName;
  final String? token;
  UserModel({
    required this.id,
    required this.userName,
    required this.email,
    required this.avata,
    this.firstName,
    this.lastName,
    required this.token,
  });
  factory UserModel.fromJson(Map<String, dynamic> json) =>
      _$UserModelFromJson(json);
  Map<String, dynamic> toJson() => _$UserModelToJson(this);
}
