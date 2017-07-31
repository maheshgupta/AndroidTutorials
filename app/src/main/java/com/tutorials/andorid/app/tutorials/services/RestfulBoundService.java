package com.tutorials.andorid.app.tutorials.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class RestfulBoundService extends Service {

    private static final String TAG = "RestfulBoundService";

    private final IBinder binder = new RestfulBinder();

    public RestfulBoundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void postMessage(Message message) {
        Log.i(TAG, "postMessage: ");
    }

    public class RestfulBinder extends Binder {

        public RestfulBoundService getService() {
            return RestfulBoundService.this;
        }


    }

}
