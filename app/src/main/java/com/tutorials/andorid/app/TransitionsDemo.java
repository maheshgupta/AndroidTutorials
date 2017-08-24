package com.tutorials.andorid.app;

import android.app.ActivityOptions;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;

import com.tutorials.andorid.app.databinding.ActivityTransitionsDemoBinding;

import config.AppConfig;

public class TransitionsDemo extends AppCompatActivity {

    private ActivityTransitionsDemoBinding binding;

    private boolean isImageViewVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_transitions_demo);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_transitions_demo);
        if (BuildConfig.BUILD_TYPE.equalsIgnoreCase("debug")) {
            binding.btnShowAnimation.setText("DEBUG BUILD");
        } else {
            binding.btnShowAnimation.setText("RELEASE BUILD");
        }

        if (AppConfig.isLoggingEnabled){
            Log.i("", "onCreate: ");
        }
    }

    public void showAnimation(View view) {

        Intent intent = new Intent(this, UserProfileActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, binding.imgViewProfile, getString(R.string.transition_photo)).toBundle());
        } else {
            startActivity(intent);
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Transition transition = new Slide();
//            TransitionManager.beginDelayedTransition(binding.rootView, transition);
//        }
//        binding.imgViewProfile.setVisibility(isImageViewVisible ? View.GONE : View.VISIBLE);
//        toggleFlag();
    }

    private void toggleFlag() {
        isImageViewVisible = !isImageViewVisible;
    }

}
