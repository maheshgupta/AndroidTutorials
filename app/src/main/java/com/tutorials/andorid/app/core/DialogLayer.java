package com.tutorials.andorid.app.core;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;


public class DialogLayer extends AppCompatActivity {

    ProgressDialog progressDialog;


    public void showProgress(String title, @NonNull String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }

        progressDialog.setTitle(title);
        progressDialog.setMessage(message);

    }


}
