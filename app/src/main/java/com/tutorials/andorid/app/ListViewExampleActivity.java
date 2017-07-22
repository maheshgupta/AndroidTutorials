package com.tutorials.andorid.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tutorials.andorid.app.core.BaseActivity;
import com.tutorials.andorid.app.model.User;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ListViewExampleActivity extends BaseActivity {

    ListView listView;

    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_example);
        this.listView = (ListView) findViewById(R.id.lstView);
    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            this.connectToNetworkWithASyncTask();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    private void connectToNetworkWithASyncTask() throws MalformedURLException {
        String url = "https://jsonplaceholder.typicode.com/users";
        NetworkUserTask<ArrayList<User>> task = new NetworkUserTask<>(url, NetworkConnection.RequestType.GET, new NetworkConnection.Callback() {
            @Override
            public void onSuccess(Object response) {
                users = (ArrayList<User>) response;
                renderListView();
            }

            @Override
            public void onError(String error) {

            }
        });
        task.execute();
    }


    public void connectToNetworkNativeThreads() {
        String url = "https://jsonplaceholder.typicode.com/users";
        try {
            NetworkConnection connection = new NetworkConnection<ArrayList<User>>(url);
            connection.makeRequest(NetworkConnection.RequestType.GET, new NetworkConnection.Callback() {
                @Override
                public void onSuccess(Object response) {
                    users = (ArrayList<User>) response;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            renderListView();
                        }
                    });
//                    renderListView();

                }

                @Override
                public void onError(String error) {
                    Toast.makeText(ListViewExampleActivity.this, "Unknown error, Please try again later...", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void renderListView() {
        String[] names = {"Leanne Graham",
                "Romaguera-Crona",
                "Ervin Howell",
                "Deckow-Crist",
                "Clementine Bauch",
                "Romaguera-Jacobson",
                "Patricia Lebsack",
                "Robel-Corkery",
                "Chelsey Dietrich",
                "Keebler LLC",
                "Mrs. Dennis Schulist",
                "Considine-Lockman",
                "Kurtis Weissnat"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        this.listView.setAdapter(adapter);
    }


}
