package com.tutorials.andorid.app;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SampleFragment extends Fragment {

    private View fragmentView;



    private static final String PARAM = "PARAM";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static SampleFragment getInstance(String param) {
        Bundle bundle = new Bundle();
        bundle.putString(PARAM, param);

        SampleFragment sampleFragment = new SampleFragment();
        sampleFragment.setArguments(bundle);

        return sampleFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.simple_fragment, container, false);
        TextView txtViewParam = fragmentView.findViewById(R.id.txtView_fragment_param);

        String param = getArguments().getString(PARAM, "Default Value");
        txtViewParam.setText(param);
        return fragmentView;
    }
}
