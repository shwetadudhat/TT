package com.technlogi.tt.driver.ui.driverlist;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.technlogi.tt.IntentActivity;
import com.technlogi.tt.R;
import com.technlogi.tt.common.utils.ClickEvent;
import com.technlogi.tt.driver.adapter.DriverListAdapter;
import com.technlogi.tt.driver.adapter.VehicleListAdapter;
import com.technlogi.tt.driver.models.DriverModel;
import com.technlogi.tt.driver.models.VehicleModel;
import com.technlogi.tt.user.adapters.BookingDetailsAdapter;
import com.technlogi.tt.user.models.BookingDetailsModel;
import com.technlogi.tt.user.models.DriversDetailsModel;
import com.technlogi.tt.user.models.LocationModel;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;
import static com.technlogi.tt.Constant.AADHAAR_DOC;
import static com.technlogi.tt.Constant.HIDE_VIEW;
import static com.technlogi.tt.Constant.LICENSE_DOC;
import static com.technlogi.tt.Constant.RESEND_OTP_TIME_DURATION;
import static com.technlogi.tt.Constant.RESEND_OTP_TIME_INTERVAL;

public class DriverList extends Fragment implements DriverListAdapter.DriverInterface {

    private RecyclerView rvDriverList;
    private ImageView driverListBack;
    private FloatingActionButton fabDriverAdd;

    private Dialog addDriverDialog;
    private Uri licenseDoc,aadhaarDoc;

    private Button addToListBtn;
    private ImageButton editBtn,delBtn,drList_viewDocBtn,drList_uploadDocBtn,drList_viewAdrDocbtn,drList_uploadAdrDocbtn;
    private ImageView cutBtn;
    private ImageView driverPhoto;
    private TextInputEditText drList_drName,drList_drId,drList_drLicense,drList_drAadhaar,drList_drMobileNo,drList_drLicenseDoc,drList_drAadharDoc,drList_drEmail,drList_drRating,drList_drAddress,drList_drOTP;
    private TextView verifyBtn,resendBtn,title;

    private static final int PICK_IMAGE = 2;

    Uri imageUri;

    private ArrayList<DriverModel> driverList;
    private int position;

    private int resendOtpCountDown = 45;

    private CountDownTimer countDownTimer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvDriverList =  view.findViewById(R.id.rvDriverList);
        driverListBack =  view.findViewById(R.id.driverListBack);
        fabDriverAdd = (FloatingActionButton) view.findViewById(R.id.fabDriverAdd);

        InitializeAddDriverDialog();

        driverListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickEvent.getInstance().getClickEvent().onBackFrag();
//                ClickEvent.getInstance().getClickEvent().onBackFrag();
            }
        });

        fabDriverAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                addDriverDialog.show();
