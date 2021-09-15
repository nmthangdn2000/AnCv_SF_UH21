package thang.com.wref.Components.Chart

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class FloodPredictionXAxisFormatter(
    private val numDaysPrev: Int,
    private val dateFormat: String
): ValueFormatter() {

    private val calendar: Calendar = Calendar.getInstance();

    init {
        calendar.add(Calendar.DAY_OF_YEAR, - numDaysPrev + 1)
    }

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        val simpleDateFormat: SimpleDateFormat = SimpleDateFormat(dateFormat)

        val time: Float = calendar.timeInMillis + value * 24 * 60 * 60 * 1000;

        return simpleDateFormat.format(Date(time.toLong()));
    }
}