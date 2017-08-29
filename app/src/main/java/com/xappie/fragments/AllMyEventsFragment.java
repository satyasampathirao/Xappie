package com.xappie.fragments;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.AllEventsListAdapter;
import com.xappie.adapters.AllMyEventsListAdapter;
import com.xappie.models.EntertainmentModel;

import java.util.ArrayList;

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
        setGridViewData();
    }

    /*This method is used to set the lsit view data*/
    private void setGridViewData() {
        AllMyEventsListAdapter allEventsListAdapter = new AllMyEventsListAdapter(mParent, getEntertainData());
        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xAD, 0x24,
                        0x3E)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Edit");
                // set item title fontsize
                openItem.setTitleSize(15);
                openItem.setIcon(R.drawable.ic_mode_edit_white_24dp);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);


                // create "open" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xEF, 0x4E,
                        0x54)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set item title
                deleteItem.setTitle("Delete");
                // set item title fontsize
                deleteItem.setTitleSize(15);
                deleteItem.setIcon(R.drawable.ic_delete_sweep_white_24dp);
                // set item title font color
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        listView.setMenuCreator(creator);
        listView.setAdapter(allEventsListAdapter);
    }

    private ArrayList<EntertainmentModel> getEntertainData() {
        ArrayList<EntertainmentModel> entertainmentModels = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            EntertainmentModel entertainmentModel = new EntertainmentModel();
            //entertainmentModel.setId(R.drawable.video_hint);
            entertainmentModel.setTitle("Rarandoi");
            entertainmentModels.add(entertainmentModel);
        }
        return entertainmentModels;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

}
