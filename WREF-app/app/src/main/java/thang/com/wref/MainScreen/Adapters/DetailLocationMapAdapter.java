package thang.com.wref.MainScreen.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import thang.com.wref.R;

import static thang.com.wref.util.Constants.BASE_URL;

public class DetailLocationMapAdapter extends RecyclerView.Adapter<DetailLocationMapAdapter.ViewHodler>{
    private static final String TAG = "DetailLocationMapAdapter";

    private Context context;
    private ArrayList<String> imgURl;

    public DetailLocationMapAdapter(Context context, ArrayList<String> imgURl) {
        this.context = context;
        this.imgURl = imgURl;
    }

    @NonNull
    @Override
    public DetailLocationMapAdapter.ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_detail_location_map, parent,false);
        return new DetailLocationMapAdapter.ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailLocationMapAdapter.ViewHodler holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + imgURl.get(position));
        Glide.with(context).load(BASE_URL+"uploads/"+imgURl.get(position)).centerCrop().fitCenter().into(holder.img);
    }
    public int getImage(String imageName) {

        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        return drawableResourceId;
    }
    @Override
    public int getItemCount() {
        return imgURl.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        private RoundedImageView img;
        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            img = (RoundedImageView) itemView.findViewById(R.id.img);
        }
    }
}
