package com.technlogi.tt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class InvitesList extends AppCompatActivity {

    private RecyclerView rvInvitesList;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invites_list);

        rvInvitesList = findViewById(R.id.rvInvitesList);
        backBtn = findViewById(R.id.invites_BackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        InviteModel inviteModel = new InviteModel("Suresh","$200 voucher earned");
        ArrayList<InviteModel> history = new  ArrayList();
        history.add(inviteModel);
        InvitesListAdapter adapter = new InvitesListAdapter(history);
        rvInvitesList.setLayoutManager(new LinearLayoutManager(this));
        rvInvitesList.setHasFixedSize(false);
        rvInvitesList.setAdapter(adapter);

    }
}