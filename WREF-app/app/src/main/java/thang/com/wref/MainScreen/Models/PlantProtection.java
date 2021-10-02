package thang.com.wref.MainScreen.Models;

import com.google.gson.annotations.SerializedName;

import thang.com.wref.StoriesScreen.Models.UsersModel;

public class PlantProtection {
    @SerializedName("_id")
    private String id;
    private String name;
    private ShopModel shop;
    private String image;
    private String detail;
    private String qrcode;
//    @SerializedName("create_at")
//    private int createAt;


    public PlantProtection(String id, String name, ShopModel shop, String image, String detail, String qrcode) {
        this.id = id;
        this.name = name;
        this.shop = shop;
        this.image = image;
        this.detail = detail;
        this.qrcode = qrcode;
    }

    @Override
    public String toString() {
        return "PlantProtection{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", shop=" + shop +
                ", image='" + image + '\'' +
                ", detail='" + detail + '\'' +
                ", qrcode='" + qrcode + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShopModel getShop() {
        return shop;
    }

    public void setShop(ShopModel shop) {
        this.shop = shop;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }
}
