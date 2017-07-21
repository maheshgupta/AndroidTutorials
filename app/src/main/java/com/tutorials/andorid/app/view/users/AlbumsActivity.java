package com.tutorials.andorid.app.view.users;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.model.Album;
import com.tutorials.andorid.app.model.user.User;
import com.tutorials.andorid.app.service.NetworkTask;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.ArrayList;

import adapters.AlbumsAdapter;

public class AlbumsActivity extends AppCompatActivity {

    private static final String USER = "USER";
    private static final String TAG = "AlbumsActivity";

    private User selectedUser;

    private ListView albumsListView;

    public static Intent createIntent(@NonNull Context context, @NonNull User user) {
        Intent intent = new Intent(context, AlbumsActivity.class);
        intent.putExtra(USER, user);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);
        this.selectedUser = getIntent().getParcelableExtra(USER);
        this.albumsListView = (ListView) findViewById(R.id.albumsListView);
    }


    @Override
    protected void onResume() {
        super.onResume();
        this.pullAlbums();
        Toast.makeText(this, "User : " + this.selectedUser.getName(), Toast.LENGTH_SHORT).show();
    }

    private void pullAlbums() {
        try {

            String url = "https://jsonplaceholder.typicode.com/albums?userId="+this.selectedUser.getId();
            Type typeToken = new TypeToken<ArrayList<Album>>() {
            }.getType();

            new NetworkTask<ArrayList<Album>>(url, typeToken) {
                @Override
                public void onSuccess(ArrayList<Album> response) {
                    Log.i(TAG, "onSuccess: ");
                    renderAlbumsList(response);
                }

                @Override
                public void onError(String error) {
                    Log.i(TAG, "onError: ");
                    Toast.makeText(AlbumsActivity.this, "Some error, Please try again later..", Toast.LENGTH_SHORT).show();
                }
            }.sendRequest(NetworkTask.RequestType.GET);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void renderAlbumsList(ArrayList<Album> albums) {
        ArrayList<Album> tempAlbums = new ArrayList<>();

        tempAlbums.addAll(albums);
        tempAlbums.addAll(albums);
        tempAlbums.addAll(albums);
        tempAlbums.addAll(albums);

        AlbumsAdapter adapter = new AlbumsAdapter(this, 0, tempAlbums);
        this.albumsListView.setAdapter(adapter);

    }


}
