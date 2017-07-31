package com.tutorials.andorid.app.view.users;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.tutorials.andorid.app.R;

public class NotificationActivity extends AppCompatActivity {

    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        String key = getIntent().getStringExtra("KEY");
        Log.i(TAG, "onCreate: Key : " + key);
    }


    private void renderUi() {
        TextView txtViewMessage = (TextView) findViewById(R.id.text_view_message);
        txtViewMessage.setText("Notification message is ");
    }

}
