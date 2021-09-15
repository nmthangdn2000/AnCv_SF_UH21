package thang.com.wref.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import thang.com.wref.LoginScreen.LoginActivity;
import thang.com.wref.LoginScreen.SharedPreferencesManagement;
import thang.com.wref.MainScreen.MainActivity;
import thang.com.wref.MainScreen.Models.ResponseModel;
import thang.com.wref.StoriesScreen.Models.UsersModel;
import thang.com.wref.util.NetworkUtil;
import thang.com.wref.R;
import thang.com.wref.Components.Retrofits.UserRetrofit;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    private static final int SPLASH_SCREEN = 3000;
    private Handler handler;;

    private ImageView logoImg;
    private TextView logoName;
    private Animation animationUp, animationDown;

    private Retrofit retrofit;
    private NetworkUtil networkUtil;
    private UserRetrofit userRetrofit;

    private int check = 0;
    private SharedPreferencesManagement sharedPreferencesManagement;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logoImg.setImageDrawable(null);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        handler = new Handler();
        networkUtil = new NetworkUtil();
        retrofit = networkUtil.getRetrofit();
        sharedPreferencesManagement = new SharedPreferencesManagement(this);
        mappingView();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, SPLASH_SCREEN);

    }
    private void mappingView(){
        logoImg = (ImageView) findViewById(R.id.logoImg);
        logoName = (TextView) findViewById(R.id.logoName);

        animationUp = AnimationUtils.loadAnimation(this, R.anim.logo_text);
        animationDown = AnimationUtils.loadAnimation(this, R.anim.logo_img);

        logoImg.startAnimation(animationDown);
        logoName.startAnimation(animationUp);
    }
    private void getData(){
        // call api user authentication
        // if true move to Home Activity
        // else false move to Login Activity
        userRetrofit = retrofit.create(UserRetrofit.class);
        Call<ResponseModel<UsersModel>> usersModelCall = userRetrofit.checkLogin(sharedPreferencesManagement.getTOKEN());
        usersModelCall.enqueue(new Callback<ResponseModel<UsersModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<UsersModel>> call, Response<ResponseModel<UsersModel>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(SplashActivity.this, "không có mạng", Toast.LENGTH_SHORT).show();
                    activitiLogin();
                }else{
                    ResponseModel<UsersModel> responseModel = response.body();
                    UsersModel usersModel = responseModel.getData();
                    Log.d(TAG, "onResponse: "+ usersModel);
                    String[] strings = sharedPreferencesManagement.getTOKEN().split(" ");
                    usersModel.setToken(strings[1]);
                    Log.d(TAG, "onResponse: "+ usersModel);
                    sharedPreferencesManagement.saveData(usersModel);

                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                call.cancel();
            }

            @Override
            public void onFailure(Call<ResponseModel<UsersModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+ t.getMessage());
                call.cancel();
                activitiLogin();
            }
        });
    }
    private void activitiLogin(){
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        // Animation
        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair<View, String>(logoImg, "logo_img");
        pairs[1] = new Pair<View, String>(logoName, "logo_text");

        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, pairs);
        startActivity(intent, activityOptions.toBundle());
        finish();
    }
}