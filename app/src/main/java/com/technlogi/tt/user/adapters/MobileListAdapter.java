package com.technlogi.tt.user.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.technlogi.tt.R;

import java.util.ArrayList;

public class MobileListAdapter extends RecyclerView.Adapter<MobileListAdapter.MobileViewHolder> {

    private ArrayList<String> models = new ArrayList<>();
    private OrderItemClicks clicks;

    public MobileListAdapter(ArrayList<String> models) {
        this.models = models;
    }

    public MobileListAdapter setClicks(OrderItemClicks clicks) {
        this.clicks = clicks;
        return this;
    }

    @NonNull
    @Override
    public MobileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mobile_list_view,parent,false);
        return new MobileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MobileViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvMobileNo.setText(models.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicks.onItemClick(null,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class MobileViewHolder extends RecyclerView.ViewHolder{

        TextView tvMobileNo;

        public MobileViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMobileNo =  itemView.findViewById(R.id.tvMobileNo);
        }
    }

    public interface OrderItemClicks {

        void onItemClick(Object obj, int position);
    }
}