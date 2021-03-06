package com.xappie.fragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.DeviceTokenUpdateModel;
import com.xappie.models.Model;
import com.xappie.parser.DeviceTokenUpdateParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsSettingsFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = NotificationsSettingsFragment.class.getSimpleName();
    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;


    @BindView(R.id.tv_notification)
    TextView tv_notification;
    @BindView(R.id.tv_back)
    TextView tv_back;

    @BindView(R.id.linear_allow)
    LinearLayout linear_allow;
    @BindView(R.id.tv_allow)
    TextView tv_allow;
    @BindView(R.id.switch_button_allow)
    SwitchCompat switch_button_allow;
    @BindView(R.id.tv_choose)
    TextView tv_choose;
    @BindView(R.id.linear_deals)
    LinearLayout linear_deals;
    @BindView(R.id.tv_deals)
    TextView tv_deals;
    @BindView(R.id.switch_button_deals)
    SwitchCompat switch_button_deals;
    @BindView(R.id.linear_discussions)
    LinearLayout ll_discussions;
    @BindView(R.id.linear_gallery)
    LinearLayout ll_gallery;
    @BindView(R.id.linear_entertainment)
    LinearLayout ll_entertainment;
    @BindView(R.id.linear_classifieds)
    LinearLayout ll_classifieds;
    @BindView(R.id.linear_top_stories)
    LinearLayout ll_top_stories;
    @BindView(R.id.linear_events)
    LinearLayout ll_events;
    @BindView(R.id.linear_videos)
    LinearLayout ll_top_videos;
    @BindView(R.id.linear_jobs)
    LinearLayout ll_jobs;
    @BindView(R.id.tv_discussions)
    TextView tv_discussions;
    @BindView(R.id.tv_entertainment)
    TextView tv_entertainment;
    @BindView(R.id.tv_top_stories)
    TextView tv_top_stories;
    @BindView(R.id.tv_text_gallery)
    TextView tv_text_gallery;
    @BindView(R.id.tv_videos)
    TextView tv_videos;
    @BindView(R.id.tv_events)
    TextView tv_events;
    @BindView(R.id.tv_classifieds)
    TextView tv_classifieds;
    @BindView(R.id.tv_jobs)
    TextView tv_jobs;
    @BindView(R.id.b_notification_settings_update)
    Button btn_notification_settings_update;
    @BindView(R.id.switch_button_jobs)
    SwitchCompat sw_button_jobs;
    @BindView(R.id.switch_button_entertainment)
    SwitchCompat sw_button_entertainment;
    @BindView(R.id.switch_button_events)
    SwitchCompat sw_button_events;
    @BindView(R.id.switch_button_videos)
    SwitchCompat sw_button_videos;
    @BindView(R.id.switch_button_gallery)
    SwitchCompat sw_button_gallery;
    @BindView(R.id.switch_button_classifieds)
    SwitchCompat sw_button_classifieds;
    @BindView(R.id.switch_button_discussion)
    SwitchCompat sw_button_discussions;
    @BindView(R.id.switch_button_top_stories)
    SwitchCompat sw_button_top_stories;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;
    private Typeface mTypefaceOpenSansBold;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        mFrameLayout = (FrameLayout) getActivity().findViewById(R.id.content_frame);
        mParams = (CoordinatorLayout.LayoutParams) mFrameLayout.getLayoutParams();
        Utility.sendGoogleAnalytics(mParent, TAG);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (appBarLayout != null) {
            mParams.setBehavior(null);
            mFrameLayout.requestLayout();
            appBarLayout.setVisibility(View.GONE);
        }
        View rootView = inflater.inflate(R.layout.fragment_notifications_settings, container, false);
        ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    private void initUI() {
        setTypeFace();
    }

    @OnCheckedChanged(R.id.switch_button_allow)
    void ClassifiedsPreferenceChange(boolean isChecked) {
        if (isChecked) {
            Utility.setSharedPrefBooleanData(mParent, Constants.ALLOW_NOTIFICATIONS, true);
        } else {
            Utility.setSharedPrefBooleanData(mParent, Constants.ALLOW_NOTIFICATIONS, false);
        }
    }

    private void setTypeFace() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);
        mTypefaceOpenSansBold = Utility.getOpenSansBold(mParent);


        tv_notification.setTypeface(mTypefaceOpenSansRegular);
        tv_back.setTypeface(Utility.getMaterialIconsRegular(mParent));
        tv_classifieds.setTypeface(mTypefaceOpenSansRegular);
        tv_discussions.setTypeface(mTypefaceOpenSansRegular);
        tv_entertainment.setTypeface(mTypefaceOpenSansRegular);
        tv_events.setTypeface(mTypefaceOpenSansRegular);
        tv_jobs.setTypeface(mTypefaceOpenSansRegular);
        tv_text_gallery.setTypeface(mTypefaceOpenSansRegular);
        tv_videos.setTypeface(mTypefaceOpenSansRegular);
        tv_top_stories.setTypeface(mTypefaceOpenSansRegular);
        tv_allow.setTypeface(mTypefaceOpenSansRegular);
        tv_choose.setTypeface(mTypefaceOpenSansRegular);
        tv_deals.setTypeface(mTypefaceOpenSansRegular);
        btn_notification_settings_update.setTypeface(mTypefaceOpenSansRegular);
        getNotificationsPageContents();
    }

    private void getNotificationsPageContents() {
        String s = Utility.getSharedPrefStringData(mParent, Constants.NOTIFICATIONS_PAGE_CONTENTS);
        if (Utility.isValueNullOrEmpty(s)) {
            Utility.setSharedPrefStringData(mParent, Constants.NOTIFICATIONS_PAGE_CONTENTS, Constants.NOTIFICATIONS_PAGE_CONTENTS_DATA);
        }
        String mSharedPreferenceString = Utility.getSharedPrefStringData(mParent, Constants.NOTIFICATIONS_PAGE_CONTENTS);
        String[] namesAfterSplit = mSharedPreferenceString.split(",");
        for (int i = 0; i < namesAfterSplit.length; i++) {
            if (namesAfterSplit[i].equalsIgnoreCase("discussions")) {
                sw_button_discussions.setChecked(true);
            } else if (namesAfterSplit[i].equalsIgnoreCase("entertainments")) {
                sw_button_entertainment.setChecked(true);
            } else if (namesAfterSplit[i].equalsIgnoreCase("stories")) {
                sw_button_top_stories.setChecked(true);
            } else if (namesAfterSplit[i].equalsIgnoreCase("videos")) {
                sw_button_videos.setChecked(true);
            } else if (namesAfterSplit[i].equalsIgnoreCase("galleries")) {
                sw_button_gallery.setChecked(true);
            } else if (namesAfterSplit[i].equalsIgnoreCase("events")) {
                sw_button_events.setChecked(true);
            } else if (namesAfterSplit[i].equalsIgnoreCase("Classifieds")) {
                sw_button_classifieds.setChecked(true);
            } else if (namesAfterSplit[i].equalsIgnoreCase("jobs")) {
                sw_button_jobs.setChecked(true);
            }
        }
    }

    @OnClick(R.id.tv_back)
    public void navigateBack() {
        mParent.onBackPressed();
    }

    @OnClick(R.id.b_notification_settings_update)
    public void navigateHome() {
        Intent dashBoardIntent = new Intent(getActivity(), DashBoardActivity.class);
        startActivity(dashBoardIntent);
    }

    @OnCheckedChanged(R.id.switch_button_videos)
    void videosPreferenceChange(boolean isChecked) {
        changePreferenceSettings("videos", isChecked);
    }

    @OnCheckedChanged(R.id.switch_button_entertainment)
    void entertainmentPreferenceChange(boolean isChecked) {
        changePreferenceSettings("entertainments", isChecked);
    }

    @OnCheckedChanged(R.id.switch_button_top_stories)
    void storiesPreferenceChange(boolean isChecked) {
        changePreferenceSettings("stories", isChecked);
    }

    @OnCheckedChanged(R.id.switch_button_discussion)
    void discussionsPreferenceChange(boolean isChecked) {
        changePreferenceSettings("discussions", isChecked);
    }

    @OnCheckedChanged(R.id.switch_button_gallery)
    void galleriesPreferenceChange(boolean isChecked) {
        changePreferenceSettings("galleries", isChecked);
    }

    @OnCheckedChanged(R.id.switch_button_events)
    void eventsPreferenceChange(boolean isChecked) {
        changePreferenceSettings("events", isChecked);
    }

    @OnCheckedChanged(R.id.switch_button_classifieds)
    void classifiedsPreferenceChange(boolean isChecked) {
        changePreferenceSettings("classifieds", isChecked);
    }

    @OnCheckedChanged(R.id.switch_button_jobs)
    void jobsPreferenceChange(boolean isChecked) {
        changePreferenceSettings("jobs", isChecked);
    }

    /**
     * This is the universal method for the preferences
     */
    private void changePreferenceSettings(String name, boolean isChecked) {
        Utility.showLog("" + name, "" + isChecked);
        String mSharedPreferenceString = Utility.getSharedPrefStringData(mParent, Constants.NOTIFICATIONS_PAGE_CONTENTS);
        String[] namesAfterSplit = mSharedPreferenceString.split(",");
        ArrayList<String> namesAfterArrayList = new ArrayList<>(Arrays.asList(namesAfterSplit));
        if (isChecked) {
            String finalString = "";
            String eventString = "";
            if (!namesAfterArrayList.contains(name))
                namesAfterArrayList.add(name);
            for (int i = 0; i < namesAfterArrayList.size(); i++) {
                if (i == namesAfterArrayList.size()) {
                    finalString = finalString + namesAfterArrayList.get(i);
                    if (namesAfterArrayList.get(i).equalsIgnoreCase("events")
                            || namesAfterArrayList.get(i).equalsIgnoreCase("classifieds")
                            || namesAfterArrayList.get(i).equalsIgnoreCase("jobs")) {
                        eventString = eventString + namesAfterArrayList.get(i);
                    }
                } else {
                    finalString = finalString + namesAfterArrayList.get(i) + ",";
                    if (namesAfterArrayList.get(i).equalsIgnoreCase("events")
                            || namesAfterArrayList.get(i).equalsIgnoreCase("classifieds")
                            || namesAfterArrayList.get(i).equalsIgnoreCase("jobs")) {
                        eventString = eventString + namesAfterArrayList.get(i) + ",";
                    }
                }
            }
            Utility.setSharedPrefStringData(mParent, Constants.NOTIFICATIONS_PAGE_CONTENTS, finalString);
            Utility.setSharedPrefStringData(mParent, Constants.NOTIFICATIONS_PAGE_EVENTS_CONTENTS, eventString);
        } else {
            String finalString = "";
            String eventString = "";
            int index = namesAfterArrayList.indexOf(name);
            namesAfterArrayList.remove(index);
            for (int i = 0; i < namesAfterArrayList.size(); i++) {
                if (i == namesAfterArrayList.size()) {
                    finalString = finalString + namesAfterArrayList.get(i);
                    if (namesAfterArrayList.get(i).equalsIgnoreCase("events")
                            || namesAfterArrayList.get(i).equalsIgnoreCase("classifieds")
                            || namesAfterArrayList.get(i).equalsIgnoreCase("jobs")) {
                        eventString = eventString + namesAfterArrayList.get(i);
                    }
                } else {
                    finalString = finalString + namesAfterArrayList.get(i) + ",";
                    if (namesAfterArrayList.get(i).equalsIgnoreCase("events")
                            || namesAfterArrayList.get(i).equalsIgnoreCase("classifieds")
                            || namesAfterArrayList.get(i).equalsIgnoreCase("jobs")) {
                        eventString = eventString + namesAfterArrayList.get(i) + ",";
                    }
                }
            }
            Utility.setSharedPrefStringData(mParent, Constants.NOTIFICATIONS_PAGE_CONTENTS, finalString);
            Utility.setSharedPrefStringData(mParent, Constants.NOTIFICATIONS_PAGE_EVENTS_CONTENTS, eventString);
        }


    }

    @OnClick(R.id.b_notification_settings_update)
    public void updateHome() {
        updateDeviceData();
        Intent dashBoardIntent = new Intent(getActivity(), DashBoardActivity.class);
        startActivity(dashBoardIntent);
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
        paramMap.put("notifications", Utility.getSharedPrefStringData(mParent, Constants.NOTIFICATIONS_PAGE_CONTENTS)
                + "," + "ads,banners," + Utility.getSharedPrefStringData(mParent, Constants.NOTIFICATIONS_PAGE_EVENTS_CONTENTS));

        DeviceTokenUpdateParser mDeviceTokenUpdateParser = new DeviceTokenUpdateParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(mParent, Utility.getResourcesString(mParent,
                R.string.please_wait), false,
                APIConstants.UPDATE_DEVICE_PREFERENCE, paramMap,
                APIConstants.REQUEST_TYPE.POST, this, mDeviceTokenUpdateParser);
        Utility.execute(serverIntractorAsync);
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof DeviceTokenUpdateModel) {
                Utility.showToastMessage(mParent, "Updated successfully");
            }
        }
    }
}
