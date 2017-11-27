package com.xappie.fragments;


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
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.Model;
import com.xappie.models.MyAppliedJobsModel;
import com.xappie.parser.MyAppliedJobsParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobsAppliedFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = JobsAppliedFragment.class.getSimpleName();
    private DashBoardActivity mParent;

    @BindView(R.id.list_jobs_applied)
    ListView list_jobs_applied;
    @BindView(R.id.tv_no_job_applied)
    TextView tv_no_job_applied;

    private JobsAppliedAdapter mJobAppliedAdapter;
    private ArrayList<MyAppliedJobsModel> mMyAppliedJobsModelsList;
    private boolean endScroll = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
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
        getAppliedJobs();
    }

    private void getAppliedJobs() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        MyAppliedJobsParser mMyAppliedJobsParser = new MyAppliedJobsParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(), Utility.getResourcesString(getActivity(),
                R.string.please_wait), true,
                APIConstants.MY_APPLIED_JOBS_URL, paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mMyAppliedJobsParser);
        Utility.execute(serverIntractorAsync);
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model.isStatus()) {
                if (model instanceof MyAppliedJobsModel) {
                    MyAppliedJobsModel mMyAppliedJobsModel = (MyAppliedJobsModel) model;
                    if (mMyAppliedJobsModelsList == null) {
                        if (mMyAppliedJobsModel.getMyAppliedJobsModels() == null) {
                            tv_no_job_applied.setVisibility(View.VISIBLE);
                            list_jobs_applied.setVisibility(View.GONE);
                        } else {
                            tv_no_job_applied.setVisibility(View.GONE);
                            list_jobs_applied.setVisibility(View.VISIBLE);
                            if (mMyAppliedJobsModelsList == null) {
                                mMyAppliedJobsModelsList = new ArrayList<>();
                            }
                            mMyAppliedJobsModelsList.addAll(mMyAppliedJobsModel.getMyAppliedJobsModels());
                            if (mJobAppliedAdapter == null) {
                                setDataToList();
                            }
                        }
                    } else {
                        list_jobs_applied.setVisibility(View.VISIBLE);
                        tv_no_job_applied.setVisibility(View.GONE);
                        if (mMyAppliedJobsModel.getMyAppliedJobsModels() != null && mMyAppliedJobsModel.getMyAppliedJobsModels().size() > 0) {
                            mMyAppliedJobsModelsList.addAll(mMyAppliedJobsModel.getMyAppliedJobsModels());
                            if (mJobAppliedAdapter == null) {
                                setDataToList();
                            } else {
                                mJobAppliedAdapter.notifyDataSetChanged();
                            }
                        } else {
                            endScroll = true;
                        }
                    }
                }
            }
        }
    }

    private void setDataToList() {
        mJobAppliedAdapter = new JobsAppliedAdapter(mParent, mMyAppliedJobsModelsList);
        list_jobs_applied.setAdapter(mJobAppliedAdapter);
    }
}
