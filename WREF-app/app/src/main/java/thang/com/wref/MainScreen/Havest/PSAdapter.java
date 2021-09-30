package thang.com.wref.MainScreen.Havest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

import thang.com.wref.R;

public class PSAdapter extends PagerAdapter {

    private List<PhotoModel> photoModelList;

    public PSAdapter(List<PhotoModel> photoModelList) {
        this.photoModelList = photoModelList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo_statistics, container, false);
        ImageView imageView = view.findViewById(R.id.img);
        PhotoModel photoModel = photoModelList.get(position);
        imageView.setImageResource(photoModel.getImg());
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if (photoModelList != null) return photoModelList.size();
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
