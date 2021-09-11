package thang.com.wref.Components.AI

import android.content.Context
import android.util.Log
import org.tensorflow.lite.support.common.FileUtil
import java.nio.MappedByteBuffer

class Utils {
    companion object {
        val TAG: String = Utils::class.java.simpleName;

        public fun loadModelFile(app: Context, modelPath: String): MappedByteBuffer {
            Log.d(TAG, "Load Model: $modelPath");

            return FileUtil.loadMappedFile(app, modelPath);
        }
    }
}