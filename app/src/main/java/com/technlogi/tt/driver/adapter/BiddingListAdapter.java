package com.technlogi.tt.driver.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.technlogi.tt.BookingPendingDetails;
import com.technlogi.tt.R;
import com.technlogi.tt.user.activities.BookingDetailsActivity;
import com.technlogi.tt.user.activities.RideBidList;
import com.technlogi.tt.user.models.BookingDetailsModel;
import com.technlogi.tt.user.models.DriversDetailsModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.technlogi.tt.Constant.DRIVER_PANEL;

public class BiddingListAdapter extends RecyclerView.Adapter<BiddingListAdapter.BiddingListViewHolder> {

    private Context context;
    private ArrayList<BookingDetailsModel> detailsModels =  new ArrayList<>();

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

        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_bid_form);
        dialog.getWindow().setBackgroundDrawableResource(R.color.fully_transparent);
        ImageView cutBtn = (ImageView) dialog.findViewById(R.id.bidCutBtn);
        TextInputEditText rate = (TextInputEditText) dialog.findViewById(R.id.bid_rateAmount);
        TextInputEditText bidAmount = (TextInputEditText) dialog.findViewById(R.id.bid_bidAmount);

        cutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        rate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0) {
                    bidAmount.setText((Float.parseFloat(s.toString())*100)+"");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        bidAmount.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                rate.setText((Float.parseFloat(s.toString())/100)+"");
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, RideBidList.class);
                intent1.putExtra("from",DRIVER_PANEL);
                context.startActivity(intent1);
            }
        });

        holder.vehicleListSpinnerForBid = dialog.findViewById(R.id.vehicleListSpinnerForBid);

        DriversDetailsModel driversDetailsModel = new DriversDetailsModel();
        driversDetailsModel.setStrDriverName("VehicleList");
        List<DriversDetailsModel> list = new ArrayList<>();
        list.add(driversDetailsModel);

        list.add(driversDetailsModel);
        list.add(driversDetailsModel);
        list.add(driversDetailsModel);
        list.add(driversDetailsModel);

        VehicleListSpinnerAdapter vehicleListSpinnerAdapter = new VehicleListSpinnerAdapter(context,R.layout.spinner_vehicle_list_layout,list);
        holder.vehicleListSpinnerForBid.setAdapter(vehicleListSpinnerAdapter);


    }

    @Override
    public int getItemCount() {
        return detailsModels.size();
    }

    class BiddingListViewHolder extends RecyclerView.ViewHolder{

        private MaterialTextView dateTime,vehicleNo,pickup,destination,cost,driverName;
        private Button accept,reject;
        private Spinner vehicleListSpinnerForBid;

        public BiddingListViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTime = itemView.findViewById(R.id.tvPrevDate);
            vehicleNo = itemView.findViewById(R.id.tvPrevVehicleNo);
            cost = itemView.findViewById(R.id.tvPrevCost);
            pickup = itemView.findViewById(R.id.tvPrevPickup);
            destination = itemView.findViewById(R.id.tvPrevDestination);
            accept = itemView.findViewById(R.id.acceptBtn);
            reject = itemView.findViewById(R.id.rejectBtn);
            driverName = itemView.findViewById(R.id.tvPrevDriverName);
            driverName.setText("");

            accept.setVisibility(View.GONE);
            reject.setVisibility(View.VISIBLE);
            reject.setText("Bid");
        }
    }
}
