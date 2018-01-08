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
import android.widget.LinearLayout;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.AllMyJobsListAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.IAmGoingModel;
import com.xappie.models.JobsListModel;
import com.xappie.models.JobsModel;
import com.xappie.models.Model;
import com.xappie.parser.IAmGoingParser;
import com.xappie.parser.JobsListParser;
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
public class AllMyJobsFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = AllMyJobsFragment.class.getSimpleName();
    private DashBoardActivity mParent;

    @BindView(R.id.listView)
    SwipeMenuListView listView;
    @BindView(R.id.ll_no_data)
    LinearLayout ll_no_data;
    private int mDeletePosition = -1;

    private JobsListModel jobsListModel;
    private AllMyJobsListAdapter allmyJobsListAdapter;
    private IAmGoingModel iAmGoingModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        Utility.sendGoogleAnalytics(mParent, TAG);
    }


    public AllMyJobsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_all_my_jobs, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {
        getMyJobsData("" + 1);
    }

    private void getMyJobsData(String pageNo) {
        try {


            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            //linkedHashMap.put("type", "Public");
            linkedHashMap.put(Constants.PAGE_NO, pageNo);
            linkedHashMap.put(Constants.PAGE_SIZE, Constants.PAGE_SIZE_VALUE);
            JobsListParser jobsListParser = new JobsListParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_MY_JOBS, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, jobsListParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getDeleteData(String delete_id) {
        try {

            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            IAmGoingParser eventsListParser = new IAmGoingParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.DELETE_JOB + delete_id, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, eventsListParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setGridViewData() {
        allmyJobsListAdapter = new AllMyJobsListAdapter(mParent, jobsListModel.getJobsModels());

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0xFF,
                        0xFF)));
                // set item width
                openItem.setWidth(dp2px(70));
                // set item title
                // openItem.setTitle("Edit");
                // set item title fontsize
                openItem.setTitleSize(15);
                openItem.setIcon(R.drawable.edit);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);


                // create "open" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0xFF,
                        0xFF)));
                // set item width
                deleteItem.setWidth(dp2px(70));
                // set item title
                // deleteItem.setTitle("Delete");
                // set item title fontsize
                deleteItem.setTitleSize(15);
                deleteItem.setIcon(R.drawable.delete);
                // set item title font color
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        listView.setMenuCreator(creator);
        listView.setAdapter(allmyJobsListAdapter);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.JOBS_ID, String.valueOf(jobsListModel.getJobsModels().get(position).getId()));
                        Utility.navigateAllJobsFragment(new PostJobFragment(), PostJobFragment.TAG, bundle, mParent);
                        break;
                    case 1:
                        mDeletePosition = position;
                        getDeleteData(String.valueOf(jobsListModel.getJobsModels().get(position).getId()));
                        break;
                }

                return false;
            }

        });
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof JobsListModel) {
                jobsListModel = (JobsListModel) model;
                if (jobsListModel != null && jobsListModel.getJobsModels().size() > 0) {
                    setGridViewData();
                    listView.setVisibility(View.VISIBLE);
                    ll_no_data.setVisibility(View.GONE);
                } else {
                    listView.setVisibility(View.GONE);
                    ll_no_data.setVisibility(View.VISIBLE);
                }
            } else if (model instanceof IAmGoingModel) {
                iAmGoingModel = (IAmGoingModel) model;
                Utility.showToastMessage(mParent, iAmGoingModel.getMessage());
                if (iAmGoingModel.isStatus()) {
                    ArrayList<JobsModel> jobsModels = jobsListModel.getJobsModels();
                    jobsModels.remove(mDeletePosition);
                    jobsListModel.setJobsModels(jobsModels);
                    allmyJobsListAdapter.notifyDataSetChanged();
                }
            }
        }

    }
}
