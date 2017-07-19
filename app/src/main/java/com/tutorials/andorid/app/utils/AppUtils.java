package com.tutorials.andorid.app.utils;


import android.util.Log;

import com.tutorials.andorid.app.BuildConfig;

public class AppUtils {

    public static boolean isDebugBuild() {
        return BuildConfig.BUILD_TYPE == "debug";
    }

    public static boolean isReleaseBuild() {
        return BuildConfig.BUILD_TYPE == "release";
    }


    public static void log(String tag, String message) {
        if (isDebugBuild()) {
            Log.i(tag, message);
        }
    }

}
