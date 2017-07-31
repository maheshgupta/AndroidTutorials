package com.tutorials.andorid.app.tutorials.network;


import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.tutorials.andorid.app.model.user.User;
import com.tutorials.andorid.app.service.NetworkTask;
import com.tutorials.andorid.app.view.users.UsersActivity;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class RestfulHandler {
    public interface Callback {
        void onSuccess(Object object);

        void onError(String error);
    }


    public static void getUsers(final RestfulHandler.Callback callback) {
        try {
            Type typeToken = new TypeToken<ArrayList<User>>() {
            }.getType();
            new NetworkTask<ArrayList<User>>("https://jsonplaceholder.typicode.com/users", typeToken) {
                @Override
                public void onSuccess(ArrayList<User> response) {
                    callback.onSuccess(response);
                }

                @Override
                public void onError(String error) {
                    callback.onError(error);
                }
            }.sendRequest(NetworkTask.RequestType.GET);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
