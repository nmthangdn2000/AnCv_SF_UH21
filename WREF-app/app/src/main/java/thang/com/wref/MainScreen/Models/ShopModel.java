package thang.com.wref.MainScreen.Models;

import com.google.gson.annotations.SerializedName;

public class ShopModel {
    @SerializedName("_id")
    private String id;
    private String idUser;
    private String name;
    private String description;
    private String city;
    private String district;
    private String subDistrict;
    private String address;
    private String media;
    @SerializedName("create_at")
    private int createAt;
    @SerializedName("update_at")
    private int updateAt;

    public ShopModel(String id, String idUser, String name, String description, String city, String district, String subDistrict, String address, String media, int createAt, int updateAt) {
        this.id = id;
        this.idUser = idUser;
        this.name = name;
        this.description = description;
        this.city = city;
        this.district = district;
        this.subDistrict = subDistrict;
        this.address = address;
        this.media = media;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "ShopModel{" +
                "id='" + id + '\'' +
                ", idUser='" + idUser + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", subDistrict='" + subDistrict + '\'' +
                ", address='" + address + '\'' +
                ", media='" + media + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public int getCreateAt() {
        return createAt;
    }

    public void setCreateAt(int createAt) {
        this.createAt = createAt;
    }

    public int getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(int updateAt) {
        this.updateAt = updateAt;
    }
}
