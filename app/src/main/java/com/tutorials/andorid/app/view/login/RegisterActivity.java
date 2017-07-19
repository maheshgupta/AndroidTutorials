package com.tutorials.andorid.app.view.login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.core.BaseActivity;

public class RegisterActivity extends BaseActivity {

    private FirebaseAuth firebase = FirebaseAuth.getInstance();

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_register;
    }

    @Override
    public String getLogTag() {
        return "RegisterActivity";
    }


    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    public void doRegister(View view) {
        String txtEmail, txtPassword;
        EditText editTextEmail = (EditText) findViewById(R.id.edit_text_email);
        EditText editTextPwd = (EditText) findViewById(R.id.edit_text_password);
        txtEmail = editTextEmail.getText().toString();
        txtPassword = editTextPwd.getText().toString();
        if (TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)) {
            Toast.makeText(this, "Invalid entries", Toast.LENGTH_SHORT).show();
            return;
        }
        showPleaseWait();
        firebase.createUserWithEmailAndPassword(txtEmail, txtPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                dismissProgress();
                Toast.makeText(RegisterActivity.this, getResources().getString(R.string.register_success), Toast.LENGTH_SHORT).show();
                gotoLogin();
            }
        }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                dismissProgress();
                log("Failed to register : " + task.getResult().toString());
            }
        });
    }


}
