package thang.com.wref.Components.Retrofits;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import thang.com.wref.MainScreen.Models.InforAgriModel;
import thang.com.wref.MainScreen.Models.ThemeAgriModel;

public interface InforAgriRetrofit {
    @GET("api/inforAgri/theme/all")
    Call<List<ThemeAgriModel>> getThemeAgri(
            @Header("Authorization") String auth
    );
    @GET("api/inforAgri/theme/{id}/all")
    Call<List<InforAgriModel>> getInforAgri(
            @Header("Authorization") String auth,
            @Path("id") String id
    );
    @GET("api/inforAgri/detail/{id}")
    Call<List<InforAgriModel>> getInforAgriDetail(
            @Header("Authorization") String auth,
            @Path("id") String id
    );
}
