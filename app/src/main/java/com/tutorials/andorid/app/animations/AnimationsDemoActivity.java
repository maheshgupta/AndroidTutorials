package com.tutorials.andorid.app.animations;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tutorials.andorid.app.R;

public class AnimationsDemoActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textViewStatus;
    boolean status = true;
    ViewGroup rootView;
    Button btnStartAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animations_demo);
        imageView = (ImageView) findViewById(R.id.imgview_profile);
        textViewStatus = (TextView) findViewById(R.id.txt_view_status);
        rootView = (ViewGroup) findViewById(R.id.root_view);
        btnStartAnimation = (Button) findViewById(R.id.btn_start_animation);
    }

    public void startAnimation(View view) {
//        makeObjectAnimation();
//        makeValueAnimation();
//        startXmlAnimation();
        makeTransition();
    }


    private void makeTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            /**/
            TransitionManager.beginDelayedTransition(rootView);
            imageView.setVisibility(status ? View.GONE : View.VISIBLE);
            status = !status;
        }
    }


    private void startXmlAnimation() {
        AnimatorSet animator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.bounce_animation_profile);
        animator.setTarget(imageView);
        animator.start();
    }

    private void makeObjectAnimation() {
        ObjectAnimator objectAnimator = status ? ObjectAnimator.ofFloat(imageView, "translationY", 0, 300) : ObjectAnimator.ofFloat(imageView, "translationY", 300, 0);

        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                status = !status;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        objectAnimator.start();
    }


    private void makeValueAnimation() {
        ValueAnimator valueAnimator = status ? ValueAnimator.ofFloat(0, 400) : ValueAnimator.ofFloat(400, 0);

        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(20);

        valueAnimator.setInterpolator(new BounceInterpolator());

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                long t = valueAnimator.getCurrentPlayTime();

                imageView.setTranslationY((Float) valueAnimator.getAnimatedValue());
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                status = !status;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });


        valueAnimator.start();
    }

}
