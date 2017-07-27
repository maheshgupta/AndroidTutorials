package com.tutorials.andorid.app.handlers;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MyHandlerThreadOne extends Thread {
    private static final String TAG = "MyHandlerThreadOne";

    public Handler mHandler;

    @Override
    public void run() {
        Log.i(TAG, "run: Started the Handler.. Waiting for messages.");
        Looper.prepare();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                workOnMessage(msg);
            }
        };
        Looper.loop();
    }

    private void workOnMessage(Message message) {
        Log.i(TAG, "workOnMessage: I have recieved to work on");
    }

}
