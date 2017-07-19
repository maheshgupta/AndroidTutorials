package com.tutorials.andorid.app.view.dashboard;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.core.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends BaseFragment {


    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_cart;
    }

    @Override
    public String getLogTag() {
        return "CartFragment";
    }
}
