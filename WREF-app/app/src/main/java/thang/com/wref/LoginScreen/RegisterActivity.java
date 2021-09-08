package thang.com.wref.LoginScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import thang.com.wref.LoginScreen.Models.ErrModel;
import thang.com.wref.util.NetworkUtil;
import thang.com.wref.R;
import thang.com.wref.Components.Retrofits.UserRetrofit;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "RegisterActivity";

    private TextView btnLoginPage;
    private TextInputLayout edit_username, edit_email, edit_Password, edit_EnterPassword;
    private AppCompatButton btnSignUp;

    private Retrofit retrofit;
    private NetworkUtil networkUtil;
    private UserRetrofit userRetrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        networkUtil = new NetworkUtil();
        retrofit = networkUtil.getRetrofit();
        mappingView();
        checkSignup();
    }

    private void mappingView() {
        btnLoginPage = (TextView) findViewById(R.id.btnLoginPage);
        edit_username = (TextInputLayout) findViewById(R.id.edit_username);
        edit_email = (TextInputLayout) findViewById(R.id.edit_email);
        edit_Password = (TextInputLayout) findViewById(R.id.edit_Password);
        edit_EnterPassword = (TextInputLayout) findViewById(R.id.edit_EnterPassword);
        btnSignUp = (AppCompatButton) findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(this);
        btnLoginPage.setOnClickListener(this);
    }
    private void checkSignup(){
        String userName = edit_username.getEditText().getText().toString();
        String Email = edit_email.getEditText().getText().toString();
        String Password = edit_Password.getEditText().getText().toString();
        if(!ValidateUserName() | !ValidateEmail() | !ValidatePassword() | !ValidateEnterPassword()){
            return;
        }else{
            userRetrofit = retrofit.create(UserRetrofit.class);
            Call<ErrModel> errModelCall = userRetrofit.postSignup(userName, Email, Password);
            errModelCall.enqueue(new Callback<ErrModel>() {
                @Override
                public void onResponse(Call<ErrModel> call, Response<ErrModel> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "lỗi mạng", Toast.LENGTH_SHORT).show();
                    }else {
                        ErrModel errModel = response.body();
                        if(!errModel.isSuccess()){
                            Toast.makeText(RegisterActivity.this, "Tài khoảng/ email đã tồn tại", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        }
                        edit_username.getEditText().setText("");
                        edit_email.getEditText().setText("");
                        edit_Password.getEditText().setText("");
                        edit_EnterPassword.getEditText().setText("");
                        ActivityCompat.finishAfterTransition(RegisterActivity.this);
                    }
                    call.cancel();
                }

                @Override
                public void onFailure(Call<ErrModel> call, Throwable t) {
                    call.cancel();
                    Log.d(TAG, "lỗi " + t.getMessage());
                }
            });
        }
    }
    private Boolean ValidateUserName(){
        String val = edit_username.getEditText().getText().toString();
        if(val.isEmpty()){
            edit_username.setError("Tên đăng nhập không được trống");
            return false;
        }else{
            edit_username.setError(null);
            return true;
        }
    }
    private Boolean ValidateEmail(){
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
        String val = edit_Password.getEditText().getText().toString();
        if(val.isEmpty()){
            edit_Password.setError("Tên đăng nhập không được trống");
            return false;
        }else{
            edit_Password.setError(null);
            return true;
        }
    }
    private Boolean ValidateEnterPassword(){
        String val = edit_EnterPassword.getEditText().getText().toString();
        if(val.isEmpty()){
            edit_EnterPassword.setError("Tên đăng nhập không được trống");
            return false;
        }else if(val != edit_Password.getEditText().getText().toString()){
            edit_EnterPassword.setError("Mật khẩu không khớp");
            return false;
        }else{
            edit_EnterPassword.setError(null);
            return true;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLoginPage:
                Toast.makeText(this, "aaaaa", Toast.LENGTH_SHORT).show();
                ActivityCompat.finishAfterTransition(this);
                break;
            default:
                break;
        }
    }
}