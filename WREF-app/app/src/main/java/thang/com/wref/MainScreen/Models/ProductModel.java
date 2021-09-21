package thang.com.wref.MainScreen.Models;

import com.google.gson.annotations.SerializedName;

public class ProductModel {
    @SerializedName("_id")
    private String id;
    private String name;
    private ShopModel idShop;
    private int price;
    private String media;
    private String company;
    private String saleOff;
    private boolean type;
    private String ingredient;
    private String effect;
    private String userManual;
    private String note;

    public ProductModel(String id, String name, ShopModel idShop, int price, String media, String company, String saleOff, boolean type, String ingredient, String effect, String userManual, String note) {
        this.id = id;
        this.name = name;
        this.idShop = idShop;
        this.price = price;
        this.media = media;
        this.company = company;
        this.saleOff = saleOff;
        this.type = type;
        this.ingredient = ingredient;
        this.effect = effect;
        this.userManual = userManual;
        this.note = note;
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", idShop=" + idShop +
                ", price=" + price +
                ", media='" + media + '\'' +
                ", company='" + company + '\'' +
                ", saleOff='" + saleOff + '\'' +
                ", type=" + type +
                ", ingredient='" + ingredient + '\'' +
                ", effect='" + effect + '\'' +
                ", userManual='" + userManual + '\'' +
                ", note='" + note + '\'' +
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

    public ShopModel getIdShop() {
        return idShop;
    }

    public void setIdShop(ShopModel idShop) {
        this.idShop = idShop;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSaleOff() {
        return saleOff;
    }

    public void setSaleOff(String saleOff) {
        this.saleOff = saleOff;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getUserManual() {
        return userManual;
    }

    public void setUserManual(String userManual) {
        this.userManual = userManual;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
