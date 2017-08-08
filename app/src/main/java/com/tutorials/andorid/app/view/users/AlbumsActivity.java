package com.tutorials.andorid.app.view.users;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.tutorials.andorid.app.MyBroadCastReciever;
import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.core.BaseActivity;
import com.tutorials.andorid.app.model.Album;
import com.tutorials.andorid.app.model.user.User;
import com.tutorials.andorid.app.retrofit.jsonplaceholder.AlbumsService;
import com.tutorials.andorid.app.service.NetworkTask;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.ArrayList;

import adapters.AlbumsAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlbumsActivity extends AppCompatActivity {

    private static final String USER = "USER";
    private static final String TAG = "AlbumsActivity";

    private User selectedUser;

    private ListView albumsListView;

    BroadcastReceiver broadcastReceiver;

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
        this.registerMyBroadReciever();

    }

    private void registerMyBroadReciever() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.sample.mybroadcast");
        broadcastReceiver = new MyBroadCastReciever();
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.pullAlbums();
        Toast.makeText(this, "User : " + this.selectedUser.getName(), Toast.LENGTH_SHORT).show();
    }


    private void pullAlbums() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AlbumsService albumsService = retrofit.create(AlbumsService.class);
        Call<ArrayList<Album>> albumsForUser = albumsService.getAlbumsForUser(this.selectedUser.getId());
        albumsForUser.enqueue(new Callback<ArrayList<Album>>() {
            @Override
            public void onResponse(Call<ArrayList<Album>> call, Response<ArrayList<Album>> response) {
                renderAlbumsList(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Album>> call, Throwable t) {
                Toast.makeText(AlbumsActivity.this, "Failed to retrieve the albums", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void pullAlbumsNative() {
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

        AlbumsAdapter adapter = new AlbumsAdapter(this, 0, tempAlbums);
        this.albumsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(AlbumsActivity.this, tempAlbums.get(i).getTitle(), Toast.LENGTH_SHORT).show();
                showPhotos(tempAlbums.get(i));
            }
        });
        this.albumsListView.setAdapter(adapter);
    }


    private void showPhotos(@NonNull Album album) {
        startActivity(PhotosActivity.createIntent(AlbumsActivity.this, album));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }
}
