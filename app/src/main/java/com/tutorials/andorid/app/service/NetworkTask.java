package com.tutorials.andorid.app.service;


import android.support.annotation.NonNull;

import com.google.android.gms.common.api.Response;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class NetworkTask<T> {

    public interface ResponseHandler<T> {
        void onSuccess(T response);

        void onError(String error);
    }

    private InputStream inputStream = null;

    private HttpsURLConnection httpsURLConnection = null;

    private URL url;

    public NetworkTask(@NonNull String url, ResponseHandler handler) {

        try {
            this.url = new URL(url);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }


}
