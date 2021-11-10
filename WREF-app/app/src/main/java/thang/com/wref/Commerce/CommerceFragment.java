package thang.com.wref.Commerce;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import thang.com.wref.Components.Retrofits.ProductRetrofit;
import thang.com.wref.LoginScreen.SharedPreferencesManagement;
import thang.com.wref.MainScreen.Models.ResponseModel;
import thang.com.wref.R;
import thang.com.wref.util.NetworkUtil;

public class CommerceFragment extends Fragment {
    private static final String TAG = "CommerceFragment";

    private View view;
    private ImageSlider slide;
    private RecyclerView rcvItemQuicklink, rcvLayoutItem;
    private CardView rootBlur;
    private SearchView etSearchView;
    private ArrayList<ItemLayoutModel> itemLayoutModels;
    private NetworkUtil networkUtil;
    private Retrofit retrofit;
    private ProductRetrofit productRetrofit;
    private SharedPreferencesManagement sharedPreferencesManagement;
    private ItemLayoutProductAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkUtil = new NetworkUtil();
        retrofit = networkUtil.getRetrofit();
        itemLayoutModels = new ArrayList<>();
        sharedPreferencesManagement = new SharedPreferencesManagement(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_commerce, container, false);
        slide = (ImageSlider) view.findViewById(R.id.slide);
        rcvItemQuicklink = (RecyclerView) view.findViewById(R.id.rcvItemQuicklink);
        rcvLayoutItem = (RecyclerView) view.findViewById(R.id.rcvLayoutItem);
        rootBlur = (CardView) view.findViewById(R.id.rootBlur);
        etSearchView = (SearchView) view.findViewById(R.id.etSearchView);

        searchView();
        setUpRcvQuickLink();
        setUpRcvLayoutItem();
        setupSlide();
        getData();
        return view;
    }

//    @Override
//    public void onConfigurationChanged(@NonNull Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
//            Log.d(TAG, "bật");
//        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
//            Log.d(TAG, "tắt");
//        }
//    }

    private void setupSlide() {
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("http://baniglobal.com/wp-content/uploads/2018/07/banner-phun-thuoc...png", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://nonglongtin.com.vn/wp-content/uploads/2020/06/banner-01.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://ducthanhco.vn/uploads/slides/banner-ema.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://btnmt.1cdn.vn/2021/06/20/1352_quangcao__1199x600-1-.png", ScaleTypes.FIT));

        slide.setImageList(slideModels);
    }

    private void searchView() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setUpRcvQuickLink(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvItemQuicklink.setLayoutManager(linearLayoutManager);
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Tất cả");
        strings.add("Cà chua");
        strings.add("Dưa leo");
        strings.add("Lúa");
        strings.add("Hạt giống");
        strings.add("Phân bón");
        strings.add("Thuốc trừ sâu");
        QuickLinkAdapter quickLinkAdapter = new QuickLinkAdapter(strings);
        rcvItemQuicklink.setAdapter(quickLinkAdapter);
    }

    private void setUpRcvLayoutItem() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false);
        rcvLayoutItem.setLayoutManager(linearLayoutManager);
        rcvLayoutItem.setNestedScrollingEnabled(false);
    }

    private void getData(){
        productRetrofit = retrofit.create(ProductRetrofit.class);
        Call<ResponseModel<List<ItemLayoutModel>>> call = productRetrofit.getFullProduct(sharedPreferencesManagement.getTOKEN());
        call.enqueue(new Callback<ResponseModel<List<ItemLayoutModel>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<ItemLayoutModel>>> call, Response<ResponseModel<List<ItemLayoutModel>>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getContext(), "lỗi mạng", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    ResponseModel<List<ItemLayoutModel>> responseModel = response.body();
                    List<ItemLayoutModel> res = responseModel.getData();
                    for (ItemLayoutModel data : res){
                        itemLayoutModels.add(data);
                    }
                    Log.d(TAG, "onResponse: cái lozz"+ itemLayoutModels.get(0).getTitle());
                }
                adapter.notifyDataSetChanged();
                call.cancel();
            }

            @Override
            public void onFailure(Call<ResponseModel<List<ItemLayoutModel>>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                call.cancel();
            }
        });
        adapter = new ItemLayoutProductAdapter(itemLayoutModels, getContext());
        rcvLayoutItem.setAdapter(adapter);
    }
}