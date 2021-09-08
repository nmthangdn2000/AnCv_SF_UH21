package thang.com.wref.MainScreen.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import thang.com.wref.MainScreen.Models.LocationModel;
import thang.com.wref.R;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHodler>{
    private ArrayList<LocationModel> arrLocation;
    private Context context;
    private clickItemLocation mOnClick;

    public LocationAdapter(ArrayList<LocationModel> arrLocation, Context context, clickItemLocation mOnClick) {
        this.arrLocation = arrLocation;
        this.context = context;
        this.mOnClick = mOnClick;
    }

    @NonNull
    @Override
    public LocationAdapter.ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_location, parent,false);
        return new LocationAdapter.ViewHodler(view);
    }
    // set data to view
    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHodler holder, int position) {
        holder.txtNameLocation.setText(arrLocation.get(position).getName());
        holder.txtLat_Long.setText(arrLocation.get(position).getLatiude()+", "+arrLocation.get(position).getLongitude());
    }

    @Override
    public int getItemCount() {
        return arrLocation.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder implements View.OnClickListener{
        private RelativeLayout itemLocation;
        private TextView txtNameLocation, txtLat_Long;
        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            txtNameLocation = (TextView) itemView.findViewById(R.id.txtNameLocation);
            txtLat_Long = (TextView) itemView.findViewById(R.id.txtLat_Long);
            itemLocation = (RelativeLayout) itemView.findViewById(R.id.itemLocation);

            itemLocation.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.itemLocation:
                    mOnClick.onclick(getAdapterPosition());
                    break;
                default:
                    break;
            }
        }
    }
    public interface clickItemLocation{
        void onclick(int position);
    }
}
