package thang.com.wref.Components.AI

import android.content.Context
import android.util.Log
import com.beust.klaxon.Klaxon

class MinMaxScaler(
    private val dataMin: FloatArray,
    private val dataMax: FloatArray,
    private val scaledMin: Float,
    private val scaledMax: Float
) {

    companion object {
        val TAG: String = MinMaxScaler::class.java.simpleName;

        class ScalerJson (
            val min: Float,
            val max: Float,
            val dataMin: ArrayList<Float>,
            val dataMax: ArrayList<Float>
        )

        public fun loadScaler(app: Context, jsonPath: String): MinMaxScaler {
            Log.d(TAG, "Load Scaler");

            val scalerData: ScalerJson = Klaxon()
                .parse<ScalerJson>(app.assets.open(jsonPath))!!

            return MinMaxScaler(
                scalerData.dataMin.toFloatArray(), scalerData.dataMax.toFloatArray(),
                scalerData.min, scalerData.max
            )
        }
    }

    public fun transform(data: FloatArray): FloatArray {
        return data.mapIndexed<Float> { index, data ->
            val X_std = (data - dataMin[index]) / (dataMax[index] - dataMin[index]);
            val X_scaled = X_std * (scaledMax - scaledMin) + scaledMin;
            X_scaled;
        }.toFloatArray()
    }

    public fun inverseTransform(data: FloatArray): FloatArray {
        return data.mapIndexed<Float> { index, data ->
            val X_std = (data - scaledMin) / (scaledMax- scaledMin);
            val X_real = X_std * (dataMax[index] - dataMin[index]) + dataMin[index];
            X_real;
        }.toFloatArray()
    }
}