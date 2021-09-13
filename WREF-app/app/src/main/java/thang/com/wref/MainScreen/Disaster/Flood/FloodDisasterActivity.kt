package thang.com.wref.MainScreen.Disaster.Flood

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import thang.com.wref.Components.AI.FloodPrediction
import thang.com.wref.R
import thang.com.wref.databinding.FloodDisasterActivityBinding
import java.util.*
import kotlin.collections.HashMap

class FloodDisasterActivity : AppCompatActivity() {

    companion object {
        val TAG: String = FloodDisasterActivity::class.java.simpleName;
    }

    private lateinit var binding: FloodDisasterActivityBinding;
    private var step: Int = 1;

    private val calendar: Calendar = Calendar.getInstance();
    private val flowLevelColor: HashMap<Int, Int> = HashMap();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FloodDisasterActivityBinding.inflate(layoutInflater);
        setContentView(binding.root)

        // Get resource
        flowLevelColor[0] = resources.getColor(R.color.cs_success, null);
        flowLevelColor[1] = resources.getColor(R.color.blue, null);
        flowLevelColor[2] = Color.parseColor("#FDE74C");
        flowLevelColor[3] = Color.parseColor("#FFAA5A");
        flowLevelColor[4] = Color.parseColor("#FC5130");
        flowLevelColor[5] = Color.parseColor("#DF57BC");

        binding.rltBack.setOnClickListener {
            finish();
        }

        // handle UI steps
        binding.button.setOnClickListener {
            when (step) {
                1 -> {
                    showUIStep1();

                    step += 1;
                }

                2 -> {
                    showUIStep2();

                    step += 1;
                }

                3 -> {
                    val floodPrediction: FloodPrediction = FloodPrediction(this);
                    val predictedFlow: Float = floodPrediction.predict(
                        binding.flowD0.editText!!.text.toString().toFloat(),
                        binding.flowD1.editText!!.text.toString().toFloat(),
                        binding.flowD2.editText!!.text.toString().toFloat(),
                        binding.flowD3.editText!!.text.toString().toFloat(),
                        binding.flowD4.editText!!.text.toString().toFloat(),
                        binding.flowD5.editText!!.text.toString().toFloat(),
                        binding.flowD6.editText!!.text.toString().toFloat(),
                        binding.flowD7.editText!!.text.toString().toFloat(),
                        binding.flowD8.editText!!.text.toString().toFloat(),
                        binding.flowD9.editText!!.text.toString().toFloat(),
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

                    showUIStep3(flowLevel, flowLevelColor[flowLevel]!!, predictedFlow);
                }
            }
        }
    }

    private fun showUIStep1() {
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
    }

    private fun showUIStep2() {
        binding.button.text = "Dự đoán";
        binding.title.text = "Dự báo lũ"
        binding.subText.text = "Để dự đoán được lũ sớm, ứng dụng cần dữ liệu lưu lượng trong vòng 10 ngày trước. Xin vui lòng cung câp dữ liệu theo các ô dưới đây."

        binding.step2.setCardBackgroundColor(resources.getColor(R.color.blue, null));
        binding.step2.foreground = null;
        binding.step2Text.setTextColor(resources.getColor(R.color.white));
        binding.step1.setCardBackgroundColor(null);
        binding.step1.foreground = resources.getDrawable(R.drawable.border_card_circle, null);
        binding.step1Text.setTextColor(resources.getColor(R.color.cs_gray));
        binding.step3.setCardBackgroundColor(null);
        binding.step3.foreground = resources.getDrawable(R.drawable.border_card_circle, null);
        binding.step3Text.setTextColor(resources.getColor(R.color.cs_gray));

        binding.lnlFlowInput.visibility = View.VISIBLE;
    }

    private fun showUIStep3(floodLevel: Int, color: Int, flow: Float) {
        binding.lnlFlowInput.visibility = View.GONE;

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