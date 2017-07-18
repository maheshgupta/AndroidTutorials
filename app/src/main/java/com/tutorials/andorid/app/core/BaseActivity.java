package com.tutorials.andorid.app.core;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public abstract class BaseActivity extends DialogLayer {

    protected final String TAG = getLogTag();

    public abstract int getLayoutResourceId();

    public abstract String getLogTag();


    public void log(String message) {
        Log.i(TAG, message);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
    }




}
