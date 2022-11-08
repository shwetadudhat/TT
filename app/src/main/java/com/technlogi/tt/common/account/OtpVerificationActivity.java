package com.technlogi.tt.common.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.technlogi.tt.user.MainActivity;
import com.technlogi.tt.PermissionReqActivity;
import com.technlogi.tt.R;

import static com.technlogi.tt.Constant.RESEND_OTP_TIME_DURATION;
import static com.technlogi.tt.Constant.RESEND_OTP_TIME_INTERVAL;
import static com.technlogi.tt.Constant.locationPermissionRequest;

public class OtpVerificationActivity extends AppCompatActivity {

    private TextInputEditText otp_edit_text_1,otp_edit_text_2,otp_edit_text_3,otp_edit_text_4;
    private TextView resend_otp_btn;
    private int resendOtpCountDown = 45;

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        otp_edit_text_1 = (TextInputEditText) findViewById(R.id.otp_edit_text_1);
        otp_edit_text_2 = (TextInputEditText) findViewById(R.id.otp_edit_text_2);
        otp_edit_text_3 = (TextInputEditText) findViewById(R.id.otp_edit_text_3);
        otp_edit_text_4 = (TextInputEditText) findViewById(R.id.otp_edit_text_4);
        resend_otp_btn = (TextView) findViewById(R.id.resend_otp_btn);

        otp_edit_text_1.addTextChangedListener(new GenericTextWatcher(otp_edit_text_2,otp_edit_text_1));
        otp_edit_text_2.addTextChangedListener(new GenericTextWatcher(otp_edit_text_3,otp_edit_text_1));
        otp_edit_text_3.addTextChangedListener(new GenericTextWatcher(otp_edit_text_4,otp_edit_text_2));
        otp_edit_text_4.addTextChangedListener(new GenericTextWatcher(otp_edit_text_4,otp_edit_text_3));

        resentOTPCountDown();
        countDownTimer.start();

        resend_otp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resend_otp_btn.setEnabled(false);
                countDownTimer.cancel();
                resendOtpCountDown = 45;
//                resentOTPCountDown();
                countDownTimer.start();
            }
        });

        otp_edit_text_2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()==KeyEvent.ACTION_DOWN && keyCode==KeyEvent.KEYCODE_DEL){
                    otp_edit_text_1.requestFocus();
                    otp_edit_text_2.setText("");
                    return true;
                }
                return false;
            }
        });

        otp_edit_text_3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()==KeyEvent.ACTION_DOWN && keyCode==KeyEvent.KEYCODE_DEL){
                    otp_edit_text_2.requestFocus();
                    otp_edit_text_3.setText("");
                    return true;
                }
                return false;
            }
        });

        otp_edit_text_4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()==KeyEvent.ACTION_DOWN && keyCode==KeyEvent.KEYCODE_DEL){
                    otp_edit_text_3.requestFocus();
                    otp_edit_text_4.setText("");
                    return true;
                }
                return false;
            }
        });


        findViewById(R.id.btnLoginVerify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(locationPermissionRequest(OtpVerificationActivity.this) &&  checkGPSStatus(getApplicationContext())){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(getApplicationContext(), PermissionReqActivity.class));
                    finish();
                }



            }
        });
    }
    public static boolean checkGPSStatus(Context context){
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE );
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return statusOfGPS;
    }

    public void resentOTPCountDown() {
        countDownTimer = new CountDownTimer(RESEND_OTP_TIME_DURATION,RESEND_OTP_TIME_INTERVAL){

            @Override
            public void onTick(long millisUntilFinished) {
                resend_otp_btn.setText("Resend in "+resendOtpCountDown+" sec");
                resendOtpCountDown--;
            }

            @Override
            public void onFinish() {
                resend_otp_btn.setText("Resend OTP");
                resend_otp_btn.setEnabled(true);
            }
        };
    }

    public  class GenericTextWatcher implements TextWatcher {

        private View view1,view2;
        private GenericTextWatcher (View view1,View view2){
            this.view1 = view1;
            this.view2 = view2;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String text = s.toString();

            if (text.length() == 1) {
                view1.requestFocus();
            } else if (text.length() == 0) {
                view2.requestFocus();
            }
        }
    }
}