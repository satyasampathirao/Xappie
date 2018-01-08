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
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.EventGoingGridAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.EventsGoingWithCountModel;
import com.xappie.models.Model;
import com.xappie.models.WhoIsGoingListModel;
import com.xappie.parser.WhoIsGoingListParser;
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
public class EventsGoingMaybeGoingFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = EventsGoingMaybeGoingFragment.class.getSimpleName();
    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;


    /**
     * Going Toolbar
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

    private Typeface mTypefaceFontAwesomeWebFont;

    /**
     * Going setup
     */
    @BindView(R.id.ll_languages)
    LinearLayout ll_languages;
    @BindView(R.id.grid_view)
    GridView grid_view;

    @BindView(R.id.ll_no_data_event_going)
    LinearLayout ll_no_data_event_going;
    @BindView(R.id.tv_no_data_event_going)
    TextView tv_no_data_event_going;

    private String mId;
    private WhoIsGoingListModel mWhoIsGoingListModel;
    private EventGoingGridAdapter eventGoingGridAdapter;
    private String mCurrentTag = "GOING";

    private ArrayList<EventsGoingWithCountModel> mLanguagesData;
    private String mCurrent = "1";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        mFrameLayout = (FrameLayout) getActivity().findViewById(R.id.content_frame);
        mParams = (CoordinatorLayout.LayoutParams) mFrameLayout.getLayoutParams();
        Utility.sendGoogleAnalytics(mParent, TAG);
        if (getArguments() != null && getArguments().containsKey(Constants.EVENT_ID)) {
            mId = getArguments().getString(Constants.EVENT_ID);
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
        View rootView = inflater.inflate(R.layout.fragment_events_going_maybe_going, container, false);
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
        mCurrent = "1";
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);


        tv_notification_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(Utility.getResourcesString(mParent, R.string.events));
        tv_title.setTypeface(Utility.getOpenSansRegular(mParent));

        tv_location_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notifications_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        getGoingData();
        setGoing();
        getGoingDetails(mCurrent);
    }

    private void getGoingDetails(String type) {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            WhoIsGoingListParser whoIsGoingListParser = new WhoIsGoingListParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.WHOIS_GOING + mId + "/" + type, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, whoIsGoingListParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This method is used to set the grid view data
     */
    private void setGridViewData() {

        if (mWhoIsGoingListModel != null && mWhoIsGoingListModel.getWhoIsGoingModels().size() > 0) {
            eventGoingGridAdapter = new EventGoingGridAdapter(mParent, mWhoIsGoingListModel.getWhoIsGoingModels());
            grid_view.setAdapter(eventGoingGridAdapter);
            ll_no_data_event_going.setVisibility(View.GONE);
            grid_view.setVisibility(View.VISIBLE);

            if (mCurrent.equalsIgnoreCase("1")) {
                EventsGoingWithCountModel eventsGoingWithCountModel = mLanguagesData.get(0);
                eventsGoingWithCountModel.setCount("" + mWhoIsGoingListModel.getWhoIsGoingModels().size());
                mLanguagesData.set(0, eventsGoingWithCountModel);
                setGoing();
            } else {
                EventsGoingWithCountModel eventsGoingWithCountModel = mLanguagesData.get(1);
                eventsGoingWithCountModel.setCount("" + mWhoIsGoingListModel.getWhoIsGoingModels().size());
                mLanguagesData.set(1, eventsGoingWithCountModel);
                setGoing();
            }
        } else {
            ll_no_data_event_going.setVisibility(View.VISIBLE);
            grid_view.setVisibility(View.GONE);
        }
    }

    /**
     * This method is used to set the languages
     */
    private void setGoing() {
        ll_languages.removeAllViews();
        for (int i = 0; i < mLanguagesData.size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.language_item, null);
            TextView tv_language_name = (TextView) ll.findViewById(R.id.tv_language_name);
            View view = ll.findViewById(R.id.view);
            if (!Utility.isValueNullOrEmpty(mLanguagesData.get(i).getCount())) {
                tv_language_name.setText(mLanguagesData.get(i).getName() + " (" + mLanguagesData.get(i).getCount() + ")");
            } else {
                tv_language_name.setText(mLanguagesData.get(i).getName());
            }
            tv_language_name.setTextColor(Utility.getColor(mParent, R.color.white));
            tv_language_name.setTypeface(Utility.getOpenSansRegular(mParent));

            tv_language_name.setId(i);
            tv_language_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = v.getId();
                    mCurrentTag = mLanguagesData.get(pos).getName();
                    mWhoIsGoingListModel = null;
                    eventGoingGridAdapter = null;
                    setGoing();
                    if (mCurrentTag.contains("MAYBE")) {
                        mCurrent = "2";
                        getGoingDetails("" + 2);
                    } else {
                        mCurrent = "1";
                        getGoingDetails("" + 1);
                    }
                }
            });

            if (mCurrentTag != null && mLanguagesData.get(i).getName().equalsIgnoreCase(mCurrentTag)) {
                view.setVisibility(View.VISIBLE);
                tv_language_name.setTextColor(Utility.getColor(mParent, R.color.white));
                view.setBackgroundColor(Utility.getColor(mParent, R.color.white));
            } else {
                view.setVisibility(View.GONE);
            }

            ll_languages.addView(ll);
        }
    }

    /*This method is used to set the languages
    private void setGoing() {
        ll_languages.removeAllViews();
        for (int i = 0; i < getGoingData().size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.language_item, null);
            TextView tv_language_name = (TextView) ll.findViewById(R.id.tv_language_name);
            View view = (View) ll.findViewById(R.id.view);
            tv_language_name.setText(getGoingData().get(i));
            tv_language_name.setTextColor(Utility.getColor(mParent, R.color.white));
            tv_language_name.setTypeface(Utility.getOpenSansBold(mParent));
            if (i == 0) {
                view.setVisibility(View.VISIBLE);
                tv_language_name.setTextColor(Utility.getColor(mParent, R.color.white));
                view.setBackgroundColor(Utility.getColor(mParent, R.color.white));
            } else {
                view.setVisibility(View.GONE);
            }
            ll_languages.addView(ll);
        }
    }*/

    private void getGoingData() {
        mLanguagesData = new ArrayList<>();
        EventsGoingWithCountModel eventsGoingWithCountModel = new EventsGoingWithCountModel();
        eventsGoingWithCountModel.setName("GOING");
        eventsGoingWithCountModel.setCount("");
        mLanguagesData.add(eventsGoingWithCountModel);


        EventsGoingWithCountModel eventsGoingWithCountModel1 = new EventsGoingWithCountModel();
        eventsGoingWithCountModel1.setName("MAYBE GOING");
        eventsGoingWithCountModel1.setCount("");
        mLanguagesData.add(eventsGoingWithCountModel1);

    }

    @OnClick({R.id.tv_notification_arrow_back_icon,
            R.id.tv_notification_menu_icon})
    void backToTheHome() {
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

    @OnClick(R.id.tv_location_icon)
    public void navigateLocation() {
        Utility.navigateDashBoardFragment(new CountriesFragment(), CountriesFragment.TAG, null, mParent);
    }


    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof WhoIsGoingListModel) {
                mWhoIsGoingListModel = (WhoIsGoingListModel) model;
                setGridViewData();
            }
        }
    }
}
