package thang.com.wref.MainScreen.Models;

import com.google.gson.annotations.SerializedName;

public class LocationModel {
    @SerializedName("_id")
    private String id;
    @SerializedName("longitude")
    private float longitude;
    @SerializedName("latiude")
    private float latiude;
    @SerializedName("name")
    private String name;

    public LocationModel(String id, float longitude, float latiude, String name) {
        this.id = id;
        this.longitude = longitude;
        this.latiude = latiude;
        this.name = name;
    }

    @Override
    public String toString() {
        return "LocationModel{" +
                "id='" + id + '\'' +
                ", longitude=" + longitude +
                ", latiude=" + latiude +
                ", name='" + name + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatiude() {
        return latiude;
    }

    public void setLatiude(float latiude) {
        this.latiude = latiude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
