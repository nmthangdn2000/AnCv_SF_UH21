package thang.com.wref.MainScreen.Map;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import thang.com.wref.MainScreen.Adapters.DetailLocationMapAdapter;
import thang.com.wref.LoginScreen.SharedPreferencesManagement;
import thang.com.wref.Components.Retrofits.LocationRetrofit;
import thang.com.wref.MainScreen.Weather.IconWeather;
import thang.com.wref.MainScreen.Weather.TimeCaculater;
import thang.com.wref.MainScreen.Weather.currentWeather;
import thang.com.wref.MainScreen.Models.WeatherLocationModel;
import thang.com.wref.util.NetworkUtil;
import thang.com.wref.R;

public class DetailLocationMap extends Fragment {
    private static final String TAG = "DetailLocationMap";

    private View view;
    private RecyclerView rcvInfTouchLocation;
    private DetailLocationMapAdapter detailLocationMapAdapter;
    private TextView txtNameLocation, txtDesLocation;
    private ArrayList<String> arrImg;
    private Context context;
    private Retrofit retrofit;
    private NetworkUtil networkUtil;
    private String nameLocation="", address ="", addressLine="";

    private SharedPreferencesManagement sharedPreferencesManagement;
    private LocationRetrofit locationRetrofit;
    private float lati=0, longti =0;
    private currentWeather weatherCurrent;
    private IconWeather iconWeather;
    private ImageView imgWeather;
    private LinearLayout notImg;
    private LottieAnimationView lottieLoadingData;
    private RelativeLayout rltData, dataChart;
    private ArrayList<String> tack;
    private WeatherLocationModel weatherLocationModel;
    private TimeCaculater timeCaculater;
    private ArrayList<Entry> entries;
    private LottieAnimationView lottieLoading;
    private LineChart chart;
    private setDataDetailWeather setDataDetailWeather;


