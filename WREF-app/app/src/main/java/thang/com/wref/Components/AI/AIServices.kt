package thang.com.wref.Components.AI

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.util.Log
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.label.Category
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import org.tensorflow.lite.task.vision.classifier.ImageClassifier.ImageClassifierOptions
import java.nio.MappedByteBuffer
import kotlin.math.*

class AIServices(
        private val app: Context
) {

    private lateinit var inputImage: TensorImage;
    private lateinit var imageProcessor: ImageProcessingOptions;
    private val MODEL_NAME: String = "lite-model_disease-classification.tflite";

    companion object {
        val TAG: String = AIServices::class.java.simpleName;
        val translateMap = HashMap<String, String>();

        init {
            translateMap["Tomato Healthy"] = "Cà chua_Khỏe Mạnh";
            translateMap["Tomato Septoria Leaf Spot"] = "Cà chua_Đốm lá";
            translateMap["Tomato Bacterial Spot"] = "Cà chua_Đốm vi khuẩn";
            translateMap["Tomato Blight"] = "Cà chua_Úa sớm";
            translateMap["Cabbage Healthy"] = "Cải bắp_Khỏe mạnh";
            translateMap["Tomato Spider Mite"] = "Cà chua_Nhện đỏ";
            translateMap["Tomato Leaf Mold"] = "Cà chua_Mốc xám";
            translateMap["Tomato_Yellow Leaf Curl Virus"] = "Cà chua_Xoăn vàng lá";
            translateMap["Soy_Frogeye_Leaf_Spot"] = "Đậu nành_Đốm mắt ếch";
            translateMap["Soy_Downy_Mildew"] = "Đậu nành_Đốm Phấn";
            translateMap["Maize_Ravi_Corn_Rust"] = "Ngô_Gỉ ngô";
            translateMap["Maize_Healthy"] = "Ngô_Khỏe mạnh";
            translateMap["Maize_Grey_Leaf_Spot"] = "Ngô_Đốm lá";
            translateMap["Maize_Lethal_Necrosis"] = "Ngô_Sọc lá";
            translateMap["Soy_Healthy"] = "Đậu nành_Khỏe mạnh";
            translateMap["Cabbage Black Rot"] = "Cải bắp_Thối đen";
        }
    }



    private fun loadImage(bitmap: Bitmap) {
        Log.d(TAG, "Load bitmap image...");

        inputImage = TensorImage.fromBitmap(bitmap);

        val width: Int = inputImage.width;
        val height: Int = inputImage.height;
        val croppedSize: Int = min(width, height);

        imageProcessor = ImageProcessingOptions
                .builder()
                .setRoi(Rect(
                        /*left=*/ (width - croppedSize) / 2,
                        /*top=*/ (height - croppedSize) / 2,
                        /*right=*/ (width + croppedSize) / 2,
                        /*bottom=*/ (height + croppedSize) / 2
                ))
                .build();
    }

    private fun loadModelFile(): MappedByteBuffer {
        return FileUtil.loadMappedFile(app, MODEL_NAME);
    }

    private fun classify(): List<Classifications> {
        Log.d(TAG, "Classifying ...");

        val options: ImageClassifierOptions = ImageClassifierOptions
                .builder()
                .setMaxResults(5)
                .build();

        val imageClassifier: ImageClassifier = ImageClassifier
                .createFromBufferAndOptions(
                        loadModelFile(),
                        options
                );

        return imageClassifier.classify(inputImage, imageProcessor);
    }

    public fun predict(bitmap: Bitmap): ArrayList<HashMap<String, String>> {
        Log.d(TAG, "Begin predict...");

        loadImage(bitmap);
        val categories: List<Category> = classify()[0].categories;

        Log.d(TAG, "Predict done!");

        val result: ArrayList<HashMap<String, String>> = ArrayList();

        for (category: Category in categories) {

            val score = category.score * 100;
            if (score == 0.toFloat()) continue;

            val diseaseInfoMapped = translateMap[category.label];
            val diseaseInfo = diseaseInfoMapped!!.split("_", ignoreCase = true, limit = 2);

            diseaseInfo.apply {
                val prediction: HashMap<String, String> = HashMap();

                if (size == 1) {
                    prediction["plantName"] = diseaseInfo[0];
                    prediction["diseaseName"] = diseaseInfo[0];
                    prediction["percent"] = "%.2f".format(score);
                } else {
                    prediction["plantName"] = diseaseInfo[0];
                    prediction["diseaseName"] = diseaseInfo[1];
                    prediction["percent"] = "%.2f".format(score);
                }

                result.add(prediction);
            }
        }

        return result;
    }
}