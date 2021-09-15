package thang.com.wref.Components.Retrofits;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import thang.com.wref.MainScreen.Models.HarvesthelperModel;
import thang.com.wref.MainScreen.Models.ResponseModel;

public interface HarvesthelperRetrofit {
    @GET("api/harvesthelper/all")
    Call<List<HarvesthelperModel>> getAll(
            @Header("Authorization") String auth
    );

    @GET("api/harvesthelper/id/{id}")
    Call<HarvesthelperModel> getByID(
            @Header("Authorization") String auth,
            @Path("id") String id
    );
}
