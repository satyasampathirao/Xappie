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
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreferenceFragment extends Fragment {

    public static final String TAG = PreferenceFragment.class.getSimpleName();
    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;

    /**
     * Gallery Toolbar
     */


    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_location_icon)
    TextView tv_location_icon;
    @BindView(R.id.tv_notification_menu_icon)
    TextView tv_notifications_icon;
    @BindView(R.id.tv_language_icon)
    TextView tv_language_icon;
    @BindView(R.id.tv_customization_icon)
    TextView tv_customization_icon;
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.tv_notification)
    TextView tv_notification;
    @BindView(R.id.tv_language)
    TextView tv_language;
    @BindView(R.id.tv_customization)
    TextView tv_customization;
    @BindView(R.id.tv_back)
    TextView tv_back;



    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;



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
        View rootView = inflater.inflate(R.layout.fragment_preferences, container, false);
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
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);


        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(Utility.getResourcesString(mParent, R.string.preferences));
        tv_title.setTypeface(mTypefaceOpenSansRegular);

        tv_location_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notifications_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_customization_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language.setTypeface(mTypefaceOpenSansRegular);
        tv_customization.setTypeface(mTypefaceOpenSansRegular);
        tv_notification.setTypeface(mTypefaceOpenSansRegular);
        tv_location.setTypeface(mTypefaceOpenSansRegular);
        tv_back.setTypeface(Utility.getMaterialIconsRegular(mParent));

    }

    /**
     * This method is used for back from the fragment
     */
    @OnClick(R.id.tv_back)
    void backToTheHome() {
        mParent.onBackPressed();
    }

    @OnClick({R.id.tv_location, R.id.tv_location_icon})

    void navigateToLocation()
    {
        Utility.navigateDashBoardFragment(new CountriesFragment(), CountriesFragment.TAG, null, mParent);
    }

    @OnClick({R.id.tv_notification, R.id.tv_notification_menu_icon})

    void navigateToNotifications()
    {
        Utility.navigateDashBoardFragment(new NotificationsSettingsFragment(), NotificationsSettingsFragment.TAG, null, mParent);
    }

    @OnClick ({R.id.tv_language_icon,R.id.tv_language})

    void navigateToLanguages()
    {
        Utility.navigateDashBoardFragment(new LanguageFragment(),LanguageFragment.TAG,null,mParent);
    }

    @OnClick ({R.id.tv_customization,R.id.tv_customization_icon})

    void navigateToCustomization()
    {
        Utility.navigateDashBoardFragment(new HomePageCustomizationFragment(),HomePageCustomizationFragment.TAG,null,mParent);
    }
}
