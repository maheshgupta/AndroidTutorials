package com.tutorials.andorid.app.tutorials.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DummyService extends Service {
    public DummyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
