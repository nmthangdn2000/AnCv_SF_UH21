package thang.com.wref.Components.Retrofits;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import thang.com.wref.MainScreen.Models.ResponseModel;

public interface OrderRetrofit {
    @POST("api/order")
    @FormUrlEncoded
    Call<ResponseModel<OrderRetrofit>> postOrder (
            @Header("Authorization") String auth,
            @Field("idProduct") String idProduct,
            @Field("idUser") String idUser,
            @Field("amount") int amount,
            @Field("deliveryTo") String deliveryTo

    );
}
