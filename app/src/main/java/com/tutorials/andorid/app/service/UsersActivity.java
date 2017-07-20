package com.tutorials.andorid.app.service;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.model.user.User;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity {

    private static final String TAG = "UsersActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
    }


    @Override
    protected void onResume() {
        super.onResume();
        this.pullUser();
    }

    private void pullUsers() {
        try {
            new NetworkTask<ArrayList<User>>("https://jsonplaceholder.typicode.com/users") {
                @Override
                public void onSuccess(ArrayList<User> response) {
                    Log.i(TAG, "onSuccess");
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


    private void pullUser() {
        try {
            new NetworkTask<User>("https://jsonplaceholder.typicode.com/users/1") {
                @Override
                public void onSuccess(User response) {
                    Log.i(TAG, "onSuccess: ");
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


}
