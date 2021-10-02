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
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import thang.com.wref.CameraPredictionScreen.Adapters.PesticideAdapter;
import thang.com.wref.Components.Retrofits.OrderRetrofit;
import thang.com.wref.Components.Retrofits.ProductRetrofit;
import thang.com.wref.LoginScreen.SharedPreferencesManagement;
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
    private String idProduct = "", idPlant = "";
    private RelativeLayout rltBack;
    private NetworkUtil networkUtil;
    private Retrofit retrofit;
    private ProductRetrofit productRetrofit;
    private OrderRetrofit orderRetrofit;
    private RoundedImageView qr_code;
    private SharedPreferencesManagement sharedPreferencesManagement;
    private PesticideAdapter suggestedPesticidesAdapter;
    private ArrayList<HashMap<String, String>> suggestedPesticides= new ArrayList<>();
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
            mappingVieProduct();
            getProductById();
        }
        else {
            mappingViewPlant();
            getPlantProtectionById();
        }

        setupSuggestedPesticidesRecyclerView();
        addPesticide("614a02ad57b54f3358b5b160", "Daconil 75WP", "Rất tốt", "50.000 VNĐ", "15g", "https://www.phandoiso1.com/wp-content/uploads/2020/12/Daconil-75WP-15g.jpg");
        addPesticide("614a02ad57b54f3358b5b161","Overamis 300SC", "Hiệu quả cao", "80.000 VNĐ", "100ml", "https://vietnamnongnghiepsach.com.vn/wp-content/uploads/2017/08/thu%E1%BB%91c-overamis-300sc.jpg");
        addPesticide("614a02ad57b54f3358b5b162","Revus Opti 440SC", "Hiệu quả cao", "285.000 VNĐ", "100ml", "https://product.hstatic.net/1000220686/product/rovus_opti_100ml_fix_master.jpg");
        addPesticide("614a02ad57b54f3358b5b163", "AMISTAR 250SC", "Hiệu quả cao", "185.000 VNĐ", "100ml", "https://product.hstatic.net/1000220686/product/amistar-250sc_daa8583d2a524151a57f64fa57c819fb_master.jpg");
    }

    private void setupSuggestedPesticidesRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        suggestedPesticidesAdapter = new PesticideAdapter(suggestedPesticides, this);

        suggestedPesticidesView.setLayoutManager(layoutManager);
        suggestedPesticidesView.setAdapter(suggestedPesticidesAdapter);
    }

    private void addPesticide(String id, String name, String quality, String price, String weight, String image) {
        HashMap<String, String> pesticide = new HashMap<>();

        pesticide.put("id", id);
        pesticide.put("name", name);
        pesticide.put("quality", quality);
        pesticide.put("price", price);
        pesticide.put("weight", weight);
        pesticide.put("image", image);

        suggestedPesticides.add(pesticide);
        suggestedPesticidesAdapter.notifyDataSetChanged();
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

        btnSignUp.setOnClickListener(this);
        reduced.setOnClickListener(this);
        increase.setOnClickListener(this);
        rltBack.setOnClickListener(this);
     }

     private void mappingVieProduct(){
         ingredient = (TextView) findViewById(R.id.ingredient);
         effect = (TextView) findViewById(R.id.effect);
         userManual = (TextView) findViewById(R.id.userManual);
         note = (TextView) findViewById(R.id.note);
     }

     private void mappingViewPlant() {
         txtLocation = (TextView) findViewById(R.id.txtLocation);
         txtDetail = (TextView) findViewById(R.id.txtDetail);
         qr_code = (RoundedImageView) findViewById(R.id.qr_code);
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
            case R.id.rltBack:
                finish();
                break;
            default:
                break;
        }
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