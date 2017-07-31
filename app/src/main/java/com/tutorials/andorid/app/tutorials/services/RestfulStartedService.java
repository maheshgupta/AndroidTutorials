package com.tutorials.andorid.app.tutorials.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class RestfulStartedService extends Service {

    private static final String TAG = "RestfulStartedService";


    private ServiceHandlerThread serviceHandlerThread;

    public RestfulStartedService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        serviceHandlerThread = new ServiceHandlerThread();
        serviceHandlerThread.start();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        serviceHandlerThread.postMessage(Message.obtain());
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void messageHandler(Message message) {
        Log.i(TAG, "messageHandler: ");
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private class ServiceHandlerThread extends HandlerThread {

        private Handler handler;


        public ServiceHandlerThread() {
            super("SERVICE HANDLER THREAD");
        }

        @Override
        protected void onLooperPrepared() {
            handler = new Handler(getLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    messageHandler(msg);
                }
            };
            super.onLooperPrepared();
        }

        public void postMessage(Message message) {
            if (handler != null) {
                handler.sendMessage(message);
            } else {
                Log.i(TAG, "postMessage: failed to post the message");
            }
        }


    }


}
