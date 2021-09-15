package thang.com.wref.LoginScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import thang.com.wref.MainScreen.MainActivity;
import thang.com.wref.MainScreen.Models.ResponseModel;
import thang.com.wref.StoriesScreen.Models.UsersModel;
import thang.com.wref.util.NetworkUtil;
import thang.com.wref.R;
import thang.com.wref.Components.Retrofits.UserRetrofit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "LoginActivity";

    private TextView btnSignUpPage;
    private ImageView logoImg;
    private TextView logoName, logoPage;
    private TextInputLayout edit_email, edit_password;
    private AppCompatButton btnLogin;

    private Retrofit retrofit;
    private NetworkUtil networkUtil;
    private UserRetrofit userRetrofit;

    private SharedPreferencesManagement sharedPreferencesManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        networkUtil = new NetworkUtil();
        retrofit = networkUtil.getRetrofit();
        sharedPreferencesManagement = new SharedPreferencesManagement(LoginActivity.this);
        mappingView();
    }
    private Boolean ValidateUserName(){
        String val = edit_email.getEditText().getText().toString();
        if(val.isEmpty()){
            edit_email.setError("Tên đăng nhập không được trống");
            return false;
        }else{
            edit_email.setError(null);
            return true;
        }
    }
    private Boolean ValidatePassword(){
        String val = edit_password.getEditText().getText().toString();
        if(val.isEmpty()){
            edit_password.setError("Mật khẩu không được trống");
            return false;
        }else{
            edit_password.setError(null);
            return true;
        }
    }
    private void checkLogin() {
        if(!ValidateUserName() | !ValidatePassword())
            return;
        else{
            userRetrofit = retrofit.create(UserRetrofit.class);
            Call<ResponseModel<UsersModel>> errModelCall = userRetrofit.postSigin(edit_email.getEditText().getText().toString(), edit_password.getEditText().getText().toString());
            errModelCall.enqueue(new Callback<ResponseModel<UsersModel>>() {
                @Override
                public void onResponse(Call<ResponseModel<UsersModel>> call, Response<ResponseModel<UsersModel>> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "lỗi mạng", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        ResponseModel<UsersModel> responseModel = response.body();
                        UsersModel usersModels = responseModel.getData();
                        Log.d(TAG, "onResponse: " + response.body());
                        sharedPreferencesManagement.saveData(usersModels);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    call.cancel();
                }

                @Override
                public void onFailure(Call<ResponseModel<UsersModel>> call, Throwable t) {
                    Log.d(TAG, "lỗi "+ t.getMessage());
                    call.cancel();
                }
            });
        }
    }

    private void mappingView() {
        btnSignUpPage = (TextView) findViewById(R.id.btnSignUpPage);
        logoImg = (ImageView) findViewById(R.id.logoImg);
        logoName = (TextView) findViewById(R.id.logoName);
        logoPage = (TextView) findViewById(R.id.logoPage);
        edit_email = (TextInputLayout) findViewById(R.id.edit_email);
        edit_password = (TextInputLayout) findViewById(R.id.edit_password);
        btnLogin = (AppCompatButton) findViewById(R.id.btnLogin);

        btnSignUpPage.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignUpPage:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                Pair[] pairs = new Pair[6];

                pairs[0] = new Pair<View, String>(logoName, "logo_text");
                pairs[1] = new Pair<View, String>(logoPage, "logo_textPage");
                pairs[2] = new Pair<View, String>(edit_email, "edit_email");
                pairs[3] = new Pair<View, String>(edit_password, "edit_password");
                pairs[4] = new Pair<View, String>(btnLogin, "btnLogin");
                pairs[5] = new Pair<View, String>(btnSignUpPage, "btn_text_tran");

                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
                startActivity(intent, activityOptions.toBundle());
                break;
            case R.id.btnLogin:
                checkLogin();
                break;
            default:
                break;
        }
    }
}