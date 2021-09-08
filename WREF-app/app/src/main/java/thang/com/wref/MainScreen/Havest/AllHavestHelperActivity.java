package thang.com.wref.MainScreen.Havest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import thang.com.wref.MainScreen.Adapters.AllHavestHelperAdapter;
import thang.com.wref.Components.Animation.RecyclerViewAnimation;
import thang.com.wref.LoginScreen.SharedPreferencesManagement;
import thang.com.wref.MainScreen.Models.HarvesthelperModel;
import thang.com.wref.util.NetworkUtil;
import thang.com.wref.R;
import thang.com.wref.Components.Retrofits.HarvesthelperRetrofit;

public class AllHavestHelperActivity extends AppCompatActivity {
    private static final String TAG = "AllHavestHelperActivity";

    private Toolbar toolbar;
    private RecyclerView rcvAllHavestHelper;

    private ArrayList<HarvesthelperModel> harvesthelperModelsArr;
    private NetworkUtil networkUtil;
    private Retrofit retrofit;
    private AllHavestHelperAdapter adapter;
    private HarvesthelperRetrofit harvesthelperRetrofit;
    private SharedPreferencesManagement sharedPreferencesManagement;

    private RecyclerViewAnimation recyclerViewAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_havest_helper);

        sharedPreferencesManagement = new SharedPreferencesManagement(this);
        networkUtil = new NetworkUtil();
        retrofit = networkUtil.getRetrofit();
        recyclerViewAnimation = new RecyclerViewAnimation();

        setUpToolBar();
        mapingView();
        getData();
    }

    private void setUpToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarInfor);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Kiến thức trồng trọt");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void mapingView() {
        rcvAllHavestHelper = (RecyclerView) findViewById(R.id.rcvAllHavestHelper);

        rcvAllHavestHelper.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false);
        rcvAllHavestHelper.setLayoutManager(linearLayoutManager);
        recyclerViewAnimation.setAnimationRecyclerviewVertical(rcvAllHavestHelper, TAG);
    }

    private void getData(){
        harvesthelperModelsArr = new ArrayList<>();
        harvesthelperRetrofit = retrofit.create(HarvesthelperRetrofit.class);
        // get data from api
        // sharedPreferencesManagement.getTOKEN() : get token user authentication
        Call<List<HarvesthelperModel>> listCall = harvesthelperRetrofit.getAll(sharedPreferencesManagement.getTOKEN());
        listCall.enqueue(new Callback<List<HarvesthelperModel>>() {
            @Override
            public void onResponse(Call<List<HarvesthelperModel>> call, Response<List<HarvesthelperModel>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(AllHavestHelperActivity.this, "lỗi mạng", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    List<HarvesthelperModel> harvesthelperModels = response.body();
                    for (HarvesthelperModel data : harvesthelperModels){
                        harvesthelperModelsArr.add(data);
                    }
                }
                adapter.notifyDataSetChanged();
                call.cancel();
            }

            @Override
            public void onFailure(Call<List<HarvesthelperModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                call.cancel();
            }
        });
        // set data to recyclerview
        adapter = new AllHavestHelperAdapter(harvesthelperModelsArr, this);
        rcvAllHavestHelper.setAdapter(adapter);
    }
}