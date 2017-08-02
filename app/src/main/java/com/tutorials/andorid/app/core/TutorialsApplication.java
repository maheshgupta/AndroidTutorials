package com.tutorials.andorid.app.core;

import android.app.Application;

import com.facebook.stetho.Stetho;


public class TutorialsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.newInitializerBuilder(this)
                .enableDumpapp(
                        Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(
                        Stetho.defaultInspectorModulesProvider(this))
                .build();
    }
}
