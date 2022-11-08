package com.technlogi.tt.common.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.technlogi.tt.R;
import com.technlogi.tt.SupportChatActivity;

public class BookingCancelDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_cancel_details);

        findViewById(R.id.activityBookingCancelBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingCancelDetails.super.onBackPressed();
            }
        });

//        findViewById(R.id.bookingCancelDetails_mailInvoice).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent email = new Intent(Intent.ACTION_SEND);
//                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"TaxiBooking@gmail.com"});
//                email.putExtra(Intent.EXTRA_SUBJECT,"COMPLAIN");
//                email.setType("message/rfc822");
//                startActivity(Intent.createChooser(email,"Choose an Email CLient"));
//            }
//        });
        
        findViewById(R.id.bookingCancelDetails_support).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookingCancelDetails.this, SupportChatActivity.class));
            }
        });
        
    }

}