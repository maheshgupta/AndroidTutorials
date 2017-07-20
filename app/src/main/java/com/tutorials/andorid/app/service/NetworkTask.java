package com.tutorials.andorid.app.service;


import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tutorials.andorid.app.model.user.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public abstract class NetworkTask<T> extends AsyncTask<Void, Void, T> {


    HttpsURLConnection connection = null;

    private URL remoteUrl;
    private InputStream inputStream;
    private RequestType requestType;
    private Type mResponseType;


    public abstract void onSuccess(T response);

    public abstract void onError(String error);

    private String error;


    public enum RequestType {
        GET, POST
    }

    public NetworkTask(String url, Type responseType) throws MalformedURLException {
        this.remoteUrl = new URL(url);
        this.mResponseType = responseType;

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
                String jsonString = this.readStream(this.inputStream);
                response = gson.fromJson(jsonString, this.mResponseType);
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
