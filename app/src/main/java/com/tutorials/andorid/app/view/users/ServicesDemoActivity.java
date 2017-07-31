package com.tutorials.andorid.app.view.users;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.handlers.MyHandlerThread;
import com.tutorials.andorid.app.handlers.MyHandlerThreadOne;
import com.tutorials.andorid.app.services.MyService;

public class ServicesDemoActivity extends AppCompatActivity {

    private Handler handler;
    private MyHandlerThreadOne myHandlerThreadOne;

    private MyHandlerThread handlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_demo);
    }

    public void makeStartedService(View view) {
//        Intent intent = new Intent(this, MyService.class);
//        startService(intent);
//        this.myHandlerThreadOne = new MyHandlerThreadOne();
//        myHandlerThreadOne.start();

        handlerThread = new MyHandlerThread();
        handlerThread.start();
    }

    public void stopStartedService(View view) {
        Message message = Message.obtain();
//        this.myHandlerThreadOne.mHandler.sendMessage(message);
        this.handlerThread.postTask(new Runnable() {
            @Override
            public void run() {
                Log.i("", "run: Called the runnable");
            }
        });
    }


    public void makeBindService(View view) {

    }

}
