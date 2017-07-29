package com.xappie.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shankar Pilli on 07/28/2017
 */

public class AllMyEventsFragment extends Fragment {

    public static final String TAG = AllMyEventsFragment.class.getSimpleName();
    private DashBoardActivity mParent;

    /**
     * AllEvents List set up
     */
    @BindView(R.id.listView)
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_my_events, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {

    }

}