//                editBtn.setVisibility(View.GONE);

                AddDriverDialog(1);

            }
        });

        DriverModel driverModel = new DriverModel();
        driverModel.setDriverName("X-Ray");
        driverModel.setDriverMobileNumber("1234567890");
        driverModel.setDriverEmail("abcdefgh@gmail.com");
        driverModel.setDriverRating("Rating");
        driverModel.setUserId("4KDI459");
        driverModel.setDriverLicenceNumber("JHIURE454698");
        ArrayList<DriverModel> driverDetails = new  ArrayList();
        driverDetails.add(driverModel);
        DriverListAdapter adapter = new DriverListAdapter(getContext(),driverDetails,this);

        view.findViewById(R.id.drMsgLayout_noList_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rvDriverList.setLayoutManager(new LinearLayoutManager(getContext()));
                rvDriverList.setHasFixedSize(false);
                rvDriverList.setAdapter(adapter);

                view.findViewById(R.id.driverList_addDriverMsg).setVisibility(View.GONE);
                view.findViewById(R.id.fabDriverAdd).setVisibility(View.VISIBLE);
            }
        });

        view.findViewById(R.id.drMsgLayout_noList_addDriverTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDriverDialog.show();
            }
        });

        if (view.findViewById(R.id.driverList_addDriverMsg).getVisibility() == View.VISIBLE) {
            AddDriverDialog(1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ClickEvent.getInstance().getClickEvent().onChangeFrag(HIDE_VIEW);
        View v = inflater.inflate(R.layout.fragment_driver_list, container, false);

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


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            cropImage();
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
//                user_profile_image.setImageBitmap(bitmap);
//            }catch (IOException e) {
//
//            }
        } else if (requestCode == 1) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = bundle.getParcelable("data");
                driverPhoto.setImageBitmap(bitmap);
            }
        }else if (requestCode == 3) {
            if(data.hasExtra("data")){
//                Bundle bundle = data.getExtras();
//                Bitmap bitmap = bundle.getParcelable("data");
                addDriverDialog.show();
                byte[] imageBytes = Base64.decode(data.getStringExtra("data"),Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
                driverPhoto.setImageBitmap(decodedImage);
            }
        }else if (requestCode == LICENSE_DOC) {
            licenseDoc = data.getData();
            drList_uploadDocBtn.setImageTintList(getResources().getColorStateList(R.color.colorPrimary));
            drList_uploadDocBtn.setBackgroundResource(R.drawable.border_rectangle_color_primary);
            drList_viewDocBtn.setImageTintList(getResources().getColorStateList(R.color.colorPrimary));
            drList_viewDocBtn.setBackgroundResource(R.drawable.border_rectangle_color_primary);
        } else if (requestCode == AADHAAR_DOC) {
            aadhaarDoc = data.getData();
            drList_uploadAdrDocbtn.setImageTintList(getResources().getColorStateList(R.color.colorPrimary));
            drList_uploadAdrDocbtn.setBackgroundResource(R.drawable.border_rectangle_color_primary);
            drList_viewAdrDocbtn.setImageTintList(getResources().getColorStateList(R.color.colorPrimary));
            drList_viewAdrDocbtn.setBackgroundResource(R.drawable.border_rectangle_color_primary);
        }
    }

    private void cropImage() {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(imageUri,"image/*");

            cropIntent.putExtra("crop","true");
            cropIntent.putExtra("outputX",180);
            cropIntent.putExtra("outputY",180);
            cropIntent.putExtra("aspectX",3);
            cropIntent.putExtra("aspectY",3);
            cropIntent.putExtra("scaleUpIfNeeded",true);
            cropIntent.putExtra("return-data",true);

            startActivityForResult(cropIntent,1);
        }catch (ActivityNotFoundException ax) {

        }
    }

    public Intent getFileChooserIntentForImageAndPdf() {
        String[] mimeTypes = {"image/*", "application/pdf"};
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT)
                .setType("image/*|application/pdf")
                .putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        return intent;
    }

    public void InitializeAddDriverDialog() {
        addDriverDialog = new Dialog(getContext());

        addDriverDialog.setContentView(R.layout.dialog_driver_list_form);
        addDriverDialog.getWindow().setBackgroundDrawableResource(R.color.fully_transparent);

        title = (TextView) addDriverDialog.findViewById(R.id.dialog_driverListFormTitle);

        addToListBtn = (Button) addDriverDialog.findViewById(R.id.drList_addToListBtn);
        editBtn = (ImageButton) addDriverDialog.findViewById(R.id.drList_editbtn);
        delBtn = (ImageButton) addDriverDialog.findViewById(R.id.drList_delBtn);
        cutBtn = (ImageView) addDriverDialog.findViewById(R.id.drList_cutBtn);
        driverPhoto = (ImageView) addDriverDialog.findViewById(R.id.drList_drProfileImage);
        drList_drName = (TextInputEditText) addDriverDialog.findViewById(R.id.drList_drName);
        drList_drId = (TextInputEditText) addDriverDialog.findViewById(R.id.drList_drId);
        drList_drId.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
//        drList_drLicense = (TextInputEditText) addDriverDialog.findViewById(R.id.drList_drLicense);
//        drList_drAadhaar = (TextInputEditText) addDriverDialog.findViewById(R.id.drList_drAadhaar);
        drList_drMobileNo = (TextInputEditText) addDriverDialog.findViewById(R.id.drList_drMobileNo);
        drList_drEmail = (TextInputEditText) addDriverDialog.findViewById(R.id.drList_drEmail);
//        drList_drRating = (TextInputEditText) addDriverDialog.findViewById(R.id.drList_drRating);
        drList_drAddress = (TextInputEditText) addDriverDialog.findViewById(R.id.drList_drAddress);
        drList_drLicenseDoc = (TextInputEditText) addDriverDialog.findViewById(R.id.drList_drLicenseDoc);
        drList_drLicenseDoc.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        drList_viewDocBtn = (ImageButton) addDriverDialog.findViewById(R.id.drList_viewDocbtn);
        drList_viewAdrDocbtn = (ImageButton) addDriverDialog.findViewById(R.id.drList_viewAdrDocbtn);
        drList_uploadDocBtn = (ImageButton) addDriverDialog.findViewById(R.id.drList_uploadDocbtn);
        drList_uploadAdrDocbtn = (ImageButton) addDriverDialog.findViewById(R.id.drList_uploadAdrDocbtn);
        drList_drAadharDoc = (TextInputEditText) addDriverDialog.findViewById(R.id.drList_drAadhaarDoc);
        drList_drAadharDoc.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        drList_drOTP = (TextInputEditText) addDriverDialog.findViewById(R.id.drList_drOTP);
        verifyBtn = (TextView) addDriverDialog.findViewById(R.id.drList_mobileVerificationBtn);
        resendBtn = (TextView) addDriverDialog.findViewById(R.id.drList_resentOTPBtn);

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDriverDialog.findViewById(R.id.drList_drOTPParent).setVisibility(View.VISIBLE);
                drList_drOTP.setVisibility(View.VISIBLE);
                resendBtn.setVisibility(View.VISIBLE);
                resendBtn.setEnabled(false);

                resendOTPCountDown();
                countDownTimer.start();
            }
        });

        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendBtn.setEnabled(false);
                countDownTimer.cancel();
                resendOtpCountDown = 45;
