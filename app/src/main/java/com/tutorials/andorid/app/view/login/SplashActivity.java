package com.tutorials.andorid.app.view.login;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.core.BaseActivity;

public class SplashActivity extends BaseActivity {


    private FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                dismissProgress();
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                goToDashBoard();

            } else {
                // User is signed out
                dismissProgress();
                Log.d(TAG, "onAuthStateChanged:signed_out");
                gotoLogin();
            }
        }
    };


    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_splash;
    }

    @Override
    public String getLogTag() {
        return "SplashActivity";
    }

    @Override
    protected void onStart() {
        super.onStart();
        showPleaseWait();

        getFireBase().addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (this.mAuthListener != null) {
            getFireBase().removeAuthStateListener(mAuthListener);
        }
    }
}
