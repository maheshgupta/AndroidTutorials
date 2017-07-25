package com.tutorials.andorid.app.view.users;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class MyBroadCastReceiever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("MyBroadCastReceiever", "onReceive: MaheshGupta");
        Toast.makeText(context, "Receieved a broadcast", Toast.LENGTH_SHORT).show();
    }
}