//                resendOTPCountDown();
                countDownTimer.start();
            }
        });

        cutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDriverDialog.dismiss();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                verifyBtn.setVisibility(View.VISIBLE);
//                verifyBtn.setEnabled(true);
                title.setText("Update Driver");
                addToListBtn.setVisibility(View.VISIBLE);
                addToListBtn.setText("Update");
                addToListBtn.setEnabled(true);
                driverPhoto.setEnabled(true);
                drList_drName.setEnabled(true);
                drList_drId.setEnabled(true);
//                drList_drLicense.setEnabled(true);
//                drList_drAadhaar.setEnabled(true);
//                drList_drMobileNo.setEnabled(true);
                drList_drEmail.setEnabled(true);
//                drList_drRating.setEnabled(true);
                drList_drAddress.setEnabled(true);
                drList_drLicenseDoc.setEnabled(true);
                drList_viewDocBtn.setEnabled(true);
                drList_viewAdrDocbtn.setEnabled(true);
                drList_uploadDocBtn.setEnabled(true);
                drList_uploadAdrDocbtn.setEnabled(true);
                drList_drAadharDoc.setEnabled(true);
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog confirmationDialog = new Dialog(getContext());
                confirmationDialog.setContentView(R.layout.dialog_confirm_layout);
                ((TextView)confirmationDialog.findViewById(R.id.dialog_confirm_msg)).setText("Are you sure you want to delete Rohit (ID1234)?");
                confirmationDialog.show();
                confirmationDialog.findViewById(R.id.confirmDialog_cancelBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmationDialog.dismiss();
                    }
                });
            }
        });

        addToListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDriverDialog.dismiss();
            }
        });

        driverPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(Intent.createChooser(intent,"Pick an Image"),PICK_IMAGE);

                startActivityForResult(new Intent(getContext(), IntentActivity.class),3);
            }
        });

        drList_uploadDocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(getFileChooserIntentForImageAndPdf(),LICENSE_DOC);
            }
        });

        drList_uploadAdrDocbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(getFileChooserIntentForImageAndPdf(),AADHAAR_DOC);
            }
        });

        drList_viewDocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW, licenseDoc);
                    startActivity(intent);
                }
                catch (ActivityNotFoundException e)
                {
                    //tel the user to install viewer to perform this action
                }
            }
        });

        drList_viewAdrDocbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW, aadhaarDoc);
                    startActivity(intent);
                }
                catch (ActivityNotFoundException e)
                {
                    //tel the user to install viewer to perform this action
                }
            }
        });
    }

    public void resendOTPCountDown() {
        countDownTimer = new CountDownTimer(RESEND_OTP_TIME_DURATION,RESEND_OTP_TIME_INTERVAL){

            @Override
            public void onTick(long millisUntilFinished) {
                resendBtn.setText("Resend in "+resendOtpCountDown+" sec");
                resendOtpCountDown--;
                verifyBtn.setEnabled(false);
            }

            @Override
            public void onFinish() {
                resendBtn.setText("Resend OTP");
                resendBtn.setEnabled(true);
            }
        };
    }

    @Override
    public void AddDriverDialog(int index) {
        addDriverDialog.show();

        if (index == 1) {

            title.setText("Add Driver");

            editBtn.setVisibility(View.GONE);
            delBtn.setVisibility(View.GONE);
            verifyBtn.setVisibility(View.VISIBLE);
            addToListBtn.setText("Add to List");
            addToListBtn.setVisibility(View.VISIBLE);
            addToListBtn.setEnabled(true);
            driverPhoto.setEnabled(true);
            drList_drName.setEnabled(true);
            drList_drId.setEnabled(true);
//                drList_drLicense.setEnabled(true);
//                drList_drAadhaar.setEnabled(true);
//                drList_drMobileNo.setEnabled(true);
            drList_drEmail.setEnabled(true);
//                drList_drRating.setEnabled(true);
            drList_drAddress.setEnabled(true);
            drList_drLicenseDoc.setEnabled(true);
            drList_viewDocBtn.setEnabled(true);
            drList_viewAdrDocbtn.setEnabled(true);
            drList_uploadDocBtn.setEnabled(true);
            drList_uploadAdrDocbtn.setEnabled(true);
            drList_drAadharDoc.setEnabled(true);

        } else if (index == 2) {

            title.setText("Driver Detail");

            addDriverDialog.findViewById(R.id.drList_drOTPParent).setVisibility(View.GONE);
            drList_drOTP.setVisibility(View.GONE);
            resendBtn.setVisibility(View.GONE);
            verifyBtn.setVisibility(View.GONE);
            addToListBtn.setVisibility(View.GONE);
                editBtn.setVisibility(View.VISIBLE);
                delBtn.setVisibility(View.VISIBLE);
                driverPhoto.setEnabled(false);
                drList_drName.setEnabled(false);
                drList_drId.setEnabled(false);
//                drList_drLicense.setEnabled(false);
//                drList_drAadhaar.setEnabled(false);
                drList_drMobileNo.setEnabled(false);
                drList_drEmail.setEnabled(false);
//                drList_drRating.setEnabled(false);
                drList_drAddress.setEnabled(false);
                drList_drLicenseDoc.setEnabled(false);
                drList_viewDocBtn.setEnabled(false);
                drList_viewAdrDocbtn.setEnabled(false);
                drList_uploadDocBtn.setEnabled(false);
                drList_uploadAdrDocbtn.setEnabled(false);
//        drList_drAadharDoc.setEnabled(false);
                addDriverDialog.show();

                drList_drName.setText(driverList.get(position).getDriverName());
                drList_drMobileNo.setText(driverList.get(position).getDriverMobileNumber());
                drList_drEmail.setText(driverList.get(position).getDriverEmail());
//                drList_drRating.setText(driverList.get(position).getDriverRating());
                drList_drId.setText(driverList.get(position).getUserId());
//                drList_drLicense.setText(driverList.get(position).getDriverLicenceNumber());
        }
    }

    @Override
    public void setDataForDriverDialog(ArrayList<DriverModel> driverList, int position) {
        this.driverList = driverList;
        this.position = position;
    }
}
