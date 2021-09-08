package thang.com.wref.Components.Retrofits;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import thang.com.wref.MainScreen.Models.DetailWeatherModel;

public interface WeatherRetrofit {
    @POST("/api/detailweather")
    @FormUrlEncoded
    Call<DetailWeatherModel> getWeather (
            @Header("Authorization") String auth,
            @Field("lat") float lati,
            @Field("long") float longti
    );

    @POST("/api/getweather24h")
    @FormUrlEncoded
    Call<DetailWeatherModel> getWeather24h (
            @Header("Authorization") String auth,
            @Field("lat") float lati,
            @Field("long") float longti
    );
}
