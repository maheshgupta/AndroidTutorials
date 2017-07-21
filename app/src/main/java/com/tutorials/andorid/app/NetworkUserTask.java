package com.tutorials.andorid.app;


import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUserTask<T> extends AsyncTask<Void, Void, Void> {

    private HttpsURLConnection connection;
    private InputStream inputStream;
    private NetworkConnection.RequestType requestType;
    private NetworkConnection.Callback callback;
    private URL remoteUrl;
    private Object responseFromServer;
    private String error;

    public interface Callback {
        void onSuccess(Object response);

        void onError(String error);
    }

    public NetworkUserTask(String url, NetworkConnection.RequestType type, NetworkConnection.Callback callback) throws MalformedURLException {
        this.remoteUrl = new URL(url);

        this.requestType = type;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            makeGETRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (TextUtils.isEmpty(this.error)) {
            this.callback.onSuccess(this.responseFromServer);
        } else {
            this.callback.onError(this.error);
        }
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
            this.responseFromServer = users;
        } else {
            this.error = "Unknown Error";
        }
    }
}
