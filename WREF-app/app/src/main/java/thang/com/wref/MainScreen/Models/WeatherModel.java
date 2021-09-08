package thang.com.wref.MainScreen.Models;

import com.google.gson.annotations.SerializedName;

public class WeatherModel {
    @SerializedName("_id")
    private String id;
    @SerializedName("idLocation")
    private LocationModel idLocation;
    @SerializedName("infor")
    private LocationModel infor;
}
