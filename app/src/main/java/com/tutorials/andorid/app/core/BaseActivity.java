package com.tutorials.andorid.app.core;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.tutorials.andorid.app.utils.AppUtils;
import com.tutorials.andorid.app.view.dashboard.DashboardActivity;
import com.tutorials.andorid.app.view.login.LoginActivity;


public abstract class BaseActivity extends DialogLayer {

    protected final String TAG = getLogTag();

    public abstract int getLayoutResourceId();

    public abstract String getLogTag();


    public void log(String message) {
        AppUtils.log(TAG, message);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
    }


    public void gotoLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goToDashBoard() {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    public FirebaseAuth getFireBase() {
        return FirebaseAuth.getInstance();
    }


}
