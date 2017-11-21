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
 * Created by Shankar on 11/21/2017.
 */


public class ClassifiedsTabFragment extends Fragment {

    public static final String TAG = ClassifiedsTabFragment.class.getSimpleName();
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

    @BindView(R.id.tv_all_classifieds)
    TextView tv_all_classifieds;
    @BindView(R.id.tv_add_classified)
    TextView tv_add_classified;
    @BindView(R.id.tv_my_classifieds)
    TextView tv_my_classifieds;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;

    private String mId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        mFrameLayout = (FrameLayout) getActivity().findViewById(R.id.content_frame);
        mParams = (CoordinatorLayout.LayoutParams) mFrameLayout.getLayoutParams();
        if (getArguments() != null && getArguments().containsKey(Constants.CLASSIFIEDS_CATEGORY_ID)) {
            mId = getArguments().getString(Constants.CLASSIFIEDS_CATEGORY_ID);
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
        View rootView = inflater.inflate(R.layout.fragment_classifieds_tab, container, false);
        ButterKnife.bind(this, rootView);
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

        tv_notification_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(Utility.getResourcesString(mParent, R.string.classifieds));
        tv_title.setTypeface(mTypefaceOpenSansRegular);

        tv_location_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notifications_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_all_classifieds.setTypeface(mTypefaceOpenSansRegular);
        tv_add_classified.setTypeface(mTypefaceOpenSansRegular);
        tv_my_classifieds.setTypeface(mTypefaceOpenSansRegular);

        Bundle bundle = new Bundle();
        bundle.putString(Constants.CLASSIFIEDS_CATEGORY_ID, mId);
        Utility.navigateAllEventsFragment(new ClassifiedsListFragment(), ClassifiedsListFragment.TAG, null, mParent);

    }

    /**
     * This method is used to navigate all events fragment
     */
    @OnClick(R.id.tv_all_classifieds)
    void navigateAllEvents() {
        Utility.navigateAllEventsFragment(new ClassifiedsListFragment(), ClassifiedsListFragment.TAG, null, mParent);
    }

    /**
     * This method is used to navigate all events fragment
     */
    @OnClick(R.id.tv_add_classified)
    void navigateAddNewEvents() {
        if (!Utility.getSharedPrefBooleanData(mParent, Constants.IS_LOGIN_COMPLETED)) {
            Utility.showToastMessage(mParent, "Login First");
            Intent intent = new Intent(mParent, LoginActivity.class);
            startActivity(intent);
        } else {
            Utility.navigateAllEventsFragment(new ClassifiedsAddFragment(), ClassifiedsAddFragment.TAG, null, mParent);
        }
    }

    /**
     * This method is used to navigate all events fragment
     */
    @OnClick(R.id.tv_my_classifieds)
    void navigateMyEvents() {
        if (!Utility.getSharedPrefBooleanData(mParent, Constants.IS_LOGIN_COMPLETED)) {
            Utility.showToastMessage(mParent, "Login First");
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
