package thang.com.wref.MainScreen.Havest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import thang.com.wref.R;

public class ProductionStatisticsActivity extends AppCompatActivity {
    private static final String TAG = "ProductionStatisticsActivity";
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private List<PhotoModel> photoModelList;
    private TextInputEditText inputHarvestDate;
    private DatePickerDialog.OnDateSetListener setListener;
    private RelativeLayout rltBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_statistics);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        circleIndicator = (CircleIndicator) findViewById(R.id.circleIndicator3);
        inputHarvestDate = (TextInputEditText) findViewById(R.id.inputHarvestDate);
        rltBack = (RelativeLayout) findViewById(R.id.rltBack);

        photoModelList = getListPhoto();
        PSAdapter psAdapter = new PSAdapter(photoModelList);
        viewPager.setAdapter(psAdapter);

        circleIndicator.setViewPager(viewPager);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        inputHarvestDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: quần què");
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ProductionStatisticsActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        setListener, year, month, day
                );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                inputHarvestDate.setText(date);
            }
        };

        rltBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private List<PhotoModel> getListPhoto() {
        List<PhotoModel> photoModelList = new ArrayList<>();
        photoModelList.add(new PhotoModel(R.drawable.thu_hoach_ca_chua_1));
        photoModelList.add(new PhotoModel(R.drawable.thu_hoach_ca_chua_2));
        photoModelList.add(new PhotoModel(R.drawable.thu_hoach_ca_chua_3));
        photoModelList.add(new PhotoModel(R.drawable.thu_hoach_ca_chua_4));

        return photoModelList;
    }
}