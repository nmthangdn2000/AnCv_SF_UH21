package thang.com.wref.MainScreen.Weather;

import thang.com.wref.R;

public class IconWeather {
    public int IconWeather(String desWeather){
        if(desWeather.equals("Rain"))
            return R.drawable.ic_rain_sun_small;
        else if(desWeather.equals("Clear"))
            return R.drawable.ic_sun_small;
        else if(desWeather.equals("Clouds"))
            return R.drawable.ic_sun_small;
        else if(desWeather.equals("Clear"))
            return R.drawable.ic_sun_small;
        else return 0;
    }
    // conver Temp to °C
    public String Temp(float max, float min){
        float c_max = max - 273;
        float c_min = min - 273;
        return (int) c_max + "/"+ (int) c_min + "°C";
    }
    // change data from english to vietnamese
    public String typeWeather(String des){
        if(des.equals("moderate rain"))
            return "Mưa vừa";
        else if(des.equals("heavy intensity rain"))
            return "Mưa lớn";
        else if(des.equals("light rain"))
            return "Mưa nhỏ";
        else return "";
    }
    // change data number to string
    public String wind_Deg(int deg){
        if(deg <= 75 )
            return "Đông bắc";
        else if (deg > 75 && deg <= 110)
            return "Đông";
        else if(deg > 120 && deg <= 180 )
            return "Đông nam";
        else if(deg > 180 && deg <= 230 )
            return "Tây nam";
        else if(deg > 230 && deg <= 270 )
            return "Tây";
        else if(deg > 270 && deg <= 360 )
            return "Tây bắc";
        else return "Đông bắc";
    }
    public int IconWeatherAnimation(String desWeather){
        if(desWeather.equals("Rain"))
            return 1;
        else if(desWeather.equals("Clear"))
            return 2;
        else if(desWeather.equals("Clouds"))
            return 3;
        else if(desWeather.equals("Clear"))
            return R.drawable.ic_sun_small;
        else return 2;
    }
}
