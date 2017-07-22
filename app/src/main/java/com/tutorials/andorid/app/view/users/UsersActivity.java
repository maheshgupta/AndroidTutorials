package com.tutorials.andorid.app.view.users;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.model.user.User;
import com.tutorials.andorid.app.service.NetworkTask;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.ArrayList;

import adapters.UsersAdapter;

public class UsersActivity extends AppCompatActivity {

    private static final String TAG = "UsersActivity";

    private ArrayList<User> users = null;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        this.setupWindowAnimations();
        this.listView = (ListView) findViewById(R.id.usersListView);
    }

    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Transition transition = new Slide();
            transition.setDuration(1000);
            getWindow().setExitTransition(transition);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            super.startActivity(intent);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        this.pullUsers();
    }

    private void pullUsers() {
        try {
            Type typeToken = new TypeToken<ArrayList<User>>() {
            }.getType();
            new NetworkTask<ArrayList<User>>("https://jsonplaceholder.typicode.com/users", typeToken) {
                @Override
                public void onSuccess(ArrayList<User> response) {
                    Log.i(TAG, "onSuccess");
                    users = response;
                    UsersActivity.this.renderUsers();
                }

                @Override
                public void onError(String error) {
                    Log.i(TAG, "onError: ");
                }
            }.sendRequest(NetworkTask.RequestType.GET);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    private void renderUsers() {
        if (this.users == null || this.users.size() <= 0) {
            Toast.makeText(this, "No users...", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] userNames = new String[this.users.size()];
        for (int i = 0; i < this.users.size(); i++) {
            userNames[i] = this.users.get(i).getName();
        }

//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, userNames);

        UsersAdapter adapter = new UsersAdapter(this, this.users);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                showAlbumsActivity(position);
            }
        });
        this.listView.setAdapter(adapter);
    }


    private void showUserProfileScreen(int index) {
        if (this.users == null || this.users.size() <= 0) return;
        User user = this.users.get(index);
        if (user != null) {
            startActivity(UserProfileActivity.createIntent(UsersActivity.this, user));
        }
    }


    private void showAlbumsActivity(int index) {
        if (this.users == null || this.users.size() <= 0) return;
        User user = this.users.get(index);
        if (user != null) {
            startActivity(AlbumsActivity.createIntent(UsersActivity.this, user));
        }
    }


}
