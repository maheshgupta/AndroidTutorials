package com.tutorials.andorid.app.view.users;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.core.BaseActivity;
import com.tutorials.andorid.app.model.Album;
import com.tutorials.andorid.app.model.Photo;

public class PhotoActivity extends BaseActivity {

    private static final String PHOTO = "PHOTO";
    private static final String TAG = "PhotosActivity";

    private Photo photo;


    public static Intent createIntent(@NonNull Context context, @NonNull Photo photo) {
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra(PHOTO, photo);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        this.photo = getIntent().getParcelableExtra(PHOTO);
    }


    @Override
    protected void onResume() {
        super.onResume();
        ImageView imageView = (ImageView) findViewById(R.id.image_view_photo);
        Glide.with(this).load(this.photo.getUrl()).into(imageView);
    }

    public void logoutApp(View view) {
        super.sendLogOutEvent();
    }

}
