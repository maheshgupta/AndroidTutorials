package com.tutorials.andorid.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }



    public void goToAnotherScreen(View view) {
//        EditText editText = (EditText) findViewById(R.id.editSomeText);
//        Intent intent = new Intent(HomeActivity.this, ActivityTwo.class);
//        intent.putExtra(ActivityTwo.PARAM, editText.getText().toString());
//        startActivity(intent);
        new Thread(new MyThread()).start();
    }


    private class MyThread implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ImageView imageView = (ImageView) findViewById(R.id.imgView);
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.image02));
                }
            });


        }
    }


}
