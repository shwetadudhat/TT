package com.technlogi.tt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class SupportChatActivity extends AppCompatActivity {

    private RecyclerView rvSupportChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_chat);

        findViewById(R.id.supportChat_backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupportChatActivity.super.onBackPressed();
            }
        });

        rvSupportChat = findViewById(R.id.supportChat_rvMsgList);

        SupportMassageModel supportMassageModel = new SupportMassageModel();
        supportMassageModel.setType(1);
        supportMassageModel.setTime("09:30 AM");
        supportMassageModel.setMassage("Hi Good Morning");

        SupportMassageModel supportMassageModel1 = new SupportMassageModel();
        supportMassageModel1.setType(0);
        supportMassageModel1.setTime("09:31 AM");
        supportMassageModel1.setMassage("Good Morning");

        ArrayList<SupportMassageModel> arrayList = new ArrayList<>();
        arrayList.add(supportMassageModel);
        arrayList.add(supportMassageModel1);

        SupportMassageAdapter adapter = new SupportMassageAdapter(this,arrayList);
        rvSupportChat.setLayoutManager(new LinearLayoutManager(this));
        rvSupportChat.setHasFixedSize(false);
        rvSupportChat.setAdapter(adapter);
    }
}