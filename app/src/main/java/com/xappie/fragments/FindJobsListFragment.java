package com.xappie.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.activities.LoginActivity;
import com.xappie.adapters.FindJobsListAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.EntertainmentModel;
import com.xappie.models.JobsListModel;
import com.xappie.models.JobsModel;
import com.xappie.models.Model;
import com.xappie.models.StateModel;
import com.xappie.models.StatesListModel;
import com.xappie.parser.JobsListParser;
import com.xappie.parser.StatesParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindJobsListFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = FindJobsListFragment.class.getSimpleName();
    private DashBoardActivity mParent;

    /**
     * AllEvents List set up
     */
    @BindView(R.id.list_view)
    ListView list_view;
    @BindView(R.id.ll_no_data)
    LinearLayout ll_no_data;
    @BindView(R.id.ll_city_types)
    LinearLayout ll_city_types;

    private StatesListModel mStatesListModel;

    private StateModel stateModel;
    private JobsListModel jobsListModel;

    private String mFilterCategory = "";
    private String mFilterCity = "";
    private String mFilterState = "";
    private String mFilterCountry = "";
    private String mFilterCompany = "";
    private String mFindJobsFrom = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(Constants.FILTER_CATEGORY)) {
                mFilterCategory = bundle.getString(Constants.FILTER_CATEGORY);
            }
            if (bundle.containsKey(Constants.FILTER_CITY)) {
                mFilterCity = bundle.getString(Constants.FILTER_CITY);
            }
            if (bundle.containsKey(Constants.FILTER_STATE)) {
                mFilterState = bundle.getString(Constants.FILTER_STATE);
            }
            if (bundle.containsKey(Constants.FILTER_COUNTRY)) {
                mFilterCountry = bundle.getString(Constants.FILTER_COUNTRY);
            }
            if (bundle.containsKey(Constants.FILTER_SEARCH)) {
                mFilterCompany = bundle.getString(Constants.FILTER_SEARCH);
            }
            if (bundle.containsKey(Constants.FILTER_FROM)) {
                mFindJobsFrom = bundle.getString(Constants.FILTER_FROM);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_find_jobs_list, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {
        stateModel = new StateModel();
        stateModel.setId(Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID));
        getCitiesList();
    }

    private void setGridViewData() {

        FindJobsListAdapter findJobsListAdapter = new FindJobsListAdapter(mParent, jobsListModel.getJobsModels());
        list_view.setAdapter(findJobsListAdapter);
    }

    private void getCitiesList() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            StatesParser statesParser = new StatesParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_CITIES + "/" + Utility.getSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID), linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, statesParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getJobsData(String pageNo) {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            //linkedHashMap.put("type", "Public");
            if (Utility.isValueNullOrEmpty(mFilterCountry)) {
                linkedHashMap.put("country", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_ID));
            } else {
                linkedHashMap.put("country", mFilterCountry);
            }
            if (Utility.isValueNullOrEmpty(mFilterState)) {
                linkedHashMap.put("state", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID));
            } else {
                linkedHashMap.put("state", mFilterState);
            }
            if (Utility.isValueNullOrEmpty(mFilterState)) {
                linkedHashMap.put("city", stateModel.getId());
            } else {
                linkedHashMap.put("city", mFilterCity);
            }
            linkedHashMap.put("company", mFilterCompany);
            linkedHashMap.put("category", mFilterCategory);

            linkedHashMap.put(Constants.PAGE_NO, pageNo);
            linkedHashMap.put(Constants.PAGE_SIZE, Constants.PAGE_SIZE_VALUE);
            JobsListParser jobsListParser = new JobsListParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_JOBS, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, jobsListParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This method is used navigate post
     */
    @OnClick(R.id.fab)
    void navigateToPost() {

        if (!Utility.getSharedPrefBooleanData(mParent, Constants.IS_LOGIN_COMPLETED)) {
            Utility.showToastMessage(mParent, "Login First");
            Intent intent = new Intent(mParent, LoginActivity.class);
            startActivity(intent);
        } else {

            Utility.navigateAllJobsFragment(new PostJobFragment(), PostJobFragment.TAG, null, mParent);
        }
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof JobsListModel) {
                jobsListModel = (JobsListModel) model;
                if (jobsListModel != null && jobsListModel.getJobsModels().size() > 0) {
                    list_view.setVisibility(View.VISIBLE);
                    ll_no_data.setVisibility(View.GONE);
                    setGridViewData();
                } else {
                    ll_no_data.setVisibility(View.VISIBLE);
                    list_view.setVisibility(View.GONE);
                }
            } else if (model instanceof StatesListModel) {
                mStatesListModel = (StatesListModel) model;
                if (mStatesListModel.getStateModels().size() > 0) {
                    for (int i = 0; i < mStatesListModel.getStateModels().size(); i++) {
                        if (Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID).equalsIgnoreCase(mStatesListModel.getStateModels().get(i).getId())) {
                            stateModel = mStatesListModel.getStateModels().get(i);
                        }
                    }
                    setCityTypes();
                    getJobsData(" " + 1);
                }
            }

        }
    }

    private void setCityTypes() {
        ll_city_types.removeAllViews();
        for (int i = 0; i < mStatesListModel.getStateModels().size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.videos_type_item, null);
            TextView tv_type = (TextView) ll.findViewById(R.id.tv_type);
            tv_type.setText(mStatesListModel.getStateModels().get(i).getName());
            tv_type.setTextColor(Utility.getColor(mParent, R.color.white));
            tv_type.setTypeface(Utility.getOpenSansRegular(mParent));

            tv_type.setId(i);
            tv_type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = v.getId();
                    stateModel = mStatesListModel.getStateModels().get(pos);
                    setCityTypes();
                    getJobsData("" + 1);
                }
            });

            if (mStatesListModel != null && mStatesListModel.getStateModels().get(i).getId() == stateModel.getId()) {
                tv_type.setTextColor(Utility.getColor(mParent, R.color.white));
                tv_type.setBackground(Utility.getDrawable(mParent, R.drawable.bg_color_rect));
            } else {
                tv_type.setTextColor(Utility.getColor(mParent, R.color.types_color));
                tv_type.setBackground(null);
            }

            ll_city_types.addView(ll);
        }
    }

}
