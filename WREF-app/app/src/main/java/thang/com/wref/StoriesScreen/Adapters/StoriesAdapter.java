package thang.com.wref.StoriesScreen.Adapters;

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

import de.hdodenhof.circleimageview.CircleImageView;
import thang.com.wref.LoginScreen.SharedPreferencesManagement;
//import thang.com.wref.Stories.ViewpagerStoriesActivity;
import thang.com.wref.StoriesScreen.Models.StoriesModels;
import thang.com.wref.R;

import static thang.com.wref.util.Constants.BASE_URL;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.ViewHolder>{
    private ArrayList<StoriesModels> arrayList;
    private Context context;
    private SharedPreferencesManagement sharedPreferencesManagement;
    private onCLickStories mListener;

    public StoriesAdapter(ArrayList<StoriesModels> arrayList, Context context, onCLickStories mListener) {
        this.arrayList = arrayList;
        this.context = context;
        this.mListener= mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_stories, parent,false);
        return new StoriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        sharedPreferencesManagement = new SharedPreferencesManagement(context);
        // check id user login for stories
        if(arrayList.get(position).getUsers().getId().equals(sharedPreferencesManagement.getID())){
            holder.txtUserName.setText("Tin của bạn");
        }else{
            holder.txtUserName.setText(arrayList.get(position).getUsers().getUsername());
        }
        Glide.with(context).load(BASE_URL+"uploads/"+arrayList.get(position).getMedia()[0]).fitCenter().centerCrop().into(holder.imgStories);
    }
    public int getImage(String imageName) {

        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        return drawableResourceId;
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private CircleImageView imgUserStories;
        private RoundedImageView imgStories;
        private TextView txtUserName;
        public SpringAnimation translationX;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUserStories = (CircleImageView) itemView.findViewById(R.id.imgUserStories);
            txtUserName = (TextView) itemView.findViewById(R.id.txtUserName);
            imgStories = (RoundedImageView) itemView.findViewById(R.id.imgStories);
            // bouncing animation
            translationX = new SpringAnimation(itemView, SpringAnimation.TRANSLATION_X).setSpring(
                    new SpringForce().setFinalPosition(0f)
                            .setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY)
                            .setStiffness(SpringForce.STIFFNESS_LOW)
            );
            imgStories.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imgStories:
                    mListener.onClick(getAdapterPosition());
                    break;
                default:
                    break;
            }
        }
    }
    public interface onCLickStories{
        void onClick(int position);
    }
}
