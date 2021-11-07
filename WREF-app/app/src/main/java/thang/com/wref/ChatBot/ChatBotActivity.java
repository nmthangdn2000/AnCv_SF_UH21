package thang.com.wref.ChatBot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import thang.com.wref.Components.Retrofits.ChatMessageRetrofit;
import thang.com.wref.Components.Retrofits.UserRetrofit;
import thang.com.wref.LoginScreen.SharedPreferencesManagement;
import thang.com.wref.MainScreen.MainActivity;
import thang.com.wref.MainScreen.Models.ResponseModel;
import thang.com.wref.R;
import thang.com.wref.SplashScreen.SplashActivity;
import thang.com.wref.StoriesScreen.Models.UsersModel;
import thang.com.wref.util.NetworkUtil;

public class ChatBotActivity extends AppCompatActivity {
    private static final String TAG = "ChatBotActivity";
    private RecyclerView rcvContentChat;
    private EditText et_message;
    private RelativeLayout btnSend, rltBack;
    private ChatBotAdapter chatBotAdapter;
    private ArrayList<MessageModel> mMessage;
    private String entity = "", oldIntent= "";
    private int repeat = 0;
    private ChatMessageRetrofit chatMessageRetrofit;
    private NetworkUtil networkUtil;
    private Retrofit retrofit;
    private SharedPreferencesManagement sharedPreferencesManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);
        networkUtil = new NetworkUtil();
        retrofit = networkUtil.getRetrofit();
        sharedPreferencesManagement = new SharedPreferencesManagement(this);

        rcvContentChat = (RecyclerView) findViewById(R.id.rcvContentChat);
        et_message = (EditText) findViewById(R.id.et_message);
        btnSend = (RelativeLayout) findViewById(R.id.btnSend);
        rltBack = (RelativeLayout) findViewById(R.id.rltBack);

        mMessage = new ArrayList<>();
        mMessage.add(new MessageModel(2, "text", "Xin chào",null, entity, oldIntent, repeat));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvContentChat.setLayoutManager(linearLayoutManager);
        chatBotAdapter = new ChatBotAdapter(mMessage);
        rcvContentChat.setAdapter(chatBotAdapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        rltBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void sendMessage(){
        String message = et_message.getText().toString().trim();
        if(TextUtils.isEmpty(message)) return;

        mMessage.add(new MessageModel(1, "text", message,null, entity, oldIntent, repeat));
        chatBotAdapter.notifyDataSetChanged();
        rcvContentChat.scrollToPosition(mMessage.size() - 1);

        et_message.setText("");
        postDataMessage(message);
    }

    private void postDataMessage(String mess) {
        chatMessageRetrofit = retrofit.create(ChatMessageRetrofit.class);
        Call<ResponseModel<MessageModel>> call = chatMessageRetrofit.postMessage(sharedPreferencesManagement.getTOKEN(), mess, entity, oldIntent, repeat);
        call.enqueue(new Callback<ResponseModel<MessageModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<MessageModel>> call, Response<ResponseModel<MessageModel>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(ChatBotActivity.this, "không có mạng", Toast.LENGTH_SHORT).show();
                }else{
                    ResponseModel<MessageModel> responseModel = response.body();
                    MessageModel messageModel = responseModel.getData();
                    Log.d(TAG, "onResponse: "+ messageModel);
                    entity = messageModel.getEntity();
                    oldIntent = messageModel.getOldIntent();
                    repeat = messageModel.getRepeat();
                    mMessage.add(new MessageModel(
                            2, messageModel.getType(), messageModel.getMessage(), messageModel.getData(), entity,oldIntent, repeat));
                    chatBotAdapter.notifyDataSetChanged();
                    rcvContentChat.scrollToPosition(mMessage.size() - 1);
                }
                call.cancel();
            }

            @Override
            public void onFailure(Call<ResponseModel<MessageModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+ t.getMessage());
                call.cancel();
                mMessage.add(new MessageModel(2, "text", "Xin lỗi chúng tôi không hiểu ý của bạn",null, entity, oldIntent, repeat));
                chatBotAdapter.notifyDataSetChanged();
                rcvContentChat.scrollToPosition(mMessage.size() - 1);
            }
        });
    }
}