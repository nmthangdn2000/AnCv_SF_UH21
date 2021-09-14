package thang.com.wref.Components.Retrofits;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import thang.com.wref.LoginScreen.Models.ErrModel;
import thang.com.wref.MainScreen.Models.ResponseModel;
import thang.com.wref.StoriesScreen.Models.UsersModel;

public interface UserRetrofit {
    @POST("api/signin")
    @FormUrlEncoded
    Call<ResponseModel<UsersModel>> postSigin(
            @Field("email") String email,
            @Field("password") String password
    );

    @POST("api/signup")
    @FormUrlEncoded
    Call<ErrModel> postSignup(
            @Field("userName") String userName,
            @Field("email") String email,
            @Field("passWord") String password
    );

    @GET("api/checklogin")
    Call<ResponseModel<UsersModel>> checkLogin(
            @Header("Authorization") String auth
    );
}
