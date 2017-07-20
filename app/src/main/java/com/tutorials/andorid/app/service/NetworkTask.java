package com.tutorials.andorid.app.service;


import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public abstract class NetworkTask<T> extends AsyncTask<Void, Void, T> {


    HttpsURLConnection connection = null;

    private URL remoteUrl;
    private InputStream inputStream;
    private RequestType requestType;


    public abstract void onSuccess(T response);

    public abstract void onError(String error);

    private String error;


    public enum RequestType {
        GET, POST
    }

    public NetworkTask(String url) throws MalformedURLException {
        this.remoteUrl = new URL(url);
    }


    public void sendRequest(RequestType type) {
        this.requestType = type;
        execute();
    }

    @Override
    protected T doInBackground(Void... voids) {
        T response = null;
        if (this.requestType == RequestType.GET) {
            response = makeGetRequest();
        }
        return response;
    }

    @Override
    protected void onPostExecute(T t) {
        if (t == null) {
            onError(this.error);
        } else {
            onSuccess(t);
        }
    }


    private T makeGetRequest() {
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
                    this.error = "Unknown error, Unable to read response.";
                }

                Gson gson = new Gson();
                BufferedReader reader = new BufferedReader(new InputStreamReader(this.inputStream));

                response = gson.fromJson(reader, new TypeToken<T>() {
                }.getType());
                Log.i("", "_makeGetRequest: ");
            } else {
                this.error = "HTTP error code: " + responseCode;
            }

        } catch (IOException e) {
            this.error = e.getMessage();
        } finally {
            if (this.inputStream != null) try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
//                this.error = e.getMessage();
            }
            if (this.connection != null) this.connection.disconnect();
        }
        return response;
    }
}
