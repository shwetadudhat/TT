package com.technlogi.tt.driver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.technlogi.tt.IntentActivity;
import com.technlogi.tt.R;
import com.technlogi.tt.user.activities.EditProfileActivity;

import java.io.File;

import static com.technlogi.tt.Constant.PICK_IMAGE;
import static com.technlogi.tt.Constant.PICK_IMAGE_OR_PDF;

public class OwnerProfileActivity extends AppCompatActivity {

    ImageView user_profile_image;

    Uri imageUri;

    private FloatingActionButton editBtn;
    private TextInputEditText name,email,mobileNo,address,licenseNo,aadhaarNo;
    private Button saveBtn;
    private MaterialTextView title;
    private ImageButton licenseUpload,licenseView,aadhaarUpload,aadhaarView;

    private Uri file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_profile);

        user_profile_image = (ImageView) findViewById(R.id.owner_profile_image);
        editBtn = (FloatingActionButton) findViewById(R.id.owner_fabEditProfile);
        name = (TextInputEditText) findViewById(R.id.owner_profileName);
        email = (TextInputEditText) findViewById(R.id.owner_profileEmail);
        mobileNo = (TextInputEditText) findViewById(R.id.owner_profileMobileNumber);
        address = (TextInputEditText) findViewById(R.id.owner_profileAddress);
        saveBtn = (Button) findViewById(R.id.owner_updateProfileButton);
        title = (MaterialTextView) findViewById(R.id.owner_profileTitle);
        licenseNo = (TextInputEditText) findViewById(R.id.owner_licenseNumber);
        aadhaarNo = (TextInputEditText) findViewById(R.id.owner_aadhaarNumber);
        licenseUpload = (ImageButton) findViewById(R.id.owner_uploadLicenseDocbtn);
        licenseView = (ImageButton) findViewById(R.id.owner_viewLicenseDocbtn);
        aadhaarUpload = (ImageButton) findViewById(R.id.owner_uploadAdrDocbtn);
        aadhaarView = (ImageButton) findViewById(R.id.owner_viewAdrDocbtn);

        licenseUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(getFileChooserIntentForImageAndPdf(),PICK_IMAGE_OR_PDF);
            }
        });

        aadhaarUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(getFileChooserIntentForImageAndPdf(),PICK_IMAGE_OR_PDF);
            }
        });


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("Update Profile");
                name.setEnabled(true);
                email.setEnabled(true);
//                mobileNo.setEnabled(true);
                address.setEnabled(true);
                licenseNo.setEnabled(true);
                licenseUpload.setEnabled(true);
                licenseView.setEnabled(true);
                aadhaarNo.setEnabled(true);
                aadhaarUpload.setEnabled(true);
                aadhaarView.setEnabled(true);
                saveBtn.setVisibility(View.VISIBLE);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("Profile");
                name.setEnabled(false);
                email.setEnabled(false);
//                mobileNo.setEnabled(false);
                address.setEnabled(false);
                licenseNo.setEnabled(false);
                licenseUpload.setEnabled(false);
                licenseView.setEnabled(false);
                aadhaarNo.setEnabled(false);
                aadhaarUpload.setEnabled(false);
                aadhaarView.setEnabled(false);
                saveBtn.setVisibility(View.GONE);
            }
        });


        user_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                IntentActivity intentActivity = new IntentActivity();
//                intentActivity.ImageSelectOption(user_profile_image);

//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent,"Pick an image"), PICK_IMAGE);

//                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(Intent.createChooser(intent,"Pick an Image"),PICK_IMAGE);

                startActivityForResult(new Intent(OwnerProfileActivity.this, IntentActivity.class),PICK_IMAGE);
            }
        });

        findViewById(R.id.owner_editProfileBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
//            imageUri = data.getData();
//            cropImage();
////            try {
////                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
////                user_profile_image.setImageBitmap(bitmap);
////            }catch (IOException e) {
////
////            }
//        } else if (requestCode == 1) {
//            if (data != null) {
//                Bundle bundle = data.getExtras();
//                Bitmap bitmap = bundle.getParcelable("data");
//                user_profile_image.setImageBitmap(bitmap);
//            }
//        }
//    }
//
//    private void cropImage() {
//        try {
//            Intent cropIntent = new Intent("com.android.camera.action.CROP");
//            cropIntent.setDataAndType(imageUri,"image/*");
//
//            cropIntent.putExtra("crop","true");
//            cropIntent.putExtra("outputX",300);
//            cropIntent.putExtra("outputY",300);
//            cropIntent.putExtra("aspectX",5);
//            cropIntent.putExtra("aspectY",5);
//            cropIntent.putExtra("scaleUpIfNeeded",false);
//            cropIntent.putExtra("return-data",true);
//
//            startActivityForResult(cropIntent,1);
//        }catch (ActivityNotFoundException ax) {
//
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            if(data.hasExtra("data")){
//                Bundle bundle = data.getExtras();
//                Bitmap bitmap = bundle.getParcelable("data");

                byte[] imageBytes = Base64.decode(data.getStringExtra("data"),Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
                user_profile_image.setImageBitmap(decodedImage);
            }

        }else if (requestCode == PICK_IMAGE_OR_PDF) {
            file = data.getData();
//            drList_drLicenseDoc.setText(file.getPath());
        }
    }

    public Intent getFileChooserIntentForImageAndPdf() {
        String[] mimeTypes = {"image/*", "application/pdf"};
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT)
                .setType("image/*|application/pdf")
                .putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        return intent;
    }
}