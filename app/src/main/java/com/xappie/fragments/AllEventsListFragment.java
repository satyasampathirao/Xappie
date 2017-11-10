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
import com.xappie.adapters.AllEventsListAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.EventsListModel;
import com.xappie.models.Model;
import com.xappie.models.StateModel;
import com.xappie.models.StatesListModel;
import com.xappie.parser.EventsListParser;
import com.xappie.parser.StatesParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shankar Pilli on 07/28/2017
 */
public class AllEventsListFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = AllEventsListFragment.class.getSimpleName();
    private DashBoardActivity mParent;

    private EventsListModel eventsListModel;
    private StatesListModel mStatesListModel;

    private StateModel stateModel;
    /**
     * AllEvents List set up
     */
    @BindView(R.id.list_view)
    ListView list_view;
    @BindView(R.id.ll_city_types)
    LinearLayout ll_city_types;

    @BindView(R.id.ll_no_data)
    LinearLayout ll_no_data;

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
        stateModel = new StateModel();
        stateModel.setId(Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID));
        getCitiesList();
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


    /**
     * This method is used to get the all the events data from the server
     */
    private void getEventsData(String pageNo) {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            //linkedHashMap.put("type", "Public");
            linkedHashMap.put("country", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_ID));
            linkedHashMap.put("state", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID));
            linkedHashMap.put("city", stateModel.getId());
            linkedHashMap.put(Constants.PAGE_NO, pageNo);
            linkedHashMap.put(Constants.PAGE_SIZE, Constants.PAGE_SIZE_VALUE);
            EventsListParser eventsListParser = new EventsListParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_EVENTS, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, eventsListParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*This method is used to set the lsit view data*/
    private void setGridViewData() {
        AllEventsListAdapter allEventsListAdapter = new AllEventsListAdapter(mParent, eventsListModel.getEventsModels());
        list_view.setAdapter(allEventsListAdapter);
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
            Utility.navigateAllEventsFragment(new AddNewEventFragment(), AddNewEventFragment.TAG, null, mParent);
        }
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof EventsListModel) {
                eventsListModel = (EventsListModel) model;
                if (eventsListModel != null && eventsListModel.getEventsModels().size() > 0) {
                    ll_no_data.setVisibility(View.GONE);
                    list_view.setVisibility(View.VISIBLE);
                    setGridViewData();
                } else {
                    ll_no_data.setVisibility(View.VISIBLE);
                    list_view.setVisibility(View.GONE);
                }
            } else if (model instanceof StatesListModel) {
                mStatesListModel = (StatesListModel) model;
                if (mStatesListModel.getStateModels().size() > 0) {
                    for (int i = 0; i < mStatesListModel.getStateModels().size(); i++) {
                        if (Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID)
                                .equalsIgnoreCase(mStatesListModel.getStateModels().get(i).getId())) {
                            stateModel = mStatesListModel.getStateModels().get(i);
                        }
                    }
                    setVideoTypes();
                    getEventsData("" + 1);
                }
            }
        }
    }


    /**
     * This method is used to set the video types
     */
    private void setVideoTypes() {
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
                    setVideoTypes();
                    getEventsData("" + 1);
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

