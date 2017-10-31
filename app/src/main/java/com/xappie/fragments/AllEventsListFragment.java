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
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.EventsListModel;
import com.xappie.models.Model;
import com.xappie.parser.EventsListParser;
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
        getEventsData("" + 1);
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
            linkedHashMap.put("city", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID));
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
        Utility.navigateAllEventsFragment(new AddNewEventFragment(), AddNewEventFragment.TAG, null, mParent);
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof EventsListModel) {
                eventsListModel = (EventsListModel) model;
                if (eventsListModel != null && eventsListModel.getEventsModels().size() > 0) {
                    setGridViewData();
                }
            }
        }
    }
}

