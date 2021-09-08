package thang.com.wref.MainScreen.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class WeatherLocationModel {
    @SerializedName("weather")
    private DetailWeatherModel weather;
    @SerializedName("media")
    private String[] media;

    public WeatherLocationModel(DetailWeatherModel weather, String[] media) {
        this.weather = weather;
        this.media = media;
    }

    @Override
    public String toString() {
        return "WeatherLocationModel{" +
                "weather=" + weather +
                ", media=" + Arrays.toString(media) +
                '}';
    }

    public DetailWeatherModel getWeather() {
        return weather;
    }

    public void setWeather(DetailWeatherModel weather) {
        this.weather = weather;
    }

    public String[] getMedia() {
        return media;
    }

    public void setMedia(String[] media) {
        this.media = media;
    }
}
