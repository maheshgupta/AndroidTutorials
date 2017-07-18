package com.tutorials.andorid.app.view.login;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.core.BaseActivity;

public class LoginActivity extends BaseActivity {

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_login;
    }

    @Override
    public String getLogTag() {
        return "LoginActivity";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initUi();
    }

    private void underLineRegister() {
        TextView txtViewRegister = (TextView) findViewById(R.id.txt_view_register);
        if (txtViewRegister != null) {
            txtViewRegister.setText(Html.fromHtml(getResources().getString(R.string.register_now)));
        }
    }

    private void initUi() {

        this.underLineRegister();

        EditText editTextPassword = (EditText) findViewById(R.id.edit_text_password);
        editTextPassword.setOnEditorActionListener(this.passwordDoneListener);
    }


    private TextView.OnEditorActionListener passwordDoneListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
            if (textView.getId() == R.id.edit_text_password && actionId == EditorInfo.IME_ACTION_DONE) {
            }
            return false;
        }
    };


    private void startLogin() {
        Toast.makeText(LoginActivity.this, "Doing Login", Toast.LENGTH_SHORT).show();
    }


    public void doLogin(View view) {
        String txtEmail, txtPassword;
        EditText editTextEmail = (EditText) findViewById(R.id.edit_text_email);
        EditText editTextPwd = (EditText) findViewById(R.id.edit_text_password);
        txtEmail = editTextEmail.getText().toString();
        txtPassword = editTextPwd.getText().toString();
        if (TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)) {
            Toast.makeText(this, "Invalid entries", Toast.LENGTH_SHORT).show();
            return;
        }
        this.startLogin();
    }


    public void goToRegisterPage(View view) {
        startActivity(RegisterActivity.createIntent(LoginActivity.this));
    }


}
