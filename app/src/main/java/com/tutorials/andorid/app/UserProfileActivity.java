package com.tutorials.andorid.app;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;

public class UserProfileActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWindowTransitions();
        setContentView(R.layout.activity_user_profile2);

//        DataBindingUtil.setContentView(this, R.layout.activity_user_profile2);

    }


    private void setWindowTransitions() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Transition transition = new Slide();
            transition.addTarget(R.id.txt_view_lorem);


            getWindow().setEnterTransition(transition);
        }
    }
}
