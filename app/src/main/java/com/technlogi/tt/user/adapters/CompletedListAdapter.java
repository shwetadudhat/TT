package com.technlogi.tt.user.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textview.MaterialTextView;
import com.technlogi.tt.R;
import com.technlogi.tt.user.activities.BookingDetailsActivity;
import com.technlogi.tt.user.models.BookingDetailsModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CompletedListAdapter extends RecyclerView.Adapter<CompletedListAdapter.CompletedListViewHolder> {

    private ArrayList<BookingDetailsModel> detailsModels =  new ArrayList<>();

    public CompletedListAdapter(ArrayList<BookingDetailsModel> detailsModels) {
        this.detailsModels = detailsModels;
    }

    @NonNull
    @Override
    public CompletedListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_ride_details,parent,false);
        return new CompletedListAdapter.CompletedListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedListViewHolder holder, int position) {

        holder.dateTime.setText(detailsModels.get(position).getStrDateTime());
        holder.vehicleNo.setText(detailsModels.get(position).getDriverDetails().getStrVehicleNo());
        holder.pickup.setText(detailsModels.get(position).getPickup().getStrLocationName());
        holder.destination.setText(detailsModels.get(position).getDestination().getStrLocationName());
        holder.cost.setText(detailsModels.get(position).getStrCost());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), BookingDetailsActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return detailsModels.size();
    }

    class CompletedListViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView dateTime,vehicleNo,pickup,destination,cost;
        private Button accept,reject;
        private ImageView driverPhoto,cancelStamp;

        public CompletedListViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTime = itemView.findViewById(R.id.tvPrevDate);
            vehicleNo = itemView.findViewById(R.id.tvPrevVehicleNo);
            cost = itemView.findViewById(R.id.tvPrevCost);
            pickup = itemView.findViewById(R.id.tvPrevPickup);
            destination = itemView.findViewById(R.id.tvPrevDestination);
            accept = itemView.findViewById(R.id.acceptBtn);
            reject = itemView.findViewById(R.id.rejectBtn);
            driverPhoto = itemView.findViewById(R.id.iv_rideDetails_DriverPhoto);
            cancelStamp = itemView.findViewById(R.id.iv_rideDetails_cancelStamp);

            accept.setVisibility(View.GONE);
            reject.setVisibility(View.GONE);
            cancelStamp.setVisibility(View.GONE);
        }
    }
}
