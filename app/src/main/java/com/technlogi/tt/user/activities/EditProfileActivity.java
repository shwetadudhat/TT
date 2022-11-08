package com.technlogi.tt.user.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.technlogi.tt.IntentActivity;
import com.technlogi.tt.R;

import static com.technlogi.tt.Constant.PICK_IMAGE;

public class EditProfileActivity extends AppCompatActivity {

    ImageView user_profile_image;
//    private static final int PICK_IMAGE = 2;

    Uri imageUri;

    private FloatingActionButton editBtn;
    private TextInputEditText name,email,mobileNo,type,address;
    private Button saveBtn;
    private MaterialTextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_profile);

        user_profile_image = (ImageView) findViewById(R.id.user_profile_image);
        editBtn = (FloatingActionButton) findViewById(R.id.fabEditProfile);
        name = (TextInputEditText) findViewById(R.id.profileName);
        email = (TextInputEditText) findViewById(R.id.profileEmail);
        mobileNo = (TextInputEditText) findViewById(R.id.profileMobileNumber);
//        type = (TextInputEditText) findViewById(R.id.profileUserType);
        address = (TextInputEditText) findViewById(R.id.profileAddress);
        saveBtn = (Button) findViewById(R.id.updateProfileButton);
        title = (MaterialTextView) findViewById(R.id.profileTitle);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("Update Profile");
                name.setEnabled(true);
                email.setEnabled(true);
//                mobileNo.setEnabled(true);
//                type.setEnabled(true);
                address.setEnabled(true);

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
//                type.setEnabled(false);
                address.setEnabled(false);

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

                startActivityForResult(new Intent(EditProfileActivity.this, IntentActivity.class),PICK_IMAGE);
            }
        });

        findViewById(R.id.editProfileBack).setOnClickListener(new View.OnClickListener() {
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

        }
    }


}