import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:retail_agent_werf/models/products.model/product.model.dart';

class DetailProduct extends StatefulWidget {
  final ProductModel productModel;
  final Color color;
  const DetailProduct(
      {Key? key, required this.productModel, required this.color})
      : super(key: key);

  @override
  _DetailProductState createState() => _DetailProductState();
}

class _DetailProductState extends State<DetailProduct> {
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    print(widget.productModel.id);
  }

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Scaffold(
      body: SafeArea(
        child: SingleChildScrollView(
          child: Container(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Stack(
                  clipBehavior: Clip.none,
                  children: [
                    Container(
                      padding: const EdgeInsets.only(bottom: 60),
                      child: Hero(
                        tag: "image-${widget.productModel.id}-${widget.color}",
                        child: Image.asset(
                          "assets/images/${widget.productModel.media}",
                          width: size.width,
                        ),
                      ),
                    ),
                    Positioned(
                      top: 0,
                      left: 0,
                      right: 0,
                      child: AppBar(
                        backgroundColor: Colors.transparent,
                        elevation: 0,
                        iconTheme: IconThemeData(
                          color: Colors.black,
                        ),
                      ),
                    ),
                    Positioned(
                      bottom: 0,
                      left: 0,
                      right: 0,
                      child: Container(
                        margin: const EdgeInsets.symmetric(
                          horizontal: 40,
                        ),
                        padding: const EdgeInsets.fromLTRB(20, 20, 20, 10),
                        decoration: BoxDecoration(
                            color: Colors.white,
                            borderRadius: BorderRadius.circular(8),
                            boxShadow: [
                              BoxShadow(
                                color: Colors.grey.withOpacity(0.5),
                                spreadRadius: 5,
                                blurRadius: 7,
                                offset:
                                    Offset(0, 3), // changes position of shadow
                              ),
                            ]),
                        child: Column(
                          children: [
                            Row(
                              children: [
                                Expanded(
                                  flex: 1,
                                  child: Hero(
                                    tag:
                                        "name-${widget.productModel.id}-${widget.color}",
                                    child: Text(
                                      widget.productModel.name,
                                      maxLines: 1,
                                      overflow: TextOverflow.ellipsis,
                                      style: TextStyle(
                                        color: Colors.black,
                                        fontWeight: FontWeight.bold,
                                        fontSize: 16,
                                      ),
                                    ),
                                  ),
                                ),
                                Expanded(
                                  flex: 1,
                                  child: Hero(
                                    tag:
                                        "price-${widget.productModel.id}-${widget.color}",
                                    child: Text(
                                      "${widget.productModel.price}",
                                      textAlign: TextAlign.end,
                                      style: TextStyle(
                                        color: widget.color == Color(0xFFB2E2FE)
                                            ? Color(0xFF62AEFB)
                                            : widget.color,
                                        fontWeight: FontWeight.bold,
                                        fontSize: 16,
                                      ),
                                    ),
                                  ),
                                ),
                              ],
                            ),
                            SizedBox(
                              height: 20,
                            ),
                            Row(
                              children: [
                                Expanded(
                                  flex: 1,
                                  child: Hero(
                                    tag:
                                        "company-${widget.productModel.id}-${widget.color}",
                                    child: Text(
                                      "${widget.productModel.company}",
                                      maxLines: 1,
                                      overflow: TextOverflow.ellipsis,
                                      style: TextStyle(
                                        color: Colors.black,
                                        fontWeight: FontWeight.bold,
                                        fontSize: 14,
                                      ),
                                    ),
                                  ),
                                ),
                                Expanded(
                                  flex: 1,
                                  child: Hero(
                                    tag:
                                        "saleOff-${widget.productModel.id}-${widget.color}",
                                    child: Text(
                                      "${widget.productModel.saleOff}",
                                      textAlign: TextAlign.end,
                                      style: TextStyle(
                                        color: widget.color == Color(0xFFB2E2FE)
                                            ? Color(0xFF62AEFB)
                                            : widget.color,
                                        fontWeight: FontWeight.bold,
                                        fontSize: 14,
                                      ),
                                    ),
                                  ),
                                ),
                              ],
                            ),
                            SizedBox(
                              height: 10,
                            ),
                            Center(
                              child: ElevatedButton(
                                onPressed: () {
                                  print("sss");
                                },
                                child: Text("Đặt hàng ngay"),
                                style: ElevatedButton.styleFrom(
                                  shape: RoundedRectangleBorder(
                                    borderRadius: BorderRadius.circular(100),
                                  ),
                                  primary: Colors.green,
                                  padding: const EdgeInsets.symmetric(
                                    vertical: 10,
                                    horizontal: 50,
                                  ),
                                ),
                              ),
                            ),
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
                SizedBox(
                  height: 20,
                ),
                _inforProduct("Thành phần", widget.productModel.ingredient),
                SizedBox(
                  height: 20,
                ),
                _inforProduct("Công dụng", widget.productModel.effect),
                SizedBox(
                  height: 20,
                ),
                _inforProduct(
                    "Hướng dẫn sử dụng", widget.productModel.userManual),
                SizedBox(
                  height: 20,
                ),
                _inforProduct("Lưu ý", widget.productModel.note),
                SizedBox(
                  height: 20,
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  Container _inforProduct(title, content) {
    return Container(
      margin: const EdgeInsets.symmetric(horizontal: 20),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.start,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            title,
            textAlign: TextAlign.justify,
            style: TextStyle(
                fontSize: 14, color: Colors.black, fontWeight: FontWeight.bold),
          ),
          SizedBox(
            height: 10,
          ),
          Text(
            content,
            textAlign: TextAlign.justify,
            style: TextStyle(fontSize: 12, color: Colors.grey),
          ),
        ],
      ),
    );
  }
}
