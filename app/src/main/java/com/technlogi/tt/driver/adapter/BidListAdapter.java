package com.technlogi.tt.driver.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.technlogi.tt.R;
import com.technlogi.tt.user.adapters.VehicleGoodsAdapter;
import com.technlogi.tt.user.models.VehicleModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BidListAdapter extends RecyclerView.Adapter<BidListAdapter.BidListViewHolder> {

    private List<VehicleModel> vehicleModels = new ArrayList<>();
    private boolean isBidding;

    public BidListAdapter(List<VehicleModel> vehicleModels,boolean isBidding) {
        this.vehicleModels = vehicleModels;
        this.isBidding = isBidding;
    }

    @NonNull
    @Override
    public BidListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_side_bidding_list_cardview_layout,parent,false);
        return new BidListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BidListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return vehicleModels.size();
    }

    class BidListViewHolder extends RecyclerView.ViewHolder {

        public BidListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
