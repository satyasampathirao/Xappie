package com.xappie.fragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.CitiesActivity;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.StatesListAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.DeviceTokenUpdateModel;
import com.xappie.models.Model;
import com.xappie.models.StateModel;
import com.xappie.models.StatesListModel;
import com.xappie.parser.DeviceTokenUpdateParser;
import com.xappie.parser.StatesParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CitiesFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = CitiesFragment.class.getSimpleName();
    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;


    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_location)
    TextView tv_location;

    @BindView(R.id.city_list_view)
    ListView city_list_view;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;
    private Typeface mTypefaceOpenSansBold;

    private StatesListModel mStatesListModel;
    private Bundle bundle;
    private String mSelectedCountryName;
    private String mSelectedCountryId;
    private String mSelectedStateName;
    private String mSelectedStateId;

    private ArrayList<StateModel> stateModels;
    private StatesListAdapter statesListAdapter;
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        mFrameLayout = (FrameLayout) getActivity().findViewById(R.id.content_frame);
        mParams = (CoordinatorLayout.LayoutParams) mFrameLayout.getLayoutParams();

        bundle = getArguments();

        if (bundle.containsKey(Constants.SELECTED_STATE_ID)) {
            mSelectedCountryName = bundle.getString(Constants.SELECTED_COUNTRY_NAME);
            mSelectedStateName = bundle.getString(Constants.SELECTED_STATE_NAME);
            mSelectedStateId = bundle.getString(Constants.SELECTED_STATE_ID);
            mSelectedCountryId = bundle.getString(Constants.SELECTED_COUNTRY_ID);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (appBarLayout != null) {
            mParams.setBehavior(null);
            mFrameLayout.requestLayout();
            appBarLayout.setVisibility(View.GONE);
        }
        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_cities, container, false);
        ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    private void initUI() {
        setTypeFace();
    }

    private void setTypeFace() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);
        mTypefaceOpenSansBold = Utility.getOpenSansBold(mParent);

        tv_back.setTypeface(Utility.getMaterialIconsRegular(mParent));
        tv_location.setTypeface(mTypefaceOpenSansRegular);

        getCitiesList();
    }

    private void getCitiesList() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            StatesParser statesParser = new StatesParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_CITIES + "/" + mSelectedStateId, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, statesParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof StatesListModel) {
                mStatesListModel = (StatesListModel) model;
                if (mStatesListModel.getStateModels().size() == 0) {
                    Utility.showToastMessage(mParent, Utility.getResourcesString(mParent, R.string.no_states_found));
                } else {
                    stateModels = mStatesListModel.getStateModels();
                    statesListAdapter = new StatesListAdapter(mParent, mStatesListModel.getStateModels());
                    city_list_view.setAdapter(statesListAdapter);
                }
            }
        }
    }


    @OnClick(R.id.tv_back)
    public void navigateToBack() {
        mParent.onBackPressed();
    }


    @OnItemClick(R.id.city_list_view)
    void onItemClick(int position) {
        for (int i = 0; i < stateModels.size(); i++) {
            StateModel stateModel = stateModels.get(i);
            stateModel.setmSelected(false);
            stateModels.set(i, stateModel);
        }

        StateModel stateModel = stateModels.get(position);
        stateModel.setmSelected(true);
        statesListAdapter.notifyDataSetChanged();

        Utility.setSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_ID, mSelectedCountryId);
        Utility.setSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_NAME, mSelectedCountryName);
        Utility.setSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID, mSelectedStateId);
        Utility.setSharedPrefStringData(mParent, Constants.SELECTED_STATE_NAME, mSelectedStateName);
        Utility.setSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID, mStatesListModel.getStateModels().get(position).getId());
        Utility.setSharedPrefStringData(mParent, Constants.SELECTED_CITY_NAME, mStatesListModel.getStateModels().get(position).getName());

        String s = Utility.getSharedPrefStringData(mParent, Constants.HOME_PAGE_CONTENTS);
        if (Utility.isValueNullOrEmpty(s)) {
            Utility.setSharedPrefStringData(mParent, Constants.HOME_PAGE_CONTENTS, Constants.HOME_PAGE_CONTENTS_DATA);
            Utility.setSharedPrefStringData(mParent, Constants.HOME_PAGE_EVENTS_CONTENTS, Constants.EVENTS_CLASSIFIEDS_JOBS);
        }

       /* Intent intent = new Intent(mParent, DashBoardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent); */

        Bundle bundle = new Bundle();
        bundle.putString(Constants.SELECTED_COUNTRY_NAME, mSelectedCountryName);
        bundle.putString(Constants.SELECTED_COUNTRY_ID, mSelectedCountryId);
        bundle.putString(Constants.SELECTED_STATE_ID, mSelectedStateId);
        bundle.putString(Constants.SELECTED_STATE_NAME, mSelectedStateName);
        bundle.putString(Constants.SELECTED_CITY_ID,mStatesListModel.getStateModels().get(position).getId());
        bundle.putString(Constants.SELECTED_CITY_NAME,mStatesListModel.getStateModels().get(position).getName());

        Utility.navigateDashBoardFragment(new LocalitiesFragment(), LocalitiesFragment.TAG, bundle, mParent);
        updateDeviceData();
    }

    private void updateDeviceData() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        paramMap.put("device_type", Constants.DEVICE_TYPE);
        paramMap.put("token", Utility.getSharedPrefStringData(mParent, Constants.KEY_FCM_TOKEN));
        paramMap.put("country", mSelectedCountryId);
        paramMap.put("state", mSelectedStateId);
        paramMap.put("city", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID));
        paramMap.put("language", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_LANGUAGE_ID));
        paramMap.put("modules", Constants.HOME_PAGE_CONTENTS_DATA + "," + Constants.EVENTS_CLASSIFIEDS_JOBS);
        paramMap.put("notifications", Constants.HOME_PAGE_CONTENTS_DATA + "," + Constants.EVENTS_CLASSIFIEDS_JOBS);

        DeviceTokenUpdateParser mDeviceTokenUpdateParser = new DeviceTokenUpdateParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(mParent, Utility.getResourcesString(mParent,
                R.string.please_wait), false,
                APIConstants.UPDATE_DEVICE_PREFERENCE, paramMap,
                APIConstants.REQUEST_TYPE.POST, this, mDeviceTokenUpdateParser);
        Utility.execute(serverIntractorAsync);
    }
}
