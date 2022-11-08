package com.technlogi.tt.driver.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.internal.NavigationMenuItemView;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.technlogi.tt.R;
import com.technlogi.tt.user.MainActivity;
import com.technlogi.tt.user.models.DriversDetailsModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.technlogi.tt.Constant.PHONE_CALL_REQUEST;

public class DriverVehicleListAdapter extends RecyclerView.Adapter<DriverVehicleListAdapter.DriverVehicleListViewHolder> {

    Context context;
    ArrayList<DriversDetailsModel> detailsModels = new ArrayList<>();
    private vhMinorUpdate vhMinorUpdate;

    public interface vhMinorUpdate {
        public void JumpToDriverList();
    }

    public DriverVehicleListAdapter (Context context,ArrayList<DriversDetailsModel> detailsModels,vhMinorUpdate vhMinorUpdate) {
        this.context = context;
        this.detailsModels = detailsModels;
        this.vhMinorUpdate = vhMinorUpdate;
    }

    @NonNull
    @Override
    public DriverVehicleListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_driver_vehicle_layout,parent,false);
        return new DriverVehicleListAdapter.DriverVehicleListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverVehicleListViewHolder holder, int position) {

//        holder.vehicleNo.setText(detailsModels.get(position).getStrVehicleNo());
//        holder.vehicleName.setText(detailsModels.get(position).getStrVehicleName());
//        holder.driverName.setText(detailsModels.get(position).getStrDriverName());

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog vehicleEdit = new Dialog(context);
                vehicleEdit.setContentView(R.layout.dialog_vehicle_minor_update);
                vehicleEdit.getWindow().setBackgroundDrawableResource(R.color.fully_transparent);
                vehicleEdit.show();

                Dialog capacityUnitDialog = new Dialog(context);
                capacityUnitDialog.setContentView(R.layout.dialog_capacity_unit_popup);
                capacityUnitDialog.getWindow().setBackgroundDrawableResource(R.color.fully_transparent);
                RadioGroup capacityUnitRadioGroup = capacityUnitDialog.findViewById(R.id.capacityUnit_radioGroup);
                capacityUnitDialog.findViewById(R.id.capacityUnit_dialog_cut_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        capacityUnitDialog.dismiss();
                    }
                });
                capacityUnitDialog.findViewById(R.id.capacityUnit_select_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String s = ((MaterialRadioButton)capacityUnitDialog.findViewById(capacityUnitRadioGroup.getCheckedRadioButtonId())).getText().toString();
                            holder.mtv_capacityUnit.setText(s);
                        } catch (Exception e){

                        }
                        capacityUnitDialog.dismiss();
                    }
                });

//                holder.driverAllotmentSpinner = vehicleEdit.findViewById(R.id.vmu_driverAllotmentSinner);
                holder.driverAllotmentEdit = vehicleEdit.findViewById(R.id.vmu_driverAllotmentEdit);
                holder.mtv_capacityUnit = vehicleEdit.findViewById(R.id.vmu_mtv_capacityUnit);
                holder.iv_capacity_unit = vehicleEdit.findViewById(R.id.vmu_iv_capacity_unit);

                vehicleEdit.findViewById(R.id.vmu_cutBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vehicleEdit.dismiss();
                    }
                });

                DriversDetailsModel driversDetailsModel = new DriversDetailsModel();
                driversDetailsModel.setStrDriverName("Own");
                driversDetailsModel.setStrDriverMobile("9876543210");
                List<DriversDetailsModel> list = new ArrayList<>();
                list.add(driversDetailsModel);

                DriversDetailsModel driversDetailsModel1 = new DriversDetailsModel();
                driversDetailsModel1.setStrDriverName("DriverName");
                driversDetailsModel1.setStrDriverMobile("9876543210");

                list.add(driversDetailsModel1);
                list.add(driversDetailsModel1);
                list.add(driversDetailsModel1);
                list.add(driversDetailsModel1);

//                DriverAllotmentSpinnerAdapter driverAllotmentSpinnerAdapter = new DriverAllotmentSpinnerAdapter(context,R.layout.driver_allotment_layout,list);
//                holder.driverAllotmentSpinner.setAdapter(driverAllotmentSpinnerAdapter);

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_confirm_layout);
                dialog.setCanceledOnTouchOutside(false);
                ((TextView)dialog.findViewById(R.id.dialog_confirm_msg)).setText("This driver is alloted to Maruti Car (DH02BR1234). Are you sure to allot this driver to new vehicle?");

                dialog.findViewById(R.id.confirmDialog_cancelBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.findViewById(R.id.confirmDialog_okButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                Driver Allotment Process
                    }
                });

//                holder.driverAllotmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        view.findViewById(R.id.driverAllotmentStatus).setVisibility(View.GONE);
//
//                        if (position!=0) {
//                            dialog.show();
//                        }
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });

                holder.iv_capacity_unit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        capacityUnitDialog.show();
                    }
                });

                Dialog driverAllotmentDialog = new Dialog(context);
                driverAllotmentDialog.setContentView(R.layout.dialog_driver_allotment);
                driverAllotmentDialog.getWindow().setBackgroundDrawableResource(R.color.fully_transparent);
                driverAllotmentDialog.findViewById(R.id.driverAllotment_dialog_cut_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        driverAllotmentDialog.dismiss();
                    }
                });

                DriversDetailsModel model = new DriversDetailsModel();
                model.setStrDriverName("Own");
                model.setStrDriverMobile("9876543210");
                ArrayList<DriversDetailsModel> driverList = new ArrayList<>();
                driverList.add(model);

                DriversDetailsModel model1 = new DriversDetailsModel();
                model1.setStrDriverName("DriverName");
                model1.setStrDriverMobile("9876543210");

                driverList.add(model1);
                driverList.add(model1);
                driverList.add(model1);
                driverList.add(model1);

                DriverAllotmentAdapter adapter = new DriverAllotmentAdapter(context,driverList);
                RecyclerView rvDriverAllotmentList = driverAllotmentDialog.findViewById(R.id.driverAllotment_rvDriverList);
                rvDriverAllotmentList.setLayoutManager(new LinearLayoutManager(context));
                rvDriverAllotmentList.setHasFixedSize(false);
                rvDriverAllotmentList.setAdapter(adapter);

                holder.driverAllotmentEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        driverAllotmentDialog.show();
                    }
                });

                vehicleEdit.findViewById(R.id.vmu_AddDriverIcon).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vhMinorUpdate.JumpToDriverList();
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return detailsModels.size();
    }

    class DriverVehicleListViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView vehicleNo,vehicleName,driverName;
        private Switch drDsb_vehicleSwitch;
        private Button callBtn;

//        private Spinner driverAllotmentSpinner;
        private MaterialTextView mtv_capacityUnit;
        private ImageView iv_capacity_unit;
        private TextInputEditText driverAllotmentEdit;

        private NavigationMenuItemView driverAdd;

        public DriverVehicleListViewHolder(@NonNull View itemView) {
            super(itemView);

            drDsb_vehicleSwitch = itemView.findViewById(R.id.drDsb_vehicleSwitch);
            callBtn = itemView.findViewById(R.id.drVhLayout_driverCallBtn);

            driverAdd = itemView.findViewById(R.id.nav_driver_driver_list);
//            driverAllotmentEdit  = itemView.findViewById(R.id.vmu_driverAllotmentEdit);
//            vehicleNo = itemView.findViewById(R.id.dr_vehicleNo);
//            vehicleName = itemView.findViewById(R.id.dr_vehicleName);
//            driverName = itemView.findViewById(R.id.dr_driverName);
        }
    }
}
