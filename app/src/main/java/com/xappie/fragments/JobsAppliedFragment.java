package com.xappie.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.JobsAppliedAdapter;
import com.xappie.models.JobsModel;
import com.xappie.models.MyAppliedJobsModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobsAppliedFragment extends Fragment {

    public static final String TAG = JobsAppliedFragment.class.getSimpleName();
    private DashBoardActivity mParent;

    @BindView(R.id.list_jobs_applied)
    ListView lv_jobs_applied;
    @BindView(R.id.tv_no_job_applied)
    TextView tv_no_job_applied;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceOpenSansBold;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
    }

    public JobsAppliedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_jobs_applied, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }
    private void initUI() {
        setTypeFace();
    }
    private void setTypeFace() {

        JobsAppliedAdapter  jobsAppliedAdapter = new JobsAppliedAdapter(mParent,getJobsData());
        lv_jobs_applied.setAdapter(jobsAppliedAdapter);

    }

    private ArrayList<MyAppliedJobsModel> getJobsData() {
        ArrayList<MyAppliedJobsModel> myAppliedJobsModels = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            MyAppliedJobsModel myAppliedJobsModel = new MyAppliedJobsModel();
            myAppliedJobsModel.setId(R.drawable.video_hint);
            myAppliedJobsModel.setTitle("Rarandoi");
            myAppliedJobsModels.add(myAppliedJobsModel);
        }
        return myAppliedJobsModels;
    }
}
