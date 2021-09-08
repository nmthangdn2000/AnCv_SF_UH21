package thang.com.wref.Components.Retrofits;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import thang.com.wref.MainScreen.Models.LocationModel;
import thang.com.wref.MainScreen.Models.WeatherLocationModel;

public interface LocationRetrofit {
    @GET("api/getalllocation")
    Call<List<LocationModel>> getAllLocation(
            @Header("Authorization") String auth
    );

    @POST("api/location")
    @FormUrlEncoded
    Call<WeatherLocationModel> getWeatherLocation(
            @Header("Authorization") String auth,
            @Field("name") String name,
            @Field("lati") float lati,
            @Field("longitude") float longti
    );
}
