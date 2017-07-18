package com.tutorials.andorid.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tutorials.andorid.app.model.UserProfile;

public class HomeActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CAMERA = 100;

    private ImageView imageView;

    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, TAG + " onCreate: ");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setStatus("Just Stated...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, TAG + "onResume: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        setStatus("Loading...");
    }


    private void setStatus(String message) {
        TextView txtViewStatus = (TextView) findViewById(R.id.txt_view_status);
        txtViewStatus.setText(message);
    }

    public void showWebViewExample(View view) {
        Intent intent = new Intent(HomeActivity.this, WebViewActivityExample.class);
        startActivity(intent);
    }

    public void showCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
        Intent finalIntent = Intent.createChooser(intent, "Take Via");
        if (finalIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(finalIntent);
        }
    }

    public void showProfilePage(View view) {
        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName("Ritwik");
        userProfile.setLastName("Vallabdhas");
        userProfile.setEmail("ritwik@gmail.com");
        userProfile.setPhoneNumber("+1111111111");
        startActivity(ProfileActivity.createIntent(this, userProfile));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(bitmap);
        }
    }
}
