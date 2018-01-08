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
import com.xappie.adapters.LanguagesListAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.DeviceTokenUpdateModel;
import com.xappie.models.LanguageListModel;
import com.xappie.models.LanguageModel;
import com.xappie.models.Model;
import com.xappie.parser.DeviceTokenUpdateParser;
import com.xappie.parser.LanguageParser;
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
public class LanguageFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = LanguageFragment.class.getSimpleName();


    @BindView(R.id.language_list_item)
    ListView language_list_item;
    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_language)
    TextView tv_language;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;
    private LanguageListModel mLanguageListModel;
    private Typeface mTypefaceMaterialIcon;

    private ArrayList<LanguageModel> languageModels;
    private LanguagesListAdapter languagesListAdapter;

    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        mFrameLayout = (FrameLayout) getActivity().findViewById(R.id.content_frame);
        mParams = (CoordinatorLayout.LayoutParams) mFrameLayout.getLayoutParams();
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
        rootView = inflater.inflate(R.layout.fragment_language, container, false);
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
        mTypefaceMaterialIcon = Utility.getMaterialIconsRegular(mParent);


        tv_language.setTypeface(mTypefaceOpenSansRegular);
        tv_back.setTypeface(mTypefaceMaterialIcon);

        getLanguagesData();
    }

    private void getLanguagesData() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            LanguageParser lookUpEventTypeParser = new LanguageParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_LANGUAGES, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, lookUpEventTypeParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof LanguageListModel) {
                mLanguageListModel = (LanguageListModel) model;
                languageModels = mLanguageListModel.getLanguageModels();
                languagesListAdapter = new LanguagesListAdapter(mParent, languageModels);
                language_list_item.setAdapter(languagesListAdapter);
            } else if (model instanceof DeviceTokenUpdateModel) {
                Intent dashBoardIntent = new Intent(getActivity(), DashBoardActivity.class);
                startActivity(dashBoardIntent);
            }
        }
    }

    /**
     * This method is used to select preferred language
     */
    @OnItemClick(R.id.language_list_item)
    void onItemClick(int position) {

        for (int i = 0; i < languageModels.size(); i++) {
            LanguageModel languageModel = languageModels.get(i);
            languageModel.setmSelected(false);
            languageModels.set(i, languageModel);
        }

        LanguageModel languageModel = languageModels.get(position);
        languageModel.setmSelected(true);
        languagesListAdapter.notifyDataSetChanged();

        Utility.setSharedPrefStringData(mParent, Constants.SELECTED_LANGUAGE, mLanguageListModel.getLanguageModels().get(position).getName());
        Utility.setSharedPrefStringData(mParent, Constants.SELECTED_LANGUAGE_ID, mLanguageListModel.getLanguageModels().get(position).getId());

        updateDeviceData();
    }

    private void updateDeviceData() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        paramMap.put("device_type", Constants.DEVICE_TYPE);
        paramMap.put("token", Utility.getSharedPrefStringData(mParent, Constants.KEY_FCM_TOKEN));
        paramMap.put("country", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_ID));
        paramMap.put("state", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID));
        paramMap.put("city", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID));
        if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(mParent, Constants.SELECTED_LOCALITY_ID)))
            paramMap.put("locality", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_LOCALITY_ID));
        paramMap.put("language", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_LANGUAGE_ID));
        paramMap.put("modules", Utility.getSharedPrefStringData(mParent, Constants.HOME_PAGE_CONTENTS)
                + "," + "ads,banners," + Utility.getSharedPrefStringData(mParent, Constants.HOME_PAGE_EVENTS_CONTENTS)
                + Utility.getSharedPrefStringData(mParent, Constants.HOME_PAGE_JOBS_CONTENTS));
        paramMap.put("notifications", Constants.HOME_PAGE_CONTENTS_DATA + "," + Constants.EVENTS_CLASSIFIEDS_JOBS);

        DeviceTokenUpdateParser mDeviceTokenUpdateParser = new DeviceTokenUpdateParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(mParent, Utility.getResourcesString(mParent,
                R.string.please_wait), true,
                APIConstants.UPDATE_DEVICE_PREFERENCE, paramMap,
                APIConstants.REQUEST_TYPE.POST, this, mDeviceTokenUpdateParser);
        Utility.execute(serverIntractorAsync);
    }

    @OnClick(R.id.tv_back)
    public void navigateBack() {
        mParent.onBackPressed();
    }
}
