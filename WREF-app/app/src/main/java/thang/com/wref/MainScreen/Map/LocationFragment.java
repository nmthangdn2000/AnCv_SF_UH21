package thang.com.wref.MainScreen.Map;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import thang.com.wref.MainScreen.Adapters.LocationAdapter;
import thang.com.wref.LoginScreen.SharedPreferencesManagement;
import thang.com.wref.MainScreen.Models.LocationModel;
import thang.com.wref.Components.Retrofits.LocationRetrofit;
import thang.com.wref.util.NetworkUtil;
import thang.com.wref.R;

public class LocationFragment extends Fragment {
    private static final String TAG = "LocationFragment";

    private View view;
    private SearchView searchView;
    private RecyclerView rcvLocation;

    private NetworkUtil networkUtil;
    private Retrofit retrofit;
    private LocationRetrofit locationRetrofit;
    private ArrayList<LocationModel> arrlocationModels;
    private SharedPreferencesManagement sharedPreferencesManagement;
    private LocationAdapter locationAdapter;
    private LocationAdapter.clickItemLocation mListenenr;
    private TextView btnLocation;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    public LocationFragment(TextView btnLocation, SlidingUpPanelLayout slidingUpPanelLayout) {
        this.btnLocation = btnLocation;
        this.slidingUpPanelLayout = slidingUpPanelLayout;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkUtil = new NetworkUtil();
        retrofit = networkUtil.getRetrofit();
        arrlocationModels = new ArrayList<>();
        sharedPreferencesManagement = new SharedPreferencesManagement(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_location, container, false);
        setUpOnClickItemLocation();
        mappingView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData();
    }

    private void setUpOnClickItemLocation() {
        mListenenr = new LocationAdapter.clickItemLocation() {
            @Override
            public void onclick(int position) {
                btnLocation.setText(arrlocationModels.get(position).getName());
                btnLocation.setTag(arrlocationModels.get(position).getId());
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        };
    }

    private void mappingView() {
        searchView = (SearchView) view.findViewById(R.id.searchView);
        rcvLocation = (RecyclerView) view.findViewById(R.id.rcvLocation);

        rcvLocation.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext().getApplicationContext(), RecyclerView.VERTICAL, false
        );
        rcvLocation.setLayoutManager(linearLayoutManager);
    }
    private void getData(){
        locationRetrofit = retrofit.create(LocationRetrofit.class);
        // get all location from api
        Call<List<LocationModel>> listCall = locationRetrofit.getAllLocation(sharedPreferencesManagement.getTOKEN());
        listCall.enqueue(new Callback<List<LocationModel>>() {
            @Override
            public void onResponse(Call<List<LocationModel>> call, Response<List<LocationModel>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Lỗi mạng", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    List<LocationModel> locationModels = response.body();
                    for (LocationModel locationModel : locationModels){
                        arrlocationModels.add(locationModel);
                    }
                    locationAdapter.notifyDataSetChanged();
                }
                call.cancel();
            }

            @Override
            public void onFailure(Call<List<LocationModel>> call, Throwable t) {
                call.cancel();
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
        // set data to recyclerview
        locationAdapter = new LocationAdapter(arrlocationModels, getContext(), mListenenr);
        rcvLocation.setAdapter(locationAdapter);
    }
}