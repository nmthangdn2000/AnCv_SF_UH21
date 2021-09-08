package thang.com.wref.MainScreen.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import thang.com.wref.MainScreen.Models.CropsModel;
import thang.com.wref.R;

public class HarvesthelperAdapter extends RecyclerView.Adapter<HarvesthelperAdapter.ViewHodler>{
    private ArrayList<CropsModel> arrayList;
    private Context context;

    public HarvesthelperAdapter(ArrayList<CropsModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_harvesthelper, parent,false);
        return new HarvesthelperAdapter.ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        holder.titleCrops.setText(arrayList.get(position).getName());
        holder.desCrops.setText(arrayList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder{
        private TextView titleCrops, desCrops;
        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            titleCrops = (TextView) itemView.findViewById(R.id.titleCrops);
            desCrops = (TextView) itemView.findViewById(R.id.desCrops);
        }
    }
}
