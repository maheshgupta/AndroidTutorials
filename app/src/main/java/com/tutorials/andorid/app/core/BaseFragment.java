package com.tutorials.andorid.app.core;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tutorials.andorid.app.utils.AppUtils;

public abstract class BaseFragment extends Fragment {

    protected final String TAG = getLogTag();

    private View fragmentView;

    public abstract int getLayoutResourceId();

    public abstract String getLogTag();

    public void log(String message) {
        AppUtils.log(TAG, message);
    }

    /**
     * Called, after inflation. Handy place for view elements initialization.
     */
    public void performInitialization(View rootView) {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.fragmentView = inflater.inflate(getLayoutResourceId(), container, false);
        performInitialization(this.fragmentView);
        return this.fragmentView;
    }


    public View getFragmentView() {
        return fragmentView;
    }
}
