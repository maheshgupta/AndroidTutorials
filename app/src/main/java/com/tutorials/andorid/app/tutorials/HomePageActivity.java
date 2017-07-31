package com.tutorials.andorid.app.tutorials;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.tutorials.services.ServicesActivity;

import java.util.ArrayList;

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
            case 0:
                break;
            case 1:
                break;
            case 2:
                intent.setClass(this, ServicesActivity.class);
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

}
