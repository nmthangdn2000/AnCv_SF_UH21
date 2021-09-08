package thang.com.wref.util.MyCamera

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import thang.com.wref.CameraPredictionScreen.CameraPredictFragment
import java.io.File
import java.io.InputStream

class CameraUtilities(
        private val app: Activity
) {

    public var photoUri: Uri? = null;

    companion object {
        val REQUEST_IMAGE_CAPTURE: Int = 1;
        val FILEPROVIDER_AUTHORITY: String = "thang.com.wref.fileprovider";
    }

    public fun dispatchTakePictureIntent() {
        val takePictureIntent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent?.apply {
            resolveActivity(app.packageManager)?.also {
                val photoFile: File = try {
                    CameraStorage(app).createPhotoFile();
                } catch (error: Exception) {
                    Log.e(CameraPredictFragment.TAG, error.message!!);
                    throw error;
                }

                Log.d(CameraPredictFragment.TAG, "Photo will be save at: ${photoFile.path}");

                photoFile?.also {
                    photoUri = FileProvider.getUriForFile(
                            app,
                            FILEPROVIDER_AUTHORITY,
                            it
                    );

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    app.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

                    Log.d(CameraPredictFragment.TAG, "Taking picture...");
                }
            }
        }
    }

    public fun loadBitmap(): Bitmap? {
        var inputStream: InputStream? = null;

        try {
            inputStream = app.contentResolver.openInputStream(photoUri!!);
            return BitmapFactory.decodeStream(inputStream);
        } catch (error: Exception) {
            Log.d(CameraPredictFragment.TAG, error.message!!);
            throw error;
        } finally {
            inputStream?.apply {
                try {
                    close();
                } catch (error: Exception) {
                    Log.e(CameraPredictFragment.TAG, "Close error: ${error.message}");
                    throw error;
                }
            }
        }
    }
}