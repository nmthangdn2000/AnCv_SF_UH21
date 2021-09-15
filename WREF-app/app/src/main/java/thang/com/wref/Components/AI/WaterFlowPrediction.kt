package thang.com.wref.Components.AI

import android.content.Context
import android.util.Log
import org.tensorflow.lite.Interpreter
import kotlin.collections.HashMap

class WaterFlowPrediction(
    private val app: Context
) {

    private lateinit var model: Interpreter;
    private lateinit var scalerX: MinMaxScaler;
    private lateinit var scalerY: MinMaxScaler;

    companion object {
        val TAG: String = WaterFlowPrediction::class.java.simpleName;
        private val SCALER_X: String = "Scalers/FloodPrediction/scalerX.json";
        private val SCALER_Y: String = "Scalers/FloodPrediction/scalerY.json";
        private val MODEL_NAME: String = "flood_prediction_model.tflite";
    }

    private fun loadModelAndMinMaxScaler() {
        Log.d(TAG, "Load Model and initiate MinMaxScaler");

        this.model = Interpreter(Utils.loadModelFile(app, MODEL_NAME));
        this.scalerX = MinMaxScaler.loadScaler(app, SCALER_X);
        this.scalerY = MinMaxScaler.loadScaler(app, SCALER_Y);
    }

    // Flood Prediction Model has 2 inputs with shape: (1, 10), (1, 1)
    private fun preProcessInputs(inputReal: FloatArray): Array<Any> {
        Log.d(TAG, "Preprocessing inputs...");

        // Scaling inputs
        val input1Scaled: FloatArray = FloatArray(10);
        val input2Scaled: FloatArray = FloatArray(1);
        val inputScaled:FloatArray = this.scalerX.transform(inputReal);

        input2Scaled[0] = inputScaled[0];
        for (i in 1..10)
            input1Scaled[i - 1] = inputScaled[i];

        // separate 2 inputs to 2 tensors
        val input0: Array<FloatArray> = arrayOf(input1Scaled);
        val input1: Array<FloatArray> = arrayOf(input2Scaled);
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
        d0: Float, d1: Float, d2: Float, d3: Float, d4: Float,
        d5: Float, d6: Float, d7: Float, d8: Float, d9: Float,
        time: Float
    ): Float
    {

        loadModelAndMinMaxScaler();

        val inputs: Array<Any> = preProcessInputs(floatArrayOf(
            d0, d1, d2, d3, d4, d5, d6, d7 ,d8 ,d9,
            time
        ));

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