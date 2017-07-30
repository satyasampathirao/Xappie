package com.xappie.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.AllEventsListAdapter;
import com.xappie.models.EntertainmentModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by Shankar Pilli on 07/28/2017
 */
public class AllEventsListFragment extends Fragment {

    public static final String TAG = AllEventsListFragment.class.getSimpleName();
    private DashBoardActivity mParent;

    /**
     * AllEvents List set up
     */
    @BindView(R.id.list_view)
    ListView list_view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_events_list, container, false);
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
        AllEventsListAdapter allEventsListAdapter = new AllEventsListAdapter(mParent, getEntertainData());
        list_view.setAdapter(allEventsListAdapter);
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

    /**
     * This method is used navigate post
     */
    @OnClick(R.id.fab)
    void navigateToPost() {
        Utility.navigateAllEventsFragment(new AddNewEventFragment(), AddNewEventFragment.TAG, null, mParent);
    }

    /*@OnItemClick(R.id.list_view)
    void onItemClick(int position) {
        Utility.navigateAllEventsFragment(new EventDetailViewFragment(), EventDetailViewFragment.TAG, null, mParent);
    }*/
}

