package com.technlogi.tt.user.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.technlogi.tt.BookingPendingDetails;
import com.technlogi.tt.R;
import com.technlogi.tt.driver.adapter.BidListAdapter;
import com.technlogi.tt.user.adapters.VehicleGoodsAdapter;
import com.technlogi.tt.user.models.VehicleModel;

import java.util.ArrayList;

import static com.technlogi.tt.Constant.DRIVER_PANEL;
import static com.technlogi.tt.Constant.PASSANGER_PANEL;

public class RideBidList extends AppCompatActivity {

    private RecyclerView rvRideBidList;
    private ImageView rideBidBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_bid_list);

        rvRideBidList = (RecyclerView) findViewById(R.id.rvRideBidList);
        rideBidBack = (ImageView) findViewById(R.id.rideBidBack);
        rideBidBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               RideBidList.super.onBackPressed();
            }
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        rvRideBidList.setMinimumHeight((int) (height-400));

        VehicleModel model =  new VehicleModel();
        model.setIntId(1);
        model.setIntMaxLoad(1000);
        model.setStrVehicleName("Maruti Car");
        model.setStrVehicleType("Car");
        ArrayList<VehicleModel> models = new ArrayList<>();
        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);

        if (getIntent().getIntExtra("from",0) == PASSANGER_PANEL){
            VehicleGoodsAdapter adapter = new VehicleGoodsAdapter(models,true);
            rvRideBidList.setLayoutManager(new LinearLayoutManager(this));
            rvRideBidList.setHasFixedSize(false);
            rvRideBidList.setAdapter(adapter);
        } else if (getIntent().getIntExtra("from",0) == DRIVER_PANEL) {
            BidListAdapter adapter = new BidListAdapter(models,true);
            rvRideBidList.setLayoutManager(new LinearLayoutManager(this));
            rvRideBidList.setHasFixedSize(false);
            rvRideBidList.setAdapter(adapter);
        }


        findViewById(R.id.biddingBookingCancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingCancelReasonDialogInitialize();
            }
        });

        findViewById(R.id.bidList_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterDialogInitialize();
            }
        });
    }

    private void BookingCancelReasonDialogInitialize() {
        Dialog dialog = new Dialog(RideBidList.this);
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

    private void FilterDialogInitialize() {
        Dialog filterDialog = new Dialog(RideBidList.this);
        filterDialog.setContentView(R.layout.dialog_filter_popup);
        filterDialog.getWindow().setBackgroundDrawableResource(R.color.fully_transparent);

        filterDialog.show();

        filterDialog.findViewById(R.id.filter_dialog_cut_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDialog.dismiss();
            }
        });
    }
}