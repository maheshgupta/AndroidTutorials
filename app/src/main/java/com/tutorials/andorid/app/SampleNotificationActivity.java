package com.tutorials.andorid.app;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tutorials.andorid.app.view.users.NotificationActivity;
import com.tutorials.andorid.app.view.users.UsersActivity;

public class SampleNotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_notification);
        sendNotification();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void sendNotification() {
        NotificationCompat.Builder notificationBuilder
                = new NotificationCompat.Builder(SampleNotificationActivity.this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Notification cool")
                .setContentText("My Very first Notification");
        Intent resultIntent = new Intent(this, NotificationActivity.class);


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(SampleNotificationActivity.this);
        stackBuilder.addParentStack(NotificationActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(100, notificationBuilder.build());
    }
}