    public DetailLocationMap(Context context, String nameLocation, String address, float lati, float longti, LottieAnimationView lottieLoading, RelativeLayout dataChart, LineChart chart, setDataDetailWeather setDataDetailWeather) {
        this.context = context;
        this.nameLocation = nameLocation;
        this.address = address;
        this.lati = lati;
        this.longti = longti;
        this.lottieLoading = lottieLoading;
        this.dataChart = dataChart;
        this.chart = chart;
        this.setDataDetailWeather = setDataDetailWeather;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrImg = new ArrayList<>();
        networkUtil = new NetworkUtil();
        retrofit = networkUtil.getRetrofit();
        iconWeather = new IconWeather();
        tack = new ArrayList<>();
        timeCaculater = new TimeCaculater();
        entries = new ArrayList<>();
        sharedPreferencesManagement = new SharedPreferencesManagement(context);
        String[] strings = address.split(", ");
        addressLine = strings[0];
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail_location_maps, container, false);
        mappingView();
        setUpRecyclerView();
        getData();
        return view;
    }

    private void mappingView() {
        rcvInfTouchLocation = (RecyclerView) view.findViewById(R.id.rcvInfTouchLocation);
        txtNameLocation = (TextView) view.findViewById(R.id.txtNameLocation);
        txtDesLocation = (TextView) view.findViewById(R.id.txtDesLocation);
        imgWeather = (ImageView) view.findViewById(R.id.imgWeather);
        notImg = (LinearLayout) view.findViewById(R.id.notImg);
        lottieLoadingData = (LottieAnimationView) view.findViewById(R.id.lottieLoadingData);
        rltData = (RelativeLayout) view.findViewById(R.id.rltData);


        txtNameLocation.setText(address);

    }

    private void setUpRecyclerView() {
        rcvInfTouchLocation.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                context, RecyclerView.HORIZONTAL, false
        );
        rcvInfTouchLocation.setLayoutManager(linearLayoutManager);

    }
    // get weather data with location from api
    private void getData(){
        lottieLoading.setVisibility(View.VISIBLE);
        dataChart.setVisibility(View.INVISIBLE);
        locationRetrofit = retrofit.create(LocationRetrofit.class);
        Call<WeatherLocationModel> weatherLocationModelCall = locationRetrofit.getWeatherLocation(sharedPreferencesManagement.getTOKEN(), addressLine, lati, longti);
        weatherLocationModelCall.enqueue(new Callback<WeatherLocationModel>() {
            @Override
            public void onResponse(Call<WeatherLocationModel> call, Response<WeatherLocationModel> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, "Lỗi mạng", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    weatherLocationModel = response.body();

                    Log.d(TAG, "onResponse: "+weatherLocationModel.getMedia());
                    for (int i = 0; i < weatherLocationModel.getMedia().length; i++){
                        arrImg.add(weatherLocationModel.getMedia()[i]);
                    }
                    if(arrImg.size() == 0){
                        notImg.setVisibility(View.VISIBLE);
                        rcvInfTouchLocation.setVisibility(View.INVISIBLE);
                    }else{
                        notImg.setVisibility(View.GONE);
                        rcvInfTouchLocation.setVisibility(View.VISIBLE);
                    }
                    detailLocationMapAdapter.notifyDataSetChanged();
                    txtDesLocation.setText(""+iconWeather.typeWeather(weatherLocationModel.getWeather().getCurrent()
                            .getWeather()[0].getDescription())+" "+(int) (weatherLocationModel.getWeather().getCurrent().getTemp()-273)+"/"+
                            (int) (weatherLocationModel.getWeather().getCurrent().getFeels_like() - 273)+"°C");
                    imgWeather.setImageResource(iconWeather.IconWeather(weatherLocationModel.getWeather().getCurrent()
                            .getWeather()[0].getMain()));
                    addDataChar();
                    Log.d(TAG, "weather: "+weatherLocationModel);
                    setDataDetailWeather.setDataDetailWeather(weatherLocationModel);
                }
                call.cancel();
                // hidden icon loading
                lottieLoadingData.pauseAnimation();
                lottieLoadingData.setVisibility(View.GONE);
                rltData.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<WeatherLocationModel> call, Throwable t) {
                call.cancel();
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
        // set data to recyclerview
        detailLocationMapAdapter = new DetailLocationMapAdapter(context, arrImg);
        rcvInfTouchLocation.setAdapter(detailLocationMapAdapter);
    }
    public WeatherLocationModel getWeather(){
        return weatherLocationModel;
    }
    // set data to chart
    public void addDataChar(){
        tack.clear();
        entries.clear();
        for (int i = 0; i < weatherLocationModel.getWeather().getDaily().length - 3; i++) {
            tack.add(timeCaculater.dayStringFormat(weatherLocationModel.getWeather().getDaily()[i].getDt()));
            entries.add(new Entry(i, weatherLocationModel.getWeather().getDaily()[i].getTemp().getMax()-273));
        }
        setupChar();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lottieLoading.setVisibility(View.GONE);
                dataChart.setVisibility(View.VISIBLE);
            }
        }, 2000);
    }
    private void setupChar(){

        Legend legend = chart.getLegend();
        legend.setEnabled(false);

        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setEnabled(false);

        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setEnabled(false);
//        xAxis.setAxisMinimum(-0.5f);
//        xAxis.setGranularity(1f);
//
//        xAxis.setTextSize(14f);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);

        chart.getDescription().setEnabled(false);
        chart.setScaleEnabled(false);
        chart.setExtraOffsets(5f,5f,5f,5f);

        LineDataSet lineDataSet = new LineDataSet(entries, "Data set 1");
        lineDataSet.setFillAlpha(35);
        lineDataSet.setValueTextSize(12f);
        lineDataSet.setLineWidth(2f);
        lineDataSet.setHighlightEnabled(false);
        lineDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) Math.floor(value)) + "°C";
            }
        });
        lineDataSet.notifyDataSetChanged();

        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);

        LineData lineData = new LineData(iLineDataSets);
        yAxisLeft.setAxisMaximum(lineData.getYMax()+0.5f);
        yAxisLeft.setAxisMinimum(lineData.getYMin()-5f);
        xAxis.setAxisMaximum(lineData.getXMax()+0.5f);
        chart.setVisibleXRangeMaximum(5);
        chart.setData(lineData);
    }
    public interface setDataDetailWeather{
        void setDataDetailWeather(WeatherLocationModel weatherLocationModel);
    }
}
