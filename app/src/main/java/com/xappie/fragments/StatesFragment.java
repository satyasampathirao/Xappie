package com.xappie.fragments;


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
import com.xappie.adapters.StatesListAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.Model;
import com.xappie.models.StateModel;
import com.xappie.models.StatesListModel;
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
public class StatesFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = StatesFragment.class.getSimpleName();

    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;
    private Typeface mTypefaceOpenSansBold;

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
    @BindView(R.id.tv_arrow_right_icon)
    TextView tv_arrow_right_icon;
    @BindView(R.id.tv_city_name)
    TextView tv_city_name;
    @BindView(R.id.city_list_view)
    ListView city_list_view;


    private StatesListModel mStatesListModel;
    private Bundle bundle;
    private String mSelectedCountryId;
    private String mSelectedCountryName;
    private View rootView;
    private ArrayList<StateModel> stateModels;
    private StatesListAdapter statesListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        mFrameLayout = (FrameLayout) getActivity().findViewById(R.id.content_frame);
        mParams = (CoordinatorLayout.LayoutParams) mFrameLayout.getLayoutParams();

        bundle = getArguments();
        if (bundle.containsKey(Constants.SELECTED_COUNTRY_ID)) {
            mSelectedCountryId = bundle.getString(Constants.SELECTED_COUNTRY_ID);
            mSelectedCountryName = bundle.getString(Constants.SELECTED_COUNTRY_NAME);
        }
    }

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
        rootView = inflater.inflate(R.layout.fragment_states, container, false);
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

        tv_cities.setTypeface(mTypefaceOpenSansRegular);
        tv_cities.setText(Utility.getResourcesString(mParent, R.string.states));
        tv_countries_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_countries_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_arrow_right_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_city_name.setTypeface(mTypefaceOpenSansRegular);
        tv_country_name.setTypeface(mTypefaceOpenSansRegular);
        tv_country_name.setText(mSelectedCountryName);

        tv_city_name.setText(Utility.getResourcesString(mParent, R.string.select_state));

        getStatesList();


    }

    private void getStatesList() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            StatesParser statesParser = new StatesParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_STATES + "/" + mSelectedCountryId, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, statesParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
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

    @OnClick({R.id.tv_countries_arrow_back_icon, R.id.tv_countries_menu_icon})
    public void navigateToBack() {
        mParent.onBackPressed();
    }

    @OnClick(R.id.tv_notifications_icon)
    public void navigateToNotification() {
        Utility.navigateDashBoardFragment(new NotificationsFragment(), NotificationsFragment.TAG, null, mParent);
    }

    @OnClick(R.id.tv_language_icon)
    public void navigateToLanguage() {
        Utility.navigateDashBoardFragment(new LanguageFragment(), LanguageFragment.TAG, null, mParent);
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

        Bundle bundle = new Bundle();
        bundle.putString(Constants.SELECTED_COUNTRY_NAME, mSelectedCountryName);
        bundle.putString(Constants.SELECTED_COUNTRY_ID, mSelectedCountryId);
        bundle.putString(Constants.SELECTED_STATE_ID, mStatesListModel.getStateModels().get(position).getId());
        bundle.putString(Constants.SELECTED_STATE_NAME, mStatesListModel.getStateModels().get(position).getName());

        Utility.navigateDashBoardFragment(new CitiesFragment(), CitiesFragment.TAG, bundle, mParent);
    }

}
