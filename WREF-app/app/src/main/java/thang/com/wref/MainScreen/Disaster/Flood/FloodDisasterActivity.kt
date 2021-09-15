package thang.com.wref.MainScreen.Disaster.Flood

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import thang.com.wref.Components.AI.WaterFlowPrediction
import thang.com.wref.Components.Retrofits.WeatherRetrofit
import thang.com.wref.LoginScreen.SharedPreferencesManagement
import thang.com.wref.MainScreen.Models.DetailWeatherModel
import thang.com.wref.R
import thang.com.wref.databinding.FloodDisasterActivityBinding
import thang.com.wref.util.NetworkUtil
import java.lang.Error
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import thang.com.wref.Components.Chart.FloodPredictionXAxisFormatter


class FloodDisasterActivity : AppCompatActivity() {

    companion object {
        val TAG: String = FloodDisasterActivity::class.java.simpleName;
    }

    private lateinit var binding: FloodDisasterActivityBinding;
    private var step: Int = 1;

    private lateinit var networkUtil: NetworkUtil;
    private lateinit var retrofit: Retrofit;
    private lateinit var weatherRetrofit: WeatherRetrofit;
    private lateinit var sharedPreferencesManagement: SharedPreferencesManagement;
    private lateinit var detailWeatherModel: DetailWeatherModel;

    private val calendar: Calendar = Calendar.getInstance();
    private val floodLevelColor: HashMap<Int, Int> = HashMap();

    private val inputWaterFlow: HashMap<String, Float> = HashMap<String, Float>();
    private val inputTemp: FloatArray = FloatArray(5);
    private val inputHumd: FloatArray = FloatArray(5);
    private val inputRain: FloatArray = FloatArray(5);

    init {
        inputWaterFlow["current"] = 30F;
        inputWaterFlow["day1Pre"] = 50F;
        inputWaterFlow["day2Pre"] = 40F;
        inputWaterFlow["day3Pre"] = 45F;
        inputWaterFlow["day4Pre"] = 47F;
        inputWaterFlow["day5Pre"] = 56F;
        inputWaterFlow["day6Pre"] = 70F;
        inputWaterFlow["day7Pre"] = 62F;
        inputWaterFlow["day8Pre"] = 51F;
        inputWaterFlow["day9Pre"] = 32F;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up Services
        binding = FloodDisasterActivityBinding.inflate(layoutInflater);
        setContentView(binding.root)

        networkUtil = NetworkUtil()
        retrofit = networkUtil.retrofit
        weatherRetrofit = retrofit.create(WeatherRetrofit::class.java)
        sharedPreferencesManagement = SharedPreferencesManagement(applicationContext)

        // Get resource
        floodLevelColor[0] = resources.getColor(R.color.cs_success, null);
        floodLevelColor[1] = resources.getColor(R.color.blue, null);
        floodLevelColor[2] = Color.parseColor("#FDE74C");
        floodLevelColor[3] = Color.parseColor("#FFAA5A");
        floodLevelColor[4] = Color.parseColor("#FC5130");
        floodLevelColor[5] = Color.parseColor("#DF57BC");

        showUIStep1();

        // handle Back button
        binding.rltBack.setOnClickListener {
            finish();
        }

        // handle UI steps
        binding.button.setOnClickListener {
            when (step) {
                1 -> {
                    step += 1;
                    showUIStep2();
                    getWeatherAndDrawChart();
                }

                2 -> {
                    step += 1;
//                    val prediction: HashMap<String, Any> = predictFlow();
                    showUIStep3(3, 350F);
                }

                3 -> {
                    // TODO add Web view
                }
            }
        }
    }

