package com.tutorials.andorid.app.tutorials.animations;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BaseInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.tutorials.andorid.app.R;

public class TransitionDemoActivity extends AppCompatActivity {

    ImageView imageView;
    ViewGroup rootView;
    boolean isImageVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_demo);
        this.imageView = (ImageView) findViewById(R.id.img_view_profile_image);
        this.rootView = (ViewGroup) findViewById(R.id.root_view_transition_screen);
    }


    public void showProfile(View view) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            TransitionManager.beginDelayedTransition(this.rootView, new Fade());
//        }
//        toggleProfilePicture();
        makePropertyAnimation();
    }


    private void toggleProfilePicture() {
        this.imageView.setVisibility(isImageVisible ? View.INVISIBLE : View.VISIBLE);
        toggleImageVisibleFlag();
    }

    private void toggleImageVisibleFlag() {
        isImageVisible = !isImageVisible;
    }

    private void makePropertyAnimation() {
        ValueAnimator valueAnimator = null;
        if (isImageVisible) {
            valueAnimator = ValueAnimator.ofInt(0, 400);
        } else {
            valueAnimator = ValueAnimator.ofInt(400, 0);
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Integer animatedValue = (Integer) valueAnimator.getAnimatedValue();
                Log.i("", "onAnimationUpdate: Value : " + animatedValue);
                imageView.setRotation(animatedValue);
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                toggleImageVisibleFlag();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.setDuration(2000);
        valueAnimator.start();
    }


}
