package com.technlogi.tt;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;
import com.technlogi.tt.common.utils.ClickEvent;

import static com.technlogi.tt.Constant.HIDE_VIEW;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RefersEarnFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RefersEarnFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MaterialTextView seaAll;
    private ImageView backBtn,copyBtn;
    private Button shareCodeBtn;
    private TextView tvCode;

    public RefersEarnFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RefersEarnFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RefersEarnFragment newInstance(String param1, String param2) {
        RefersEarnFragment fragment = new RefersEarnFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backBtn = (ImageView) view.findViewById(R.id.referAndEarn_BackBtn);
        shareCodeBtn = view.findViewById(R.id.shareCode_Btn);
        copyBtn = view.findViewById(R.id.referAndEarn_codeCopyBtn);
        tvCode = view.findViewById(R.id.referAndEarn_tvCode);

        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Copy to ClipBoard : "+tvCode.getText().toString(), Toast.LENGTH_SHORT).show();
                ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("referCode",tvCode.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickEvent.getInstance().getClickEvent().onBackFrag();
            }
        });

        shareCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_invite_methods);
                dialog.getWindow().setBackgroundDrawableResource(R.color.fully_transparent);
                dialog.show();

                dialog.findViewById(R.id.got_it).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent1 = new Intent(Intent.ACTION_SEND);
                        intent1.putExtra(Intent.EXTRA_TEXT,tvCode.getText().toString());
                        intent1.setType("text/plain");
                        startActivity(Intent.createChooser(intent1,"Share Code"));
                    }
                });

                dialog.findViewById(R.id.invite_method_cut_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });

        seaAll = view.findViewById(R.id.RAE_sea_all);
        seaAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), InvitesList.class);
                startActivity(intent1);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ClickEvent.getInstance().getClickEvent().onChangeFrag(HIDE_VIEW);
        View v = inflater.inflate(R.layout.fragment_refers_earn, container, false);

        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    if (keyCode == KeyEvent.KEYCODE_BACK){
                        ClickEvent.getInstance().getClickEvent().onBackFrag();
                        return true;
                    }
                }
                return false;
            }
        });

        return v;
    }
}