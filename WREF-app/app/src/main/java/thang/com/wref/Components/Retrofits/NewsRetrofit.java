package thang.com.wref.Components.Retrofits;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import thang.com.wref.LoginScreen.Models.ErrModel;
import thang.com.wref.StoriesScreen.Models.NewsModels;

public interface NewsRetrofit {
    @GET("api/posts")
    Call<List<NewsModels>> getNews (
        @Header("Authorization") String auth
    );

    @Multipart
    @POST("api/posts")
    Call<ErrModel> postPossts(
            @Header("Authorization") String auth,
            @Part("idlocation") RequestBody locationName,
            @Part("content") RequestBody document,
            @Part List<MultipartBody.Part> upload
    );

    @DELETE("api/deleteposts/{id}")
    Call<ErrModel> deletePosts(
            @Header("Authorization") String auth,
            @Path("id") String idNews
    );
}
