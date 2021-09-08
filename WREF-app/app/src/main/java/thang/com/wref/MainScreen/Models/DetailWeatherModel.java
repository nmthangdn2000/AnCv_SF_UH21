package thang.com.wref.MainScreen.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

import thang.com.wref.MainScreen.Weather.currentWeather;
import thang.com.wref.MainScreen.Weather.dailyWeather;

public class DetailWeatherModel {
    @SerializedName("current")
    private currentWeather current;
    @SerializedName("daily")
    private dailyWeather[] daily;
    @SerializedName("hourly")
    private currentWeather[] hourly;

    public DetailWeatherModel(currentWeather current, dailyWeather[] daily, currentWeather[] hourly) {
        this.current = current;
        this.daily = daily;
        this.hourly = hourly;
    }

    @Override
    public String toString() {
        return "DetailWeatherModel{" +
                "current=" + current +
                ", daily=" + Arrays.toString(daily) +
                ", hourly=" + Arrays.toString(hourly) +
                '}';
    }

    public currentWeather getCurrent() {
        return current;
    }

    public void setCurrent(currentWeather current) {
        this.current = current;
    }

    public dailyWeather[] getDaily() {
        return daily;
    }

    public void setDaily(dailyWeather[] daily) {
        this.daily = daily;
    }

    public currentWeather[] getHourly() {
        return hourly;
    }

    public void setHourly(currentWeather[] hourly) {
        this.hourly = hourly;
    }
}
