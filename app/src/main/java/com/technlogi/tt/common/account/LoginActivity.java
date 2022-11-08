package com.technlogi.tt.common.account;

import static com.technlogi.tt.Constant.REQUEST_PHONE_STATE_CODE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.technlogi.tt.R;
import com.technlogi.tt.user.adapters.MobileListAdapter;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    Button btnNextOtpCofirm;

        private ProgressDialog progressDialog;

    private TextInputLayout filledTextField,filledTextRefer;
    private TextInputEditText edit_text,et_refer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        getMobileNo();
        progressDialog = new ProgressDialog( LoginActivity.this, R.style.MyAlertDialogStyle );
        progressDialog.setCancelable( false );
        progressDialog.setCanceledOnTouchOutside( false );


        filledTextField=findViewById(R.id.filledTextField);
        filledTextRefer=findViewById(R.id.filledTextRefer);
        btnNextOtpCofirm=findViewById(R.id.btnNextOtpCofirm);
        edit_text=findViewById(R.id.edit_text);
        et_refer=findViewById(R.id.et_refer);

        btnNextOtpCofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check validation....
//                if (edit_text.getText().toString().equals("")){
//                    validateAuthLogin();
//                }else{
//                    clearLoginErrors();
                    startActivity(new Intent(getApplicationContext(), OtpVerificationActivity.class));
                    finish();
//                }

            }
        });
    }

    public ArrayList<String> getMobileNo() {
        ArrayList<String> mobilNos = new ArrayList<>();
        TelephonyManager tMgr = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {


                tMgr = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
                mPhoneNumber = tMgr.getLine1Number();
                if(mPhoneNumber != null ) {
                    if(!mPhoneNumber.isEmpty()) {
                        mobilNos.add(mPhoneNumber);
                        alertMobileOtp(mobilNos);
                    }
                }
            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},REQUEST_PHONE_STATE_CODE);
            }

        }else{

            mPhoneNumber = tMgr.getLine1Number();
            if(mPhoneNumber != null)
                mobilNos.add(mPhoneNumber);

            alertMobileOtp(mobilNos);
        }


        return mobilNos;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PHONE_STATE_CODE){
            getMobileNo();
        }
    }

    public void alertMobileOtp(ArrayList<String> mobileNos){
        //  mobileNos.add("Other");

        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_mobile_list);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.findViewById(R.id.img_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tvNoneOTA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        RecyclerView rvAlertMobileList =  dialog.findViewById(R.id.rvAlertMobileList);

        rvAlertMobileList.setHasFixedSize(false);
        rvAlertMobileList.setLayoutManager(new LinearLayoutManager(this));
        MobileListAdapter mobileListAdapter =  new MobileListAdapter(mobileNos)
                .setClicks(new MobileListAdapter.OrderItemClicks() {
                    @Override
                    public void onItemClick(Object obj, int position) {

                        mobileNos.add(position,mobileNos.get(position).replace("+",""));
                        mobileNos.add(mobileNos.get(position).trim());
                        if(mobileNos.get(position).length()>10){
                            int extra = mobileNos.get(position).length() - 10;
                            if(extra>0){
                                mobileNos.add(position,mobileNos.get(position).substring(extra,mobileNos.get(position).length()));
                            }
                        }
                        edit_text.setText(mobileNos.get(position));
                        validateAuthLogin();
                        dialog.dismiss();
                    }
                });
        rvAlertMobileList.setAdapter(mobileListAdapter);

        dialog.show();

    }

    private void validateAuthLogin() {
        if (TextUtils.isEmpty( edit_text.getText().toString().trim() )) {
            //set error
            filledTextField.setErrorEnabled( true );
            edit_text.setError( "Cannot set empty phone number!" );
        } else if (edit_text.getText().toString().trim().length() < 6 || edit_text.getText().toString().trim().length() > 13) {
            //show error
            filledTextField.setErrorEnabled( true );
            edit_text.setError( "Phone number is not valid!" );
        }/* else { //note login with mobile number
            loginUserWithAuthCode();
        }*/
    }

    private void loginUserWithAuthCode() {
        showProgress();


        //api call
    }

    private void showProgress() {
        progressDialog.setTitle( "Please wait..." );
        progressDialog.show();
    }

    private void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog.cancel();
        }
    }

    private void clearLoginErrors() {
        filledTextField.setErrorEnabled( false );
        filledTextField.setError( null );
    }


}