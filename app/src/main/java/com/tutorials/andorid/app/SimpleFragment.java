package com.tutorials.andorid.app;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SimpleFragment extends Fragment {

    private static final String TAG = "SimpleFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, TAG + " onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, TAG + " onCreateView: ");
        return inflater.inflate(R.layout.simple_fragment, container, false);
    }

    @Override
    public void onAttach(Context context) {
        Log.i(TAG, TAG + " onAttach: ");
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        Log.i(TAG, TAG + " onResume: ");
        super.onResume();
    }
}
