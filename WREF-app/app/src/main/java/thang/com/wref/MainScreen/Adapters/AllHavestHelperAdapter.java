package thang.com.wref.MainScreen.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import thang.com.wref.MainScreen.Models.HarvesthelperModel;
import thang.com.wref.R;

public class AllHavestHelperAdapter extends RecyclerView.Adapter<AllHavestHelperAdapter.ViewHodler>{
    private final static String TAG = "AllHavestHelperAdapter";
    private ArrayList<HarvesthelperModel> arrayList;
    private Context context;

    public AllHavestHelperAdapter(ArrayList<HarvesthelperModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_in_theme_agri, parent,false);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        Glide.with(context).load(
                "https://res-5.cloudinary.com/do6bw42am/image/upload/c_scale,f_auto,h_300/v1/"
                        +arrayList.get(position).getImageUrl()
                ).centerCrop().fitCenter().into(holder.imgAgri);
        holder.txtTitleAgri.setText(arrayList.get(position).getName());
        holder.txtDesPlant.setText(arrayList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + arrayList.size());
        return arrayList.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RoundedImageView imgAgri;
        private TextView txtTitleAgri, txtDesPlant;
        public float currentVelocity = 0f;
        public SpringAnimation rotation;
        public SpringAnimation translationY;
        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            imgAgri = (RoundedImageView) itemView.findViewById(R.id.imgAgri);
            txtTitleAgri = (TextView) itemView.findViewById(R.id.txtTitleAgri);
            txtDesPlant = (TextView) itemView.findViewById(R.id.txtDesPlant);
            // bouncing animtion
            translationY = new SpringAnimation(itemView, SpringAnimation.TRANSLATION_Y).setSpring(
                    new SpringForce().setFinalPosition(0f)
                            .setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY)
                            .setStiffness(SpringForce.STIFFNESS_LOW)
            );
        }

        @Override
        public void onClick(View v) {

        }
    }
}
