package com.technlogi.tt.user.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.technlogi.tt.R;
import com.technlogi.tt.user.activities.BookingDetailsActivity;
import com.technlogi.tt.user.models.BookingDetailsModel;

import java.util.ArrayList;

public class BookingDetailsAdapter extends RecyclerView.Adapter<BookingDetailsAdapter.BookingDetailsViewHolder> {

    private ArrayList<BookingDetailsModel> detailsModels =  new ArrayList<>();

    public BookingDetailsAdapter(ArrayList<BookingDetailsModel> detailsModels) {
        this.detailsModels = detailsModels;
    }

    @NonNull
    @Override
    public BookingDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_ride_details,parent,false);
        return new BookingDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingDetailsViewHolder holder, int position) {
        holder.tvPrevDate.setText(detailsModels.get(position).getStrDateTime());
        holder.tvPrevVehicleNo.setText(detailsModels.get(position).getDriverDetails().getStrVehicleNo());
        holder.tvPrevPickup.setText(detailsModels.get(position).getPickup().getStrLocationName());
        holder.tvPrevDestination.setText(detailsModels.get(position).getDestination().getStrLocationName());
        holder.tvPrevCost.setText(detailsModels.get(position).getStrCost());
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

    class BookingDetailsViewHolder extends RecyclerView.ViewHolder{
        private MaterialTextView tvPrevDate;
        private MaterialTextView tvPrevVehicleNo;
        private MaterialTextView tvPrevPickup;
        private MaterialTextView tvPrevDestination;
        private MaterialTextView tvPrevCost;

        public BookingDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPrevDate =  itemView.findViewById(R.id.tvPrevDate);
            tvPrevDate =  itemView.findViewById(R.id.tvPrevDate);
            tvPrevVehicleNo =  itemView.findViewById(R.id.tvPrevVehicleNo);
            tvPrevPickup =  itemView.findViewById(R.id.tvPrevPickup);
            tvPrevDestination =  itemView.findViewById(R.id.tvPrevDestination);
            tvPrevCost =  itemView.findViewById(R.id.tvPrevCost);
        }
    }
}
