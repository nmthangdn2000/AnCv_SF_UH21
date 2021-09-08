package thang.com.wref.MainScreen.Havest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import thang.com.wref.MainScreen.Adapters.HarvesthelperAdapter;
import thang.com.wref.LoginScreen.SharedPreferencesManagement;
import thang.com.wref.MainScreen.Models.CropsModel;
import thang.com.wref.MainScreen.Models.HarvesthelperModel;
import thang.com.wref.util.NetworkUtil;
import thang.com.wref.R;
import thang.com.wref.Components.Retrofits.HarvesthelperRetrofit;

public class HarvesthelperActivity extends AppCompatActivity {
    private static final String TAG = "HarvesthelperActivity";

    private RecyclerView rcvHaverst;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private ImageView imgTitle;
    private RelativeLayout loadPage;
    private CoordinatorLayout cdlMain;
    private LottieAnimationView lottieLoadingData;

    private HarvesthelperAdapter harvesthelperAdapter;

    private NetworkUtil networkUtill;
    private Retrofit retrofit;
    private HarvesthelperRetrofit harvesthelperRetrofit;
    private ArrayList<CropsModel> cropsModelsArr;
    private String title = "", idPlant = "";

    private SharedPreferencesManagement sharedPreferencesManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harvesthelper);
        sharedPreferencesManagement = new SharedPreferencesManagement(this);

        networkUtill = new NetworkUtil();
        retrofit = networkUtill.getRetrofit();

        mappingView();
        setUpToolBar();
        getData();
    }
    private void mappingView(){
        rcvHaverst = (RecyclerView) findViewById(R.id.rcvHaverst);
        imgTitle = (ImageView) findViewById(R.id.imgTitle);
        loadPage = (RelativeLayout) findViewById(R.id.loadPage);
        lottieLoadingData = (LottieAnimationView) findViewById(R.id.lottieLoadingData);
        cdlMain = (CoordinatorLayout) findViewById(R.id.cdlMain);

        rcvHaverst.setHasFixedSize(true);
        LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvHaverst.setLayoutManager(linearLayoutManager);
    }

    private void setUpToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title = getIntent().getStringExtra("plant");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(title.equals("Dưa leo")) idPlant = "60965426585c4e179ce5bbb7";
        else if(title.equals("Cà chua")) idPlant = "609653d1585c4e179ce5bbb6";

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.CollapsingToolbarLayout);

        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0)
                {
                    collapsingToolbarLayout.setExpandedTitleColor(Color.BLACK);
                    toolbar.getNavigationIcon().setTint(Color.BLACK);
                }
                else
                {
                    collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
                    toolbar.getNavigationIcon().setTint(Color.WHITE);
                }
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData(){
        cropsModelsArr = new ArrayList<>();
        // get data from api
        harvesthelperRetrofit = retrofit.create(HarvesthelperRetrofit.class);
        // sharedPreferencesManagement.getTOKEN() : get token user authentication
        // idPlant : required params  for api
        Call<HarvesthelperModel> call = harvesthelperRetrofit.getByID(sharedPreferencesManagement.getTOKEN(), idPlant);
        call.enqueue(new Callback<HarvesthelperModel>() {
            @Override
            public void onResponse(Call<HarvesthelperModel> call, Response<HarvesthelperModel> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(HarvesthelperActivity.this, "lỗi mạng", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    HarvesthelperModel harvesthelperModel = response.body();
                    // set data to view after get data success
                    cropsModelsArr.add(new CropsModel("Loại cây trồng", harvesthelperModel.getName()));
                    cropsModelsArr.add(new CropsModel("Sơ lược về cây trồng", harvesthelperModel.getDescription()));
                    cropsModelsArr.add(new CropsModel("Mặt trời", harvesthelperModel.getOptimalSun()));
                    cropsModelsArr.add(new CropsModel("Đất", harvesthelperModel.getOptimalSoil()));
                    cropsModelsArr.add(new CropsModel("Những lưu ý khi trồng", harvesthelperModel.getPlantingConsiderations()));
                    cropsModelsArr.add(new CropsModel("Nên trồng khi nào?", harvesthelperModel.getWhenToPlant()));
                    cropsModelsArr.add(new CropsModel("Cách gieo hạt", harvesthelperModel.getGrowingFromSeed()));
                    cropsModelsArr.add(new CropsModel("Cấy", harvesthelperModel.getTransplanting()));
                    cropsModelsArr.add(new CropsModel("Khoảng cách", harvesthelperModel.getSpacing()));
                    cropsModelsArr.add(new CropsModel("Tưới nước", harvesthelperModel.getWatering()));
                    cropsModelsArr.add(new CropsModel("Thu hoạch", harvesthelperModel.getHarvesting()));
                    // set image
                    Glide.with(HarvesthelperActivity.this).load(
                            "https://res-5.cloudinary.com/do6bw42am/image/upload/c_scale,f_auto,h_300/v1/"+harvesthelperModel.getImageUrl()
                    ).into(imgTitle);
                    // hidden loading icon
                    loadPage.setVisibility(View.GONE);
                    lottieLoadingData.clearAnimation();
                    cdlMain.setVisibility(View.VISIBLE);
                }
                harvesthelperAdapter.notifyDataSetChanged();
                call.cancel();
            }

            @Override
            public void onFailure(Call<HarvesthelperModel> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                call.cancel();
            }
        });
        // set data to recyclerview
        harvesthelperAdapter = new HarvesthelperAdapter(cropsModelsArr, this);
        rcvHaverst.setAdapter(harvesthelperAdapter);
    }
}