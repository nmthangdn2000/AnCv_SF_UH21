package thang.com.wref.MainScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;


import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import thang.com.wref.MainScreen.Adapters.ViewPagerAdapter;
import thang.com.wref.R;
import thang.com.wref.util.SocketIO;


public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    private int NumberPageCurr = 1, NumberPagePause = 1;
    private MeowBottomNavigation bottomNavigation;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter viewPagerAdapter;
    private SocketIO socketIO;

    @Override
    protected void onPause() {
        super.onPause();
        NumberPagePause = NumberPageCurr;
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigation.show(NumberPagePause, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        socketIO = new SocketIO();
        socketIO.ConnectSocket();

        mapping();
        setBottomNavigation();
    }

    private void mapping(){
        bottomNavigation = findViewById(R.id.BottomNavigation);
        viewPager2 = findViewById(R.id.ViewPager2);
        viewPager2.setUserInputEnabled(false);
        viewPager2.setOffscreenPageLimit(1);
        viewPagerAdapter = new ViewPagerAdapter(this, bottomNavigation);
        viewPager2.setAdapter(viewPagerAdapter);
//        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                bottomNavigation.show(position+1, true);
//            }
//        });
    }

    private void setBottomNavigation(){
        // set icon to bottmnavigation
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_map_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_newspaper_folded));
//        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_cloud_queue_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_photo_camera_24));
        // default button

        //event
        // Set Menu Click Listener
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                switch (item.getId()){
                    case 1:
                        Log.d(TAG, " a1 "+ item.getId());
                        NumberPageCurr = item.getId();
                        viewPager2.setCurrentItem(0);
                        break;
                    case 2:
                        Log.d(TAG, " a2 "+ item.getId());
                        NumberPageCurr = item.getId();
                        bottomNavigation.clearCount(2);
                        viewPager2.setCurrentItem(1);
                        break;
//                    case 3:
//                        Log.d(TAG, " a3 "+ item.getId());
//                        bottomNavigation.clearCount(3);
//                        viewPager2.setCurrentItem(2);
//                        break;
                    case 3:
                        Log.d(TAG, " a3 "+ item.getId());
                        NumberPageCurr = item.getId();
                        viewPager2.setCurrentItem(2);
                        break;
                    default:
                        viewPager2.setCurrentItem(0);
                        NumberPageCurr = item.getId();
                        break;
                }
            }
        });
        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                // your codes
            }
        });
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });
        bottomNavigation.show(1, true);
        bottomNavigation.setCount(2, "15");
        bottomNavigation.setCount(3, "");
    }
}