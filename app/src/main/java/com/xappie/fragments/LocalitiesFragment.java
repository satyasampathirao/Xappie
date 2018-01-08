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
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.LocalityListAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.DeviceTokenUpdateModel;
import com.xappie.models.LocalityListModel;
import com.xappie.models.LocalityModel;
import com.xappie.models.Model;
import com.xappie.parser.DeviceTokenUpdateParser;
import com.xappie.parser.LocalityParser;
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
 * Created by Santosh on 03-01-2018.
 */

public class LocalitiesFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = LocalitiesFragment.class.getSimpleName();


    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;
    private View rootView;
    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;
    private Typeface mTypefaceOpenSansBold;
    private LocalityListModel mLocalityListModel;


    private Bundle bundle;
    private String mSelectedCountryName;
    private String mSelectedCountryId;
    private String mSelectedStateName;
    private String mSelectedStateId;
    private String mSelectedCityName;
    private String mSelectedCityId;
    private ArrayList<LocalityModel> localityModels;
    private LocalityListAdapter localityListAdapter;

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_location)
    TextView tv_location;

    @BindView(R.id.city_list_view)
    ListView city_list_view;


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
            mSelectedCityName = bundle.getString(Constants.SELECTED_CITY_NAME);
            mSelectedCityId = bundle.getString(Constants.SELECTED_CITY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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

        getLocalitiesList();
    }

    private void getLocalitiesList() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            LocalityParser localityParser = new LocalityParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_LOCALITIES + "/" + mSelectedCityId, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, localityParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof LocalityListModel) {
                mLocalityListModel = (LocalityListModel) model;
                if (mLocalityListModel.getLocalityModels().size() == 0) {
                    Utility.showToastMessage(mParent, Utility.getResourcesString(mParent, R.string.no_localities_found));
                    Intent intent = new Intent(mParent, DashBoardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    localityModels = mLocalityListModel.getLocalityModels();
                    localityListAdapter = new LocalityListAdapter(mParent, mLocalityListModel.getLocalityModels());
                    city_list_view.setAdapter(localityListAdapter);
                }
            } else if (model instanceof DeviceTokenUpdateModel) {
                Intent intent = new Intent(mParent, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    }


    @OnClick(R.id.tv_back)
    public void navigateToBack() {
        mParent.onBackPressed();
    }


    @OnItemClick(R.id.city_list_view)
    void onItemClick(int position) {

        for (int i = 0; i < localityModels.size(); i++) {
            LocalityModel localityModel = localityModels.get(i);
            localityModel.setmSelected(false);
            localityModels.set(i, localityModel);
        }

        LocalityModel localityModel = localityModels.get(position);
        localityModel.setmSelected(true);
        localityListAdapter.notifyDataSetChanged();

        Utility.setSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_ID, mSelectedCountryId);
        Utility.setSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_NAME, mSelectedCountryName);
        Utility.setSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID, mSelectedStateId);
        Utility.setSharedPrefStringData(mParent, Constants.SELECTED_STATE_NAME, mSelectedStateName);
        Utility.setSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID, mSelectedCityId);
        Utility.setSharedPrefStringData(mParent, Constants.SELECTED_CITY_NAME, mSelectedCityName);

        Utility.setSharedPrefStringData(mParent, Constants.SELECTED_LOCALITY_ID, mLocalityListModel.getLocalityModels().get(position).getId());
        Utility.setSharedPrefStringData(mParent, Constants.SELECTED_LOCALITY_NAME, mLocalityListModel.getLocalityModels().get(position).getName());

        String s = Utility.getSharedPrefStringData(mParent, Constants.HOME_PAGE_CONTENTS);
        if (Utility.isValueNullOrEmpty(s)) {
            Utility.setSharedPrefStringData(mParent, Constants.HOME_PAGE_CONTENTS, Constants.HOME_PAGE_CONTENTS_DATA);
            Utility.setSharedPrefStringData(mParent, Constants.HOME_PAGE_EVENTS_CONTENTS, Constants.EVENTS_CLASSIFIEDS_JOBS);
        }

       /* Intent intent = new Intent(mParent, DashBoardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);*/
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
        paramMap.put("locality", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_LOCALITY_ID));
        paramMap.put("language", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_LANGUAGE_ID));
        paramMap.put("modules", Utility.getSharedPrefStringData(mParent, Constants.HOME_PAGE_CONTENTS)
                + "," + "ads,banners," + Utility.getSharedPrefStringData(mParent, Constants.HOME_PAGE_EVENTS_CONTENTS)
                + Utility.getSharedPrefStringData(mParent, Constants.HOME_PAGE_JOBS_CONTENTS));
        paramMap.put("notifications", Constants.HOME_PAGE_CONTENTS_DATA + "," + Constants.EVENTS_CLASSIFIEDS_JOBS);

        DeviceTokenUpdateParser mDeviceTokenUpdateParser = new DeviceTokenUpdateParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(mParent, Utility.getResourcesString(mParent,
                R.string.please_wait), false,
                APIConstants.UPDATE_DEVICE_PREFERENCE, paramMap,
                APIConstants.REQUEST_TYPE.POST, this, mDeviceTokenUpdateParser);
        Utility.execute(serverIntractorAsync);
    }
}
