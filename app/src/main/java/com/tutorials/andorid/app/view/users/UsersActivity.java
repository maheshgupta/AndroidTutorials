package com.tutorials.andorid.app.view.users;

import android.app.ActivityOptions;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.model.user.User;
import com.tutorials.andorid.app.service.NetworkTask;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.ArrayList;

import adapters.UsersAdapter;
import adapters.UsersRecyclerAdapter;

public class UsersActivity extends AppCompatActivity {

    private static final String TAG = "UsersActivity";

    private ArrayList<User> users = null;

    private ListView listView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
//        this.setupWindowAnimations();
        this.listView = (ListView) findViewById(R.id.usersListView);
    }

    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Transition transition = new Slide();
            transition.setDuration(1000);
            getWindow().setExitTransition(transition);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            super.startActivity(intent);
        }
    }

    public void sendEvent(View view) {
        this.sendNotification();
    }


    @Override
    protected void onResume() {
        super.onResume();
        this.pullUsers();
//        this.addReciever();
//        this.sendNotification();
//        sendMyBroadcast();
    }

    private void pullUsers() {
        try {
            Type typeToken = new TypeToken<ArrayList<User>>() {
            }.getType();
            new NetworkTask<ArrayList<User>>("https://jsonplaceholder.typicode.com/users", typeToken) {
                @Override
                public void onSuccess(ArrayList<User> response) {
                    Log.i(TAG, "onSuccess");
                    users = response;
                    UsersActivity.this.renderUsersAsRecyclerView();
                }

                @Override
                public void onError(String error) {
                    Log.i(TAG, "onError: ");
                }
            }.sendRequest(NetworkTask.RequestType.GET);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    private void renderUsersAsListView() {
        if (this.users == null || this.users.size() <= 0) {
            Toast.makeText(this, "No users...", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] userNames = new String[this.users.size()];
        for (int i = 0; i < this.users.size(); i++) {
            userNames[i] = this.users.get(i).getName();
        }

        UsersAdapter adapter = new UsersAdapter(this, this.users);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                showAlbumsActivity(position);
            }
        });
        this.listView.setAdapter(adapter);
    }


    private void renderUsersAsRecyclerView() {
        if (this.users == null || this.users.size() <= 0) {
            Toast.makeText(this, "No users...", Toast.LENGTH_SHORT).show();
            return;
        }
        this.recyclerView = (RecyclerView) findViewById(R.id.usersRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        UsersRecyclerAdapter usersRecyclerAdapter = new UsersRecyclerAdapter(this, this.users);

        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setHasFixedSize(true);
        usersRecyclerAdapter.setCallback(new UsersRecyclerAdapter.Callback() {
            @Override
            public void onItemClick(int position) {
                showAlbumsActivity(position);
            }
        });
        this.recyclerView.setAdapter(usersRecyclerAdapter);
    }

    private void showUserProfileScreen(int index) {
        if (this.users == null || this.users.size() <= 0) return;
        User user = this.users.get(index);
        if (user != null) {
            startActivity(UserProfileActivity.createIntent(UsersActivity.this, user));
        }
    }

    private void addReciever() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.tutorials.mybroadcast");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, "Custom One", Toast.LENGTH_SHORT).show();
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
    }

    private void showAlbumsActivity(int index) {
        if (this.users == null || this.users.size() <= 0) return;
        User user = this.users.get(index);
        if (user != null) {
            startActivity(AlbumsActivity.createIntent(UsersActivity.this, user));
        }
    }

    public void sendMyBroadcast() {
        String action = "com.tutorials.mybroadcast";
        Intent intent = new Intent();
        intent.setAction(action);
        sendBroadcast(intent);
    }


    private void sendNotification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_announcement_black_24dp)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
        Intent resultIntent = new Intent(this, NotificationActivity.class);
        resultIntent.putExtra("KEY", "value");

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        Bundle bundle = new Bundle();
        bundle.putString("KEY", "value");
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1000001, mBuilder.build());
    }


}
