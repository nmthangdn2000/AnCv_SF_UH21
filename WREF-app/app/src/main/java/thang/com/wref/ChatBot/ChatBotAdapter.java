package thang.com.wref.ChatBot;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

import thang.com.wref.MainScreen.Adapters.AgriAdapter;
import thang.com.wref.R;

public class ChatBotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<MessageModel> messageModels;
    private static final int VIEW_MY_SENT = 1;
    private static final int VIEW_BOT_SENT = 2;

    public ChatBotAdapter(ArrayList<MessageModel> messageModels) {
        this.messageModels = messageModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_MY_SENT) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.item_my_message, parent,false);
            return new ChatBotAdapter.MyViewHolder(view);
        }
        else {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.item_bot_message, parent,false);
            return new ChatBotAdapter.BotViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (messageModels.get(position).getViewType() == VIEW_MY_SENT) {
            ((MyViewHolder) holder).txtMyMessage.setText(""+messageModels.get(position).getMessage());
        }
        else {
            String mess = "";
            if(
                    messageModels.get(position).getOldIntent() != null
                    && messageModels.get(position).getOldIntent().equals("weather")
                    && messageModels.get(position).getData() != null
            ){
                Gson gson = new Gson();
                WeatherModel weatherModel = gson.fromJson(messageModels.get(position).getData(), WeatherModel.class);
                mess = messageModels.get(position).getMessage() + ":\n" + weatherModel.toString();

            } else {
                mess = messageModels.get(position).getMessage();
            }
            ((BotViewHolder) holder).txtBotMessage.setText(mess);
        }
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messageModels.get(position).getViewType() == VIEW_MY_SENT) return VIEW_MY_SENT;
        return VIEW_BOT_SENT;
    }

    public class BotViewHolder extends RecyclerView.ViewHolder{
        private TextView txtBotMessage;
        public BotViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBotMessage = (TextView) itemView.findViewById(R.id.txt_bot_message);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView txtMyMessage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMyMessage = (TextView) itemView.findViewById(R.id.txt_my_message);
        }
    }
}
