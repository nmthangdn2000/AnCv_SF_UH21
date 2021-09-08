package thang.com.wref.MainScreen.Havest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import thang.com.wref.MainScreen.Adapters.ItemThemeAgriAdapter;
import thang.com.wref.Components.Animation.RecyclerViewAnimation;
import thang.com.wref.LoginScreen.SharedPreferencesManagement;
import thang.com.wref.MainScreen.Models.InforAgriModel;
import thang.com.wref.util.NetworkUtil;
import thang.com.wref.R;
import thang.com.wref.Components.Retrofits.InforAgriRetrofit;

public class ItemThemeAgriActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "ItemThemeAgriActivity";
    private Toolbar toolbar;
    private String themeAgriId ="";
    private NetworkUtil networkUtil;
    private Retrofit retrofit;
    private InforAgriRetrofit inforAgriRetrofit;
    private ArrayList<InforAgriModel> inforAgriModelsArr;
    private String iduser = "", token ="";
    private SharedPreferencesManagement sharedPreferencesManagement;
    private ItemThemeAgriAdapter itemThemeAgriAdapter;
    private RecyclerViewAnimation recyclerViewAnimation;

    private RecyclerView rcvItemThemeAgri;
    /** The magnitude of translation distance while the list is over-scrolled. */
    private static final float OVERSCROLL_TRANSLATION_MAGNITUDE = 0.2f;
    /** The magnitude of translation distance when the list reaches the edge on fling. */
    private static final float FLING_TRANSLATION_MAGNITUDE = 0.5f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_theme_agri);

        toolbar = (Toolbar) findViewById(R.id.toolbarInfor);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("Title"));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        themeAgriId = getIntent().getStringExtra("idTheme");
        networkUtil = new NetworkUtil();
        retrofit = networkUtil.getRetrofit();
        recyclerViewAnimation = new RecyclerViewAnimation();
        sharedPreferencesManagement = new SharedPreferencesManagement(this);
        iduser= sharedPreferencesManagement.getID();
        token = sharedPreferencesManagement.getTOKEN();

        mapingView();
        getData();

    }
    private void mapingView() {
        rcvItemThemeAgri = (RecyclerView) findViewById(R.id.rcvItemThemeAgri);
        rcvItemThemeAgri.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvItemThemeAgri.setLayoutManager(linearLayoutManager);

        recyclerViewAnimation.setAnimationRecyclerviewVertical(rcvItemThemeAgri, TAG);
    }
    @Override
    public void onClick(View v) {

    }
    private void getData() {
        inforAgriModelsArr = new ArrayList<>();
        // get data from api

        inforAgriRetrofit = retrofit.create(InforAgriRetrofit.class);
        // token: get token user authentication
        // themeAgriId: required params  for api
        Call<List<InforAgriModel>> listCall = inforAgriRetrofit.getInforAgri(token, themeAgriId);
        listCall.enqueue(new Callback<List<InforAgriModel>>() {
            @Override
            public void onResponse(Call<List<InforAgriModel>> call, Response<List<InforAgriModel>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(ItemThemeAgriActivity.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    List<InforAgriModel> themeAgriModels = response.body();
                    for(InforAgriModel inforAgriModel : themeAgriModels){
                        inforAgriModelsArr.add(inforAgriModel);
                    }
                    itemThemeAgriAdapter.notifyDataSetChanged();
                }
                call.cancel();
            }

            @Override
            public void onFailure(Call<List<InforAgriModel>> call, Throwable t) {
                call.cancel();
                Log.d(TAG, "onFailure: "+ t.getMessage());
            }
        });
        // set data to recyclerview
        itemThemeAgriAdapter = new ItemThemeAgriAdapter(inforAgriModelsArr, this);
        rcvItemThemeAgri.setAdapter(itemThemeAgriAdapter);
    }



}