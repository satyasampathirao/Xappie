package com.xappie.fragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.xappie.adapters.StatesListAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.Model;
import com.xappie.models.StatesListModel;
import com.xappie.parser.StatesParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

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


    @BindView(R.id.tv_countries_arrow_back_icon)
    TextView tv_countries_arrow_back_icon;
    @BindView(R.id.tv_countries_menu_icon)
    TextView tv_countries_menu_icon;
    @BindView(R.id.tv_cities)
    TextView tv_cities;
    @BindView(R.id.tv_notifications_icon)
    TextView tv_notification_icon;
    @BindView(R.id.tv_language_icon)
    TextView tv_language_icon;
    @BindView(R.id.tv_country_name)
    TextView tv_country_name;
    @BindView(R.id.tv_state_name)
    TextView tv_state_name;
    @BindView(R.id.tv_arrow_right_icon)
    TextView tv_arrow_right_icon;
    @BindView(R.id.tv_arrow_right_icon_two)
    TextView tv_arrow_right_icon_two;
    @BindView(R.id.tv_city_name)
    TextView tv_city_name;
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

    public CitiesFragment() {
        // Required empty public constructor
    }

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
        View rootView = inflater.inflate(R.layout.fragment_cities, container, false);
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
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);
        mTypefaceOpenSansBold = Utility.getOpenSansBold(mParent);

        tv_cities.setTypeface(mTypefaceOpenSansRegular);
        tv_cities.setText(Utility.getResourcesString(mParent, R.string.cities));
        tv_countries_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_countries_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_arrow_right_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_arrow_right_icon_two.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_city_name.setTypeface(mTypefaceOpenSansRegular);
        tv_state_name.setTypeface(mTypefaceOpenSansRegular);
        tv_country_name.setTypeface(mTypefaceOpenSansRegular);

        tv_country_name.setText(mSelectedCountryName);
        tv_state_name.setText(mSelectedStateName);

        tv_city_name.setText(Utility.getResourcesString(mParent, R.string.select_city));

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
                    city_list_view.setAdapter(new StatesListAdapter(mParent, mStatesListModel.getStateModels()));
                }
            }
        }
    }


    @OnClick({R.id.tv_countries_arrow_back_icon, R.id.tv_countries_menu_icon})
    public void navigateToBack() {
        mParent.onBackPressed();
    }

    @OnClick(R.id.tv_notifications_icon)
    public void navigateNotification() {
        Utility.navigateDashBoardFragment(new NotificationsFragment(), NotificationsFragment.TAG, null, mParent);
    }

    @OnClick(R.id.tv_language_icon)
    public void navigateLanguage() {
        Utility.navigateDashBoardFragment(new LanguageFragment(), LanguageFragment.TAG, null, mParent);
    }

    @OnItemClick(R.id.city_list_view)
    void onItemClick(int position) {
        Utility.setSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_ID, mSelectedCountryId);
        Utility.setSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_NAME, mSelectedCountryName);
        Utility.setSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID, mSelectedStateId);
        Utility.setSharedPrefStringData(mParent, Constants.SELECTED_STATE_NAME, mSelectedStateName);
        Utility.setSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID, mStatesListModel.getStateModels().get(position).getId());
        Utility.setSharedPrefStringData(mParent, Constants.SELECTED_CITY_NAME, mStatesListModel.getStateModels().get(position).getName());

        String s = Utility.getSharedPrefStringData(mParent, Constants.HOME_PAGE_CONTENTS);
        if (Utility.isValueNullOrEmpty(s)) {
            Utility.setSharedPrefStringData(mParent, Constants.HOME_PAGE_CONTENTS, Constants.HOME_PAGE_CONTENTS_DATA);
        }

        Intent intent = new Intent(mParent, DashBoardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
