package thang.com.wref.Commerce;

import static thang.com.wref.util.Constants.BASE_URL;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;
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
        //BASE_URL+"uploads/"+
        Glide.with(context).load(list.get(position).getMedia()).centerCrop().fitCenter().into(holder.img);
        holder.txtPrice.setText("" + list.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private CardView rootBlurView;
        private BlurView blurView;
        private TextView txtPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            rootBlurView = (CardView) itemView.findViewById(R.id.rootBlurView);
//            blurView = (BlurView) itemView.findViewById(R.id.blurView);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
        }
    }
}
