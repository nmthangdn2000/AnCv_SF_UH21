package thang.com.wref.Components.AI

import android.content.Context
import android.util.ArrayMap
import android.util.Log
import org.tensorflow.lite.Interpreter
import thang.com.wref.R
import kotlin.collections.HashMap

class CropYieldPrediction (
    private val app: Context
) {

    private lateinit var model: Interpreter;
    private lateinit var scalerX1: MinMaxScaler;
    private lateinit var scalerY: MinMaxScaler;

    companion object {
        val TAG: String = CropYieldPrediction::class.java.simpleName;
        private val SCALER_X1: String = "Scalers/CropYieldPrediction/scalerX1.json";
        private val SCALER_Y: String = "Scalers/CropYieldPrediction/scalerY.json";
        private val MODEL_NAME: String = "crop_yield_prediction_model.tflite";

        val districts: ArrayMap<String, Int> = ArrayMap<String, Int>();
        val soilTypes: ArrayMap<String, Int> = ArrayMap<String, Int>();
        val crops: ArrayMap<String, Int> = ArrayMap<String, Int>();
        val seasons: ArrayMap<String, Int> = ArrayMap<String, Int>();
    }

    init {
        app.resources.getStringArray(R.array.district).mapIndexed() { index, district ->
            districts[district] = index;
        }

        app.resources.getStringArray(R.array.soil).mapIndexed() { index, soil ->
            soilTypes[soil] = index;
        }

        app.resources.getStringArray(R.array.tree).mapIndexed() { index, crop ->
            crops[crop] = index;
        }

        app.resources.getStringArray(R.array.season).mapIndexed() { index, season ->
            seasons[season] = index;
        }
    }

    private fun loadModelAndMinMaxScaler() {
        Log.d(TAG, "Load Model and initiate MinMaxScaler");

        this.model = Interpreter(Utils.loadModelFile(app, MODEL_NAME));
        this.scalerX1 = MinMaxScaler.loadScaler(app, SCALER_X1);
        this.scalerY = MinMaxScaler.loadScaler(app, SCALER_Y);
    }

    // Parse input2 contain: 18 districts, 5 soil types, 3 plants, 2 seasons
    private fun parseInput2(
        district: String,
        soilType: String,
        crop: String,
        season: String
    ): FloatArray {

        val districtInput: FloatArray = FloatArray(18);
        districts.map {
            if (it.key == district)
                districtInput[it.value] = 1F;
            else
                districtInput[it.value] = 0F;
        }

        val soilTypeInput: FloatArray = FloatArray(5);
        soilTypes.map {
            if (it.key == soilType)
                soilTypeInput[it.value] = 1F;
            else
                soilTypeInput[it.value] = 0F;
        }

        val cropInput: FloatArray = FloatArray(3);
        crops.map {
            if (it.key == crop)
                cropInput[it.value] = 1F;
            else
                cropInput[it.value] = 0F;
        }

        val seasonInput: FloatArray = FloatArray(2);
        seasons.map {
            if (it.key == season)
                seasonInput[it.value] = 1F;
            else
                seasonInput[it.value] = 0F;
        }

        // Combine all to 1 tensor
        val input2: FloatArray = FloatArray(28);
        var count = 0;

        soilTypeInput.map {
            input2[count] = it;
            ++count;
        }

        districtInput.map {
            input2[count] = it;
            ++count;
        }

        cropInput.map {
            input2[count] = it;
            ++count;
        }

        seasonInput.map {
            input2[count] = it;
            ++count;
        }

        return input2;
    }

    // Crop Yield Prediction Model has 2 inputs with shape: (1, 4), (1, 28)
    private fun preProcessInputs(inputReal1: FloatArray, input2: FloatArray): Array<Any> {
        Log.d(TAG, "Preprocessing inputs...");

        // Scaling input1
        val input1Scaled: FloatArray = this.scalerX1.transform(inputReal1)

        // separate 2 inputs to 2 tensors
        val input0: Array<FloatArray> = arrayOf(input1Scaled);
        val input1: Array<FloatArray> = arrayOf(input2);
        // Combine all inputs
        val inputs: Array<Any> = arrayOf(input0, input1);

        return inputs;
    }

    // Flood Prediction Model has 1 output with shape: (1, 1)
    private fun prepareOutputs(): MutableMap<Int, Any> {
        Log.d(TAG, "Preparing output...");

        // Separate 1 output to 1 tensor
        val output0: Array<FloatArray> = Array<FloatArray>(1) {
            FloatArray(1)
        };
        // Combine all outputs
        val outputs: MutableMap<Int, Any> = HashMap<Int, Any>();
        outputs[0] = output0;

        return outputs;
    }

    public fun predict(
        area: Float, temperature: Float, humidity: Float, precipitation: Float,
        district: String,
        soilType: String,
        crop: String,
        season: String
    ): Float
    {

        loadModelAndMinMaxScaler();

        val inputs: Array<Any> = preProcessInputs(
            floatArrayOf(area, temperature, humidity, precipitation),
            parseInput2(district, soilType, crop, season)
        );

        val outputs: MutableMap<Int, Any> = prepareOutputs();

        Log.d(TAG, "Predicting...");
        model.runForMultipleInputsOutputs(inputs, outputs);

        // Parse output to real value
        val predictedOutput: FloatArray = (outputs[0] as Array<FloatArray>)[0];
        val predictedReal: Float = this.scalerY.inverseTransform(predictedOutput)[0];

        Log.d(TAG, "Predicted: $predictedReal");

        return predictedReal;
    }
}