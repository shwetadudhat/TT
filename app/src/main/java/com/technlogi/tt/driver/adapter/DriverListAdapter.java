package com.technlogi.tt.driver.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.technlogi.tt.R;
import com.technlogi.tt.driver.models.DriverModel;
import com.technlogi.tt.driver.models.VehicleModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DriverListAdapter extends RecyclerView.Adapter<DriverListAdapter.DriverListViewHolder> {

    private ArrayList<DriverModel> driverList = new ArrayList<>();
    private Context context;
    private DriverInterface driverInterface;

    public interface DriverInterface{
        public void AddDriverDialog(int index);
        public void setDataForDriverDialog(ArrayList<DriverModel> driverList,int position);
    }

    public DriverListAdapter(Context context,ArrayList<DriverModel> DriverModel,DriverInterface driverInterface) {
        this.driverList = DriverModel;
        this.context = context;
        this.driverInterface = driverInterface;
    }

    @NonNull
    @Override
    public DriverListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_driver_card,parent,false);
        return new DriverListAdapter.DriverListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverListViewHolder holder, int position) {

        holder.cardDriverName.setText(driverList.get(position).getDriverName());
        holder.cardDriverMobileNo.setText(driverList.get(position).getDriverMobileNumber());
        holder.cardDriverEmail.setText(driverList.get(position).getDriverEmail());
        holder.cardDriverRating.setText(driverList.get(position).getDriverRating());
        holder.cardDriverId.setText(driverList.get(position).getUserId());
        holder.cardDriverLicenseNo.setText(driverList.get(position).getDriverLicenceNumber());

//        Dialog addDriverDialog;
//        addDriverDialog = new Dialog(context);
//        addDriverDialog.setContentView(R.layout.dialog_driver_list_form);
//        Button addToListBtn = (Button) addDriverDialog.findViewById(R.id.drList_addToListBtn);
//        ImageButton editBtn = (ImageButton) addDriverDialog.findViewById(R.id.drList_editbtn);
//        ImageButton cutBtn = (ImageButton) addDriverDialog.findViewById(R.id.drList_cutBtn);
//        ImageView driverPhoto = (ImageView) addDriverDialog.findViewById(R.id.drList_drProfileImage);
//        TextInputEditText drList_drName = (TextInputEditText) addDriverDialog.findViewById(R.id.drList_drName);
//        TextInputEditText drList_drId = (TextInputEditText) addDriverDialog.findViewById(R.id.drList_drId);
//        TextInputEditText drList_drLicense = (TextInputEditText) addDriverDialog.findViewById(R.id.drList_drLicense);
//        TextInputEditText drList_drMobileNo = (TextInputEditText) addDriverDialog.findViewById(R.id.drList_drMobileNo);
//        TextInputEditText drList_drEmail = (TextInputEditText) addDriverDialog.findViewById(R.id.drList_drEmail);
//        TextInputEditText drList_drRating = (TextInputEditText) addDriverDialog.findViewById(R.id.drList_drRating);
//        TextInputEditText drList_drAddress = (TextInputEditText) addDriverDialog.findViewById(R.id.drList_drAddress);
//        TextInputEditText drList_drLicenseDoc = (TextInputEditText) addDriverDialog.findViewById(R.id.drList_drLicenseDoc);
//        ImageButton drList_viewDocBtn = (ImageButton) addDriverDialog.findViewById(R.id.drList_viewDocbtn);
//        ImageButton drList_uploadDocBtn = (ImageButton) addDriverDialog.findViewById(R.id.drList_uploadDocbtn);
//        TextInputEditText drList_drAadharDoc = (TextInputEditText) addDriverDialog.findViewById(R.id.drList_drAadharDoc);

//        cutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addDriverDialog.dismiss();
//            }
//        });
//
//        drList_uploadDocBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                context.startActivity(getFileChooserIntentForImageAndPdf());
//            }
//        });
//
//        editBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addToListBtn.setVisibility(View.VISIBLE);
//                addToListBtn.setText("Update");
//                driverPhoto.setEnabled(true);
//                drList_drName.setEnabled(true);
//                drList_drId.setEnabled(true);
//                drList_drLicense.setEnabled(true);
//                drList_drMobileNo.setEnabled(true);
//                drList_drEmail.setEnabled(true);
//                drList_drRating.setEnabled(true);
//                drList_drAddress.setEnabled(true);
//                drList_drLicenseDoc.setEnabled(true);
//                drList_viewDocBtn.setEnabled(true);
//                drList_uploadDocBtn.setEnabled(true);
////                drList_drAadharDoc.setEnabled(true);
//            }
//        });
//
//        addToListBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (addToListBtn.getText() == "Update") {
////                    Update the data of Driver
//                }
//            }
//        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                addToListBtn.setVisibility(View.GONE);
//                editBtn.setVisibility(View.VISIBLE);
//                driverPhoto.setEnabled(false);
//                drList_drName.setEnabled(false);
//                drList_drId.setEnabled(false);
//                drList_drLicense.setEnabled(false);
//                drList_drMobileNo.setEnabled(false);
//                drList_drEmail.setEnabled(false);
//                drList_drRating.setEnabled(false);
//                drList_drAddress.setEnabled(false);
//                drList_drLicenseDoc.setEnabled(false);
//                drList_viewDocBtn.setEnabled(false);
//                drList_uploadDocBtn.setEnabled(false);
////        drList_drAadharDoc.setEnabled(false);
//                addDriverDialog.show();
//
//                drList_drName.setText(driverList.get(position).getDriverName());
//                drList_drMobileNo.setText(driverList.get(position).getDriverMobileNumber());
//                drList_drEmail.setText(driverList.get(position).getDriverEmail());
//                drList_drRating.setText(driverList.get(position).getDriverRating());
//                drList_drId.setText(driverList.get(position).getUserId());
//                drList_drLicense.setText(driverList.get(position).getDriverLicenceNumber());

                driverInterface.setDataForDriverDialog(driverList,position);
                driverInterface.AddDriverDialog(2);

            }
        });

    }

    public Intent getFileChooserIntentForImageAndPdf() {
        String[] mimeTypes = {"image/*", "application/pdf"};
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT)
                .setType("image/*|application/pdf")
                .putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        return intent;
    }

    @Override
    public int getItemCount() {
        return driverList.size();
    }

    class DriverListViewHolder extends RecyclerView.ViewHolder {

        private ImageView cardDriverPhoto;
        private TextView cardDriverName,cardDriverMobileNo,cardDriverEmail,cardDriverRating,cardDriverId,cardDriverLicenseNo;

        public DriverListViewHolder(@NonNull View itemView) {
            super(itemView);

            cardDriverPhoto = (ImageView) itemView.findViewById(R.id.cardDriverPhoto);
            cardDriverName = (TextView) itemView.findViewById(R.id.cardDriverName);
            cardDriverMobileNo = (TextView) itemView.findViewById(R.id.cardDriverMobileNo);
            cardDriverEmail = (TextView) itemView.findViewById(R.id.cardDriverEmail);
            cardDriverRating = (TextView) itemView.findViewById(R.id.cardDriverRating);
            cardDriverId = (TextView) itemView.findViewById(R.id.cardDriverId);
            cardDriverLicenseNo = (TextView) itemView.findViewById(R.id.cardDriverLicenseNo);
        }
    }
}
