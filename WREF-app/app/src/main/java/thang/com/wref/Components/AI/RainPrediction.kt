package thang.com.wref.Components.AI

import android.content.Context
import android.util.Log
import org.tensorflow.lite.Interpreter

class RainPrediction(
    private val app: Context
) {
    private lateinit var model: Interpreter;
    private lateinit var scalerXTemp: MinMaxScaler;
    private lateinit var scalerXHumd: MinMaxScaler;
    private lateinit var scalerXRainFall: MinMaxScaler;
    private lateinit var scalerXTime: MinMaxScaler;
    private lateinit var scalerY: MinMaxScaler;

    companion object {
        val TAG: String = WaterFlowPrediction::class.java.simpleName;
        private val SCALER_X_TEMP: String = "Scalers/RainPrediction/scalerXTemp.json";
        private val SCALER_X_HUMD: String = "Scalers/RainPrediction/scalerXHumd.json";
        private val SCALER_X_RAIN_FALL: String = "Scalers/RainPrediction/scalerXRainFall.json";
        private val SCALER_X_TIME: String = "Scalers/RainPrediction/scalerXTime.json";
        private val SCALER_Y: String = "Scalers/RainPrediction/scalerY.json";
        private val MODEL_NAME: String = "rain_prediction_model.tflite";
    }

    private fun loadModelAndMinMaxScaler() {
        Log.d(TAG, "Load Model and initiate MinMaxScaler");

        this.model = Interpreter(Utils.loadModelFile(app, MODEL_NAME));
        this.scalerXTemp = MinMaxScaler.loadScaler(app, SCALER_X_TEMP);
        this.scalerXHumd = MinMaxScaler.loadScaler(app, SCALER_X_HUMD);
        this.scalerXRainFall = MinMaxScaler.loadScaler(app, SCALER_X_RAIN_FALL);
        this.scalerXTime = MinMaxScaler.loadScaler(app, SCALER_X_TIME);
        this.scalerY = MinMaxScaler.loadScaler(app, SCALER_Y);
    }

    private fun scaleInput(inputReal: FloatArray, scaler: MinMaxScaler): FloatArray {
        Log.d(TAG, "Preprocessing inputs...");

        val scaledInput: FloatArray = FloatArray(inputReal.size);
        scaler.transform(inputReal).mapIndexed { index, data ->
            scaledInput[index] = data
        }

        return scaledInput;
    }

    // Rain Prediction Model has 3 inputs with shape: (1, 5, 1), (1, 1), (1, 5, 2)
    private fun preProcessInputs(
        inputTempReal: FloatArray, inputHumdReal: FloatArray,
        inputRainFallReal: FloatArray, inputTimeReal: Float
    ): Array<Any> {
        Log.d(TAG, "Preprocessing inputs...");

        // Scaling inputs
        val scaledTemp = scaleInput(inputTempReal, scalerXTemp)
        val scaledHumd = scaleInput(inputHumdReal, scalerXHumd)
        val scaledRainFall = scaleInput(inputRainFallReal, scalerXRainFall)
        val scaledTime = scaleInput(floatArrayOf(inputTimeReal), scalerXTime)

        // Reshape Rainfall to 1 input with shape (5, 1)
        val inputRainFall: Array<FloatArray> = Array<FloatArray>(5) {
            FloatArray(1)
        };

        for (shape0 in 0..4) {
            inputRainFall[shape0][0] = scaledRainFall[0];
        }

        // Put current time to 1 input with shape (1, )
        val inputTime: FloatArray = FloatArray(1);
        inputTime[0] = scaledTime[0];

        // Combine Temp and Humd to 1 input with shape (5, 2)
        val inputTempHumd: Array<FloatArray> = Array<FloatArray>(5) {
            FloatArray(2)
        };

        for (shape0 in 0..4) {
            for (shape1 in 0..1) {
                inputTempHumd[shape0][shape1] = scaledTemp[shape1];
                inputTempHumd[shape0][shape1] = scaledHumd[shape1]
            }
        }

        // Transform inputs to tensors
        val input0: Array<Array<FloatArray>> = arrayOf(inputRainFall);
        val input2: Array<Array<FloatArray>> = arrayOf(inputTempHumd);
        val input1: Array<FloatArray> = arrayOf(inputTime);

        // Combine all inputs
        return arrayOf(input0, input1, input2);
    }

    // Rain Prediction Model has 1 output with shape: (1, 1)
    private fun prepareOutputs(): MutableMap<Int, Any> {
        Log.d(WaterFlowPrediction.TAG, "Preparing output...");

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
        inputTempReal: FloatArray, inputHumdReal: FloatArray,
        inputRainFallReal: FloatArray, inputTimeReal: Float
    ): Float
    {

        loadModelAndMinMaxScaler();

        val inputs: Array<Any> = preProcessInputs(
            inputTempReal, inputHumdReal,
            inputRainFallReal, inputTimeReal
        );

        val outputs: MutableMap<Int, Any> = prepareOutputs();

        Log.d(WaterFlowPrediction.TAG, "Predicting...");
        model.runForMultipleInputsOutputs(inputs, outputs);

        // Parse output to real value
        val predictedOutput: FloatArray = (outputs[0] as Array<FloatArray>)[0];
        val predictedReal: Float = this.scalerY.inverseTransform(predictedOutput)[0];

        Log.d(WaterFlowPrediction.TAG, "Predicted: $predictedReal");

        return predictedReal;
    }
}