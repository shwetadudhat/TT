package com.technlogi.tt.driver.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.technlogi.tt.R;
import com.technlogi.tt.driver.models.VehicleModel;
import com.technlogi.tt.driver.ui.vehiclelist.VehicleList;
import com.technlogi.tt.user.adapters.BookingDetailsAdapter;
import com.technlogi.tt.user.models.BookingDetailsModel;
import com.technlogi.tt.user.models.DriversDetailsModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import static com.technlogi.tt.Constant.PHONE_CALL_REQUEST;

public class VehicleListAdapter extends RecyclerView.Adapter<VehicleListAdapter.VehicleListViewHolder> {

    ArrayList<DriversDetailsModel> vehicleList = new ArrayList<>();
    private Context context;
    private IUser iUser;

    public interface IUser {
        public void AddVehicleDialog(int index);
        public void setDataForVehicleDialog(ArrayList<DriversDetailsModel> vehicleList,int position);
    }


    public VehicleListAdapter(Context context,ArrayList<DriversDetailsModel> vehicleModel,IUser iUser) {
        this.context = context;
        this.vehicleList = vehicleModel;
        this.iUser = iUser;
    }

    @NonNull
    @Override
    public VehicleListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_driver_vehicle_layout,parent,false);
        return new VehicleListAdapter.VehicleListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleListViewHolder holder, int position) {

        holder.drDsb_vehicleSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.drDsb_vehicleSwitch.isChecked()) {
                    holder.drDsb_vehicleSwitch.setText("ONLINE");
                } else {
                    holder.drDsb_vehicleSwitch.setText("OFFLINE");
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iUser.setDataForVehicleDialog(vehicleList,position);
                iUser.AddVehicleDialog(2);

            }
        });

        holder.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity)context,new String[]{Manifest.permission.CALL_PHONE},PHONE_CALL_REQUEST);
                } else {
                    context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+"1234567890")));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    class VehicleListViewHolder extends RecyclerView.ViewHolder {

        private Switch drDsb_vehicleSwitch;
        private Button callBtn;

        public VehicleListViewHolder(@NonNull View itemView) {
            super(itemView);

            drDsb_vehicleSwitch = itemView.findViewById(R.id.drDsb_vehicleSwitch);
            callBtn = itemView.findViewById(R.id.drVhLayout_driverCallBtn);
        }
    }

}
