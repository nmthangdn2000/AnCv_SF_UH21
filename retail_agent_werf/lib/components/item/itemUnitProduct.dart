import 'package:flutter/material.dart';
import 'package:retail_agent_werf/models/products.model/product.model.dart';
import 'package:retail_agent_werf/screen/detailProduct.dart';

class ItemUnitProduct extends StatefulWidget {
  final Color color;
  final ProductModel productModel;
  const ItemUnitProduct(
      {Key? key, required this.productModel, required this.color})
      : super(key: key);

  @override
  _ItemUnitProductState createState() => _ItemUnitProductState();
}

class _ItemUnitProductState extends State<ItemUnitProduct> {
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
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
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(8),
          color: Color(0xFFF6F9FB),
        ),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Hero(
              tag: "image-${widget.productModel.id}-${widget.color}",
              child: Image.asset(
                "assets/images/" + widget.productModel.media,
                height: 100,
                width: 210,
                fit: BoxFit.cover,
              ),
            ),
            SizedBox(
              height: 10,
            ),
            Container(
              width: 210,
              padding: const EdgeInsets.only(left: 8, right: 8),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Row(
                    children: [
                      Expanded(
                        flex: 1,
                        child: Hero(
                          tag: "name-${widget.productModel.id}-${widget.color}",
                          child: Text(
                            widget.productModel.name,
                            maxLines: 1,
                            overflow: TextOverflow.ellipsis,
                            style: TextStyle(
                              color: Colors.black,
                              fontWeight: FontWeight.bold,
                              fontSize: 12,
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
                              fontSize: 12,
                            ),
                          ),
                        ),
                      ),
                    ],
                  ),
                  SizedBox(
                    height: 10,
                  ),
                  Row(
                    children: [
                      Expanded(
                        flex: 1,
                        child: Hero(
                          tag:
                              "company-${widget.productModel.id}-${widget.color}",
                          child: Text(
                            "${widget.productModel.company} ",
                            style: TextStyle(
                              fontWeight: FontWeight.w400,
                              fontSize: 10,
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
                              fontWeight: FontWeight.w400,
                              fontSize: 10,
                            ),
                          ),
                        ),
                      ),
                    ],
                  )
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
