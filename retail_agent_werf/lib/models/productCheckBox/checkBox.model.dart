class ProductCheckBox {
  String img;
  int amount;
  int totalPrice;
  String unitPrice;
  String description;
  bool value;
  bool showDescription;

  ProductCheckBox({
    required this.img,
    required this.amount,
    required this.totalPrice,
    required this.unitPrice,
    required this.description,
    this.value = false,
    this.showDescription = false,
  });
}
