package thang.com.wref.MainScreen.Weather;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import thang.com.wref.LoginScreen.SharedPreferencesManagement;
import thang.com.wref.MainScreen.Models.DetailWeatherModel;
import thang.com.wref.util.NetworkUtil;
import thang.com.wref.R;
import thang.com.wref.Components.Retrofits.WeatherRetrofit;

public class DetailWeatherFragment extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "DetailWeatherFragment";
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private LineChart chart ;
    private ArrayList<Entry> entries;
    private ArrayList<String> sDay;

    private Toolbar toolbar;
    private ImageView imgSunWeather;
    private AnimatedVectorDrawable animation;
    private RelativeLayout rltSunWeather, rltDuBao5Ngay, bacgroundImgWeather, bacgroundColorWeather, splashWeather, layoutRltWeather;
    private TextView txtNhietdo, txtDesWeather, txtWeatherCurrent, txtWeatherNextOne, txtWeatherNextTwo,
            txtDataNhietdo1, txtDataNhietdo2, txtDataNhietdo3, txtDetailGio, txtDetailNhietdo, txtDetailUVo, txtDetailApsuat,
            txtNameLocation;

    private LottieAnimationView lottieLoadingData;
    private Path path, path2;
    private int night = 0;
    private int morning = 0;

    private NetworkUtil networkUtil;
    private Retrofit retrofit;
    private WeatherRetrofit weatherRetrofit;
    private SharedPreferencesManagement sharedPreferencesManagement;
    private DetailWeatherModel detailWeatherModel;
    private ArrayList<dailyWeather> dailyWeathers;
    private IconWeather iconWeather;
    private TimeCaculater timeUtil;
    private ImageView imhWeather1, imhWeather2, imhWeather3;

    private Calendar cal;
    private int oneHourBack;

    private LocationManager locationManager;

    public DetailWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        super.onStart();
        getData();
        getData24h();
        txtNameLocation.setText(sharedPreferencesManagement.getADDRESS());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_weather);
        if(Build.VERSION.SDK_INT >= 21){
            Window window = getWindow();
            window.setStatusBarColor(DetailWeatherFragment.this.getResources().getColor(R.color.purple_700));
        }
        setupToolbar();
        networkUtil = new NetworkUtil();
        retrofit = networkUtil.getRetrofit();
        sharedPreferencesManagement = new SharedPreferencesManagement(DetailWeatherFragment.this);
        dailyWeathers = new ArrayList<>();
        iconWeather = new IconWeather();
        timeUtil = new TimeCaculater();
        weatherRetrofit = retrofit.create(WeatherRetrofit.class);

        night = ContextCompat.getColor(DetailWeatherFragment.this, R.color.nig_color);
        morning = ContextCompat.getColor(DetailWeatherFragment.this, R.color.mor_color);
        mapingView();

        entries = new ArrayList<>();
        sDay = new ArrayList<>();
        lottieLoadingData.playAnimation();
        splashWeather.setVisibility(View.VISIBLE);
        layoutRltWeather.setVisibility(View.INVISIBLE);

        cal = Calendar.getInstance();
    }
    private void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbarInfor);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thời tiết");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void mapingView(){
        rltSunWeather = (RelativeLayout) findViewById(R.id.rltSunWeather);
        rltDuBao5Ngay = (RelativeLayout) findViewById(R.id.rltDuBao5Ngay);
        chart  = (LineChart) findViewById(R.id.combinedChart);
        imgSunWeather = (ImageView) findViewById(R.id.imgSunWeather);
        txtNhietdo = (TextView) findViewById(R.id.txtNhietdo);
        txtDesWeather = (TextView) findViewById(R.id.txtDesWeather);
        bacgroundImgWeather = (RelativeLayout) findViewById(R.id.bacgroundImgWeather);
        bacgroundColorWeather = (RelativeLayout) findViewById(R.id.bacgroundColorWeather);
        txtWeatherCurrent = (TextView) findViewById(R.id.txtWeatherCurrent);
        txtWeatherNextOne = (TextView) findViewById(R.id.txtWeatherNextOne);
        txtWeatherNextTwo = (TextView) findViewById(R.id.txtWeatherNextTwo);
        txtDataNhietdo1 = (TextView) findViewById(R.id.txtDataNhietdo1);
        txtDataNhietdo2 = (TextView) findViewById(R.id.txtDataNhietdo2);
        txtDataNhietdo3 = (TextView) findViewById(R.id.txtDataNhietdo3);
        txtDetailGio = (TextView) findViewById(R.id.txtDetailGio);
        txtDetailNhietdo = (TextView) findViewById(R.id.txtDetailNhietdo);
        txtDetailUVo = (TextView) findViewById(R.id.txtDetailUVo);
        txtDetailApsuat = (TextView) findViewById(R.id.txtDetailApsuat);
        imhWeather1 = (ImageView) findViewById(R.id.imhWeather1);
        imhWeather2 = (ImageView) findViewById(R.id.imhWeather2);
        imhWeather3 = (ImageView) findViewById(R.id.imhWeather3);
        txtNameLocation = (TextView) findViewById(R.id.txtNameLocation);
        splashWeather = (RelativeLayout) findViewById(R.id.splashWeather);
        layoutRltWeather = (RelativeLayout) findViewById(R.id.layoutRltWeather);
        lottieLoadingData = (LottieAnimationView) findViewById(R.id.lottieLoadingData);

        rltDuBao5Ngay.setOnClickListener(this);
        txtNhietdo.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtNhietdo:
                animationSundown();
                break;
            case R.id.rltDuBao5Ngay:

                break;
            default:
                break;
        }
    }



    private void setDataIn5Day(){
        // set rainfall data for the next 5 days
        animationSunrise(iconWeather.IconWeather(detailWeatherModel.getCurrent().getWeather()[0].getMain()));

        txtWeatherCurrent.setText(timeUtil.dayStringFormat(detailWeatherModel.getCurrent().getDt())+"-"+iconWeather.typeWeather(detailWeatherModel.getCurrent().getWeather()[0].getDescription()));
        txtWeatherNextOne.setText(timeUtil.dayStringFormat(dailyWeathers.get(1).getDt())+"-"+iconWeather.typeWeather(dailyWeathers.get(1).getWeather()[0].getDescription()));
        txtWeatherNextTwo.setText(timeUtil.dayStringFormat(dailyWeathers.get(2).getDt())+"-"+iconWeather.typeWeather(dailyWeathers.get(2).getWeather()[0].getDescription()));

        txtDataNhietdo1.setText(iconWeather.Temp(detailWeatherModel.getCurrent().getTemp(), detailWeatherModel.getCurrent().getFeels_like()));
        txtDataNhietdo2.setText(iconWeather.Temp(dailyWeathers.get(1).getTemp().getMax(), dailyWeathers.get(1).getTemp().getMin()));
        txtDataNhietdo3.setText(iconWeather.Temp(dailyWeathers.get(2).getTemp().getMax(), dailyWeathers.get(2).getTemp().getMin()));

        txtDetailGio.setText(detailWeatherModel.getCurrent().getWind_speed()+" km/h");
        txtDetailNhietdo.setText((int) (detailWeatherModel.getCurrent().getTemp() - 273)+"°C");
        txtDetailUVo.setText(""+detailWeatherModel.getCurrent().getUvi());
        txtDetailApsuat.setText(detailWeatherModel.getCurrent().getPressure()+" mb");

        imhWeather1.setImageResource(iconWeather.IconWeather(detailWeatherModel.getCurrent().getWeather()[0].getMain()));
        imhWeather2.setImageResource(iconWeather.IconWeather(dailyWeathers.get(1).getWeather()[0].getMain()));
        imhWeather3.setImageResource(iconWeather.IconWeather(dailyWeathers.get(2).getWeather()[0].getMain()));
    }
    private void getData(){
        // get data from api
        // require params : token, longitude, latitude
        Call<DetailWeatherModel> detailWeatherModelCall = weatherRetrofit.getWeather(sharedPreferencesManagement.getTOKEN(), sharedPreferencesManagement.getLAT(), sharedPreferencesManagement.getLONG());
        detailWeatherModelCall.enqueue(new Callback<DetailWeatherModel>() {
            @Override
            public void onResponse(Call<DetailWeatherModel> call, Response<DetailWeatherModel> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(DetailWeatherFragment.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
                }else{
                    detailWeatherModel = response.body();
                    Log.d(TAG, "onResponse: "+detailWeatherModel.getCurrent().getWeather()[0].getDescription());
                    for (int i = 0; i < detailWeatherModel.getDaily().length; i++){
                        dailyWeathers.add(detailWeatherModel.getDaily()[i]);
                    }
                    setDataIn5Day();
                    showData();
                }
                call.cancel();
            }

            @Override
            public void onFailure(Call<DetailWeatherModel> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                call.cancel();
            }
        });

    }
    // get 24h rainfall data to
    private void getData24h(){
        Call<DetailWeatherModel> detailWeatherModelCall = weatherRetrofit.getWeather24h(sharedPreferencesManagement.getTOKEN(), sharedPreferencesManagement.getLAT(), sharedPreferencesManagement.getLONG());
        detailWeatherModelCall.enqueue(new Callback<DetailWeatherModel>() {
            @Override
            public void onResponse(Call<DetailWeatherModel> call, Response<DetailWeatherModel> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(DetailWeatherFragment.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
                }else{
                    entries.clear();
                    sDay.clear();

                    detailWeatherModel = response.body();
                    entries.add(new Entry(0, detailWeatherModel.getCurrent().getFeels_like()-273, "°C"));
                    sDay.add("Bây giờ");

                    for (int i = 0; i < detailWeatherModel.getHourly().length-24; i++){
                        cal.setTime(new Date(detailWeatherModel.getHourly()[i].getDt()));
                        oneHourBack = cal.get(Calendar.HOUR_OF_DAY);
                        Log.d(TAG, "onResponse: "+oneHourBack);
                        // convert to °C
                        entries.add(new Entry(i+1, detailWeatherModel.getHourly()[i].getTemp()-273, "°C"));
                        // format hour
                        if(oneHourBack > 9 )
                            sDay.add(oneHourBack+":00");
                        else sDay.add("0"+oneHourBack+":00");
                    }
                    setupChart();
                    lottieLoadingData.pauseAnimation();
                    splashWeather.setVisibility(View.GONE);
                    layoutRltWeather.setVisibility(View.VISIBLE);
                }
                call.cancel();
            }

            @Override
            public void onFailure(Call<DetailWeatherModel> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                call.cancel();
            }
        });
    }
    private void showData(){
        float f = detailWeatherModel.getCurrent().getTemp();
        float c = f - 273;
        Log.d(TAG, "onClick: "+c);
        String val = "";
        if(detailWeatherModel.getCurrent().getWeather()[0].getDescription().equals("light rain"))
            val = "Mưa nhỏ";
        txtDesWeather.setText(val);
        txtNhietdo.setText((int) c+"°C");
    }
    private void setupChart(){
        // create a rainfall chart
        Legend legend = chart.getLegend();
        legend.setEnabled(false);

        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setEnabled(false);

        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setAxisMinimum(-0.5f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(sDay));
        xAxis.setTextSize(14f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);

        chart.getDescription().setEnabled(false);
        chart.setScaleEnabled(false);
        chart.setExtraOffsets(5f,5f,5f,5f);

        LineDataSet lineDataSet = new LineDataSet(entries, "Data set 1");
        lineDataSet.setFillAlpha(35);
        lineDataSet.setValueTextSize(12f);
        lineDataSet.setValueTextColor(Color.WHITE);
        lineDataSet.setLineWidth(2f);
        lineDataSet.setColor(Color.WHITE);
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
    // sunrise animation
    private void animationSunrise(int img){
//        if(img == 1)
//            imgSunWeather.setImageResource(R.drawable.ic_rain_sun_small);
//        else if(img == 2)
            imgSunWeather.setImageResource(img);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.d(TAG, "animationWeather: "+ imgSunWeather.getX() +" "+imgSunWeather.getY());
            path = new Path();
            path.arcTo(0f-dpToPx(170), 0f, rltSunWeather.getRight(), rltSunWeather.getBottom()+dpToPx(300), 180f, 110f, true);

            startAnimationWeather(path, night, morning);
            if(!bacgroundImgWeather.getTag().equals("morning")){
                changeBackgroundImg(R.drawable.background_morning);
                bacgroundImgWeather.setTag("night");
            }else{
                return;
            }
        } else {
            // Create animator without using curved path
        }
    }
    // sunset animation
    private void animationSundown(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            path2 = new Path();
            path2.arcTo(0f + imgSunWeather.getLeft() + dpToPx(168), 0f + dpToPx(30), 1000f + dpToPx(50), 1000f, 270f, 90f, true);

            startAnimationWeather(path2, morning, night);
            if(!bacgroundImgWeather.getTag().equals("night")){
                changeBackgroundImg(R.drawable.background_night);
            }else{
                return;
            }

        }else{

        }
    }
    // start animation
    private void startAnimationWeather(Path path, int fromColor, int toColor){
        ObjectAnimator animator = ObjectAnimator.ofFloat(imgSunWeather, View.X, View.Y, path);
        animator.setDuration(2000);




        ValueAnimator valueAnimator = new ValueAnimator();

        valueAnimator.setIntValues(fromColor, toColor);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                bacgroundColorWeather.setBackgroundColor((int) valueAnimator.getAnimatedValue());
            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.setEvaluator(new ArgbEvaluator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator, valueAnimator);
        animatorSet.start();
    }

    private void changeBackgroundImg(int background){
        Animation fadeOut = AnimationUtils.loadAnimation(DetailWeatherFragment.this, R.anim.fade_out);
        bacgroundImgWeather.startAnimation(fadeOut);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                bacgroundImgWeather.setBackgroundResource(background);
                Animation fadeOut = AnimationUtils.loadAnimation(DetailWeatherFragment.this, R.anim.fade_in);
                bacgroundImgWeather.startAnimation(fadeOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    // conver dp to px
    private int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}