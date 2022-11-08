package com.technlogi.tt.common.fcm;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.technlogi.tt.user.MainActivity;

public class ActHandlePushService extends AppCompatActivity {

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      if (getIntent() != null) {
         if (getIntent().getStringExtra("Intent_received") != null)
            switch (Integer.parseInt(getIntent().getStringExtra("Intent_received"))) {
               case 1: {
                  openIntent(new Intent(this, MainActivity.class).putExtra("Page_Index", "3"));
               }
               break;

               case 2: {
               }
               break;

               case 3: {

               }
               break;

               case 4: {
               }
               break;

               case 5: {
               }
               break;

               default: {

               }
               break;
            }
      }
   }

   private void openIntent(Intent intent) {
      startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
      finish();
   }
}