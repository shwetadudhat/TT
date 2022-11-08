package com.technlogi.tt.user.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.technlogi.tt.R;
import com.technlogi.tt.SupportChatActivity;

import java.util.Objects;

public class BookingDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_details);
        findViewById(R.id.actirideDetailsBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingDetailsActivity.super.onBackPressed();
            }
        });

        findViewById(R.id.bookingDetails_mailInvoice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"TaxiBooking@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT,"COMPLAIN");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email,"Choose an Email CLient"));
            }
        });

        findViewById(R.id.bookingDetails_support).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookingDetailsActivity.this, SupportChatActivity.class));
            }
        });

        if(Objects.equals(getIntent().getStringExtra("data"), "cancel")) {
            findViewById(R.id.details_totalamount).setVisibility(View.GONE);
            findViewById(R.id.details_scroll).setVisibility(View.GONE);
        }
    }
}