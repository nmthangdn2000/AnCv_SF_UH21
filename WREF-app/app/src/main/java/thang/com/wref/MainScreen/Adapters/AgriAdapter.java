package thang.com.wref.MainScreen.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import thang.com.wref.MainScreen.Models.AgriModel;
import thang.com.wref.R;

public class AgriAdapter extends RecyclerView.Adapter<AgriAdapter.ViewHodler>{
    private static final String TAG = "AgriAdapter";
    private ArrayList<AgriModel> agriModels;
    private Context context;
    private onClickItemAgri mListenner;

    public AgriAdapter(ArrayList<AgriModel> agriModels, Context context, onClickItemAgri mListenner) {
        this.agriModels = agriModels;
        this.context = context;
        this.mListenner = mListenner;
    }

    @NonNull
    @Override
    public AgriAdapter.ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_agri, parent,false);
        return new AgriAdapter.ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgriAdapter.ViewHodler holder, int position) {
//        Glide.with(context).load(agriModels.get(position).getImage()).fitCenter().centerCrop().into(holder.imgAgri);
        holder.txtTitleAgri.setText(agriModels.get(position).getTitle());
        Log.d(TAG, "onBindViewHolder: "+agriModels.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return agriModels.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imgAgri;
        private TextView txtTitleAgri;
        private LinearLayout lnlItemAgri;

        public SpringAnimation translationY;
        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            imgAgri = (ImageView) itemView.findViewById(R.id.imgAgri);
            txtTitleAgri = (TextView) itemView.findViewById(R.id.txtTitleAgri);
            lnlItemAgri = (LinearLayout) itemView.findViewById(R.id.lnlItemAgri);
            // set bouncing animtion
            translationY = new SpringAnimation(itemView, SpringAnimation.TRANSLATION_Y).setSpring(
                    new SpringForce().setFinalPosition(0f)
                            .setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY)
                            .setStiffness(SpringForce.STIFFNESS_LOW)
            );

            lnlItemAgri.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.lnlItemAgri:
                    mListenner.onClick(agriModels.get(getAdapterPosition()).getId(), agriModels.get(getAdapterPosition()).getTitle());
                    break;
                default:
                    break;
            }
        }
    }
    public interface onClickItemAgri{
        void onClick(String id, String title);
    }
}
