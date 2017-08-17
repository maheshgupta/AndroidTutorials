package com.tutorials.andorid.app.animations;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tutorials.andorid.app.R;

public class AnimationsDemoActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textViewStatus;
    boolean status = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animations_demo);
        imageView = (ImageView) findViewById(R.id.imgview_profile);
        textViewStatus = (TextView) findViewById(R.id.txt_view_status);
    }

    public void startAnimation(View view) {
        ValueAnimator valueAnimator = null;
        if (status) valueAnimator = ValueAnimator.ofFloat(0, 200);
        else valueAnimator = ValueAnimator.ofFloat(200, 0);
//        valueAnimator.setDuration(000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                imageView.setTranslationY((Float) valueAnimator.getAnimatedValue());
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(status == false){
                    textViewStatus.setVisibility(View.VISIBLE);
                }else{
                    textViewStatus.setVisibility(View.INVISIBLE);
                }
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
