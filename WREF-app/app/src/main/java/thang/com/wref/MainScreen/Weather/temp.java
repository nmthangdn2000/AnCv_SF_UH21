package thang.com.wref.MainScreen.Weather;

import com.google.gson.annotations.SerializedName;

public class temp {
    @SerializedName("day")
    private float day;
    @SerializedName("min")
    private float min;
    @SerializedName("max")
    private float max;
    @SerializedName("night")
    private float night;
    @SerializedName("eve")
    private float eve;
    @SerializedName("morn")
    private float morn;

    public temp(float day, float min, float max, float night, float eve, float morn) {
        this.day = day;
        this.min = min;
        this.max = max;
        this.night = night;
        this.eve = eve;
        this.morn = morn;
    }

    @Override
    public String toString() {
        return "temp{" +
                "day=" + day +
                ", min=" + min +
                ", max=" + max +
                ", night=" + night +
                ", eve=" + eve +
                ", morn=" + morn +
                '}';
    }

    public float getDay() {
        return day;
    }

    public void setDay(float day) {
        this.day = day;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getNight() {
        return night;
    }

    public void setNight(float night) {
        this.night = night;
    }

    public float getEve() {
        return eve;
    }

    public void setEve(float eve) {
        this.eve = eve;
    }

    public float getMorn() {
        return morn;
    }

    public void setMorn(float morn) {
        this.morn = morn;
    }
}
