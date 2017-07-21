package com.tutorials.andorid.app;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tutorials.andorid.app.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class NetworkConnection<T> implements Runnable {

    private HttpsURLConnection connection;
    private InputStream inputStream;
    private RequestType requestType;
    private Callback callback;

    public interface Callback {
        void onSuccess(Object response);

        void onError(String error);
    }


    @Override
    public void run() {
        if (this.requestType == RequestType.GET) {
            try {
                this.makeGETRequest();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        }
    }

    public enum RequestType {
        GET,
        POST
    }


    private URL remoteUrl;


    public NetworkConnection(String url) throws MalformedURLException {
        this.remoteUrl = new URL(url);
    }

    public void makeRequest(RequestType type, Callback callback) throws IOException {
        this.requestType = type;
        this.callback = callback;
        new Thread(this).start();
    }


    public void makeGETRequest() throws IOException {
        this.connection = (HttpsURLConnection) this.remoteUrl.openConnection();
        this.connection.setReadTimeout(3000);
        this.connection.setConnectTimeout(5000);
        this.connection.setRequestMethod("GET");
        this.connection.setDoInput(true);
        int responseCode = this.connection.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            this.inputStream = this.connection.getInputStream();
            Gson gson = new Gson();
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.inputStream));
            Object users = gson.fromJson(reader, new TypeToken<T>() {
            }.getType());
            Log.i("", "makeGETRequest: ");
            this.callback.onSuccess(users);
        } else {
            this.callback.onError("Unknown error");
        }

    }


}
