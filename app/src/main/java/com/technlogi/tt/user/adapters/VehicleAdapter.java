package com.technlogi.tt.user.adapters;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.technlogi.tt.R;
import com.technlogi.tt.user.models.VehicleModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder>{

    private List<VehicleModel> vehicleModels = new ArrayList<>();
    private boolean isBidding;

    public VehicleAdapter(List<VehicleModel> vehicleModels,boolean isBidding) {
        this.vehicleModels = vehicleModels;
        this.isBidding = isBidding;
    }


    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_veichle_list,parent,false);
        return new VehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
//            holder.tvVehicleMxLoad.setText("Max. Load:"+vehicleModels.get(position).getIntMaxLoad());
            holder.tvVehicleName.setText(vehicleModels.get(position).getStrVehicleName());
           // holder.tvVehicleType.setText(vehicleModels.get(position).getStrVehicleType());

        holder.btnRequestForRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!holder.btnRequestForRide.getText().toString().equals("Pending")) {
                    holder.btnRequestForRide.setVisibility(View.GONE);
                    holder.passanger_requestLoader.setVisibility(View.VISIBLE);

                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {

                            holder.btnRequestForRide.post(new Runnable() {
                                @Override
                                public void run() {
                                    holder.btnRequestForRide.setVisibility(View.VISIBLE);
                                    holder.btnRequestForRide.setText("Pending");
//                                holder.btnRequestForRide.setBackgroundResource(R.color.quantum_amber500);
                                    holder.btnRequestForRide.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFC107")));
                                    holder.passanger_requestLoader.setVisibility(View.GONE);
                                }
                            });

                        }
                    },3000);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return vehicleModels.size();
    }

    class VehicleViewHolder extends RecyclerView.ViewHolder{
        private TextView tvVehicleTime;
        private TextView tvVehicleName;
        private TextView tvVehiclePrice;
        private TextView tvVehicleType;
        private TextView tvVehicleMxLoad;
        private MaterialButton btnRequestForRide;
        private ProgressBar passanger_requestLoader;

        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
          //  tvVehicleTime = itemView.findViewById(R.id.tvVehicleTime);
            tvVehicleName = itemView.findViewById(R.id.tvVehicleName);
            tvVehiclePrice = itemView.findViewById(R.id.tvVehiclePrice);
            //tvVehicleType = itemView.findViewById(R.id.tvVehicleType);
//            tvVehicleMxLoad = itemView.findViewById(R.id.tvVehicleMxLoad);
            btnRequestForRide = itemView.findViewById(R.id.btnRequestForRide);
            passanger_requestLoader = itemView.findViewById(R.id.passanger_requestLoader);
        }
    }
}
