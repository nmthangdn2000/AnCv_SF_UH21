package thang.com.wref.MainScreen.Adapters;

import android.content.Context;
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

import thang.com.wref.MainScreen.Weather.TimeCaculater;
import thang.com.wref.MainScreen.Models.InforAgriModel;
import thang.com.wref.R;

import static thang.com.wref.util.Constants.BASE_URL;

public class ItemThemeAgriAdapter extends RecyclerView.Adapter<ItemThemeAgriAdapter.ViewHodler>{
    private ArrayList<InforAgriModel> inforAgriModels;
    private Context context;
    private TimeCaculater timeCaculater = new TimeCaculater();

    public ItemThemeAgriAdapter(ArrayList<InforAgriModel> inforAgriModels, Context context) {
        this.inforAgriModels = inforAgriModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemThemeAgriAdapter.ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_in_theme_agri, parent,false);
        return new ItemThemeAgriAdapter.ViewHodler(view);
    }
    // set data to view (image, title, time)
    @Override
    public void onBindViewHolder(@NonNull ItemThemeAgriAdapter.ViewHodler holder, int position) {
        Glide.with(context).load(BASE_URL+"uploads/"+inforAgriModels.get(position).getImage()).centerCrop().fitCenter().into(holder.imgAgri);
        holder.txtTitleAgri.setText(inforAgriModels.get(position).getTitle());
        holder.txtTime.setText(timeCaculater.day(inforAgriModels.get(position).getCreated_at()));
    }

    @Override
    public int getItemCount() {
        return inforAgriModels.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RoundedImageView imgAgri;
        private TextView txtTitleAgri, txtTime;
        public float currentVelocity = 0f;
        public SpringAnimation rotation;
        public SpringAnimation translationY;
        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            imgAgri = (RoundedImageView) itemView.findViewById(R.id.imgAgri);
            txtTitleAgri = (TextView) itemView.findViewById(R.id.txtTitleAgri);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            // bouncing animation
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
