package com.tutorials.andorid.app.view.users;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

import adapters.AlbumRecyclerAdapter;
import adapters.AlbumsAdapter;

public class AlbumsActivity extends AppCompatActivity {

    private static final String USER = "USER";
    private static final String TAG = "AlbumsActivity";

    private User selectedUser;

    /**
     * listView for list view implementation
     * private ListView albumsListView;*/

    private RecyclerView albumsRecyclerView;

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
        /**
         * listView for list view implementation
         * this.albumsListView = (ListView) findViewById(R.id.albumsListView);*/
        this.albumsRecyclerView = (RecyclerView) findViewById(R.id.albumRecyclerView);
    }


    @Override
    protected void onResume() {
        super.onResume();
        this.pullAlbums();
        Toast.makeText(this, "User : " + this.selectedUser.getName(), Toast.LENGTH_SHORT).show();
    }

    private void pullAlbums() {
        try {

            String url = "https://jsonplaceholder.typicode.com/albums?userId=" + this.selectedUser.getId();
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
        final ArrayList<Album> tempAlbums = new ArrayList<>();

        tempAlbums.addAll(albums);
        tempAlbums.addAll(albums);
        tempAlbums.addAll(albums);
        tempAlbums.addAll(albums);

        /**
         *  AlbumAdapter for listView implementation
        AlbumsAdapter adapter = new AlbumsAdapter(this, 0, tempAlbums);
        this.albumsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(AlbumsActivity.this, tempAlbums.get(i).getTitle(), Toast.LENGTH_SHORT).show();
                showPhotos(tempAlbums.get(i));
            }
        });
        this.albumsListView.setAdapter(adapter);*/
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        albumsRecyclerView.setHasFixedSize(true);
        albumsRecyclerView.setLayoutManager(layoutManager);

        AlbumRecyclerAdapter adapter = new AlbumRecyclerAdapter(this, 0,tempAlbums);
        adapter.setCallback(new AlbumRecyclerAdapter.Callback() {
            @Override
            public void onClick(int position) {
                showPhotos(tempAlbums.get(position));
            }
        });
        albumsRecyclerView.setAdapter(adapter);
    }


    private void showPhotos(@NonNull Album album) {
        startActivity(PhotosActivity.createIntent(AlbumsActivity.this, album));
    }


}
