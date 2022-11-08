package com.technlogi.tt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class IntentActivity extends AppCompatActivity {

    private static final int CAMERA_IMAGE = 3;
    private static final int PICK_IMAGE = 2;
    private static final int CROP_IMAGE = 1;

    private Uri imageUri;
    private Dialog choosePhotoDialog;
    private ImageView cut,gallery,camera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        choosePhotoDialog = new Dialog(this);
        choosePhotoDialog.setContentView(R.layout.photo_choose_from_dialog);
        choosePhotoDialog.show();

        cut = choosePhotoDialog.findViewById(R.id.choose_photo_cut_icon);
        gallery = choosePhotoDialog.findViewById(R.id.choose_photo_gallery_icon);
        camera = choosePhotoDialog.findViewById(R.id.choose_photo_camera_icon);

        cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhotoDialog.dismiss();
                finish();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent,"Pick an image"), PICK_IMAGE);
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),CAMERA_IMAGE);
            }
        });




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            cropImage();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
//                user_profile_image.setImageBitmap(bitmap);
            }catch (IOException e) {

            }

        } else if (requestCode == CROP_IMAGE) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = bundle.getParcelable("data");

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                byte[] imageBytes = baos.toByteArray();
                String imageString = Base64.encodeToString(imageBytes,Base64.DEFAULT);

                Intent intent = new Intent();
                intent.putExtra("data",imageString);
                setResult(RESULT_OK,intent);
                finish();
            }
        } else if (requestCode == CAMERA_IMAGE) {
            imageUri = data.getData();
            cropImage();

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

            startActivityForResult(cropIntent,CROP_IMAGE);
        }catch (ActivityNotFoundException ax) {

        }
    }
}