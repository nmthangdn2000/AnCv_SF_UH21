package thang.com.wref.StoriesScreen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import thang.com.wref.LoginScreen.Models.ErrModel;
import thang.com.wref.MainScreen.Adapters.ImgFromGallyeAdapter;
import thang.com.wref.LoginScreen.SharedPreferencesManagement;
import thang.com.wref.util.NetworkUtil;
import thang.com.wref.R;
import thang.com.wref.Components.Retrofits.NewsRetrofit;
import thang.com.wref.MainScreen.Map.LocationFragment;

public class PostNewsActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "PostNewsActivity";
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    private Toolbar toolBar;
    private CircleImageView userAvata;
    private TextView btnLocation;
    private EditText edit_document;
    private CardView btnUploadnow, btnUploadVideo, uploadCamera;
    private RecyclerView recylerTheme, rcvImg;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private FrameLayout fragmentLocation;

    private List<Uri> mSelected;
    private ArrayList<String> pathArr;
    private ImgFromGallyeAdapter imgFromGallyeAdapter;

    private String iduser = "", token ="";
    private SharedPreferencesManagement sharedPreferencesManagement;

    private NetworkUtil networkUtil;
    private Retrofit retrofit;
    private NewsRetrofit newsRetrofit;
    private TextView btnPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_news);
        mSelected = new ArrayList<>();
        pathArr = new ArrayList<>();
        sharedPreferencesManagement = new SharedPreferencesManagement(PostNewsActivity.this);
        iduser= sharedPreferencesManagement.getID();
        token = sharedPreferencesManagement.getTOKEN();
        networkUtil = new NetworkUtil();
        retrofit = networkUtil.getRetrofit();

        setUpToolBar();
        mapPingView();
        clospaneSlidingUpPanel();
        setUpRecyclerView();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLocation:
                LocationFragment locationFragment = new LocationFragment(btnLocation, slidingUpPanelLayout);
                getSupportFragmentManager().beginTransaction().add(R.id.fragmentLocation, locationFragment, "locationFragment").commit();
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                break;
            case R.id.btnUploadnow:
                getImage();
                break;
            case R.id.btnUploadVideo:
                break;
            case R.id.uploadCamera:
                break;
            case R.id.btnPost:
                UploadToServer();
                break;
            default:
                break;
        }
    }
    private void mapPingView(){
        userAvata = (CircleImageView) findViewById(R.id.userAvata);
        btnLocation = (TextView) findViewById(R.id.btnLocation);
        edit_document = (EditText) findViewById(R.id.edit_document);
        btnUploadnow = (CardView) findViewById(R.id.btnUploadnow);
        btnUploadVideo = (CardView) findViewById(R.id.btnUploadVideo);
        uploadCamera = (CardView) findViewById(R.id.uploadCamera);
        recylerTheme = (RecyclerView) findViewById(R.id.recylerTheme);
        rcvImg = (RecyclerView) findViewById(R.id.rcvImg);
        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.slidingUpPanelLayout);
        fragmentLocation = (FrameLayout) findViewById(R.id.fragmentLocation);
        btnPost = (TextView) findViewById(R.id.btnPost);

        btnLocation.setOnClickListener(this);
        btnPost.setOnClickListener(this);
        btnUploadnow.setOnClickListener(this);
    }
    private void setUpRecyclerView(){
        rcvImg.setHasFixedSize(true);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(PostNewsActivity.this);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setAlignItems(AlignItems.STRETCH);
        rcvImg.setLayoutManager(flexboxLayoutManager);
    }
    // get path image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data != null){
            if(requestCode == IMAGE_PICK_CODE){
                mSelected = Matisse.obtainResult(data);
                showImg();
                for (int i = 0; i < mSelected.size(); i++) {
                    pathArr.add(getPathFromURI(PostNewsActivity.this, mSelected.get(i)));
                }
            }
        }
    }
    private void UploadToServer(){
        if(btnLocation.getTag() == null)
            Toast.makeText(this, "Bạn chưa chọn địa điểm", Toast.LENGTH_SHORT).show();
        else {
            // prepare necessary data
            RequestBody text = RequestBody.create(edit_document.getText().toString(), MultipartBody.FORM);
            RequestBody location = RequestBody.create(btnLocation.getTag().toString(), MultipartBody.FORM);
            // UAttach multiple photos
            List<MultipartBody.Part> parts = new ArrayList<>();
            for (int i = 0; i < pathArr.size(); i++) {
                File file = new File(pathArr.get(i));
                RequestBody body = RequestBody.create(file, MediaType.parse("multipart/form-data"));
                parts.add(MultipartBody.Part.createFormData("image", pathArr.get(i), body));
            }
            // sent data to api
            newsRetrofit = retrofit.create(NewsRetrofit.class);
            Call<ErrModel> errModelCall = newsRetrofit.postPossts(token, location, text, parts);
            errModelCall.enqueue(new Callback<ErrModel>() {
                @Override
                public void onResponse(Call<ErrModel> call, Response<ErrModel> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(PostNewsActivity.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
                    } else {
                        ErrModel errModel = response.body();
                        if (!errModel.isSuccess()) {
                            Toast.makeText(PostNewsActivity.this, "Không thể đăng bài", Toast.LENGTH_SHORT).show();
                        } else {
                            setResult(Activity.RESULT_OK);
                            finish();
                        }
                    }
                    call.cancel();
                }

                @Override
                public void onFailure(Call<ErrModel> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                    call.cancel();
                }
            });
        }
    }

    // show image selected to view
    private void showImg(){
        imgFromGallyeAdapter = new ImgFromGallyeAdapter(mSelected, PostNewsActivity.this);
        rcvImg.setAdapter(imgFromGallyeAdapter);
        rcvImg.setVisibility(View.VISIBLE);
    }
    // get image from photo library
    private void getImage(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission, PERMISSION_CODE);
            }else{
                pickImgFromGalley();
            }
        }else{
            pickImgFromGalley();
        }
    }
    // open all image in photo library
    private void pickImgFromGalley() {
        Matisse.from(PostNewsActivity.this)
                .choose(MimeType.ofAll())
                .countable(true)
                .maxSelectable(9)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .imageEngine(new GlideEngine())
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .showPreview(false) // Default is `true`
                .forResult(IMAGE_PICK_CODE);
    }

    private void setUpToolBar() {
        toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }

    private void clospaneSlidingUpPanel(){
        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.d(TAG, "onPanelSlide: mở");
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if(newState == SlidingUpPanelLayout.PanelState.COLLAPSED){
                    clearFragment();
                }
            }
        });
    }
    private void clearFragment(){
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment.getTag().equals("locationFragment")) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }
    }
    // below code is used to get absolute path of the image
    public static String getPathFromURI(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    private int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}