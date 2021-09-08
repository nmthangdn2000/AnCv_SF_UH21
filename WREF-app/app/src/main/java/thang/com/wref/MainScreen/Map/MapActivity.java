package thang.com.wref.MainScreen.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.Layer;
import com.google.maps.android.data.kml.KmlLayer;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import thang.com.wref.LoginScreen.SharedPreferencesManagement;
import thang.com.wref.MainScreen.Weather.IconWeather;
import thang.com.wref.MainScreen.Models.WeatherLocationModel;
import thang.com.wref.MainScreen.Weather.TimeCaculater;
import thang.com.wref.R;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener{
    private final static String TAG = "MapActivity";
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private GoogleMap map;
    private MapView mapView;
    private KmlLayer layer;
    private Vector<LatLng> latLngs;
    private Polygon polygon;
    private SearchView searchView;
    private FusedLocationProviderClient client;
    private LinearLayout location, typeMap, showTypeMap, btnChangeMapGis, btnChangeGoogleMap, btnMapTemp, btnMapCloud, btnMapRain;
    private Task<Location> task;
    private Marker marker;
    private FrameLayout frameInforTouchLocation;
    private ImageView iconsearch;
    private DetailLocationMap detailLocationMap;
    private Bundle savedInstanceStates;
    private Toolbar toolbar;
    // gis
    private ArcGISMap mapGis;
    private ServiceFeatureTable serviceFeatureTable;
    private FeatureLayer layerGis;
    private String freatureLayerUrl = "https://services3.arcgis.com/U26uBjSD32d7xvm2/arcgis/rest/services/MyMapService_DaNang/FeatureServer/1";
    private FragmentTransaction fragmentTransaction;
    private SupportMapFragment mapFragment;
    private TileOverlay tileOverlay;
    private SharedPreferencesManagement sharedPreferencesManagement;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private LottieAnimationView lottieLoading;
    private RelativeLayout dataChart, rltDataCharBottm;
    private LineChart chart;
    private TextView txtDayOfWeek1, txtDay1, txtTypeWeather1, txtWind1, txtDataWind1,
            txtDayOfWeek2, txtDay2, txtTypeWeather2, txtWind2, txtDataWind2,
            txtDayOfWeek3, txtDay3, txtTypeWeather3, txtWind3, txtDataWind3,
            txtDayOfWeek4, txtDay4, txtTypeWeather4, txtWind4, txtDataWind4,
            txtDayOfWeek5, txtDay5, txtTypeWeather5, txtWind5, txtDataWind5;
    private ImageView imgWeather1, imgWeather2, imgWeather3, imgWeather4, imgWeather5;
    //    private WeatherLocationModel weatherLocationModel;
    private DetailLocationMap.setDataDetailWeather setDataDetailWeather;

    private IconWeather iconWeather;
    private TimeCaculater timeCaculater;
    private Calendar cal;
    private int day = 0;
    private int month = 0;
    private String addressLocationInfor = "";

    private static final int COLOR_WHITE_ARGB = 0xffffffff;
    private static final int COLOR_GREEN_ARGB = 0xff388E3C;
    private static final int COLOR_PURPLE_ARGB = 0xff81C784;
    private static final int COLOR_ORANGE_ARGB = 0xffF57F17;
    private static final int COLOR_BLUE_ARGB = 0xffF9A825;
    private static final int COLOR_BLACK_ARGB = 0xff000000;

    private static final int POLYGON_STROKE_WIDTH_PX = 8;
    private static final int PATTERN_DASH_LENGTH_PX = 20;
    private static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);

    private static final int PATTERN_GAP_LENGTH_PX = 20;
    private static final PatternItem DOT = new Dot();
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    // Create a stroke pattern of a gap followed by a dash.
    private static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);

    // Create a stroke pattern of a dot followed by a gap, a dash, and another gap.
    private static final List<PatternItem> PATTERN_POLYGON_BETA =
            Arrays.asList(DOT, GAP, DASH, GAP);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        latLngs = new Vector<>();
        sharedPreferencesManagement = new SharedPreferencesManagement(MapActivity.this);
        iconWeather = new IconWeather();
        timeCaculater = new TimeCaculater();
        savedInstanceStates = savedInstanceState;
        mappingView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        openGooogleMap();
        searchLocation();
        setDataDetailWeather();
        clospaneSlidingUpPanel();
    }

    private void openGooogleMap(){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mapFragment = SupportMapFragment.newInstance();
        fragmentTransaction.replace(R.id.mapHome, mapFragment, "googlemap").commit();
        mapFragment.getMapAsync(this);
    }
    private void mappingView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        mapView = (MapView) findViewById(R.id.mapHome);
        searchView = (SearchView) findViewById(R.id.searchView);
        client = LocationServices.getFusedLocationProviderClient(this.getApplicationContext());
        location = (LinearLayout) findViewById(R.id.location);
        frameInforTouchLocation = (FrameLayout) findViewById(R.id.frameInforTouchLocation);
        iconsearch = (ImageView) findViewById(R.id.iconsearch);
        typeMap = (LinearLayout) findViewById(R.id.typeMap);
        showTypeMap = (LinearLayout) findViewById(R.id.showTypeMap);
        btnChangeMapGis = (LinearLayout) findViewById(R.id.btnChangeMapGis);
        btnChangeGoogleMap = (LinearLayout) findViewById(R.id.btnChangeGoogleMap);
        btnMapTemp = (LinearLayout) findViewById(R.id.btnMapTemp);
        btnMapCloud = (LinearLayout) findViewById(R.id.btnMapCloud);
        btnMapRain = (LinearLayout) findViewById(R.id.btnMapRain);
        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.slidingUpPanelLayout);
        lottieLoading = (LottieAnimationView) findViewById(R.id.lottieLoading);
        dataChart = (RelativeLayout) findViewById(R.id.rltdataChart);
        rltDataCharBottm = (RelativeLayout) findViewById(R.id.rltDataCharBottm);
        chart = (LineChart) findViewById(R.id.combinedChart);

        txtDayOfWeek1 = (TextView) findViewById(R.id.txtDayOfWeek1);
        txtDay1 = (TextView) findViewById(R.id.txtDay1);
        txtTypeWeather1 = (TextView) findViewById(R.id.txtTypeWeather1);
        txtWind1 = (TextView) findViewById(R.id.txtWind1);
        txtDataWind1 = (TextView) findViewById(R.id.txtDataWind1);

        txtDayOfWeek2 = (TextView) findViewById(R.id.txtDayOfWeek2);
        txtDay2 = (TextView) findViewById(R.id.txtDay2);
        txtTypeWeather2 = (TextView) findViewById(R.id.txtTypeWeather2);
        txtWind2 = (TextView) findViewById(R.id.txtWind2);
        txtDataWind2 = (TextView) findViewById(R.id.txtDataWind2);

        txtDayOfWeek3 = (TextView) findViewById(R.id.txtDayOfWeek3);
        txtDay3= (TextView) findViewById(R.id.txtDay3);
        txtTypeWeather3 = (TextView) findViewById(R.id.txtTypeWeather3);
        txtWind3 = (TextView) findViewById(R.id.txtWind3);
        txtDataWind3 = (TextView) findViewById(R.id.txtDataWind3);

        txtDayOfWeek4 = (TextView) findViewById(R.id.txtDayOfWeek4);
        txtDay4 = (TextView) findViewById(R.id.txtDay4);
        txtTypeWeather4 = (TextView) findViewById(R.id.txtTypeWeather4);
        txtWind4 = (TextView) findViewById(R.id.txtWind4);
        txtDataWind4 = (TextView) findViewById(R.id.txtDataWind4);

        txtDayOfWeek5 = (TextView) findViewById(R.id.txtDayOfWeek5);
        txtDay5 = (TextView) findViewById(R.id.txtDay5);
        txtTypeWeather5 = (TextView) findViewById(R.id.txtTypeWeather5);
        txtWind5 = (TextView) findViewById(R.id.txtWind5);
        txtDataWind5 = (TextView) findViewById(R.id.txtDataWind5);

        imgWeather1 = (ImageView) findViewById(R.id.imgWeather1);
        imgWeather2 = (ImageView) findViewById(R.id.imgWeather2);
        imgWeather3 = (ImageView) findViewById(R.id.imgWeather3);
        imgWeather4 = (ImageView) findViewById(R.id.imgWeather4);
        imgWeather5 = (ImageView) findViewById(R.id.imgWeather5);


        btnMapTemp.setOnClickListener(this);
        btnMapCloud.setOnClickListener(this);
        btnMapRain.setOnClickListener(this);
        iconsearch.setOnClickListener(this);
        typeMap.setOnClickListener(this);
        location.setOnClickListener(this);
        btnChangeMapGis.setOnClickListener(this);
        btnChangeGoogleMap.setOnClickListener(this);

    }
    private void searchLocation() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchLocation(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                searchLocation(newText);
                return false;
            }
        });
    }
    // google map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng latLng = new LatLng(16.051841, 108.168782);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        setupGoogleMap();
        getLocationPermission();
    }
    // type map (Temperature, Clouds, Precipitation)
    private void changeTypeMap(String mapName) {
        TileProvider tileProvider = new UrlTileProvider(256, 256) {
            @Override
            public URL getTileUrl(int x, int y, int zoom) {
                String s = String.format(
                        "https://tile.openweathermap.org/map/%s/%d/%d/%d.png?appid=33e61186254ecc50e7d4298f5fb97f4d",
                        mapName, zoom, x, y);
                Log.d(TAG, "getTileUrl: "+s);
                if (!checkTileExists(x, y, zoom)) {
                    return null;
                }
                try {
                    return new URL(s);
                } catch (MalformedURLException e) {
                    throw new AssertionError(e);
                }
            }
            private boolean checkTileExists(int x, int y, int zoom) {
                int minZoom = 1;
                int maxZoom = 20;
                return (zoom >= minZoom && zoom <= maxZoom);
            }
        };
        if (tileOverlay != null)
            tileOverlay.remove();
        tileOverlay = map.addTileOverlay(new TileOverlayOptions()
                .tileProvider(tileProvider));
    }

    private void setOnclickFeature(KmlLayer layer) {
        layer.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
            @Override
            public void onFeatureClick(Feature feature) {
                String addressName = feature.getProperties().toString();
                String[] parts = addressName.split("=");
                String part2 = parts[1]; // 034556
                searchLocation(method(part2));
                Log.d(TAG, "onFeatureClick: "+method(part2));
                ArrayList o = (ArrayList) feature.getGeometry().getGeometryObject();
                ArrayList<LatLng> b = (ArrayList<LatLng>) o.get(0);
                PolylineMap(b);

            }
        });
    }

    private void PolylineMap(ArrayList<LatLng> b) {
        latLngs.clear();
        for (LatLng latLng : b) {
            latLngs.add(new LatLng(latLng.latitude, latLng.longitude));
        }
        if (polygon != null)
            polygon.remove();
        polygon = map.addPolygon(new PolygonOptions()
                .clickable(true)
                .addAll(latLngs));
        polygon.setTag("alpha");
        stylePolygon(polygon);
    }

    private void searchLocation(String query) {
        List<Address> addressesList = null;
        if (query != null || !query.equals("")) {
            Geocoder geocoder = new Geocoder(MapActivity.this);
            try {
                addressesList = geocoder.getFromLocationName(query, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addressesList != null && addressesList.size() > 0) {
                Address address = addressesList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                Log.d(TAG, "searchLocation: "+latLng);
                if(marker != null)
                    marker.remove();
                marker = map.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(query)
                        .snippet(address.getAddressLine(0)));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                showDetailLocationMap(query, address.getAddressLine(0), (float) address.getLatitude(), (float) address.getLongitude());
            }
        }
    }
    // ask for permission
    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(MapActivity.this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(false);
            task = client.getLastLocation();
            getCurrentLocation();
            return;
        }else{
            ActivityCompat.requestPermissions(MapActivity.this,
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
                    Geocoder geocoder = new Geocoder(MapActivity.this);
                    try {
                        addressesList = geocoder.getFromLocation(lati, longti, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String addressLine = addressesList.get(0).getAddressLine(0);
                    // set current location to sharedPreferencesManagement
                    sharedPreferencesManagement.setLocation(lati, longti, addressLine);
                    LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }
            }
        });
    }
    @Override
    public void onPause(){
        mapView.pause();
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
        mapView.resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.dispose();
    }
    public String method(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ']') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }
    private void showDetailLocationMap(String query, String address, float lati, float longti) {
        if(getSupportFragmentManager().findFragmentByTag("detailLocationMap") != null)
            getSupportFragmentManager().beginTransaction().remove(detailLocationMap).commit();
        addressLocationInfor = address;
        detailLocationMap = new DetailLocationMap(this.getApplicationContext(), query, address, lati, longti, lottieLoading, dataChart, chart, setDataDetailWeather);
        getSupportFragmentManager().beginTransaction().add(R.id.frameInforTouchLocation, detailLocationMap, "detailLocationMap").commit();
        if(iconsearch.getTag().equals("location")){
            iconsearch.setTag("backMap");
            slidingUpPanelLayout.setAnchorPoint(0.35f);
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
        }
    }
    private void clospaneSlidingUpPanel(){
        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.d(TAG, "onPanelSlide: mở");
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.d(TAG, "onPanelStateChanged: "+ newState);
                if(newState == SlidingUpPanelLayout.PanelState.COLLAPSED){
                    // when close SlidingUpPanelLayout
                    rltDataCharBottm.setVisibility(View.INVISIBLE);
                    iconsearch.setTag("location");
//                    iconsearch.setImageResource(R.drawable.ic_baseline_location_on_24);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setDisplayShowHomeEnabled(false);
                    getSupportActionBar().setTitle("");
                }else if(newState == SlidingUpPanelLayout.PanelState.EXPANDED){
                    // when open full SlidingUpPanelLayout
                    if(rltDataCharBottm.getVisibility() != View.VISIBLE){
                        rltDataCharBottm.setVisibility(View.VISIBLE);
                        setSupportActionBar(toolbar);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        getSupportActionBar().setDisplayShowHomeEnabled(true);
                        getSupportActionBar().setTitle(addressLocationInfor);
                    }
                }else if(newState == SlidingUpPanelLayout.PanelState.ANCHORED){
                    // when open 1/3 SlidingUpPanelLayout
                    if(rltDataCharBottm.getVisibility() == View.VISIBLE) {
                        rltDataCharBottm.setVisibility(View.INVISIBLE);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        getSupportActionBar().setDisplayShowHomeEnabled(false);
                        getSupportActionBar().setTitle("aaa");
                    }
                }
            }
        });
    }
    public void setDataDetailWeather(){
        setDataDetailWeather = new DetailLocationMap.setDataDetailWeather() {
            @Override
            public void setDataDetailWeather(WeatherLocationModel weatherLocationModel) {
                ArrayList<String> strings= new ArrayList<>();
                cal = Calendar.getInstance();
                strings.clear();
                cal.clear();
                for (int index = 0; index < 5; index++) {
                    cal.setTime(new Date((long) weatherLocationModel.getWeather().getDaily()[index].getDt()*1000));
                    day = cal.get(Calendar.DAY_OF_MONTH);
                    month = cal.get(Calendar.MONTH);
                    cal.clear();
                    strings.add(day+"/"+(month+1));
                }
                txtDayOfWeek1.setText("Hôm nay");
                txtDay1.setText(strings.get(0));
                txtTypeWeather1.setText(""+iconWeather.typeWeather(weatherLocationModel.getWeather().getCurrent().getWeather()[0].getDescription()));
                txtWind1.setText(iconWeather.wind_Deg(weatherLocationModel.getWeather().getCurrent().getWind_deg()));
                txtDataWind1.setText(weatherLocationModel.getWeather().getCurrent().getWind_speed()+" km/h");
                imgWeather1.setImageResource(iconWeather.IconWeather(weatherLocationModel.getWeather().getCurrent().getWeather()[0].getMain()));

                txtDayOfWeek2.setText("Ngày mai");
                txtDay2.setText(strings.get(1));
                txtTypeWeather2.setText(""+iconWeather.typeWeather(weatherLocationModel.getWeather().getDaily()[1].getWeather()[0].getDescription()));
                txtWind2.setText(iconWeather.wind_Deg(weatherLocationModel.getWeather().getDaily()[1].getWind_deg()));
                txtDataWind2.setText(weatherLocationModel.getWeather().getDaily()[1].getWind_speed()+" km/h");
                imgWeather2.setImageResource(iconWeather.IconWeather(weatherLocationModel.getWeather().getDaily()[1].getWeather()[0].getMain()));

                txtDayOfWeek3.setText(timeCaculater.dayStringFormat(weatherLocationModel.getWeather().getDaily()[2].getDt()));
                txtDay3.setText(strings.get(2));
                txtTypeWeather3.setText(""+iconWeather.typeWeather(weatherLocationModel.getWeather().getDaily()[2].getWeather()[0].getDescription()));
                txtWind3.setText(iconWeather.wind_Deg(weatherLocationModel.getWeather().getDaily()[2].getWind_deg()));
                txtDataWind3.setText(weatherLocationModel.getWeather().getDaily()[2].getWind_speed()+" km/h");
                imgWeather3.setImageResource(iconWeather.IconWeather(weatherLocationModel.getWeather().getDaily()[2].getWeather()[0].getMain()));

                txtDayOfWeek4.setText(timeCaculater.dayStringFormat(weatherLocationModel.getWeather().getDaily()[3].getDt()));
                txtDay4.setText(strings.get(3));
                txtTypeWeather4.setText(""+iconWeather.typeWeather(weatherLocationModel.getWeather().getDaily()[3].getWeather()[0].getDescription()));
                txtWind4.setText(iconWeather.wind_Deg(weatherLocationModel.getWeather().getDaily()[3].getWind_deg()));
                txtDataWind4.setText(weatherLocationModel.getWeather().getDaily()[3].getWind_speed()+" km/h");
                imgWeather4.setImageResource(iconWeather.IconWeather(weatherLocationModel.getWeather().getDaily()[3].getWeather()[0].getMain()));

                txtDayOfWeek5.setText(timeCaculater.dayStringFormat(weatherLocationModel.getWeather().getDaily()[4].getDt()));
                txtDay5.setText(strings.get(4));
                txtTypeWeather5.setText(""+iconWeather.typeWeather(weatherLocationModel.getWeather().getDaily()[4].getWeather()[0].getDescription()));
                txtWind5.setText(iconWeather.wind_Deg(weatherLocationModel.getWeather().getDaily()[4].getWind_deg()));
                txtDataWind5.setText(weatherLocationModel.getWeather().getDaily()[4].getWind_speed()+" km/h");
                imgWeather5.setImageResource(iconWeather.IconWeather(weatherLocationModel.getWeather().getDaily()[4].getWeather()[0].getMain()));
            }
        };
    }
    private void clearImg(){

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.location:
                if(showTypeMap.getVisibility() == View.VISIBLE)
                    showTypeMap.setVisibility(View.INVISIBLE);
                getLocationPermission();
                break;
            case R.id.iconsearch:
                if (polygon != null)
                    polygon.remove();
                if(showTypeMap.getVisibility() == View.VISIBLE)
                    showTypeMap.setVisibility(View.INVISIBLE);
                if(iconsearch.getTag().equals("backMap")){
                    iconsearch.setTag("location");
//                    iconsearch.setImageResource(R.drawable.ic_baseline_location_on_24);
                    getSupportFragmentManager().beginTransaction().remove(detailLocationMap).commit();
                    Animation animationDown = AnimationUtils.loadAnimation(MapActivity.this, R.anim.bottom_down);
                    frameInforTouchLocation.startAnimation(animationDown);
                }
                finish();
                break;
            case R.id.typeMap:
                if(showTypeMap.getVisibility() == View.VISIBLE)
                    showTypeMap.setVisibility(View.INVISIBLE);
                else showTypeMap.setVisibility(View.VISIBLE);
                break;
            case R.id.btnChangeGoogleMap:
                mapView.setMap(null);
                openGooogleMap();
                setupGoogleMap();
                showTypeMap.setVisibility(View.INVISIBLE);
                break;
            case R.id.btnChangeMapGis:
                setupMapGis();
                showTypeMap.setVisibility(View.INVISIBLE);
                break;
            case R.id.btnMapTemp:
                checkMap();
                changeTypeMap("temp_new" );
                showTypeMap.setVisibility(View.INVISIBLE);
                break;
            case R.id.btnMapRain:
                checkMap();
                changeTypeMap("precipitation_new" );
                showTypeMap.setVisibility(View.INVISIBLE);
                break;
            case R.id.btnMapCloud:
                checkMap();
                changeTypeMap("clouds_new" );
                showTypeMap.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }
    private void checkMap(){
        if(getSupportFragmentManager().findFragmentByTag("googlemap") == null){
            mapView.setMap(null);
            openGooogleMap();
            setupGoogleMap();
        }
    }
    private void setupGoogleMap(){
        try {
            layer = new KmlLayer(map, R.raw.dananga, MapActivity.this.getApplicationContext());
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        layer.addLayerToMap();

        setOnclickFeature(layer);
    }
    private void setupMapGis() {
        if(getSupportFragmentManager().findFragmentByTag("googlemap") != null)
            getSupportFragmentManager().beginTransaction().remove(mapFragment).commit();
        if (mapView != null) {
            Basemap.Type basemapType = Basemap.Type.OPEN_STREET_MAP;
            double latitude = 16.048856; //16.048856, 108.201523
            double longitude = 108.201523;
            int levelOfDetail = 11;
            mapGis = new ArcGISMap(basemapType, latitude, longitude, levelOfDetail);
            serviceFeatureTable = new ServiceFeatureTable(freatureLayerUrl);
            layerGis = new FeatureLayer(serviceFeatureTable);

            mapGis.getOperationalLayers().add(layerGis);
            mapView.setMap(mapGis);
        }
    }
    private void stylePolygon(Polygon polygon) {
        String type = "";
        // Get the data object stored with the polygon.
        if (polygon.getTag() != null) {
            type = polygon.getTag().toString();
        }

        List<PatternItem> pattern = null;
        int strokeColor = COLOR_BLACK_ARGB;
        int fillColor = COLOR_WHITE_ARGB;

        switch (type) {
            // If no type is given, allow the API to use the default.
            case "alpha":
                // Apply a stroke pattern to render a dashed line, and define colors.
                pattern = PATTERN_POLYGON_ALPHA;
                strokeColor = COLOR_GREEN_ARGB;
                fillColor = COLOR_PURPLE_ARGB;
                break;
            case "beta":
                // Apply a stroke pattern to render a line of dots and dashes, and define colors.
                pattern = PATTERN_POLYGON_BETA;
                strokeColor = COLOR_ORANGE_ARGB;
                fillColor = COLOR_BLUE_ARGB;
                break;
        }

        polygon.setStrokePattern(pattern);
        polygon.setStrokeWidth(POLYGON_STROKE_WIDTH_PX);
        polygon.setStrokeColor(strokeColor);
        polygon.setFillColor(fillColor);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if( requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }
    }
}