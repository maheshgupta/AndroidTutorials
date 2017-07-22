package com.tutorials.andorid.app.core;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

import com.tutorials.andorid.app.HomeActivity;


public class BaseActivity extends AppCompatActivity {


    private ProgressDialog progressDialog;

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
}
