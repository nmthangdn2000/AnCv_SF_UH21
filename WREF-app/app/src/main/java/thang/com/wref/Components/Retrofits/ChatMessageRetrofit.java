package thang.com.wref.Components.Retrofits;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import thang.com.wref.ChatBot.MessageModel;
import thang.com.wref.MainScreen.Models.ResponseModel;

public interface ChatMessageRetrofit {
    @POST("api/bot/message")
    @FormUrlEncoded
    Call<ResponseModel<MessageModel>> postMessage(
            @Header("Authorization") String auth,
            @Field("message") String message,
            @Field("entity") String entity,
            @Field("oldIntent") String oldIntent,
            @Field("repeat") int repeat
    );
}
