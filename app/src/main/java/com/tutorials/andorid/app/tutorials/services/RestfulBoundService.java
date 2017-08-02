package com.tutorials.andorid.app.tutorials.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import java.io.FileDescriptor;

public class RestfulBoundService extends Service {

    private static final String TAG = "RestfulBoundService";

    private final IBinder binder = new RestfulBinder();

    RestfulHandler restfulHandler = new RestfulHandler();

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

        public RestfulHandler restfulBinder() {
            return restfulHandler;
        }

    }


    public class RestfulHandler {

        public void makeNetworkCall(String url, String arg1, String arg2, Handler.Callback callback) {

        }

        public void makeDumbCall() {
            Log.i(TAG, "makeDumbCall: ");
        }

    }


}
