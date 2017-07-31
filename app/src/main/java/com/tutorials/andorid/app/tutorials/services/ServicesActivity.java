package com.tutorials.andorid.app.tutorials.services;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.tutorials.BaseActivity;

public class ServicesActivity extends BaseActivity {

    private static final String TAG = "ServicesActivity";


    RestfulBoundService.RestfulBinder restfulBinder;
    RestfulBoundService restfulBoundService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
    }


    public void startStatedService(View view) {
        Intent intent = new Intent(this, RestfulStartedService.class);
        startService(intent);
    }

    public void stopStartedService(View view) {
        Intent intent = new Intent(this, RestfulStartedService.class);
        stopService(intent);
    }

    public void startBoundService(View view) {
        if (restfulBoundService == null) {
            Intent intent = new Intent(this, RestfulBoundService.class);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        } else {
            restfulBoundService.postMessage(Message.obtain());
        }
    }

    public void stopBoundService(View view) {
        if (restfulBoundService != null) {
            unbindService(mConnection);
            restfulBoundService = null;
        }
    }


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(TAG, "onServiceConnected: ");
            restfulBinder = (RestfulBoundService.RestfulBinder) iBinder;
            restfulBoundService = restfulBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG, "onServiceDisconnected: ");
        }
    };
}
