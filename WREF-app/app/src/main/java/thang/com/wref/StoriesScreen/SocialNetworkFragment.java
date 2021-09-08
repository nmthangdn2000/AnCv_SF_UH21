package thang.com.wref.StoriesScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import thang.com.wref.StoriesScreen.Adapters.NewsAdapter;
import thang.com.wref.StoriesScreen.Adapters.StoriesAdapter;
import thang.com.wref.Components.Animation.RecyclerViewAnimation;
import thang.com.wref.LoginScreen.SharedPreferencesManagement;
import thang.com.wref.StoriesScreen.Models.NewsModels;
import thang.com.wref.StoriesScreen.Models.StoriesModels;
import thang.com.wref.util.NetworkUtil;
import thang.com.wref.R;
import thang.com.wref.Components.Retrofits.NewsRetrofit;
import thang.com.wref.Components.Retrofits.StoriesRetrofit;

public class SocialNetworkFragment extends Fragment implements View.OnClickListener, View.OnTouchListener{
    private static final String TAG = "SocialNetworkFragment";
    private static final int REQUEST_CODE_EXAMPLE = 1;
    private View view;
    private MeowBottomNavigation meowBottomNavigation;

    private RecyclerView rcvStories, rcvNews;
    private ArrayList<NewsModels> newsArr;
    private ArrayList<StoriesModels> storiesArr;
    private NewsAdapter newsAdapter;
    private StoriesAdapter storiesAdapter;
    private LinearLayout btnPostNewNews;
    private SearchView searchView;
    private SwipeRefreshLayout srlSocial;

    private ViewPager2 viewPager2story;
    private StoriesViewpaerAdapter storiesViewpaerAdapter;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private NewsAdapter.onClickRecyclerNews mListenerNews;
    private FrameLayout fragmentCommnet;
    private boolean check = false;

    private Retrofit retrofit;
    private NetworkUtil networkUtil;
    private NewsRetrofit newsRetrofit;
    private StoriesRetrofit storiesRetrofit;
    private StoriesAdapter.onCLickStories mListener;
    private List<Fragment> fragmentStories;
    private RecyclerViewAnimation recyclerViewAnimation;

    private SharedPreferencesManagement sharedPreferencesManagement;

