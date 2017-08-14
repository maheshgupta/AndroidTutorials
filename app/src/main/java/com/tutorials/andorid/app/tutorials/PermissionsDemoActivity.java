package com.tutorials.andorid.app.tutorials;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tutorials.andorid.app.R;

public class PermissionsDemoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private String[] permissionsList = {
            "LOCATIONS",
            "CONTACTS"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions_demo);
        this.initListView();
    }


    private void initListView() {
        ListView listView = (ListView) findViewById(R.id.list_view_permissions);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, permissionsList);
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
        String permission = null;
        switch (index){
            case 0:

                break;
            case 1:
                break;
        }

    }
}
