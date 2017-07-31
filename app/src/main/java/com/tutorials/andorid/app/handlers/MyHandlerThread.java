package com.tutorials.andorid.app.handlers;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;


public class MyHandlerThread extends HandlerThread {

    private static final String TAG = "MyHandlerThread";

    private static int threadCount = 0;

    Handler handler;

    public MyHandlerThread() {
        super(TAG + threadCount);
        threadCount++;
    }

    public Handler getHandler() {
        return this.handler;
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        handler = new Handler(getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                handleTheMessage(msg);
            }


        };
    }


    public void postMessage(Message message) {
        handler.sendMessage(message);
    }

    public void postTask(Runnable task) {
        handler.post(task);
    }


    private void handleTheMessage(Message message) {
        Log.i(TAG, "handleTheMessage: I have got the task to work on.. Please dont disturb ;)");
    }
}
