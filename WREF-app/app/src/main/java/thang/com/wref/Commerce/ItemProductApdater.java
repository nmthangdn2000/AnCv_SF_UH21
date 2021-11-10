package thang.com.wref.Commerce;

import static thang.com.wref.util.Constants.BASE_URL;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

import thang.com.wref.MainScreen.Models.ProductModel;
import thang.com.wref.R;

public class ItemProductApdater extends RecyclerView.Adapter<ItemProductApdater.ViewHolder>{
    private ArrayList<ProductModel> list;
    private Context context;

    public ItemProductApdater(ArrayList<ProductModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemProductApdater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_product, parent,false);
        return new ItemProductApdater.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemProductApdater.ViewHolder holder, int position) {
        if(URLUtil.isValidUrl(list.get(position).getMedia())) {
            Glide.with(context).load(list.get(position).getMedia()).centerCrop().fitCenter().into(holder.img);
        } else {
            Glide.with(context).load(BASE_URL+"uploads/"+list.get(position).getMedia()).centerCrop().fitCenter().into(holder.img);
        }
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String price = formatter.format(list.get(position).getPrice());
        holder.txtPrice.setText(price + " ₫");
        holder.txtname.setText("" + list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView txtPrice, txtname;
        public SpringAnimation translationX;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
            txtname = (TextView) itemView.findViewById(R.id.txtname);
            translationX = new SpringAnimation(itemView, SpringAnimation.TRANSLATION_X).setSpring(
                    new SpringForce().setFinalPosition(0f)
                            .setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY)
                            .setStiffness(SpringForce.STIFFNESS_LOW)
            );
        }
    }
}
