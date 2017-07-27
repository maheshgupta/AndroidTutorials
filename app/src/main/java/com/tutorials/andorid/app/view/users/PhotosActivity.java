package com.tutorials.andorid.app.view.users;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.core.BaseActivity;
import com.tutorials.andorid.app.model.Album;
import com.tutorials.andorid.app.model.Photo;
import com.tutorials.andorid.app.service.NetworkTask;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.ArrayList;

import adapters.PhotosRecyclerAdapter;

public class PhotosActivity extends BaseActivity {

    private static final String ALBUM = "ALBUM";
    private static final String TAG = "PhotosActivity";

    private Album album;


    public static Intent createIntent(@NonNull Context context, @NonNull Album album) {
        Intent intent = new Intent(context, PhotosActivity.class);
        intent.putExtra(ALBUM, album);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        this.album = getIntent().getParcelableExtra(ALBUM);
        this.pullPhotos(this.album.getId());

    }

    private void pullPhotos(int albumID) {

        String url = "https://jsonplaceholder.typicode.com/photos?albumId=" + albumID;
        Type typeToken = new TypeToken<ArrayList<Photo>>() {
        }.getType();

        try {
            new NetworkTask<ArrayList<Photo>>(url, typeToken) {
                @Override
                public void onSuccess(ArrayList<Photo> response) {
                    renderPhotos(response);
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(PhotosActivity.this, "Error while pulling photos", Toast.LENGTH_SHORT).show();
                }
            }.sendRequest(NetworkTask.RequestType.GET);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    private void renderPhotos(final ArrayList<Photo> photos) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.photosRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        PhotosRecyclerAdapter adapter = new PhotosRecyclerAdapter(this, photos);
        adapter.setCallback(new PhotosRecyclerAdapter.Callback() {
            @Override
            public void onClick(int position) {
                Photo photo = photos.get(position);
                startActivity(PhotoActivity.createIntent(PhotosActivity.this, photo));
            }
        });
        recyclerView.setAdapter(adapter);

    }


}
