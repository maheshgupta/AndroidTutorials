package com.tutorials.andorid.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CAMERA = 100;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void showWebViewExample(View view) {
        Intent intent = new Intent(HomeActivity.this, WebViewActivityExample.class);
        startActivity(intent);
    }

    public void showCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivity(intent);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
        Intent finalIntent = Intent.createChooser(intent, "Take Via");
        if (finalIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(finalIntent);
        }
    }

    public void showProfilePage(View view) {
        startActivity(ProfileActivity.createIntent(this));
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
