package com.tutorials.andorid.app.tutorials;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.animations.AnimationsDemoActivity;
import com.tutorials.andorid.app.tutorials.contentproviders.ContentProvidersActivity;
import com.tutorials.andorid.app.tutorials.locations.LocationsDemoActivity;
import com.tutorials.andorid.app.tutorials.locations.MapsDemoActivity;
import com.tutorials.andorid.app.tutorials.services.ServicesActivity;
import com.tutorials.andorid.app.tutorials.sharedprefs.SharedPrefsDemo;
import com.tutorials.andorid.app.tutorials.sqlite.ToDoHomeActivity;

public class HomePageActivity extends AppCompatActivity {

    String[] contents;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        initValues();
        initViews();
    }


    private void initViews() {
        this.listView = (ListView) findViewById(R.id.list_view_items);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.contents);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemClick(i);
            }
        });
        this.listView.setAdapter(adapter);
    }

    private void itemClick(int itemNumber) {
        Intent intent = new Intent();
        switch (itemNumber) {
            case 2:
                intent.setClass(this, ServicesActivity.class);
                break;
            case 7:
                intent.setClass(this, ContentProvidersActivity.class);
                break;
            case 8:
                intent.setClass(this, SharedPrefsDemo.class);
                break;
            case 9:
                intent.setClass(this, ToDoHomeActivity.class);
                break;
            case 10:
                intent.setClass(this, PermissionsDemoActivity.class);
                break;
            case 11:
                intent.setClass(this, LocationsDemoActivity.class);
                break;
            case 12:
                intent.setClass(this, MapsDemoActivity.class);
                break;
            case 13:
                intent.setClass(this, AnimationsDemoActivity.class);
                break;
            default:
                Toast.makeText(this, "Item not supported yet..", Toast.LENGTH_SHORT).show();
                return;
        }
        startActivity(intent);
    }


    private void initValues() {
        if (this.contents == null) {
            this.contents = getResources().getStringArray(R.array.home_page_modules);
        }
    }


    private void animationSample() {

    }

}
