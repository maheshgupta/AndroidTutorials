package com.tutorials.andorid.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tutorials.andorid.app.model.UserProfile;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    private static final int REQUEST_CODE_CAMERA = 100;

    public static final String FIRST_NAME = "FIRST_NAME";
    public static final String LAST_NAME = "LAST_NAME";
    public static final String EMAIL = "EMAIL";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String ZIP_CODE = "ZIP_CODE";
    public static final String ADDRESS1 = "ADDRESS1";
    public static final String ADDRESS2 = "ADDRESS2";

    private static final String USER_PROFILE = "USER_PROFILE";


    ImageView profileImageView;

    public static Intent createIntent(@NonNull Context context, @NonNull UserProfile userProfile) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(USER_PROFILE, userProfile);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        this.profileImageView = (ImageView) findViewById(R.id.imgview_profile);
        UserProfile userProfile = (UserProfile) getIntent().getParcelableExtra(USER_PROFILE);
        Log.i(TAG, "onCreate: FirstName : " + userProfile.getFirstName());

        EditText editTextFirstName = (EditText) findViewById(R.id.edit_text_first_name);
        if (userProfile != null) {
            editTextFirstName.setText(userProfile.getFirstName());
            Toast.makeText(this, userProfile.getFirstName(), Toast.LENGTH_SHORT).show();
        }
    }


    public void takePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    public void selectLocation(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            profileImageView.setImageBitmap(bitmap);
        }
    }

}
