package thang.com.wref.ChatBot;

import com.google.gson.annotations.SerializedName;

public class WeatherModel {
    @SerializedName("Mô tả")
    private String description;
    @SerializedName("Nhiệt độ trung bình")
    private String temp;
    @SerializedName("Nhiệt độ cao nhất")
    private String tempMax;
    @SerializedName("Nhiệt độ thấp nhất")
    private String tempMin;
    @SerializedName("Tốc độ gió")
    private String windSpeed;

    public WeatherModel(String description, String temp, String tempMax, String tempMin, String windSpeed) {
        this.description = description;
        this.temp = temp;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.windSpeed = windSpeed;
    }

    @Override
    public String toString() {
        return "Mô tả: " + description
                + "\nNhiệt độ trung bình: " + temp
                + "\nNhiệt độ cao nhất: " + tempMax
                + "\nNhiệt độ thấp nhất: " + tempMin
                + "\nTốc độ gió: " + windSpeed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }
}
