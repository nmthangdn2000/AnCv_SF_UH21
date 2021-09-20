import 'package:flutter/material.dart';
import 'package:retail_agent_werf/models/order.model/order.model.dart';

class ItemOrder extends StatefulWidget {
  final OrderModel orderModel;
  final bool? isLastItem;
  const ItemOrder({Key? key, required this.orderModel, this.isLastItem})
      : super(key: key);

  @override
  _ItemOrderState createState() => _ItemOrderState();
}

class _ItemOrderState extends State<ItemOrder> {
  @override
  Widget build(BuildContext context) {
    return Container(
      height: 111,
      padding: const EdgeInsets.symmetric(vertical: 8, horizontal: 20),
      margin: EdgeInsets.only(
          top: 6,
          left: 20,
          right: 20,
          bottom: widget.isLastItem == true ? 20 : 6),
      decoration: BoxDecoration(
        color: _colorCard(widget.orderModel.status),
        borderRadius: BorderRadius.circular(8),
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Expanded(
            flex: 2,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  widget.orderModel.name,
                  style: TextStyle(
                    color: Colors.black,
                    fontWeight: FontWeight.bold,
                    fontSize: 16,
                  ),
                ),
                Text(
                  "Số lượng: ${widget.orderModel.amount.toString()}",
                  style: TextStyle(
                    fontSize: 12,
                    color: Color(0xFF818181),
                  ),
                ),
                Text(
                  "Tổng tiền: ${widget.orderModel.totalPrice}",
                  style: TextStyle(
                    fontSize: 12,
                    color: Color(0xFF818181),
                  ),
                ),
                Text(
                  widget.orderModel.date,
                  style: TextStyle(
                    color: _colorText(widget.orderModel.status),
                    fontSize: 10,
                  ),
                ),
              ],
            ),
          ),
          Expanded(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              children: [
                Text(
                  widget.orderModel.date,
                  style: TextStyle(
                    color: _colorText(widget.orderModel.status),
                    fontSize: 10,
                  ),
                ),
                SizedBox(
                  height: 10,
                ),
                Container(
                  decoration: BoxDecoration(
                    shape: BoxShape.circle,
                    border: Border.all(
                      color: _colorText(widget.orderModel.status),
                      width: 2.0,
                    ),
                  ),
                  child: CircleAvatar(
                    radius: 20,
                    backgroundImage: AssetImage(
                      'assets/images/${widget.orderModel.avata}',
                    ),
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Color _colorCard(status) {
    switch (status) {
      case 0:
        return Color(0xFFFFE8E3);
      case 1:
        return Color(0xFFEFFBFF);
      case 2:
        return Color(0xFFE2F3FF);
      case 3:
        return Color(0xFFE2F3FF);
      default:
        return Color(0xFFFFE8E3);
    }
  }

  Color _colorText(status) {
    switch (status) {
      case 0:
        return Color(0xFFE04422);
      case 1:
        return Color(0xFF62AEFB);
      case 2:
        return Color(0xFF2285E0);
      case 3:
        return Color(0xFF22E079);
      default:
        return Color(0xFFE04422);
    }
  }

  Text _textStatus(status) {
    switch (status) {
      case 0:
        return _textWidget("Hủy", _colorText(status));
      case 1:
        return _textWidget("Đang chờ", _colorText(status));
      case 2:
        return _textWidget("Đang chuyển", _colorText(status));
      case 3:
        return _textWidget("Đã nhận", _colorText(status));
      default:
        return _textWidget("Hủy", _colorText(status));
    }
  }

  Text _textWidget(status, color) {
    return Text(
      status,
      style: TextStyle(
        fontSize: 12,
        color: color,
      ),
    );
  }
}
