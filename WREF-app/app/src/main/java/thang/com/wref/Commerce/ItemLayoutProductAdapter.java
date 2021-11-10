package thang.com.wref.Commerce;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import thang.com.wref.Components.Animation.RecyclerViewAnimation;
import thang.com.wref.R;

public class ItemLayoutProductAdapter extends RecyclerView.Adapter<ItemLayoutProductAdapter.ViewHolder>{
    private final static String TAG = "ItemLayoutProductAdapter";
    private ArrayList<ItemLayoutModel> list;
    private Context context;
    private ItemProductApdater itemProductApdater;
    private RecyclerViewAnimation recyclerViewAnimation = new RecyclerViewAnimation();

    public ItemLayoutProductAdapter(ArrayList<ItemLayoutModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemLayoutProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_layout_category_product, parent,false);
        return new ItemLayoutProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemLayoutProductAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + list.get(position).getTitle());
        Log.d(TAG, "onBindViewHolder: " + list);
        holder.txtTitle.setText("" + list.get(position).getTitle());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.rcvItemProduct.setLayoutManager(linearLayoutManager);
        holder.rcvItemProduct.setNestedScrollingEnabled(false);
        recyclerViewAnimation.setAnimationRecyclerviewHorizontal(holder.rcvItemProduct);
        itemProductApdater = new ItemProductApdater(list.get(position).getProductModel(), context);
        holder.rcvItemProduct.setAdapter(itemProductApdater);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTitle;
        private RecyclerView rcvItemProduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            rcvItemProduct = (RecyclerView) itemView.findViewById(R.id.rcvItemProduct);
        }
    }
}
