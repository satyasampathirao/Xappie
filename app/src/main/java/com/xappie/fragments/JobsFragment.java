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


    @BindView(R.id.tv_find_jobs)
    TextView tv_find_jobs;
    @BindView(R.id.tv_post_job)
    TextView tv_post_job;

    public static TextView tv_my_posts;
    @BindView(R.id.tv_jobs_applied)
    TextView tv_jobs_applied;
    @BindView(R.id.tv_jobs_search)
    TextView tv_jobs_search;

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

        if (rootView != null) {
            return rootView;
        }

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_jobs, container, false);
        tv_my_posts = rootView.findViewById(R.id.tv_my_posts);
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
    }

    private void setTypeFace() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);

        tv_notification_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(Utility.getResourcesString(mParent, R.string.jobs));
        tv_title.setTypeface(mTypefaceOpenSansRegular);

        tv_location_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notifications_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        Utility.navigateAllJobsFragment(new FindJobsListFragment(), FindJobsListFragment.TAG, null, mParent);

    }

    @OnClick(R.id.tv_find_jobs)
    void navigateFindJobs() {
        Utility.navigateAllJobsFragment(new FindJobsListFragment(), FindJobsListFragment.TAG, null, mParent);
    }

    @OnClick(R.id.tv_post_job)
    void navigatePostJobs() {
        if (!Utility.getSharedPrefBooleanData(mParent, Constants.IS_LOGIN_COMPLETED)) {
            Utility.showToastMessage(mParent, "Please Login to access the features");
            Intent intent = new Intent(mParent, LoginActivity.class);
            startActivity(intent);
        } else {
            Utility.navigateAllJobsFragment(new PostJobFragment(), PostJobFragment.TAG, null, mParent);
        }
    }

    @OnClick(R.id.tv_my_posts)
    void navigateMyPostJobs() {
        if (!Utility.getSharedPrefBooleanData(mParent, Constants.IS_LOGIN_COMPLETED)) {
            Utility.showToastMessage(mParent, "Please Login to access the features");
            Intent intent = new Intent(mParent, LoginActivity.class);
            startActivity(intent);
        } else {
            Utility.navigateAllJobsFragment(new AllMyJobsFragment(), AllMyJobsFragment.TAG, null, mParent);
        }
    }

    @OnClick(R.id.tv_jobs_applied)
    void navigateJobsApplied() {
        if (!Utility.getSharedPrefBooleanData(mParent, Constants.IS_LOGIN_COMPLETED)) {
            Utility.showToastMessage(mParent, "Please Login to access the features");
            Intent intent = new Intent(mParent, LoginActivity.class);
            startActivity(intent);
        } else {
            Utility.navigateAllJobsFragment(new JobsAppliedFragment(), JobsAppliedFragment.TAG, null, mParent);
        }
    }

    @OnClick(R.id.tv_jobs_search)
    void navigateJobsSearch() {
        Utility.navigateAllJobsFragment(new JobsSearchFragment(), JobsSearchFragment.TAG, null, mParent);
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

