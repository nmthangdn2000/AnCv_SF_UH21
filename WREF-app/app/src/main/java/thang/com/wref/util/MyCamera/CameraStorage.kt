package thang.com.wref.util.MyCamera

import android.app.Activity
import android.os.Environment
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CameraStorage (
        private val app: Activity
) {
    public fun createPhotoFile(): File {
        val timestamp: String = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Date());
        val storageDir: File = app.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!;
        return File.createTempFile(
                "CAM_${timestamp}_",
                ".jpg",
                storageDir
        );
    }
}