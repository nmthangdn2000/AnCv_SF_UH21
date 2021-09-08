package thang.com.wref.MainScreen.Adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import thang.com.wref.R;

public class ImgFromGallyeAdapter extends RecyclerView.Adapter<ImgFromGallyeAdapter.ViewHodler>{
    private static final String TAG = "ImgFromGallyeAdapter";
    private List<Uri> arrPath;
    private Context context;

    public ImgFromGallyeAdapter(List<Uri> arrPath, Context context) {
        this.arrPath = arrPath;
        this.context = context;
    }

    @NonNull
    @Override
    public ImgFromGallyeAdapter.ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_img_from_galley, parent,false);
        return new ImgFromGallyeAdapter.ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImgFromGallyeAdapter.ViewHodler holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + arrPath.get(position));
        // set image to view
        Glide.with(context).load(arrPath.get(position))
                .centerCrop().fitCenter().into(holder.imgGalley);
    }

    @Override
    public int getItemCount() {
        return arrPath.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder{
        private RoundedImageView imgGalley;
        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            imgGalley = (RoundedImageView) itemView.findViewById(R.id.imgGalley);
        }
    }
}
