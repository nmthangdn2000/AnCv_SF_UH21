package thang.com.wref.StoriesScreen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.shts.android.storiesprogressview.StoriesProgressView;
import thang.com.wref.StoriesScreen.Models.StoriesModels;
import thang.com.wref.R;

import static thang.com.wref.util.Constants.BASE_URL;

public class StoriesFragment extends Fragment implements StoriesProgressView.StoriesListener {
    private static final String TAG = "StoriesFragment";
    private View view, reverse, skip;
    private Context context;
    private EditText edtFeeback;
    private CircleImageView userAvata;
    private TextView txtusername, txtTimeStory;
    private ImageView imageStories;
    private RelativeLayout RelativeStoriesInfor;
    private LinearLayout imgStoryhear, linearStoriesBottom, btnClose;
    private StoriesProgressView stories;
    private ViewPager2 viewPager2story;
    private int counter = 0, counterPause = 0;
    private Palette palette;
    private SlidingUpPanelLayout slidingUpPanelLayout;

    private ArrayList<StoriesModels> arrayList;

    long pressTime = 0L;
    long limit = 500L;
    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        // touch handling in fragment stories
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    pressTime = System.currentTimeMillis();
                    stories.pause();
                    RelativeStoriesInfor.animate().alpha(0.0f).setDuration(500);
                    linearStoriesBottom.animate().alpha(0.0f).setDuration(500);
                    Log.d(TAG,"ACTION_DOWN");
                    return false;
                case MotionEvent.ACTION_UP:
                    long now = System.currentTimeMillis();
                    stories.resume();
                    Log.d(TAG,"ACTION_UP");
                    RelativeStoriesInfor.animate().alpha(1.0f).setDuration(500);
                    linearStoriesBottom.animate().alpha(1.0f).setDuration(500);
                    return limit < now - pressTime;
                case MotionEvent.ACTION_CANCEL:
                    long now1 = System.currentTimeMillis();
                    stories.resume();
                    Log.d(TAG,"ACTION_CANCEL");
                    RelativeStoriesInfor.animate().alpha(1.0f).setDuration(500);
                    linearStoriesBottom.animate().alpha(1.0f).setDuration(500);
                    return limit < now1 - pressTime;
            }
            return true;
        }
    };
    public StoriesFragment(ArrayList<StoriesModels> arrayList, Context context, ViewPager2 viewPager2story, SlidingUpPanelLayout slidingUpPanelLayout) {
        this.arrayList = arrayList;
        this.context = context;
        this.viewPager2story = viewPager2story;
        this.slidingUpPanelLayout = slidingUpPanelLayout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stories, container, false);
        mapingView();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupStories();
        setData();
        setUpTouch();
    }
    private void mapingView(){
        RelativeStoriesInfor = (RelativeLayout) view.findViewById(R.id.RelativeStoriesInfor);
        stories = (StoriesProgressView) view.findViewById(R.id.stories);
        imgStoryhear = (LinearLayout) view.findViewById(R.id.imgStoryhear);
        imageStories = (ImageView) view.findViewById(R.id.imageStories);
        linearStoriesBottom = (LinearLayout) view.findViewById(R.id.linearStoriesBottom);
        txtusername = (TextView) view.findViewById(R.id.txtusername);
        userAvata = (CircleImageView) view.findViewById(R.id.userAvata);
        edtFeeback = (EditText) view.findViewById(R.id.edtFeeback);
    }
    private void setupStories(){
        stories.setStoriesCount(arrayList.get(0).getMedia().length); // <- set stories
        stories.setStoryDuration(4000L); // <- set a story duration
        stories.setStoriesListener(this);
    }

    private void setUpTouch(){
        btnClose = (LinearLayout) view.findViewById(R.id.btnClose);
        reverse = (View) view.findViewById(R.id.reverse);
        skip = (View) view.findViewById(R.id.skip);
        reverse.setOnTouchListener(onTouchListener);
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter == 0){
                    viewPager2story.setCurrentItem(getItem(-1),true);
                }
                stories.reverse();
            }
        });
        skip.setOnTouchListener(onTouchListener);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stories.skip();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            }
        });
    }
    private void setData(){
//        txtTimeStory.setText(date.time(arrayListStory.get(0).getCreatedAt()));
        edtFeeback.setHint("Trả lời "+arrayList.get(0).getUsers().getUsername()+ " ...");
        txtusername.setText(""+arrayList.get(0).getUsers().getUsername());
        Glide.with(context).load(BASE_URL+"uploads/"+arrayList.get(0).getUsers().getAvata())
                .apply(new RequestOptions().override(100,100)).into(userAvata);
        // <- start progress
        imageStories = (ImageView) view.findViewById(R.id.imageStories);
        Glide.with(getContext()).asBitmap().load(BASE_URL+"uploads/"+arrayList.get(0).getMedia()[counter])
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        // set background according to the main color of the image
                        imageStories.setImageBitmap(resource);
                        palette = Palette.from(resource).generate();
                        Palette.Swatch swatch = palette.getMutedSwatch();
                        Palette.Swatch swatch1 = palette.getLightMutedSwatch();
                        if(swatch != null && swatch1 != null){
                            int colors[] = {swatch1.getRgb(),swatch.getRgb()};
                            GradientDrawable gradientDrawable = new GradientDrawable(
                                    GradientDrawable.Orientation.TOP_BOTTOM, colors);
                            imgStoryhear.setBackground(gradientDrawable);
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }
    @Override
    public void onNext() {
        if(counter < arrayList.get(0).getMedia().length){
            ++counter;
            // set background according to the main color of the image
            Glide.with(getContext()).asBitmap().load(BASE_URL+"uploads/"+arrayList.get(0).getMedia()[counter])
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            imageStories.setImageBitmap(resource);
                            palette = Palette.from(resource).generate();
                            Palette.Swatch swatch = palette.getMutedSwatch();
                            Palette.Swatch swatch1 = palette.getLightMutedSwatch();
                            if(swatch != null && swatch1 != null){
                                int colors[] = {swatch1.getRgb(),swatch.getRgb()};
                                GradientDrawable gradientDrawable = new GradientDrawable(
                                        GradientDrawable.Orientation.TOP_BOTTOM, colors);
                                imgStoryhear.setBackground(gradientDrawable);
                            }
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        }
    }

    @Override
    public void onPrev() {
        if(counter>0){
            --counter;
            // set background according to the main color of the image
            Glide.with(getContext()).asBitmap().load(BASE_URL+"uploads/"+arrayList.get(0).getMedia()[counter])
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            imageStories.setImageBitmap(resource);
                            palette = Palette.from(resource).generate();
                            Palette.Swatch swatch = palette.getMutedSwatch();
                            Palette.Swatch swatch1 = palette.getLightMutedSwatch();
                            if(swatch != null && swatch1 != null){
                                int colors[] = {swatch1.getRgb(),swatch.getRgb()};
                                GradientDrawable gradientDrawable = new GradientDrawable(
                                        GradientDrawable.Orientation.TOP_BOTTOM, colors);
                                imgStoryhear.setBackground(gradientDrawable);
                            }
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        }
    }

    @Override
    public void onComplete() {
        stories.destroy();
        viewPager2story.setCurrentItem(getItem(1),true);
    }

    @Override
    public void onDestroy() {
        stories.destroy();
        super.onDestroy();
    }

    @Override
    public void onStart() {
        setupStories();
        counter=0;
        stories.startStories(counter);
        super.onStart();
    }
    @Override
    public void onResume() {
        setupStories();
        counter = counterPause;
        stories.startStories(counter);
        super.onResume();
    }
    private int getItem(int i) {
        int number =  viewPager2story.getCurrentItem()+i;
        if(number<0){
            return 0;
        }else{
            return number;
        }
    }
    @Override
    public void onPause() {
        counterPause = counter;
        Log.d("ảnh hiện tại", " "+ counterPause);
        super.onPause();
    }

}