    private fun getWeatherAndDrawChart() {
        binding.tvLoading.visibility = View.VISIBLE;

        // get data from api
        // require params : token, longitude, latitude
        val detailWeatherModelCall: Call<DetailWeatherModel> = weatherRetrofit.getWeather(
            sharedPreferencesManagement.getTOKEN(),
            sharedPreferencesManagement.getLAT(),
            sharedPreferencesManagement.getLONG()
        )
        detailWeatherModelCall.enqueue(object : Callback<DetailWeatherModel?> {
            override fun onResponse(
                call: Call<DetailWeatherModel?>,
                response: Response<DetailWeatherModel?>
            ) {
                if (!response.isSuccessful) {
                    Log.d(TAG, "Lỗi mạng")
                    throw Error("Lỗi mạng")
                } else {
                    detailWeatherModel = response.body()!!
                    Log.d(TAG, "onResponse: " + detailWeatherModel.current)

                    val weatherDailyList = detailWeatherModel.daily;
                    for (i in 0..4) {
                        val weatherDaily = weatherDailyList[7 - i];
                        inputTemp[i] = weatherDaily.temp.day - 273; // Convert F -> C
                        inputHumd[i] = weatherDaily.humidity.toFloat();
                        inputRain[i] = weatherDaily.rain;
                    }

                    drawChart();
                }
                call.cancel()
            }

            override fun onFailure(call: Call<DetailWeatherModel?>, t: Throwable) {
                Log.d(TAG, "onFailure: " + t.message)
                call.cancel()
                throw Error(t.message)
            }
        })
    }

    private fun drawChart() {
        binding.tvLoading.visibility = View.GONE;

        val tempEntries: MutableList<Entry> = ArrayList<Entry>();
        val humdEntries: MutableList<Entry> = ArrayList<Entry>();
        val rainEntries: MutableList<Entry> = ArrayList<Entry>();
        for (i in 0..4) {
            tempEntries.add(Entry(i.toFloat(), inputTemp[i]));
            humdEntries.add(Entry(i.toFloat(), inputHumd[i]));
            rainEntries.add(Entry(i.toFloat(), inputRain[i]));
        }

        val tempDataSet: LineDataSet = LineDataSet(tempEntries, "Nhiệt độ (C)");
        tempDataSet.color = resources.getColor(R.color.cs_danger, null);
        tempDataSet.circleHoleColor = resources.getColor(R.color.cs_danger, null);
        tempDataSet.setCircleColor(resources.getColor(R.color.cs_danger, null))
        tempDataSet.axisDependency = YAxis.AxisDependency.LEFT;

        val humdDataSet: LineDataSet = LineDataSet(humdEntries, "Độ ẩm (%)");
        humdDataSet.color = resources.getColor(R.color.cs_dark, null);
        humdDataSet.circleHoleColor = resources.getColor(R.color.cs_dark, null);
        humdDataSet.setCircleColor(resources.getColor(R.color.cs_dark, null))
        humdDataSet.axisDependency = YAxis.AxisDependency.LEFT;

        val rainDataSet: LineDataSet = LineDataSet(rainEntries, "Lượng mưa (mm)");
        rainDataSet.color = resources.getColor(R.color.blue, null);
        rainDataSet.axisDependency = YAxis.AxisDependency.LEFT;
        rainDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER;
        rainDataSet.setDrawFilled(true);
        rainDataSet.fillColor = resources.getColor(R.color.blue, null);

        val lineData: LineData = LineData();
        lineData.addDataSet(tempDataSet);
        lineData.addDataSet(humdDataSet);
        lineData.addDataSet(rainDataSet);
        binding.chart.data = lineData;

        binding.chart.xAxis.position = XAxis.XAxisPosition.BOTTOM;
        binding.chart.xAxis.isEnabled = true;
        binding.chart.xAxis.granularity = 1F;
        binding.chart.xAxis.valueFormatter = FloodPredictionXAxisFormatter(5, "dd-MM");

        binding.chart.axisLeft.isEnabled = true;
        binding.chart.axisLeft.setDrawGridLines(false);
        binding.chart.axisLeft.axisMinimum = 0F;

        binding.chart.axisRight.isEnabled = false;

        binding.chart.setGridBackgroundColor(resources.getColor(R.color.cs_gray, null));
        binding.chart.legend.isWordWrapEnabled = true;
        binding.chart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER;
        binding.chart.legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM;
        binding.chart.description.isEnabled = false;
        binding.chart.invalidate();

        binding.chartArea.visibility = View.VISIBLE;
    }

