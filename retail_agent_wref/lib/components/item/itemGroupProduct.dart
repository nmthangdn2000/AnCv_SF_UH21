import 'package:flutter/material.dart';
import 'package:retail_agent_werf/models/products.model/product.model.dart';
import 'package:retail_agent_werf/screen/detailProduct.dart';

class ItemGroupProduct extends StatefulWidget {
  final ProductModel productModel;
  final Color color;
  const ItemGroupProduct(
      {Key? key, required this.productModel, required this.color})
      : super(key: key);

  @override
  _ItemGroupProductState createState() => _ItemGroupProductState();
}

class _ItemGroupProductState extends State<ItemGroupProduct> {
  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        print(widget.color);
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (context) => DetailProduct(
              productModel: widget.productModel,
              color: widget.color,
            ),
          ),
        );
      },
      child: Container(
        margin: const EdgeInsets.only(right: 20),
        padding: const EdgeInsets.all(8),
        decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(8), color: widget.color),
        child: Stack(
          children: [
            Column(
              children: [
                Hero(
                  tag: "image-${widget.productModel.id}-${widget.color}",
                  child: Image.asset(
                    "assets/images/" + widget.productModel.img,
                    height: 100,
                    width: 100,
                    fit: BoxFit.cover,
                  ),
                ),
                SizedBox(
                  height: 10,
                ),
                Container(
                  width: 100,
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.start,
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Hero(
                        tag: "name-${widget.productModel.id}-${widget.color}",
                        child: Text(
                          widget.productModel.name,
                          maxLines: 1,
                          overflow: TextOverflow.ellipsis,
                          style: TextStyle(
                            color: widget.color == Color(0xFFB2E2FE)
                                ? Colors.black
                                : Colors.white,
                            fontSize: 12,
                          ),
                        ),
                      ),
                      SizedBox(
                        height: 4,
                      ),
                      Hero(
                        tag: "price-${widget.productModel.id}-${widget.color}",
                        child: Text(
                          widget.productModel.price,
                          style: TextStyle(
                            color: widget.color == Color(0xFFB2E2FE)
                                ? Color(0xFF62AEFB)
                                : Colors.white,
                            fontWeight: FontWeight.bold,
                            fontSize: 12,
                          ),
                        ),
                      ),
                    ],
                  ),
                )
              ],
            ),
            Container(
              width: 35,
              height: 20,
              decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(100),
                color: Color(0xFFF6F9FB),
              ),
              child: Center(
                child: Hero(
                  tag: "saleOff-${widget.productModel.id}-${widget.color}",
                  child: Text(
                    widget.productModel.saleOff,
                    style: TextStyle(
                      fontSize: 10,
                      color: widget.color == Color(0xFFB2E2FE)
                          ? Color(0xFF62AEFB)
                          : widget.color,
                    ),
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
