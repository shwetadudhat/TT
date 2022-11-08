package com.technlogi.tt.common.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.technlogi.tt.PermissionReqActivity;
import com.technlogi.tt.R;

public class GivePermission extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_permission);

        findViewById(R.id.permission_give_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GivePermission.this, PermissionReqActivity.class));
            }
        });
    }
}