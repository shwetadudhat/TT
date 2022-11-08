package com.technlogi.tt.user.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.technlogi.tt.BookingPendingDetails;
import com.technlogi.tt.R;
import com.technlogi.tt.user.activities.RideBidList;
import com.technlogi.tt.user.models.BookingDetailsModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.technlogi.tt.Constant.PASSANGER_PANEL;

public class BiddingListAdapter extends RecyclerView.Adapter<BiddingListAdapter.BiddingListViewHolder> {

    private ArrayList<BookingDetailsModel> detailsModels =  new ArrayList<>();
    private Context context;

    public BiddingListAdapter(Context context,ArrayList<BookingDetailsModel> detailsModels) {
        this.context = context;
        this.detailsModels = detailsModels;
    }

    @NonNull
    @Override
    public BiddingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_ride_details,parent,false);
        return new BiddingListAdapter.BiddingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BiddingListViewHolder holder, int position) {

        holder.dateTime.setText(detailsModels.get(position).getStrDateTime());
        holder.vehicleNo.setText(detailsModels.get(position).getDriverDetails().getStrVehicleNo());
        holder.pickup.setText(detailsModels.get(position).getPickup().getStrLocationName());
        holder.destination.setText(detailsModels.get(position).getDestination().getStrLocationName());
        holder.cost.setText(detailsModels.get(position).getStrCost());
        holder.driverName.setText("");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, RideBidList.class);
                intent1.putExtra("from",PASSANGER_PANEL);
                context.startActivity(intent1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return detailsModels.size();
    }

    class BiddingListViewHolder extends RecyclerView.ViewHolder{

        private MaterialTextView dateTime,vehicleNo,pickup,destination,cost,driverName;
        private Button accept,reject;
        private ImageView driverPhoto,cancelStamp;

        public BiddingListViewHolder(@NonNull View itemView) {
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
            driverName = itemView.findViewById(R.id.tvPrevDriverName);

            accept.setVisibility(View.GONE);
            reject.setVisibility(View.VISIBLE);
            reject.setText("Cancel");
            reject.setBackgroundTintList(context.getResources().getColorStateList(R.color.red));
            cancelStamp.setVisibility(View.GONE);

            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BookingCancelReasonDialogInitialize();
                }
            });

        }

        private void BookingCancelReasonDialogInitialize() {
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_booking_cancel_reason);
            dialog.getWindow().setBackgroundDrawableResource(R.color.fully_transparent);

            dialog.findViewById(R.id.dialog_bookingCancelReasonCutBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.findViewById(R.id.dialog_bookingCancelReason_dontCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.findViewById(R.id.dialog_bookingCancelReason_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }
}
