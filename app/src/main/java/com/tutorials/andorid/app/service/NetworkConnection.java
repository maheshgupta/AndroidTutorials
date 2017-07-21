package com.tutorials.andorid.app.service;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tutorials.andorid.app.model.user.User;
import com.tutorials.andorid.app.model.user.Users;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class NetworkConnection<T> implements Runnable {

    public enum RequestType {
        GET,
        POST
    }

    public interface Callback<T> {
        void onSuccess(T response);

        void onError(String error);
    }


    HttpsURLConnection connection = null;

    private URL remoteUrl;
    private InputStream inputStream;
    private RequestType requestType;
    private Callback mCallback;


    public NetworkConnection(@NonNull String url) throws MalformedURLException {
        init(url);
    }


    private void init(@NonNull String url) throws MalformedURLException {
        this.remoteUrl = new URL(url);
    }

    public void makeRequest(RequestType type, Callback callback) {
        this.requestType = type;
        this.mCallback = callback;
        new Thread(this).start();
    }


    private T _makeGetRequest() throws IOException {
        T response = null;
        try {
            connection = (HttpsURLConnection) remoteUrl.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                this.inputStream = connection.getInputStream();
                if (inputStream == null) {
                    throw new IOException("Unknown error, Unable to read response.");
                }

                Gson gson = new Gson();
                BufferedReader reader = new BufferedReader(new InputStreamReader(this.inputStream));

                response = gson.fromJson(reader, new TypeToken<T>() {
                }.getType());
                Log.i("", "_makeGetRequest: ");
            } else {
                throw new IOException("HTTP error code: " + responseCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e);

        } finally {
            if (this.inputStream != null) inputStream.close();
            if (this.connection != null) this.connection.disconnect();
        }
        return response;
    }

    private String readStream(InputStream stream)
            throws IOException, UnsupportedEncodingException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }


    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (requestType == RequestType.GET) {
            try {
                T response = _makeGetRequest();
                this.mCallback.onSuccess(response);
            } catch (IOException e) {
                e.printStackTrace();
                this.mCallback.onError(e.getMessage());
            }
        } else {

        }
    }
}
