package thang.com.wref.Components.Retrofits;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import thang.com.wref.MainScreen.Models.ProductModel;
import thang.com.wref.MainScreen.Models.ResponseModel;

public interface ProductRetrofit {
    @GET("api/product/{id}")
    Call<ResponseModel<ProductModel>> getProductById (
            @Header("Authorization") String auth,
            @Path("id") String id
    );

    @POST("api/product")
    Call<ResponseModel<ProductModel>> getByQuery (
            @Header("Authorization") String auth,
            @Query("idShop") String idShop, // có hoặc không cũng được
            @Query("page") int page,
            @Query("limit") int limit
    );
}
