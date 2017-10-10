package com.xappie.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by Shankar 04/09/2017
 */
public class HomePageCustomizationFragment extends Fragment {

    public static final String TAG = HomePageCustomizationFragment.class.getSimpleName();
    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;

    @BindView(R.id.tv_home_customization_arrow_back_icon)
    TextView tv_home_customization_arrow_back_icon;
    @BindView(R.id.tv_preference)
    TextView tv_preference;
    @BindView(R.id.tv_home_page)
    TextView tv_home_page;

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


    @BindView(R.id.switch_button_jobs)
    SwitchCompat sw_button_jobs;
    @BindView(R.id.switch_button_entertainment)
    SwitchCompat switch_button_entertainment;
    @BindView(R.id.switch_button_events)
    SwitchCompat sw_button_events;
    @BindView(R.id.switch_button_videos)
    SwitchCompat switch_button_videos;
    @BindView(R.id.switch_button_gallery)
    SwitchCompat sw_button_gallery;
    @BindView(R.id.switch_button_classifieds)
    SwitchCompat sw_button_classifieds;
    @BindView(R.id.switch_button_discussion)
    SwitchCompat switch_button_discussion;
    @BindView(R.id.switch_button_top_stories)
    SwitchCompat switch_button_top_stories;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;


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
        if (appBarLayout != null) {
            mParams.setBehavior(null);
            mFrameLayout.requestLayout();
            appBarLayout.setVisibility(View.GONE);
        }
        View rootView = inflater.inflate(R.layout.fragment_home_page_customization, container, false);
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

        tv_home_customization_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_preference.setTypeface(mTypefaceOpenSansRegular);
        tv_home_page.setTypeface(mTypefaceOpenSansRegular);

        tv_classifieds.setTypeface(mTypefaceOpenSansRegular);
        tv_discussions.setTypeface(mTypefaceOpenSansRegular);
        tv_entertainment.setTypeface(mTypefaceOpenSansRegular);
        tv_events.setTypeface(mTypefaceOpenSansRegular);
        tv_jobs.setTypeface(mTypefaceOpenSansRegular);
        tv_text_gallery.setTypeface(mTypefaceOpenSansRegular);
        tv_videos.setTypeface(mTypefaceOpenSansRegular);
        tv_top_stories.setTypeface(mTypefaceOpenSansRegular);
        getHomePageContents();
    }

    /**
     * This method is used to get the home page contents
     */
    private void getHomePageContents() {
        String s = Utility.getSharedPrefStringData(mParent, Constants.HOME_PAGE_CONTENTS);
        if (Utility.isValueNullOrEmpty(s)) {
            Utility.setSharedPrefStringData(mParent, Constants.HOME_PAGE_CONTENTS, Constants.HOME_PAGE_CONTENTS_DATA);
        }
        String mSharedPreferenceString = Utility.getSharedPrefStringData(mParent, Constants.HOME_PAGE_CONTENTS);
        String[] namesAfterSplit = mSharedPreferenceString.split(",");
        for (int i = 0; i < namesAfterSplit.length; i++) {
            if (namesAfterSplit[i].equalsIgnoreCase("discussions")) {
                switch_button_discussion.setChecked(true);
            } else if (namesAfterSplit[i].equalsIgnoreCase("entertainment")) {
                switch_button_entertainment.setChecked(true);
            } else if (namesAfterSplit[i].equalsIgnoreCase("stories")) {
                switch_button_top_stories.setChecked(true);
            } else if (namesAfterSplit[i].equalsIgnoreCase("videos")) {
                switch_button_videos.setChecked(true);
            } else if (namesAfterSplit[i].equalsIgnoreCase("gallery")) {
                switch_button_videos.setChecked(true);
            }
        }
    }

    @OnClick({R.id.tv_home_customization_arrow_back_icon, R.id.tv_preference})
    public void navigateBack() {
        mParent.onBackPressed();
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

    /**
     * This is the universal method for the preferences
     */
    private void changePreferenceSettings(String name, boolean isChecked) {
        Utility.showLog("" + name, "" + isChecked);
        String mSharedPreferenceString = Utility.getSharedPrefStringData(mParent, Constants.HOME_PAGE_CONTENTS);
        String[] namesAfterSplit = mSharedPreferenceString.split(",");
        ArrayList<String> namesAfterArrayList = new ArrayList<>(Arrays.asList(namesAfterSplit));
        if (isChecked) {
            String finalString = "";
            if (!namesAfterArrayList.contains(name))
                namesAfterArrayList.add(name);
            for (int i = 0; i < namesAfterArrayList.size(); i++) {
                if (i == namesAfterArrayList.size()) {
                    finalString = finalString + namesAfterArrayList.get(i);
                } else {
                    finalString = finalString + namesAfterArrayList.get(i) + ",";
                }
            }
            Utility.setSharedPrefStringData(mParent, Constants.HOME_PAGE_CONTENTS, finalString);
        } else {
            String finalString = "";
            int index = namesAfterArrayList.indexOf(name);
            namesAfterArrayList.remove(index);
            for (int i = 0; i < namesAfterArrayList.size(); i++) {
                if (i == namesAfterArrayList.size()) {
                    finalString = finalString + namesAfterArrayList.get(i);
                } else {
                    finalString = finalString + namesAfterArrayList.get(i) + ",";
                }
            }
            Utility.setSharedPrefStringData(mParent, Constants.HOME_PAGE_CONTENTS, finalString);
        }

        HomeFragment.getInstance().updateData();
    }

}
