package thang.com.wref.MainScreen.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import thang.com.wref.MainScreen.Models.PPModel;
import thang.com.wref.R;

public class PPAdapter extends RecyclerView.Adapter<PPAdapter.ViewHolder> {
    private ArrayList<PPModel> arrayList;
    private Context context;

    public PPAdapter(ArrayList<PPModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_productivity_prediction, parent,false);
        return new PPAdapter.ViewHolder(view);
    }
    // set data to view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GradientDrawable gradientDrawable = (GradientDrawable) holder.lnlPP.getBackground();
        gradientDrawable.setStroke(5, Color.parseColor(arrayList.get(position).getBorderColor()));

        holder.txtAboutTime.setText(arrayList.get(position).getTime());
        holder.txtAmount.setText(arrayList.get(position).getAmount());
        holder.txtAboutTime.setTextColor(Color.parseColor(arrayList.get(position).getBorderColor()));
        holder.txtAmount.setTextColor(Color.parseColor(arrayList.get(position).getBorderColor()));

        holder.txtRate.setText(arrayList.get(position).getRate());
        holder.txtRate.setTextColor(Color.parseColor(arrayList.get(position).getBorderColor()));
        Drawable img = context.getResources().getDrawable(arrayList.get(position).getIcon());
        holder.txtRate.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        holder.txtRate.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor(arrayList.get(position).getBorderColor())));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout lnlPP;
        private TextView txtAboutTime, txtAmount, txtRate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lnlPP = (LinearLayout) itemView.findViewById(R.id.lnlPP);
            txtAboutTime = (TextView) itemView.findViewById(R.id.txtAboutTime);
            txtAmount = (TextView) itemView.findViewById(R.id.txtAmount);
            txtRate = (TextView) itemView.findViewById(R.id.txtRate);
        }
    }
}
