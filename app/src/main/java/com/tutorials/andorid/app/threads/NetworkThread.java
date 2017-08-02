package com.tutorials.andorid.app.threads;


import android.os.Message;
import android.util.Log;

public class NetworkThread extends AppThread {

    public static final int PULL_USERS = 1;

    private static final String TAG = "NetworkThread";

    @Override
    protected void workOnMessage(Message message) {
        Log.i(TAG, "NetworkThread: I got some task");
        if (message.arg1 == PULL_USERS) {

        }
    }
}