    public SocialNetworkFragment(MeowBottomNavigation meowBottomNavigation) {
        this.meowBottomNavigation = meowBottomNavigation;
    }
    public SocialNetworkFragment() {

    }
    public static SocialNetworkFragment newInstance(MeowBottomNavigation meowBottomNavigation) {
        Bundle args = new Bundle();
        SocialNetworkFragment fragment = new SocialNetworkFragment(meowBottomNavigation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferencesManagement = new SharedPreferencesManagement(getContext());
        if(Build.VERSION.SDK_INT >= 21){
            Window window = getActivity().getWindow();
            window.setStatusBarColor(getContext().getResources().getColor(R.color.colorStatusBar_Weather));
        }
        networkUtil = new NetworkUtil();
        retrofit = networkUtil.getRetrofit();
        fragmentStories = new ArrayList<>();
        recyclerViewAnimation = new RecyclerViewAnimation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_social_network, container, false);
        mapingView();
        setupRecyclerView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        storiesArr = new ArrayList<>();
        newsArr = new ArrayList<>();
        setOnClickRecyclerNews();
        onClickStories();
        addStoriesAdapter();
        addNewsAdapter();
        clospaneSlidingUpPanel();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgStories:

                break;
            case R.id.btnPostNewNews:
                clickPostNews();
                break;
            default:
                break;
        }
    }
    // set event onclick in item stories
    private void onClickStories(){
        mListener = new StoriesAdapter.onCLickStories() {
            @Override
            public void onClick(int position) {
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_down);
                meowBottomNavigation.startAnimation(animation);
                meowBottomNavigation.setVisibility(View.GONE);
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                setUpStories(position);
            }
        };

    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.SlidingUpPanelLayout:

                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_EXAMPLE) {
            if(resultCode == Activity.RESULT_OK){
                addNewsAdapter();
            }else {
               //
            }
        }
    }

    private void mapingView(){
        rcvStories = (RecyclerView) view.findViewById(R.id.rcvStories);
        rcvNews = (RecyclerView) view.findViewById(R.id.rcvNews);
        viewPager2story = (ViewPager2) view.findViewById(R.id.storiesViewpager);
        slidingUpPanelLayout = (SlidingUpPanelLayout) view.findViewById(R.id.SlidingUpPanelLayout);
        fragmentCommnet = (FrameLayout) view.findViewById(R.id.fragmentCommnet);
        btnPostNewNews = (LinearLayout) view.findViewById(R.id.btnPostNewNews);
        searchView = (SearchView) view.findViewById(R.id.searchView);
        srlSocial = (SwipeRefreshLayout) view.findViewById(R.id.srlSocial);

        btnPostNewNews.setOnClickListener(this);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        srlSocial.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addStoriesAdapter();
                addNewsAdapter();
            }
        });

    }
    // move to PostNewsActivity
    private void clickPostNews(){
        Intent intent = new Intent(getContext().getApplicationContext(), PostNewsActivity.class);
        startActivityForResult(intent, REQUEST_CODE_EXAMPLE);
    }
    private void setupRecyclerView(){
        rcvStories.setHasFixedSize(true);
        rcvNews.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManagerS = new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false
                );
        LinearLayoutManager linearLayoutManagerN = new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false
        );

        rcvNews.setLayoutManager(linearLayoutManagerN);
        rcvStories.setLayoutManager(linearLayoutManagerS);
        rcvStories.setNestedScrollingEnabled(false);
        rcvNews.setNestedScrollingEnabled(false);
    }
    private void addStoriesAdapter(){
        storiesArr.clear();
        storiesRetrofit = retrofit.create(StoriesRetrofit.class);
        // get data stories from api
        Call<List<StoriesModels>> listCall = storiesRetrofit.getStories(sharedPreferencesManagement.getTOKEN());
        listCall.enqueue(new Callback<List<StoriesModels>>() {
            @Override
            public void onResponse(Call<List<StoriesModels>> call, Response<List<StoriesModels>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getContext(), "lỗi mạng", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    List<StoriesModels> storiesModels = response.body();
                    // Select stories of logged in user to the top array
                    for(StoriesModels story : storiesModels){
                        if(story.getUsers().getId().equals(sharedPreferencesManagement.getID())) { // lấy story userLogin để đầu mảng
                            storiesArr.add(story);
                            break;
                        }
                    }
                    for(StoriesModels story : storiesModels){
                        if(!story.getUsers().getId().equals(sharedPreferencesManagement.getID())){ // lấy story userLogin để đầu mảng
                            storiesArr.add(story);
                        }
                    }
                    storiesAdapter.notifyDataSetChanged();
                }
                call.cancel();
            }

            @Override
            public void onFailure(Call<List<StoriesModels>> call, Throwable t) {
                call.cancel();
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
        // set array stories to recyclerview
        storiesAdapter = new StoriesAdapter(storiesArr, getContext().getApplicationContext(), mListener);
        rcvStories.setAdapter(storiesAdapter);
    }
    private void addNewsAdapter(){
        newsArr.clear();
        newsRetrofit = retrofit.create(NewsRetrofit.class);
        // get new Posts from api
        Call<List<NewsModels>> listCall = newsRetrofit.getNews(sharedPreferencesManagement.getTOKEN());
        listCall.enqueue(new Callback<List<NewsModels>>() {
            @Override
            public void onResponse(Call<List<NewsModels>> call, Response<List<NewsModels>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Không có mạng", Toast.LENGTH_SHORT).show();
                }else{
                    List<NewsModels> news = response.body();
                    for(NewsModels post : news){
                        newsArr.add(post);
                    }
//                    Collections.reverse(arrayPosts);
                    newsAdapter.notifyDataSetChanged();
                }
                call.cancel();
                srlSocial.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<NewsModels>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                call.cancel();
            }
        });
        // set array Posts to recyclerview
        newsAdapter = new NewsAdapter(newsArr, getContext().getApplicationContext(), mListenerNews);
        rcvNews.setAdapter(newsAdapter);
    }
    private void setUpStories(int position){
        getStories(position);
        viewPager2story.setVisibility(View.VISIBLE);
        fragmentCommnet.setVisibility(View.INVISIBLE);
        viewPager2story.setPageTransformer(new CubeTransformerViewpager());
        viewPager2story.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
    }
    private void getStories(int position){
        fragmentStories.clear();
        // get stories from api
        storiesRetrofit = retrofit.create(StoriesRetrofit.class);
        Call<List<StoriesModels>> listCall = storiesRetrofit.getStories(sharedPreferencesManagement.getTOKEN());
        listCall.enqueue(new Callback<List<StoriesModels>>() {
            @Override
            public void onResponse(Call<List<StoriesModels>> call, Response<List<StoriesModels>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getContext(), "lỗi mạng", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    List<StoriesModels> storiesModels = response.body();
                    // add stories to fragmentStories
                    for(StoriesModels story : storiesModels){
                        if(story.getUsers().getId().equals(sharedPreferencesManagement.getID())){
                            ArrayList<StoriesModels> stories = new ArrayList<>();
                            stories.add(story);
                            fragmentStories.add(new StoriesFragment(stories, getContext(), viewPager2story, slidingUpPanelLayout));
                            break;
                        }
                    }
                    for(StoriesModels story : storiesModels){
                        if(!story.getUsers().getId().equals(sharedPreferencesManagement.getID())){
                            ArrayList<StoriesModels> stories = new ArrayList<>();
                            stories.add(story);
                            fragmentStories.add(new StoriesFragment(stories, getContext(), viewPager2story, slidingUpPanelLayout));
                        }
                    }
                    storiesViewpaerAdapter.notifyDataSetChanged();
                }
                call.cancel();
            }

            @Override
            public void onFailure(Call<List<StoriesModels>> call, Throwable t) {
                call.cancel();
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
        // set array fragmentStories to StoriesViewpaerAdapter
        storiesViewpaerAdapter = new StoriesViewpaerAdapter(getFragmentManager(), getLifecycle(), fragmentStories);
        viewPager2story.setAdapter(storiesViewpaerAdapter);
        viewPager2story.setCurrentItem(position,false);
    }
    // listen SlidingUpPanel open or close
    private void clospaneSlidingUpPanel(){
        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.d(TAG, "onPanelSlide: mở");
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if(newState == SlidingUpPanelLayout.PanelState.COLLAPSED){
                    Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_up);
                    animation.setDuration(500);
                    // open SlidingUpPane then hidden meowBottomNavigation
                    meowBottomNavigation.setVisibility(View.VISIBLE);
                    meowBottomNavigation.startAnimation(animation);
                    slidingUpPanelLayout.setOverlayed(true);
                    clearFragment();
                }
            }
        });
    }
    private void setOnClickRecyclerNews(){
        mListenerNews = new NewsAdapter.onClickRecyclerNews() {
            @Override
            public void onClickComment(int position, LinearLayout btnComment) {
//                Log.d(TAG, "onClickComment: aaaaaaaaaaaaa"+ getFragmentManager().getFragments().size());
                viewPager2story.setVisibility(View.INVISIBLE);
                fragmentCommnet.setVisibility(View.VISIBLE);
                slidingUpPanelLayout.setOverlayed(false);
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_down);
                meowBottomNavigation.startAnimation(animation);
                meowBottomNavigation.setVisibility(View.GONE);
                CommentFragment commentFragment = new CommentFragment();
                getFragmentManager().beginTransaction().add(R.id.fragmentCommnet, commentFragment, "commentFragment").commit();
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
        };
    }
    private void clearFragment(){
        for (Fragment fragment : getFragmentManager().getFragments()) {
            if (fragment.getTag().equals("commentFragment")) {
                getFragmentManager().beginTransaction().remove(fragment).commit();
            }
            else if (fragment.getTag().equals("postNewsFragment")) {
                getFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }
    }
}