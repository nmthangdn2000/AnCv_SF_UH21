package thang.com.wref.MainScreen.Orders;

import static thang.com.wref.util.Constants.BASE_URL;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import thang.com.wref.CameraPredictionScreen.Adapters.PesticideAdapter;
import thang.com.wref.Commerce.ItemProductApdater;
import thang.com.wref.Components.Animation.RecyclerViewAnimation;
import thang.com.wref.Components.Retrofits.OrderRetrofit;
import thang.com.wref.Components.Retrofits.ProductRetrofit;
import thang.com.wref.LoginScreen.SharedPreferencesManagement;
import thang.com.wref.MainScreen.Models.DataPaging;
import thang.com.wref.MainScreen.Models.PlantProtection;
import thang.com.wref.MainScreen.Models.ProductModel;
import thang.com.wref.MainScreen.Models.ResponseModel;
import thang.com.wref.R;
import thang.com.wref.util.NetworkUtil;

public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "ProductDetailActivity";
    private ImageView imgProduct;
    private TextView name, price, amount, ingredient, effect, userManual, note, txtLocation, txtDetail;
    private RecyclerView suggestedPesticidesView;
    private AppCompatButton btnSignUp, reduced, increase;
    private String idProduct = "", idPlant = "", idCategory = "";
    private RelativeLayout rltBack, rl_backdrop;
    private NetworkUtil networkUtil;
    private Retrofit retrofit;
    private ProductRetrofit productRetrofit;
    private OrderRetrofit orderRetrofit;
    private RoundedImageView qr_code, qr_code_big;
    private SharedPreferencesManagement sharedPreferencesManagement;
    private ArrayList<ProductModel> productModels = new ArrayList<>();
    private ItemProductApdater itemProductApdater;
    private String QRCodeImg = null;
    private RecyclerViewAnimation recyclerViewAnimation = new RecyclerViewAnimation();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idProduct = getIntent().getStringExtra("idProduct");
        idPlant = getIntent().getStringExtra("idPlant");

        if(idProduct != null) setContentView(R.layout.activity_product_detail);
        else setContentView(R.layout.activity_product_plant_protection);

        networkUtil = new NetworkUtil();
        retrofit = networkUtil.getRetrofit();
        sharedPreferencesManagement = new SharedPreferencesManagement(ProductDetailActivity.this);
        mapingView();

        if(idProduct != null) {
            mappingViewProduct();
            getProductById();
        }
        else {
            mappingViewPlant();
            getPlantProtectionById();
        }
    }

    private void setupSuggestedPesticidesRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        itemProductApdater = new ItemProductApdater(productModels, ProductDetailActivity.this);
        recyclerViewAnimation.setAnimationRecyclerviewHorizontal(suggestedPesticidesView);
        suggestedPesticidesView.setLayoutManager(layoutManager);
        suggestedPesticidesView.setAdapter(itemProductApdater);
    }

    private void addPesticide() {
        productRetrofit = retrofit.create(ProductRetrofit.class);
        Log.d(TAG, "addPesticide:aaaaa " + idCategory);
        Call<ResponseModel<DataPaging<List<ProductModel>>>> call = productRetrofit.getProductByCategory(sharedPreferencesManagement.getTOKEN(), idCategory);
        call.enqueue(new Callback<ResponseModel<DataPaging<List<ProductModel>>>>() {
            @Override
            public void onResponse(Call<ResponseModel<DataPaging<List<ProductModel>>>> call, Response<ResponseModel<DataPaging<List<ProductModel>>>> response) {
                ResponseModel<DataPaging<List<ProductModel>>> data = response.body();
                DataPaging<List<ProductModel>> dataPaging = data.getData();
                List<ProductModel> products = dataPaging.getData();
                for (ProductModel productModel : products) {
                    productModels.add(productModel);
                }
                itemProductApdater.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseModel<DataPaging<List<ProductModel>>>> call, Throwable t) {

            }
        });

    }

    private void mapingView(){
        imgProduct = (ImageView) findViewById(R.id.img_product);
        name = (TextView) findViewById(R.id.name);
        price = (TextView) findViewById(R.id.price);
        btnSignUp = (AppCompatButton) findViewById(R.id.btnSignUp);
        reduced = (AppCompatButton) findViewById(R.id.reduced);
        increase = (AppCompatButton) findViewById(R.id.increase);
        amount = (TextView) findViewById(R.id.amount);

        suggestedPesticidesView = (RecyclerView) findViewById(R.id.rv_suggested_pesticide);
        rltBack = (RelativeLayout) findViewById(R.id.rltBack);

        qr_code = (RoundedImageView) findViewById(R.id.qr_code);
        qr_code_big = (RoundedImageView) findViewById(R.id.qr_code_big);
        rl_backdrop = (RelativeLayout) findViewById(R.id.rl_backdrop);

        btnSignUp.setOnClickListener(this);
        reduced.setOnClickListener(this);
        increase.setOnClickListener(this);
        rltBack.setOnClickListener(this);
        qr_code.setOnClickListener(this);
        rl_backdrop.setOnClickListener(this);
     }

     private void mappingViewProduct(){
         ingredient = (TextView) findViewById(R.id.ingredient);
         effect = (TextView) findViewById(R.id.effect);
         userManual = (TextView) findViewById(R.id.userManual);
         note = (TextView) findViewById(R.id.note);
     }

     private void mappingViewPlant() {
         txtLocation = (TextView) findViewById(R.id.txtLocation);
         txtDetail = (TextView) findViewById(R.id.txtDetail);
     }
     private void getProductById() {
         productRetrofit = retrofit.create(ProductRetrofit.class);
         Call<ResponseModel<ProductModel>> call = productRetrofit.getProductById(sharedPreferencesManagement.getTOKEN(), idProduct);
         call.enqueue(new Callback<ResponseModel<ProductModel>>() {
             @Override
             public void onResponse(Call<ResponseModel<ProductModel>> call, Response<ResponseModel<ProductModel>> response) {
                 if (!response.isSuccessful()) {
                     showFinishDialog("Xin lỗi, không tìm thấy thông tin sản phẩm này.");
                 } else {
                     ResponseModel<ProductModel> responseModel = response.body();
                     if(responseModel.isSuccess()) {
                         ProductModel productModel = responseModel.getData();
                         String url = "";
                         String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
                         if(IsMatch(productModel.getMedia(), regex)) {
                             url = productModel.getMedia();
                         } else {
                             url = BASE_URL+"uploads/"+productModel.getMedia();
                         }
                         Glide.with(ProductDetailActivity.this)
                                 .load(url).into(imgProduct);
                         name.setText(productModel.getName());
                         Locale locale = new Locale("vi", "vn");
                         NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
                         price.setText(""+fmt.format(productModel.getPrice()));
                         ingredient.setText(productModel.getIngredient());
                         effect.setText(productModel.getEffect());
                         userManual.setText(productModel.getUserManual());
                         note.setText(productModel.getNote());
                         idCategory = productModel.getIdCategory().getId();
                         setupSuggestedPesticidesRecyclerView();
                         addPesticide();
                         Log.d(TAG, "onResponse: " + idCategory);

                         Glide.with(ProductDetailActivity.this).load(BASE_URL+"qrCode/61580efedbcbfe2ce8e93bfb.png").into(qr_code);
                         Glide.with(ProductDetailActivity.this).load(BASE_URL+"qrCode/61580efedbcbfe2ce8e93bfb.png").into(qr_code_big);
                     }
                 }
                 call.cancel();
             }

             @Override
             public void onFailure(Call<ResponseModel<ProductModel>> call, Throwable t) {
                 Log.d(TAG, "onFailure: "+ t.getMessage());
                 showFinishDialog("Không có mạng");
                call.cancel();
             }
         });
     }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignUp:
                if(amount.getText().equals("0")) Toast.makeText(ProductDetailActivity.this, "Số lượng mua phải lơn hơn 0", Toast.LENGTH_SHORT).show();
                else buyNow();
                break;
            case R.id.reduced:
                reducedAmount();
                break;
            case R.id.increase:
                increaseAmount();
                break;
            case R.id.rl_backdrop:
                closeBackdrop();
                break;
            case R.id.qr_code:
                showQRCode();
                break;
            case R.id.rltBack:
                finish();
                break;
            default:
                break;
        }
    }

    private void showQRCode() {
        rl_backdrop.setVisibility(View.VISIBLE);
        Log.d(TAG, "Show");
    }

    private void closeBackdrop() {
        rl_backdrop.setVisibility(View.GONE);
    }

    private void reducedAmount(){
        int a = Integer.parseInt(String.valueOf(amount.getText()));
        if(a == 0) return;
        a--;
        amount.setText(""+a);
    }

    private void increaseAmount(){
        int a = Integer.parseInt(String.valueOf(amount.getText()));
        a++;
        amount.setText(""+a);
    }

    private void buyNow() {
        orderRetrofit = retrofit.create(OrderRetrofit.class);
        Call<ResponseModel<OrderRetrofit>> call = orderRetrofit.postOrder(
                sharedPreferencesManagement.getTOKEN(), idProduct, sharedPreferencesManagement.getID(), Integer.parseInt(String.valueOf(amount.getText())), "Hòa Nhơn, Hòa Vang"
        );
        call.enqueue(new Callback<ResponseModel<OrderRetrofit>>() {
            @Override
            public void onResponse(Call<ResponseModel<OrderRetrofit>> call, Response<ResponseModel<OrderRetrofit>> response) {
                if (!response.isSuccessful()) {
                    showFinishDialog("Đã có lỗi bất ngờ xảy ra, vui lòng liên hệ với WREF để tìm cách giải quyết.");
                } else {
                    ResponseModel<OrderRetrofit> responseModel = response.body();
                    if (responseModel.isSuccess()) {
                        Toast.makeText(ProductDetailActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                    }
                }
                call.cancel();
            }

            @Override
            public void onFailure(Call<ResponseModel<OrderRetrofit>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                showFinishDialog("Không có mạng");
                call.cancel();
            }
        });
    }

    private boolean IsMatch (String s, String pattern){
        try {
            Pattern patt = Pattern.compile(pattern);
            Matcher matcher = patt.matcher(s);
            return matcher.matches();
        } catch (RuntimeException e) {
            return false;
        }
    }

    private void getPlantProtectionById() {
        Log.d(TAG, "getPlantProtectionById: " + idPlant);
        productRetrofit = retrofit.create(ProductRetrofit.class);
        Call<ResponseModel<PlantProtection>> call = productRetrofit.getPlantProtectionById(sharedPreferencesManagement.getTOKEN(), idPlant);
        call.enqueue(new Callback<ResponseModel<PlantProtection>>() {
            @Override
            public void onResponse(Call<ResponseModel<PlantProtection>> call, Response<ResponseModel<PlantProtection>> response) {
                if (!response.isSuccessful()) {
                    showFinishDialog("Xin lỗi, không tìm thấy thông tin trong mã QR này.");
                } else {
                    ResponseModel<PlantProtection> responseModel = response.body();
                    if (responseModel.isSuccess()) {
                        PlantProtection plantProtection = responseModel.getData();
                        Log.d(TAG, "onResponse: "+plantProtection);
                        name.setText(plantProtection.getName());
                        price.setText("150.000 VNĐ");
                        Glide.with(ProductDetailActivity.this).load(plantProtection.getImage()).into(imgProduct);
                        Glide.with(ProductDetailActivity.this).load(BASE_URL+"qrCode/"+plantProtection.getQrcode()).into(qr_code);
                        Glide.with(ProductDetailActivity.this).load(BASE_URL+"qrCode/"+plantProtection.getQrcode()).into(qr_code_big);
//                        txtLocation.setText(plantProtection.getShop().getAddress());
                        txtDetail.setText(plantProtection.getDetail());
                    }
                }
                call.cancel();
            }

            @Override
            public void onFailure(Call<ResponseModel<PlantProtection>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                showFinishDialog("Không có mạng");
                call.cancel();
            }
        });
    }

    private void showFinishDialog(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).show();
    }
}