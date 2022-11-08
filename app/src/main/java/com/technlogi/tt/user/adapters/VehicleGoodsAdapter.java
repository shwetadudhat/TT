package com.technlogi.tt.user.adapters;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.technlogi.tt.R;
import com.technlogi.tt.user.activities.BookingDetailsActivity;
import com.technlogi.tt.user.models.VehicleModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class VehicleGoodsAdapter extends RecyclerView.Adapter<VehicleGoodsAdapter.VehicleViewHolder>{

    private List<VehicleModel> vehicleModels = new ArrayList<>();
    private boolean isBidding;

    public VehicleGoodsAdapter(List<VehicleModel> vehicleModels,boolean isBidding) {
        this.vehicleModels = vehicleModels;
        this.isBidding = isBidding;
    }


    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_goods_veichle_list,parent,false);
        return new VehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
//            holder.tvBiddingVehicleMxLoad.setText("Max. Load:"+vehicleModels.get(position).getIntMaxLoad());
            holder.tvBiddingVehicleName.setText(vehicleModels.get(position).getStrVehicleName());

            holder.btnBiddingSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


//                    Dialog dialog =  new Dialog(holder.itemView.getContext());
//                    dialog.setContentView(R.layout.dialog_confirm_layout);
//                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    dialog.show();
//
//                    MaterialTextView msg = dialog.findViewById(R.id.dialog_confirm_msg);
//
//                    if (isBidding){
//                        msg.setText("Are you Sure you want to Bid now?");
//                    }

                    if (!holder.btnBiddingSelect.getText().toString().equals("Pending")) {
                        holder.btnBiddingSelect.setVisibility(View.GONE);
                        holder.goods_requestLoader.setVisibility(View.VISIBLE);

                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
//                            holder.btnBiddingSelect.setVisibility(View.VISIBLE);
//                            holder.btnBiddingSelect.setText("Pending");
//                            holder.btnBiddingSelect.setBackgroundResource(R.color.quantum_amber500);
//                            holder.goods_requestLoader.setVisibility(View.GONE);

                                holder.btnBiddingSelect.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        holder.btnBiddingSelect.setVisibility(View.VISIBLE);
                                        holder.btnBiddingSelect.setText("Pending");
//                                    holder.btnBiddingSelect.setBackgroundTintList(ColorStateList.valueOf(R.color.quantum_amber500));
                                        holder.btnBiddingSelect.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFC107")));
                                        holder.goods_requestLoader.setVisibility(View.GONE);
                                    }
                                });
                            }
                        },3000);
                    }


                }

            });

//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), BookingDetailsActivity.class));
//                }
//            });

        //    holder.tvVehicleType.setText(vehicleModels.get(position).getStrVehicleType());
    }

    @Override
    public int getItemCount() {
        return vehicleModels.size();
    }

    class VehicleViewHolder extends RecyclerView.ViewHolder{
        private TextView tvBiddingVehicleTime;
        private TextView tvBiddingVehicleName;
        private TextView tvBiddingVehiclePrice;
        private TextView tvBiddingVehicleType;
        private TextView tvBiddingVehicleMxLoad;
        private MaterialButton btnBiddingSelect;
        private ImageView vehiclePhoto;
        private ProgressBar goods_requestLoader;

        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            //tvVehilceTime = itemView.findViewById(R.id.tvVehicleTime);
            tvBiddingVehicleName = itemView.findViewById(R.id.tvBiddingVehicleName);
            tvBiddingVehiclePrice = itemView.findViewById(R.id.tvBiddingVehiclePrice);
            //tvVehicleType = itemView.findViewById(R.id.tvVehicleType);
//            tvBiddingVehicleMxLoad = itemView.findViewById(R.id.tvBiddingVehicleMxLoad);
            btnBiddingSelect = itemView.findViewById(R.id.btnBiddingSelect);
            vehiclePhoto = itemView.findViewById(R.id.imvBiddingVehiclePhoto);
            goods_requestLoader = itemView.findViewById(R.id.goods_requestLoader);

            btnBiddingSelect.setText("Request");
        }
    }
}
