package thang.com.wref.MainScreen.Havest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import thang.com.wref.MainScreen.Adapters.AgriAdapter;
import thang.com.wref.Components.Animation.RecyclerViewAnimation;
import thang.com.wref.LoginScreen.SharedPreferencesManagement;
import thang.com.wref.MainScreen.Models.AgriModel;
import thang.com.wref.MainScreen.Models.ThemeAgriModel;
import thang.com.wref.util.NetworkUtil;
import thang.com.wref.R;
import thang.com.wref.Components.Retrofits.InforAgriRetrofit;

public class ThemeAgriActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "InforAgriActivity";
    private Toolbar toolbar;
    private NetworkUtil networkUtil;
    private Retrofit retrofit;
    private InforAgriRetrofit inforAgriRetrofit;
    private ArrayList<AgriModel> agriModelsArr;
    private String iduser = "", token ="";
    private SharedPreferencesManagement sharedPreferencesManagement;
    private AgriAdapter agriAdapter;
    private AgriAdapter.onClickItemAgri mListenner;
    private RecyclerViewAnimation recyclerViewAnimation;

    private RecyclerView rcvInforAgri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_agri);
        toolbar = (Toolbar) findViewById(R.id.toolbarInfor);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Kiến thức nông nghiệp");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        networkUtil = new NetworkUtil();
        retrofit = networkUtil.getRetrofit();
        recyclerViewAnimation = new RecyclerViewAnimation();
        sharedPreferencesManagement = new SharedPreferencesManagement(this);
        iduser= sharedPreferencesManagement.getID();
        token = sharedPreferencesManagement.getTOKEN();

        mappingView();
        eventOnClick();
        getData();

    }
    private void mappingView() {
        rcvInforAgri = (RecyclerView) findViewById(R.id.rcvInforAgri);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        rcvInforAgri.setLayoutManager(gridLayoutManager);

        recyclerViewAnimation.setAnimationRecyclerviewVertical(rcvInforAgri, "agriAdapter");
    }
    // set onclick in a item on recyclerview
    private void eventOnClick() {
        mListenner = new AgriAdapter.onClickItemAgri() {
            @Override
            public void onClick(String id, String title) {
                Intent intent = new Intent(ThemeAgriActivity.this, ItemThemeAgriActivity.class);
                intent.putExtra("idTheme", id);
                intent.putExtra("Title", title);
                startActivity(intent);
            }
        };
    }

    @Override
    public void onClick(View v) {

    }
    private void getData(){
        agriModelsArr= new ArrayList<>();
        //get data from api
        inforAgriRetrofit = retrofit.create(InforAgriRetrofit.class);
        Call<List<ThemeAgriModel>> listCall = inforAgriRetrofit.getThemeAgri(token);
        listCall.enqueue(new Callback<List<ThemeAgriModel>>() {
            @Override
            public void onResponse(Call<List<ThemeAgriModel>> call, Response<List<ThemeAgriModel>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(ThemeAgriActivity.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    List<ThemeAgriModel> themeAgriModels = response.body();
                    for(ThemeAgriModel themeAgriModel : themeAgriModels){
                        agriModelsArr.add(new AgriModel(themeAgriModel.getId(), "", themeAgriModel.getName()));
                    }
                    agriAdapter.notifyDataSetChanged();
                }
                call.cancel();
            }

            @Override
            public void onFailure(Call<List<ThemeAgriModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                call.cancel();
            }
        });
        // set data to recylerview
        agriAdapter = new AgriAdapter(agriModelsArr, this, mListenner);
        rcvInforAgri.setAdapter(agriAdapter);
    }
}