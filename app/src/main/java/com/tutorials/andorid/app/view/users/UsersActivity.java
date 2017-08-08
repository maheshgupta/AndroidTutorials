package com.tutorials.andorid.app.view.users;

import android.app.ActivityOptions;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.tutorials.andorid.app.NotificationMessageActivity;
import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.core.BaseActivity;
import com.tutorials.andorid.app.model.user.User;
import com.tutorials.andorid.app.retrofit.jsonplaceholder.UsersService;
import com.tutorials.andorid.app.service.NetworkTask;
import com.tutorials.andorid.app.services.MyService;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import adapters.UsersAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersActivity extends BaseActivity {

    private static final String TAG = "UsersActivity";

    private ArrayList<User> users = null;

    private ListView listView;


    //MVC
    //mvp
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
//        this.setupWindowAnimations();
        this.listView = (ListView) findViewById(R.id.usersListView);
        this.pullUsers();


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


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void pullUsersNative() {
        try {
            Type typeToken = new TypeToken<ArrayList<User>>() {
            }.getType();
            new NetworkTask<ArrayList<User>>("https://jsonplaceholder.typicode.com/users", typeToken) {
                @Override
                public void onSuccess(ArrayList<User> response) {
                    Log.i(TAG, "onSuccess");
                    users = response;
                    UsersActivity.this.renderUsers();
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


    private void pullUsers() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsersService usersService = retrofit.create(UsersService.class);
        Call<List<User>> callUsers = usersService.getUsers();
        if (callUsers != null) {
            callUsers.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    users = (ArrayList<User>) response.body();
                    UsersActivity.this.renderUsers();

                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {

                }
            });
        } else {
            Log.i(TAG, "pullUsers: Error");
        }
    }
    
    private void pullUser(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsersService usersService = retrofit.create(UsersService.class);
        Call<User> user = usersService.getUser(1);
        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i(TAG, "onResponse: sucess");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
            }
        });
    }
    


    private void renderUsers() {
        if (this.users == null || this.users.size() <= 0) {
            Toast.makeText(this, "No users...", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] userNames = new String[this.users.size()];
        for (int i = 0; i < this.users.size(); i++) {
            userNames[i] = this.users.get(i).getName();
        }

//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, userNames);

        UsersAdapter adapter = new UsersAdapter(this, this.users);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                showAlbumsActivity(position);
            }
        });
        this.listView.setAdapter(adapter);
    }


    private void showUserProfileScreen(int index) {
        if (this.users == null || this.users.size() <= 0) return;
        User user = this.users.get(index);
        if (user != null) {
            startActivity(UserProfileActivity.createIntent(UsersActivity.this, user));
        }
    }


    private void showAlbumsActivity(int index) {
        if (this.users == null || this.users.size() <= 0) return;
        User user = this.users.get(index);
        if (user != null) {
            startActivity(AlbumsActivity.createIntent(UsersActivity.this, user));
        }
    }

    public void sendEvent(View view) {
        this.startMyService();

    }


    private void startMyService() {
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }


    private void sendBroadCast() {
//        String action = "com.sample.mybroadcast";
//        Intent intent = new Intent();
//        intent.setAction(action);
//        sendBroadcast(intent);
    }


    private void sendNotification() {
        Intent resultIntent = new Intent(this, NotificationMessageActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationMessageActivity.class);
        stackBuilder.addNextIntent(resultIntent);


        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_assignment_late_black_24dp) //Icon
                        .setContentTitle("My notification") //Title
                        .setContentText("Hello World!");//Message

        mBuilder.setContentIntent(resultPendingIntent); //Action, when user tapped on Notification.


        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("Event tracker details:");
        for (int i = 0; i < 4; i++) {

            inboxStyle.addLine("Event - " + i);
        }
        mBuilder.setStyle(inboxStyle);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1000001, mBuilder.build());
    }


}
