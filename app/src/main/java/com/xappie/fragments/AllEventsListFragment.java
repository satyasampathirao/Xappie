package com.xappie.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.ClassifiedsListAdapter;
import com.xappie.models.EntertainmentModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shankar Pilli on 07/28/2017
 */
public class AllEventsListFragment extends Fragment {

    public static final String TAG = AllEventsListFragment.class.getSimpleName();
    private DashBoardActivity mParent;

    /**
     * AllEvents List set up
     */
    @BindView(R.id.listView)
    SwipeMenuListView listView;

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
        setGridViewData();
    }

    /*This method is used to set the lsit view data*/
    private void setGridViewData() {
        ClassifiedsListAdapter classifiedsListAdapter = new ClassifiedsListAdapter(mParent, getEntertainData());
        listView.setAdapter(classifiedsListAdapter);
    }

    private ArrayList<EntertainmentModel> getEntertainData() {
        ArrayList<EntertainmentModel> entertainmentModels = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            EntertainmentModel entertainmentModel = new EntertainmentModel();
            entertainmentModel.setId(R.drawable.video_hint);
            entertainmentModel.setTitle("Rarandoi");
            entertainmentModels.add(entertainmentModel);
        }
        return entertainmentModels;
    }

}