    private fun predictFlow(): HashMap<String, Any> {
        val waterFlowPrediction: WaterFlowPrediction = WaterFlowPrediction(this);
        val predictedFlow: Float = waterFlowPrediction.predict(
            inputWaterFlow["current"]!!,
            inputWaterFlow["day1Pre"]!!,
            inputWaterFlow["day2Pre"]!!,
            inputWaterFlow["day3Pre"]!!,
            inputWaterFlow["day4Pre"]!!,
            inputWaterFlow["day5Pre"]!!,
            inputWaterFlow["day6Pre"]!!,
            inputWaterFlow["day7Pre"]!!,
            inputWaterFlow["day8Pre"]!!,
            inputWaterFlow["day9Pre"]!!,
            calendar.get(Calendar.DAY_OF_YEAR).toFloat()
        )

        var flowLevel: Int = 0

        if (predictedFlow < 100)
            flowLevel = 0;
        else if (predictedFlow < 200)
            flowLevel = 1;
        else if (predictedFlow < 400)
            flowLevel = 2;
        else if (predictedFlow < 700)
            flowLevel = 3;
        else if (predictedFlow < 1000)
            flowLevel = 4;
        else
            flowLevel = 5;

        val result: HashMap<String, Any> = HashMap<String, Any>();
        result["flow"] = predictedFlow;
        result["flowLevel"] = flowLevel;

        return result;
    }

    private fun showUIStep1() {
        binding.tvLoading.visibility = View.GONE;

        binding.button.text = "Bắt đầu";
        binding.title.text = "Dự báo lũ"
        binding.subText.text = "Lũ thường xảy ra vào tháng 9 đến tháng 11. Dự đoán sớm lũ giúp người dân chuẩn bị tâm lí ứng phó với thiên tai, tránh mất mát mùa màng."

        binding.step1.setCardBackgroundColor(resources.getColor(R.color.blue, null));
        binding.step1.foreground = null;
        binding.step1Text.setTextColor(resources.getColor(R.color.white));
        binding.step2.setCardBackgroundColor(null);
        binding.step2.foreground = resources.getDrawable(R.drawable.border_card_circle, null);
        binding.step2Text.setTextColor(resources.getColor(R.color.cs_gray));
        binding.step3.setCardBackgroundColor(null);
        binding.step3.foreground = resources.getDrawable(R.drawable.border_card_circle, null);
        binding.step3Text.setTextColor(resources.getColor(R.color.cs_gray));

        binding.rltMain.visibility = View.GONE;
    }

    private fun showUIStep2() {
        binding.tvLoading.visibility = View.GONE;

        binding.button.text = "Dự đoán";
        binding.title.text = "Dự báo lũ"
        binding.subText.text = "Để dự đoán được chính xác, cần có dữ liệu được lấy từ trạm khí tượng TURANT - Đà Nẵng, truy cập thông qua dịch vụ OpenWeather API."

        binding.step2.setCardBackgroundColor(resources.getColor(R.color.blue, null));
        binding.step2.foreground = null;
        binding.step2Text.setTextColor(resources.getColor(R.color.white));
        binding.step1.setCardBackgroundColor(null);
        binding.step1.foreground = resources.getDrawable(R.drawable.border_card_circle, null);
        binding.step1Text.setTextColor(resources.getColor(R.color.cs_gray));
        binding.step3.setCardBackgroundColor(null);
        binding.step3.foreground = resources.getDrawable(R.drawable.border_card_circle, null);
        binding.step3Text.setTextColor(resources.getColor(R.color.cs_gray));

        binding.rltMain.visibility = View.VISIBLE;
    }

    private fun showUIStep3(floodLevel: Int, flow: Float) {
        binding.tvLoading.visibility = View.GONE;
        binding.ivFlood.visibility = View.VISIBLE;

        binding.button.text = "Phòng tránh";
        binding.title.text = "Lũ cấp $floodLevel"
        binding.subText.text = "Dự đoán: mực nước trung bình trong 5 ngày tiếp theo là ${flow}mm."

        binding.step3.setCardBackgroundColor(resources.getColor(R.color.blue, null));
        binding.step3.foreground = null;
        binding.step3Text.setTextColor(resources.getColor(R.color.white));
        binding.step1.setCardBackgroundColor(null);
        binding.step1.foreground = resources.getDrawable(R.drawable.border_card_circle, null);
        binding.step1Text.setTextColor(resources.getColor(R.color.cs_gray));
        binding.step2.setCardBackgroundColor(null);
        binding.step2.foreground = resources.getDrawable(R.drawable.border_card_circle, null);
        binding.step2Text.setTextColor(resources.getColor(R.color.cs_gray));
    }
}