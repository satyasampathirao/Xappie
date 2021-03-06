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
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.activities.LoginActivity;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shankar on 7/28/2017.
 */


public class EventsFragment extends Fragment {

    public static final String TAG = EventsFragment.class.getSimpleName();
    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;

    /**
     * Events Toolbar
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

    @BindView(R.id.tv_all_events)
    TextView tv_all_events;

    public static TextView tv_my_events;
    @BindView(R.id.tv_add_new_event)
    TextView tv_add_new_event;

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

        rootView = inflater.inflate(R.layout.fragment_events, container, false);
        ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    /**
     * This method is used for back from the fragment
     */
    @OnClick({R.id.tv_notification_arrow_back_icon,
            R.id.tv_notification_menu_icon})
    void backToTheHome() {
        mParent.onBackPressed();
    }

    private void initUI() {
        tv_my_events = rootView.findViewById(R.id.tv_my_events);
        setTypeFace();
    }

    private void setTypeFace() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);

        tv_notification_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(Utility.getResourcesString(mParent, R.string.events));
        tv_title.setTypeface(mTypefaceOpenSansRegular);

        tv_location_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notifications_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_my_events.setTypeface(mTypefaceOpenSansRegular);
        tv_add_new_event.setTypeface(mTypefaceOpenSansRegular);
        tv_all_events.setTypeface(mTypefaceOpenSansRegular);

        Utility.navigateAllEventsFragment(new AllEventsListFragment(), AllEventsListFragment.TAG, null, mParent);

    }

    /**
     * This method is used to navigate all events fragment
     */
    @OnClick(R.id.tv_all_events)
    void navigateAllEvents() {
        Utility.navigateAllEventsFragment(new AllEventsListFragment(), AllEventsListFragment.TAG, null, mParent);
    }

    /**
     * This method is used to navigate all events fragment
     */
    @OnClick(R.id.tv_add_new_event)
    void navigateAddNewEvents() {
        if (!Utility.getSharedPrefBooleanData(mParent, Constants.IS_LOGIN_COMPLETED)) {
            Utility.showToastMessage(mParent, "Please Login to access the features");
            Intent intent = new Intent(mParent, LoginActivity.class);
            startActivity(intent);
        } else {
            Utility.navigateAllEventsFragment(new AddNewEventFragment(), AddNewEventFragment.TAG, null, mParent);
        }
    }

    /**
     * This method is used to navigate all events fragment
     */
    @OnClick(R.id.tv_my_events)
    void navigateMyEvents() {
        if (!Utility.getSharedPrefBooleanData(mParent, Constants.IS_LOGIN_COMPLETED)) {
            Utility.showToastMessage(mParent, "Please Login to access the features");
            Intent intent = new Intent(mParent, LoginActivity.class);
            startActivity(intent);
        } else {
            Utility.navigateAllEventsFragment(new AllMyEventsFragment(), AllMyEventsFragment.TAG, null, mParent);
        }
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
