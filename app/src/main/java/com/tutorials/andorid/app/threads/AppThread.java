package com.tutorials.andorid.app.threads;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class AppThread extends Thread {

    private static final String TAG = "AppThread";

    private Handler handler;

    public AppThread() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                workOnMessage(msg);
            }
        };
    }

    @Override
    public void run() {
        Looper.prepare();
        Looper.loop();
    }

    protected void workOnMessage(Message message) {
        Log.i(TAG, "workOnMessage: " + message.arg1);
    }


    public void postMessage(Message message){
        this.handler.dispatchMessage(message);
    }

}
