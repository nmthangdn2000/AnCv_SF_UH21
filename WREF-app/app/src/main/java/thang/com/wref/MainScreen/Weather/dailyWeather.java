package thang.com.wref.MainScreen.Weather;

import com.google.gson.annotations.SerializedName;

public class dailyWeather {
    @SerializedName("dt")
    private int dt;
    @SerializedName("sunrise")
    private int sunrise;
    @SerializedName("sunset")
    private int sunset;
    @SerializedName("temp")
    private temp temp;
    @SerializedName("feels_like")
    private temp feels_like;
    @SerializedName("pressure")
    private int pressure;
    @SerializedName("humidity")
    private int humidity;
    @SerializedName("uvi")
    private float uvi;
    @SerializedName("clouds")
    private int clouds;
    @SerializedName("wind_speed")
    private float wind_speed;
    @SerializedName("wind_deg")
    private int wind_deg;
    @SerializedName("weather")
    private weather[] weather;
    @SerializedName("rain")
    private float rain;

    public dailyWeather(int dt, int sunrise, int sunset, temp temp, temp feels_like, int pressure, int humidity, float uvi, int clouds, float wind_speed, int wind_deg, weather[] weather, float rain) {
        this.dt = dt;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.temp = temp;
        this.feels_like = feels_like;
        this.pressure = pressure;
        this.humidity = humidity;
        this.uvi = uvi;
        this.clouds = clouds;
        this.wind_speed = wind_speed;
        this.wind_deg = wind_deg;
        this.weather = weather;
        this.rain = rain;
    }

    @Override
    public String toString() {
        return "dailyWeather{" +
                "dt=" + dt +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                ", temp=" + temp +
                ", feels_like=" + feels_like +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", uvi=" + uvi +
                ", clouds=" + clouds +
                ", wind_speed=" + wind_speed +
                ", wind_deg=" + wind_deg +
                ", weather=" + weather +
                ", rain=" + rain +
                '}';
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public int getSunrise() {
        return sunrise;
    }

    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    public int getSunset() {
        return sunset;
    }

    public void setSunset(int sunset) {
        this.sunset = sunset;
    }

    public temp getTemp() {
        return temp;
    }

    public void setTemp(temp temp) {
        this.temp = temp;
    }

    public temp getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(temp feels_like) {
        this.feels_like = feels_like;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public float getUvi() {
        return uvi;
    }

    public void setUvi(float uvi) {
        this.uvi = uvi;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public float getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(float wind_speed) {
        this.wind_speed = wind_speed;
    }

    public int getWind_deg() {
        return wind_deg;
    }

    public void setWind_deg(int wind_deg) {
        this.wind_deg = wind_deg;
    }

    public weather[] getWeather() {
        return weather;
    }

    public void setWeather(weather[] weather) {
        this.weather = weather;
    }

    public float getRain() {
        return rain;
    }

    public void setRain(float rain) {
        this.rain = rain;
    }
}
