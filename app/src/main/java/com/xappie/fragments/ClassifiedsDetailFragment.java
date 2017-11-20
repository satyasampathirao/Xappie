package com.xappie.fragments;


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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.ClassifiedsListModel;
import com.xappie.models.ClassifiedsModel;
import com.xappie.models.Model;
import com.xappie.models.StateModel;
import com.xappie.models.StatesListModel;
import com.xappie.parser.ClassifiedsDetailParser;
import com.xappie.parser.ClassifiedsParser;
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
 * Created by Shankar on 7/28/2017.
 */

public class ClassifiedsDetailFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = ClassifiedsDetailFragment.class.getSimpleName();
    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;

    /**
     * Gallery Toolbar
     */
    @BindView(R.id.tv_notification_arrow_back_icon)
    TextView tv_notification_arrow_back_icon;
    @BindView(R.id.tv_notification_menu_icon)
    TextView tv_notification_menu_icon;

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_location_icon)
    TextView tv_location_icon;
    @BindView(R.id.tv_notifications_icon)
    TextView tv_notifications_icon;
    @BindView(R.id.tv_language_icon)
    TextView tv_language_icon;

    @BindView(R.id.img_person)
    ImageView img_person;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_date)
    TextView tv_date;

    @BindView(R.id.tv_post_title)
    TextView tv_post_title;
    @BindView(R.id.tv_topic_details)
    TextView tv_topic_details;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.img_uploaded)
    ImageView img_uploaded;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;

    private ClassifiedsListModel classifiedsListModel;
    private StatesListModel mStatesListModel;
    private ClassifiedsModel classifiedsModel;
    private StateModel stateModel;
    private String mId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        mFrameLayout = (FrameLayout) getActivity().findViewById(R.id.content_frame);
        mParams = (CoordinatorLayout.LayoutParams) mFrameLayout.getLayoutParams();
        if (getArguments() != null && getArguments().containsKey(Constants.CLASSIFIEDS_ID)) {
            mId = getArguments().getString(Constants.CLASSIFIEDS_ID);
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
        View rootView = inflater.inflate(R.layout.fragment_classifieds_detail, container, false);
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

    private void setTypeFace() {

        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);

        tv_notification_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(Utility.getResourcesString(mParent, R.string.auto_mobiles));
        tv_title.setTypeface(mTypefaceOpenSansRegular);

        tv_location_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notifications_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_name.setTypeface(mTypefaceOpenSansRegular);
        tv_date.setTypeface(mTypefaceOpenSansRegular);
        tv_post_title.setTypeface(mTypefaceOpenSansRegular);
        tv_topic_details.setTypeface(mTypefaceOpenSansRegular);
        tv_price.setTypeface(Utility.getOpenSansBold(mParent));

    }
    @OnClick({R.id.tv_notification_arrow_back_icon,
            R.id.tv_notification_menu_icon})
    void backToTheHome() {
        mParent.onBackPressed();
    }

    @OnClick(R.id.tv_notifications_icon)
    public void navigateNotification()
    {
        Utility.navigateDashBoardFragment(new NotificationsFragment(),NotificationsFragment.TAG,null,mParent);
    }
    @OnClick(R.id.tv_language_icon)
    public void navigateLanguage()
    {
        Utility.navigateDashBoardFragment(new LanguageFragment(),LanguageFragment.TAG,null,mParent);
    }
    @OnClick(R.id.tv_location_icon)
    public void navigateLocation()
    {
        Utility.navigateDashBoardFragment(new CountriesFragment(),CountriesFragment.TAG,null,mParent);
    }


    private void getClassifiedsData(String pageNo) {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            //linkedHashMap.put("type", "Public");
            linkedHashMap.put("country", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_ID));
            linkedHashMap.put("state", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID));
            linkedHashMap.put(Constants.PAGE_NO, pageNo);
            linkedHashMap.put(Constants.PAGE_SIZE, Constants.PAGE_SIZE_VALUE);
            ClassifiedsDetailParser classifiedsParser = new ClassifiedsDetailParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_CLASSIFIED_DETAILS + "/" + mId , linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, classifiedsParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof ClassifiedsModel) {
                classifiedsModel = (ClassifiedsModel) model;
                setClasifiedsData();
            } else if (model instanceof StatesListModel) {
                mStatesListModel = (StatesListModel) model;
                if (mStatesListModel.getStateModels().size() > 0) {
                    for (int i = 0; i < mStatesListModel.getStateModels().size(); i++) {
                        if (Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID)
                                .equalsIgnoreCase(mStatesListModel.getStateModels().get(i).getId())) {
                            stateModel = mStatesListModel.getStateModels().get(i);
                        }
                    }
                    getClassifiedsData("" + 1);
                }
            }

        }
    }

    private void setClasifiedsData() {

        tv_name.setText(classifiedsModel.getName());
        tv_date.setText(classifiedsModel.getRecordedDate());
        tv_post_title.setText(classifiedsModel.getTitle());
        tv_price.setText(classifiedsModel.getPrice());
        tv_topic_details.setText(classifiedsModel.getDescription());
        if (!Utility.isValueNullOrEmpty(classifiedsModel.getImage())) {
            Utility.universalImageLoaderPicLoading(img_uploaded,
                    classifiedsModel.getImage(), null, R.drawable.xappie_place_holder);
        } else {
            Utility.universalImageLoaderPicLoading(img_uploaded,
                    "", null, R.drawable.xappie_place_holder);
        }

    }
    }

