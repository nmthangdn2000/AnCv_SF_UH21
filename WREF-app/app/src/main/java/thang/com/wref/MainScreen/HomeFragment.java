package thang.com.wref.MainScreen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

import thang.com.wref.LoginScreen.LoginActivity;
import thang.com.wref.LoginScreen.SharedPreferencesManagement;
import thang.com.wref.MainScreen.Disaster.Flood.FloodDisasterActivity;
import thang.com.wref.MainScreen.Havest.AllHavestHelperActivity;
import thang.com.wref.MainScreen.Havest.CropYieldActivity;
import thang.com.wref.MainScreen.Havest.HarvesthelperActivity;
import thang.com.wref.MainScreen.Map.MapActivity;
import thang.com.wref.MainScreen.Weather.DetailWeatherFragment;
import thang.com.wref.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {
    private static final String TAG = "HomeFragment";
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private GoogleMap map;
    private View view, gradientMap;
    private RelativeLayout rltWeather, rlvLogOut, rltDisaster;
    private TextView btnShownAllHarvestHelper;
    private LinearLayout lnlTrackProgress, lnlTrackProgress2, lnlCaChuaHavestHelper, lnlDuaLeoHavestHelper;
    private Task<Location> task;
    private FusedLocationProviderClient client;
    private SharedPreferencesManagement sharedPreferencesManagement;
    private SupportMapFragment mapFragment;
    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        sharedPreferencesManagement = new SharedPreferencesManagement(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        client = LocationServices.getFusedLocationProviderClient(getContext().getApplicationContext());
        mappingView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        openGooogleMap();
    }
    // google map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        getLocationPermission();
    }
    private void openGooogleMap(){
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    private void mappingView(){
        gradientMap = (View) view.findViewById(R.id.gradientMap);
        rltWeather = (RelativeLayout) view.findViewById(R.id.rltWeather);
        rltDisaster = (RelativeLayout) view.findViewById(R.id.rtlDisaster);
        lnlTrackProgress = (LinearLayout) view.findViewById(R.id.lnlTrackProgress);
        lnlTrackProgress2 = (LinearLayout) view.findViewById(R.id.lnlTrackProgress2);
        lnlCaChuaHavestHelper = (LinearLayout) view.findViewById(R.id.lnlCaChuaHavestHelper);
        lnlDuaLeoHavestHelper = (LinearLayout) view.findViewById(R.id.lnlDuaLeoHavestHelper);
        btnShownAllHarvestHelper = (TextView) view.findViewById(R.id.btnShownAllHarvestHelper);
        rlvLogOut = (RelativeLayout) view.findViewById(R.id.rlvLogOut);

        rltWeather.setOnClickListener(this);
        rltDisaster.setOnClickListener(this);
        gradientMap.setOnClickListener(this);
        lnlTrackProgress.setOnClickListener(this);
        lnlTrackProgress2.setOnClickListener(this);
        lnlCaChuaHavestHelper.setOnClickListener(this);
        lnlDuaLeoHavestHelper.setOnClickListener(this);
        btnShownAllHarvestHelper.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rtlDisaster:
                clickDisaster();
                break;
            case R.id.gradientMap:
                clickMapView();
                break;
            case R.id.rltWeather:
                clickWeather();
                break;
            case R.id.lnlTrackProgress:
                clickTrackProgress();
                break;
            case R.id.lnlTrackProgress2:
                clickTrackProgress2();
                break;
            case R.id.lnlCaChuaHavestHelper:
                clickCaChuaHavestHelper();
                break;
            case R.id.lnlDuaLeoHavestHelper:
                clickDuaLeoHavestHelper();
                break;
            case R.id.btnShownAllHarvestHelper:
                clickShownAllHavestHelper();
                break;
            case R.id.rlvLogOut:
                sharedPreferencesManagement.clearData();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
                break;
            default:
                break;
        }
    }

    private void clickDisaster() {
        Intent intent = new Intent(getContext(), FloodDisasterActivity.class);
        getActivity().startActivity(intent);
    }

    private void clickShownAllHavestHelper(){
        Intent intent = new Intent(getContext(), AllHavestHelperActivity.class);
        getActivity().startActivity(intent);
    }

    private void clickCaChuaHavestHelper(){
        Intent intent = new Intent(getContext(), HarvesthelperActivity.class);
        intent.putExtra("plant", "Cà chua");
        getActivity().startActivity(intent);
    }
    private void clickDuaLeoHavestHelper(){
        Intent intent = new Intent(getContext(), HarvesthelperActivity.class);
        intent.putExtra("plant", "Dưa leo");
        getActivity().startActivity(intent);
    }
    private void clickMapView(){
        Intent intent = new Intent(getContext(), MapActivity.class);
        getActivity().startActivity(intent);
    }
    private void clickWeather(){
        Intent intent = new Intent(getContext(), DetailWeatherFragment.class);
        getActivity().startActivity(intent);
    }
    // ask for permission
    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(false);
            task = client.getLastLocation();
            getCurrentLocation();
            return;
        }else{
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void getCurrentLocation() {
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    float lati = (float) location.getLatitude();
                    float longti = (float) location.getLongitude();
                    List<Address> addressesList = null;
                    Geocoder geocoder = new Geocoder(getContext());
                    try {
                        addressesList = geocoder.getFromLocation(lati, longti, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "onSuccess: " + addressesList.get(0).getAdminArea());
                    String addressLine = addressesList.get(0).getAddressLine(0);

                    sharedPreferencesManagement.setLocation(lati, longti, addressLine);
                    LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                    map.addMarker(new MarkerOptions().position(latLng));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }
            }
        });
    }

    private void clickTrackProgress(){
        Intent intent = new Intent(getContext(), CropYieldActivity.class);
        intent.putExtra("plant", "Dưa leo");
        getActivity().startActivity(intent);
    }
    private void clickTrackProgress2(){
        Intent intent = new Intent(getContext(), CropYieldActivity.class);
        intent.putExtra("plant", "Cà chua");
        getActivity().startActivity(intent);
    }
    @Override
    public void onStart() {
        super.onStart();
        mapFragment.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapFragment.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapFragment.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapFragment.onDestroy();
    }
}