package thang.com.wref.Commerce;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import thang.com.wref.R;

public class QuickLinkAdapter extends RecyclerView.Adapter<QuickLinkAdapter.ViewHolder> {

    private ArrayList<String> strings;

    public QuickLinkAdapter(ArrayList<String> strings) {
        this.strings = strings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_quicklink, parent,false);
        return new QuickLinkAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtQuicklink.setText(""+strings.get(position));
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtQuicklink;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtQuicklink = (TextView) itemView.findViewById(R.id.txtQuicklink);
        }
    }
}
