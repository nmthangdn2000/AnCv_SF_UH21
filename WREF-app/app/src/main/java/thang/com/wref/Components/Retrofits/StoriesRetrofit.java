package thang.com.wref.Components.Retrofits;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import thang.com.wref.StoriesScreen.Models.StoriesModels;

public interface StoriesRetrofit {
    @GET("api/stories")
    Call<List<StoriesModels>> getStories(
            @Header("Authorization") String auth
    );
}
