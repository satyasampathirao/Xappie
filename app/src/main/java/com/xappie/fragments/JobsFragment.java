package com.xappie.fragments;


import android.annotation.SuppressLint;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.activities.LoginActivity;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobsFragment extends Fragment {

    public static final String TAG = JobsFragment.class.getSimpleName();
    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;

    private LinearLayout layout_tabs;

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

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;

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

        if (appBarLayout != null) {
            mParams.setBehavior(null);
            mFrameLayout.requestLayout();
            appBarLayout.setVisibility(View.GONE);
        }

        if (rootView != null) {
            return rootView;
        }

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_jobs, container, false);
        layout_tabs = (LinearLayout) rootView.findViewById(R.id.layout_tabs);
        ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    @OnClick({R.id.tv_notification_arrow_back_icon,
            R.id.tv_notification_menu_icon})
    void backToTheHome() {
        mParent.onBackPressed();
    }

    private void initUI() {
        setTypeFace();
        setDataToHomeTabs();
    }

    private void setTypeFace() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);

        tv_notification_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(Utility.getResourcesString(mParent, R.string.bay_area_jobs));
        tv_title.setTypeface(mTypefaceOpenSansRegular);

        tv_location_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notifications_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        Utility.navigateAllJobsFragment(new FindJobsListFragment(), FindJobsListFragment.TAG, null, mParent);

    }

    private int selected_position = 0;

    private void setDataToHomeTabs() {
        layout_tabs.removeAllViews();
        for (int i = 0; i < getTabNames().size(); i++) {
            @SuppressLint("InflateParams")
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.textview_layout, null);
            TextView tv_title = (TextView) ll.findViewById(R.id.tv_title);
            tv_title.setText(getTabNames().get(i));
            if (i == selected_position) {
                tv_title.setTypeface(Utility.getOpenSansBold(mParent));
            } else {
                tv_title.setTypeface(Utility.getOpenSansRegular(mParent));
            }
            tv_title.setId(i);
            tv_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = view.getId();
                    selected_position = pos;
                    switch (selected_position) {
                        case 0:
                            Utility.navigateAllJobsFragment(new FindJobsListFragment(), FindJobsListFragment.TAG, null, mParent);
                            break;
                        case 1:
                            if (!Utility.getSharedPrefBooleanData(mParent, Constants.IS_LOGIN_COMPLETED)) {
                                Utility.showToastMessage(mParent, "To post a Job, Login First");
                                Intent intent = new Intent(mParent, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Utility.navigateAllJobsFragment(new PostJobFragment(), PostJobFragment.TAG, null, mParent);
                            }
                            break;
                        case 2:
                            if (!Utility.getSharedPrefBooleanData(mParent, Constants.IS_LOGIN_COMPLETED)) {
                                Utility.showToastMessage(mParent, "Login First");
                                Intent intent = new Intent(mParent, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Utility.navigateAllJobsFragment(new AllMyJobsFragment(), AllMyJobsFragment.TAG, null, mParent);
                            }
                            break;
                        case 3:
                            Utility.navigateAllJobsFragment(new JobsAppliedFragment(), JobsAppliedFragment.TAG, null, mParent);
                            break;
                        case 4:
                            Utility.navigateAllJobsFragment(new JobsSearchFragment(), JobsSearchFragment.TAG, null, mParent);
                            break;
                    }
                }
            });
            layout_tabs.addView(ll);
        }
    }

    private ArrayList<String> getTabNames() {
        ArrayList<String> mTabNames = new ArrayList<>();
        mTabNames.add(Utility.getResourcesString(mParent, R.string.find_jobs).toUpperCase());
        mTabNames.add(Utility.getResourcesString(mParent, R.string.post_job).toUpperCase());
        mTabNames.add(Utility.getResourcesString(mParent, R.string.my_posts).toUpperCase());
        mTabNames.add(Utility.getResourcesString(mParent, R.string.jobs_applied).toUpperCase());
        mTabNames.add(Utility.getResourcesString(mParent, R.string.jobs_search).toUpperCase());

        return mTabNames;
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

}

