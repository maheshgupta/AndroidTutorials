package com.tutorials.andorid.app;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ActivityTwo extends AppCompatActivity {

    public static final String PARAM = "PARAM";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        String fromHomeActivity = getIntent().getStringExtra(PARAM);

        TextView textView = (TextView) findViewById(R.id.txtview_param);
        textView.setText(fromHomeActivity);
    }



}
