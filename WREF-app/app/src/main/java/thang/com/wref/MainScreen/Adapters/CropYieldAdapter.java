package thang.com.wref.MainScreen.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import thang.com.wref.MainScreen.Havest.ProductionStatisticsActivity;
import thang.com.wref.MainScreen.Models.CropYieldModel;
import thang.com.wref.R;

public class CropYieldAdapter extends RecyclerView.Adapter<CropYieldAdapter.ViewHodler> {
    private static final String TAG = "CropYieldAdapter";

    private ArrayList<CropYieldModel> arr;
    private Context context;

    public CropYieldAdapter(ArrayList<CropYieldModel> arr, Context context) {
        this.arr = arr;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_process, parent,false);
        return new CropYieldAdapter.ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        holder.txtTitle.setText(arr.get(position).getTitle());
        holder.txtMore1.setText(arr.get(position).getMore1());
        holder.txtMore2.setText(arr.get(position).getMore2());
        holder.imgIconProcess.setImageResource(arr.get(position).getIcon());
        holder.imgIconProcess1.setBackgroundResource(arr.get(position).getIcon2());
        holder.lnlItemProcess.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(arr.get(position).getBackground())));
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txtTitle, txtMore1, txtMore2;
        private ImageView imgIconProcess;
        private LinearLayout imgIconProcess1, lnlItemProcess;
        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtMore1 = (TextView) itemView.findViewById(R.id.txtMore1);
            txtMore2 = (TextView) itemView.findViewById(R.id.txtMore2);
            imgIconProcess = (ImageView) itemView.findViewById(R.id.imgIconProcess);
            imgIconProcess1 = (LinearLayout) itemView.findViewById(R.id.imgIconProcess1);
            lnlItemProcess = (LinearLayout) itemView.findViewById(R.id.lnlItemProcess);

            lnlItemProcess.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.lnlItemProcess:
                    intentPage(getAdapterPosition());
                    break;
                default:
                    break;
            }
        }
    }

    private void intentPage(int position) {
        switch (position){
            case 4:
                Intent intent = new Intent(context, ProductionStatisticsActivity.class);
                context.startActivity(intent);
                break;
            default:
                break;
        }
    }
}
