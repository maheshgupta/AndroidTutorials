package com.tutorials.andorid.app.view.users;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.model.user.User;

public class UserProfileActivity extends AppCompatActivity {


    private static final String SELECTED_USER = "SELECTED_USER";

    private User user;


    public static Intent createIntent(@NonNull Context context, @NonNull User user) {
        Intent intent = new Intent(context, UserProfileActivity.class);
        intent.putExtra(SELECTED_USER, user);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        this.user = getIntent().getParcelableExtra(SELECTED_USER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Selected User : " + this.user.getName(), Toast.LENGTH_SHORT).show();
    }
}
