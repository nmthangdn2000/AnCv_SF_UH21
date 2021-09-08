package thang.com.wref.MainScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.widget.Toolbar;

import thang.com.wref.R;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        toolbar = (Toolbar) findViewById(R.id.toolbarInfor);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thông tin cá nhân");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mappingView();
        eventOnClick();
    }
    private void mappingView() {
    }
    private void eventOnClick() {
    }

    @Override
    public void onClick(View v) {

    }
}