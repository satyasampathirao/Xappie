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
import com.xappie.adapters.AllEventsListAdapter;
import com.xappie.adapters.FindJobsListAdapter;
import com.xappie.models.EntertainmentModel;
import com.xappie.models.JobsModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindJobsListFragment extends Fragment {

    public static final String TAG = FindJobsListFragment.class.getSimpleName();
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

    public FindJobsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_find_jobs_list, container, false);
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
    private void setGridViewData() {

        FindJobsListAdapter findJobsListAdapter = new FindJobsListAdapter(mParent, getJobsData());
        list_view.setAdapter(findJobsListAdapter);
    }

    private ArrayList<JobsModel> getJobsData() {
        ArrayList<JobsModel> jobsModels = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            JobsModel jobsModel = new JobsModel();
            jobsModel.setId(R.drawable.video_hint);
            jobsModel.setTitle("Rarandoi");
           jobsModels.add(jobsModel);
        }
        return jobsModels;
    }

    /**
     * This method is used navigate post
     */
    @OnClick(R.id.fab)
    void navigateToPost() {

    }

}
