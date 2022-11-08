package com.technlogi.tt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.technlogi.tt.Constant.DRIVER_PANEL;
import static com.technlogi.tt.Constant.PASSANGER_PANEL;

public class BookingPendingDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_pending_details);

        findViewById(R.id.bookingPendingDetailsBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingPendingDetails.super.onBackPressed();
            }
        });

        findViewById(R.id.pendingDetails_bookingCancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingCancelReasonDialogInitialize();
            }
        });

        findViewById(R.id.bookingPendingDetails_callIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:"+1234567890));
                startActivity(call);
            }
        });

        try {
           if (getIntent().getIntExtra("from",0) == PASSANGER_PANEL) {
               findViewById(R.id.pendingDetails_bookingAcceptBtn).setVisibility(View.GONE);
           } else if (getIntent().getIntExtra("from",0) == DRIVER_PANEL) {
               ((Button)findViewById(R.id.pendingDetails_bookingCancelBtn)).setText("Reject");
           }
        }  catch (Exception e) {

        }
    }

    private void BookingCancelReasonDialogInitialize() {
        Dialog dialog = new Dialog(BookingPendingDetails.this);
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