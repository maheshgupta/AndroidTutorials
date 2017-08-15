package com.tutorials.andorid.app.tutorials;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tutorials.andorid.app.R;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_CONTACTS;

public class PermissionsDemoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private String[] permissionsList = {
            ACCESS_COARSE_LOCATION,
            ACCESS_FINE_LOCATION,
            READ_CONTACTS,
    };
    private Boolean[] checkedItems = new Boolean[permissionsList.length];
    private static final int REQ_CODE_PERMISSIONS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions_demo);
        this.initListView();
        for (int i = 0; i < this.checkedItems.length; i++) {
            this.checkedItems[i] = false;
        }
    }


    private void initListView() {
        ListView listView = (ListView) findViewById(R.id.list_view_permissions);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, permissionsList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView textView = (CheckedTextView) view;
                textView.setChecked(!textView.isChecked());
                checkedItems[i] = textView.isChecked();
            }
        });

        listView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
        String permission = null;
        switch (index) {
            case 0:
                checkForPermissions(new String[]{READ_CONTACTS});
                break;
            case 1:
                break;
        }
    }

    private void checkForPermissions() {



        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.checkedItems.length; i++) {
            if (this.checkedItems[i]) {
                String permission = this.permissionsList[i];
                if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                    stringBuilder.append(permission + " - GRANTED " + "\n");
                } else {
                    stringBuilder.append(permission + " - NOT GRANTED " + "\n");
                }
            }
        }
        printStatus(stringBuilder.toString());
    }

    private void requestForPermssions() {
        final ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < this.checkedItems.length; i++) {
            if (this.checkedItems[i]) {
                String permission = this.permissionsList[i];
                list.add(permission);
            }
        }
        if (list.size() > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to request for permissions?");
            builder.setCancelable(false);

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    _requestForPermissions(list);
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }
    }

    private void _requestForPermissions(ArrayList<String> list) {
        if (list.size() > 0) {
            String[] permissions = list.toArray(new String[0]);
            ActivityCompat.requestPermissions(this, permissions, REQ_CODE_PERMISSIONS);
        }
    }


    private void checkForPermissions(String[] permissions) {
        if (permissions != null && permissions.length > 0) {
        }
    }


    private void printStatus(String status) {
        TextView statusView = (TextView) findViewById(R.id.txt_view_status);
        if (statusView != null) {
            statusView.setText(status);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.permissions_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_permission_approve:
                requestForPermssions();
                break;
            case R.id.menu_permssion_check:
                checkForPermissions();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQ_CODE_PERMISSIONS) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    stringBuilder.append(permissions[i] + " - GRANTED " + "\n");
                } else {
                    stringBuilder.append(permissions[i] + " - NOT GRANTED " + "\n");
                }
            }
            printStatus(stringBuilder.toString());
        }
    }
}
