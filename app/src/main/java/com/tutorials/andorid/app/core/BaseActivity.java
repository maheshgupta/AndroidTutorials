package com.tutorials.andorid.app.core;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tutorials.andorid.app.HomeActivity;
import com.tutorials.andorid.app.MyBroadCastReciever;


public class BaseActivity extends AppCompatActivity {


    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.registerBroadReciever();
    }

    public void showProgress(String title, String message) {
        if (this.progressDialog == null) {
            this.progressDialog = new ProgressDialog(this);
        }
        if (this.progressDialog.isShowing()) this.progressDialog.dismiss();


        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.show();
    }


    public void dismissDialog() {
        if (this.progressDialog != null && this.progressDialog.isShowing()) {
            this.progressDialog.dismiss();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        dismissDialog();
    }

    public void sendLogOutEvent(){
        String action = "com.myapp.logout.action";
        Intent intent = new Intent();
        intent.setAction(action);
        sendBroadcast(intent);
    }


    private void registerBroadReciever() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.myapp.logout.action");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i("", "onReceive: Im cleaning up ");
                finish();
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("", "onDestroy: ");
    }
}
