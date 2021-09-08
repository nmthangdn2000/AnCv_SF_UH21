//package thang.com.wref.Stories;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import androidx.fragment.app.Fragment;
//import androidx.viewpager2.widget.ViewPager2;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import thang.com.wref.Login.SharedPreferencesManagement;
//import thang.com.wref.StoriesScreen.Models.StoriesModels;
//import thang.com.wref.util.NetworkUtil;
//import thang.com.wref.R;
//import thang.com.wref.Components.Retrofits.StoriesRetrofit;
//
//public class ViewpagerStoriesActivity extends Fragment {
//    private View view;
//
//    private ViewPager2 viewPager2story;
//    private StoriesViewpaerAdapter storiesViewpaerAdapter;
//
//    private StoriesRetrofit storyRetrofit;
//    private NetworkUtil networkUtil;
//    private Retrofit retrofit;
//    private int numberStory;
//    private String id ="";
//    private SharedPreferencesManagement sharedPreferencesManagement;
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//
//        }
//        sharedPreferencesManagement = new SharedPreferencesManagement(getContext());
//        id = sharedPreferencesManagement.getID();
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        view = inflater.inflate(R.layout.fragment_stories, container, false);
//        mapingView();
//
//        return view;
//    }
//    private void mapingView(){
//        viewPager2story = (ViewPager2) view.findViewById(R.id.storiesViewpager);
//        Intent intent = getActivity().getIntent();
//        numberStory  = (int) intent.getSerializableExtra("numberClickStory");
//        Log.d("storhjkl", " "+numberStory);
//        networkUtil = new NetworkUtil();
//        getStory();
//        viewPager2story.setPageTransformer(new CubeTransformerViewpager());
//        viewPager2story.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//
//            }
//        });
//    }
//    private void getStory() {
//
//        List<Fragment> fragments = new ArrayList<>();
//        retrofit = networkUtil.getRetrofit();
//        storyRetrofit = retrofit.create(StoriesRetrofit.class);
//        Call<List<StoriesModels>> callstory = storyRetrofit.getStories(sharedPreferencesManagement.getTOKEN());
//        callstory.enqueue(new Callback<List<StoriesModels>>() {
//            @Override
//            public void onResponse(Call<List<StoriesModels>> call, Response<List<StoriesModels>> response) {
//                if(!response.isSuccessful()){
//                    Toast.makeText(getContext(), "lỗi rác story", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                List<StoriesModels> storys = response.body();
//                for(StoriesModels story : storys){
//                    if(story.getUsers().getId().equals(id)){
//                        ArrayList<StoriesModels> stories = new ArrayList<>();
//                        stories.add(story);
//                        fragments.add(new StoriesFragment(stories, getContext(), viewPager2story));
//                        break;
//                    }
//                }
//                for(StoriesModels story : storys){
//                    if(!story.getUsers().getId().equals(id)){
//                        ArrayList<StoriesModels> stories = new ArrayList<>();
//                        stories.add(story);
//                        fragments.add(new StoriesFragment(stories, getContext(), viewPager2story));
//                    }
//                }
////                Collections.reverse(arrayStory);
//                storiesViewpaerAdapter = new StoriesViewpaerAdapter(getFragmentManager(),getLifecycle(),fragments);
//                viewPager2story.setAdapter(storiesViewpaerAdapter);
//                viewPager2story.setCurrentItem(numberStory,false);
//            }
////
//            @Override
//            public void onFailure(Call<List<StoriesModels>> call, Throwable t) {
//                Log.d("loaddataa","Load không được lỗi : "+t.getMessage());
//            }
//        });
//    }
//}
