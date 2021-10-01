import 'dart:async';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/painting.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:intl/intl.dart';
import 'package:retail_agent_werf/apis/order.api.dart';
import 'package:retail_agent_werf/components/commom/common.dart';
import 'package:retail_agent_werf/components/loader/loading.dart';
import 'package:retail_agent_werf/models/order.model/order.model.dart';
import 'package:retail_agent_werf/utils/base_url.dart';
import 'package:retail_agent_werf/utils/constants.dart';

class OrderDetail extends StatefulWidget {
  const OrderDetail({Key? key, required this.id}) : super(key: key);
  final String id;

  @override
  State<OrderDetail> createState() => _OrderDetailState();
}

class _OrderDetailState extends State<OrderDetail> {
  late Future futureOrder;
  final GlobalKey<ScaffoldState> _scaffoldKey = GlobalKey<ScaffoldState>();
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    futureOrder = getOrder(widget.id);
    // _loadingDialog();
  }

  _loadingDialog() async {
    showDialog(
      context: context,
      builder: (BuildContext context) => Loading(),
    );
  }

  @override
  Widget build(BuildContext context) {
    final oCcy = new NumberFormat("#,##0", "vi_VND");
    Size size = MediaQuery.of(context).size;
    return Scaffold(
      key: _scaffoldKey,
      appBar: AppBar(
        iconTheme: IconThemeData(color: Colors.black),
        backgroundColor: Color(0xFFFAFAFA),
        elevation: 0,
        centerTitle: true,
        title: Text(
          "Chi tiết đơn hàng",
          style: TextStyle(
              fontSize: 20, fontWeight: FontWeight.bold, color: Colors.black),
        ),
      ),
      body: FutureBuilder(
          future: futureOrder,
          builder: (context, data) {
            if (data.hasError) {
              return Center(
                child: Text("${data.error}"),
              );
            } else if (data.hasData) {
              OrderModel order = data.data as OrderModel;
              if (order.id.isNotEmpty)
                return Container(
                  color: Color(0xFFF2F3F5),
                  child: Column(
                    children: [
                      _statusOrder(order.status),
                      SizedBox(
                        height: 20,
                      ),
                      _address(order),
                      SizedBox(
                        height: 20,
                      ),
                      _product(order, size, oCcy),
                      SizedBox(
                        height: 20,
                      ),
                      _button(order, context),
                    ],
                  ),
                );
              else
                return Center(
                  child: Text("Không có đơn hàng nào"),
                );
            } else
              return Center(
                child: Loading(),
              );
          }),
    );
  }

  Widget _product(OrderModel order, Size size, NumberFormat oCcy) {
    return Container(
      padding: const EdgeInsets.all(20),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(4),
      ),
      child: Column(
        children: [
          Row(
            children: [
              Expanded(
                flex: 2,
                child: Container(
                  margin: const EdgeInsets.only(right: 10),
                  child: Image.network(
                    Uri.tryParse(order.idProduct.media)?.hasAbsolutePath ??
                            false
                        ? order.idProduct.media
                        : "$BASE_URL_MEDIA/uploads/${order.idProduct.media}",
                    fit: BoxFit.cover,
                  ),
                ),
              ),
              Expanded(
                flex: 6,
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      order.idProduct.name,
                      style:
                          TextStyle(fontSize: 14, fontWeight: FontWeight.bold),
                    ),
                    SizedBox(
                      height: 10,
                    ),
                    Text(
                      "${order.idProduct.effect}",
                      style: TextStyle(
                        fontSize: 12,
                      ),
                      maxLines: 3,
                      overflow: TextOverflow.ellipsis,
                    ),
                  ],
                ),
              ),
            ],
          ),
          Row(
            children: [
              Expanded(child: Divider()),
            ],
          ),
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 8.0),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text("Số lương: ${order.amount}"),
                Text("${oCcy.format(order.idProduct.price)} ₫"),
              ],
            ),
          ),
          Row(
            children: [
              Expanded(child: Divider()),
            ],
          ),
          SizedBox(
            height: 10,
          ),
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 8.0),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                Text(
                  "Tổng tiền",
                  style: TextStyle(
                    fontSize: 14,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                SizedBox(
                  width: 20,
                ),
                Text(
                  "${oCcy.format(order.totalPrice)} ₫",
                  style: TextStyle(
                      fontSize: 14,
                      fontWeight: FontWeight.bold,
                      color: Colors.red),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _statusOrder(int status) {
    return Container(
      padding: const EdgeInsets.all(20),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(4),
      ),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [Text("Trạng thái đơn hàng: ${_textStatus(status)}")],
      ),
    );
  }

  Widget _address(OrderModel order) {
    return Container(
      padding: const EdgeInsets.all(20),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(4),
      ),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Expanded(
            flex: 1,
            child: Icon(
              FontAwesomeIcons.mapMarkerAlt,
              color: Colors.red,
            ),
          ),
          Expanded(
            flex: 8,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  "Địa chỉ giao hàng",
                  style: TextStyle(
                    fontSize: 14,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                SizedBox(
                  height: 10,
                ),
                Text(
                  "${order.idUser.userName}",
                  style: TextStyle(
                    fontSize: 12,
                  ),
                ),
                SizedBox(
                  height: 5,
                ),
                Text(
                  "(+84) 905646804",
                  style: TextStyle(
                    fontSize: 12,
                  ),
                ),
                SizedBox(
                  height: 5,
                ),
                Text(
                  "${order.deliveryTo}",
                  style: TextStyle(
                    fontSize: 12,
                  ),
                ),
                SizedBox(
                  height: 5,
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _button(OrderModel order, BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(20),
      child: Row(
        children: [
          _btnCancle(order, context),
          SizedBox(
            width: 20,
          ),
          _btnSubmit(order, context),
        ],
      ),
    );
  }

  Widget _btnCancle(OrderModel order, BuildContext contextParrent) {
    if (order.status == DONE || order.status == CANCEL)
      return SizedBox.shrink();
    return Expanded(
      flex: 1,
      child: ElevatedButton(
        onPressed: () {
          showDialog(
            context: context,
            builder: (context) => AlertDialog(
              content: Text("Bạn có chắc muốn hủy không?"),
              actions: [
                TextButton(
                  onPressed: () {
                    Navigator.pop(context);
                  },
                  child: Text("Không"),
                ),
                TextButton(
                  onPressed: () async {
                    bool update = await updateOrderStatus(CANCEL);
                    Navigator.pop(context);
                    if (!update) {
                      Fluttertoast.showToast(
                          msg: "Cập nhật thất bại",
                          toastLength: Toast.LENGTH_SHORT,
                          gravity: ToastGravity.CENTER,
                          timeInSecForIosWeb: 1,
                          backgroundColor: Colors.red,
                          textColor: Colors.white,
                          fontSize: 16.0);
                    } else {
                      CommomComponents.showDialogLoading(contextParrent);
                      Timer(Duration(seconds: 2), () {
                        Navigator.of(contextParrent).pop();
                        CommomComponents.showBottomSheet(
                            contextParrent,
                            "Hủy đơn hàng thành công",
                            () => Navigator.pop(contextParrent));
                      });
                    }
                  },
                  child: Text("Đồng ý"),
                ),
              ],
            ),
          );
        },
        style: ElevatedButton.styleFrom(
          padding: const EdgeInsets.symmetric(horizontal: 0, vertical: 20),
          alignment: Alignment.center,
          primary: Colors.white,
          shadowColor: Colors.transparent,
          onPrimary: Colors.red,
          shape: RoundedRectangleBorder(
            side: BorderSide(color: Colors.red),
            borderRadius: BorderRadius.circular(4),
          ),
        ),
        child: const Text(
          "Hủy",
          textAlign: TextAlign.center,
          style: TextStyle(
            fontWeight: FontWeight.bold,
            color: Colors.red,
          ),
        ),
      ),
    );
  }

  Widget _btnSubmit(OrderModel order, BuildContext contextParrent) {
    if (order.status == DONE || order.status == CANCEL)
      return SizedBox.shrink();
    return Expanded(
      flex: 1,
      child: ElevatedButton(
        onPressed: () {
          showDialog(
            context: context,
            builder: (context) => AlertDialog(
              content: _textNotifi(order.status),
              actions: [
                TextButton(
                  onPressed: () {
                    Navigator.pop(context);
                  },
                  child: Text("Không"),
                ),
                TextButton(
                  onPressed: () async {
                    bool update = await updateOrderStatus(order.status + 1);
                    Navigator.pop(context);
                    if (!update) {
                      Fluttertoast.showToast(
                          msg: "Cập nhật ${update ? "thành công" : "thất bại"}",
                          toastLength: Toast.LENGTH_SHORT,
                          gravity: ToastGravity.CENTER,
                          timeInSecForIosWeb: 1,
                          backgroundColor: Colors.red,
                          textColor: Colors.white,
                          fontSize: 16.0);
                    } else {
                      CommomComponents.showDialogLoading(contextParrent);
                      Timer(Duration(seconds: 2), () {
                        Navigator.of(contextParrent).pop();
                        CommomComponents.showBottomSheet(
                            contextParrent,
                            "Cập nhật đơn hàng thành công",
                            () => Navigator.pop(contextParrent));
                      });
                    }
                  },
                  child: Text("Đồng ý"),
                ),
              ],
            ),
          );
        },
        style: ElevatedButton.styleFrom(
          padding: const EdgeInsets.symmetric(horizontal: 0, vertical: 20),
          alignment: Alignment.center,
          primary: Colors.red,
          shadowColor: Colors.transparent,
          shape: RoundedRectangleBorder(
            side: BorderSide(color: Colors.red),
            borderRadius: BorderRadius.circular(4),
          ),
        ),
        child: const Text(
          "Xác nhận",
          textAlign: TextAlign.center,
          style: TextStyle(
            fontWeight: FontWeight.bold,
            color: Colors.white,
          ),
        ),
      ),
    );
  }

  Future<OrderModel> getOrder(String id) async {
    try {
      OrderApi api = new OrderApi();
      var data = await api.getOrderById(id);
      return data;
    } catch (e) {
      return Future.error(e);
    }
  }

  Future<bool> updateOrderStatus(int status) async {
    try {
      OrderApi api = new OrderApi();
      print("status $status");
      bool update = await api.updateOrderStatus(widget.id, status);
      return update;
    } catch (e) {
      print("ko ok $e");
      return false;
    }
  }

  Text _textNotifi(int status) {
    switch (status) {
      case PENDDING:
        return Text("Xác nhận đơn hàng.");
      case CONFIM:
        return Text("Bắt đầu giao hàng.");
      case TRANSPORT:
        return Text("Giao hàng thành công");
      default:
        return Text("Xác nhận đơn hàng.");
    }
  }

  String _textStatus(int status) {
    switch (status) {
      case PENDDING:
        return "Chờ xác nhận";
      case CONFIM:
        return "Đã xác nhận";
      case TRANSPORT:
        return "Đang giao hàng";
      case DONE:
        return "Hoàn tất";
      case CANCEL:
        return "Đã hủy";
      default:
        return "";
    }
  }
}
