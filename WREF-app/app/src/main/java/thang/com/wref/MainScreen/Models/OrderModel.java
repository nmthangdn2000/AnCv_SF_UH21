package thang.com.wref.MainScreen.Models;

import com.google.gson.annotations.SerializedName;

import thang.com.wref.StoriesScreen.Models.UsersModel;

public class OrderModel {
    @SerializedName("_id")
    private String id;
    private int amount;
    private ProductModel idProduct;
    private UsersModel idUser;
    private int totalPrice;
    private int status;
    private int statusShip;
    private String deliveryTo;
    @SerializedName("create_a")
    private int createAt;

    public OrderModel(String id, int amount, ProductModel idProduct, UsersModel idUser, int totalPrice, int status, int statusShip, String deliveryTo, int createAt) {
        this.id = id;
        this.amount = amount;
        this.idProduct = idProduct;
        this.idUser = idUser;
        this.totalPrice = totalPrice;
        this.status = status;
        this.statusShip = statusShip;
        this.deliveryTo = deliveryTo;
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", idProduct=" + idProduct +
                ", idUser=" + idUser +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", statusShip=" + statusShip +
                ", deliveryTo='" + deliveryTo + '\'' +
                ", createAt=" + createAt +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ProductModel getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(ProductModel idProduct) {
        this.idProduct = idProduct;
    }

    public UsersModel getIdUser() {
        return idUser;
    }

    public void setIdUser(UsersModel idUser) {
        this.idUser = idUser;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatusShip() {
        return statusShip;
    }

    public void setStatusShip(int statusShip) {
        this.statusShip = statusShip;
    }

    public String getDeliveryTo() {
        return deliveryTo;
    }

    public void setDeliveryTo(String deliveryTo) {
        this.deliveryTo = deliveryTo;
    }

    public int getCreateAt() {
        return createAt;
    }

    public void setCreateAt(int createAt) {
        this.createAt = createAt;
    }
}
