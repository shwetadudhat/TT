package com.technlogi.tt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SupportMassageAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<SupportMassageModel> list = new ArrayList<>();

    final int ITEM_SENT = 1;
    final int ITEM_RECIEVED = 2;

    public SupportMassageAdapter (Context context,ArrayList<SupportMassageModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_SENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.support_msg_send_layout,parent,false);
            return new SendViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.support_msg_recieve_layout,parent,false);
            return new ReceivedViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getClass() == SendViewHolder.class) {

            ((SendViewHolder) holder).sendMsg.setText(list.get(position).getMassage());
            ((SendViewHolder) holder).sendTime.setText(list.get(position).getTime());

        } else if (holder.getClass() == ReceivedViewHolder.class) {
            ((ReceivedViewHolder) holder).receivedMsg.setText(list.get(position).getMassage());
            ((ReceivedViewHolder) holder).receivedTime.setText(list.get(position).getTime());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getType() == 0){
            return ITEM_SENT;
        } else {
            return ITEM_RECIEVED;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SendViewHolder extends RecyclerView.ViewHolder {

        private TextView sendMsg;
        private TextView sendTime;

        public SendViewHolder(@NonNull View itemView) {
            super(itemView);

            sendMsg = itemView.findViewById(R.id.support_tv_msg_send);
            sendTime = itemView.findViewById(R.id.support_tv_msg_send_time);

        }
    }

    class ReceivedViewHolder extends RecyclerView.ViewHolder {

        private TextView receivedMsg;
        private TextView receivedTime;

        public ReceivedViewHolder(@NonNull View itemView) {
            super(itemView);

            receivedMsg = itemView.findViewById(R.id.support_tv_msg_receive);
            receivedTime = itemView.findViewById(R.id.support_tv_msg_receive_time);
        }
    }
}
