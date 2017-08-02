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
import com.xappie.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsSettingsFragment extends Fragment {

    public static final String TAG = NotificationsSettingsFragment.class.getSimpleName();
    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;

    @BindView(R.id.tv_notification_arrow_back_icon)
    TextView tv_notification_arrow_back_icon;
    @BindView(R.id.tv_notification_menu_icon)
    TextView tv_notification_menu_icon;
    @BindView(R.id.tv_notification)
    TextView tv_notification;
    @BindView(R.id.tv_notification_settings_icon)
    TextView tv_notification_settings_icon;
    @BindView(R.id.tv_settings)
    TextView tv_settings;
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
    private void setTypeFace() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);
        mTypefaceOpenSansBold = Utility.getOpenSansBold(mParent);

        tv_notification_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_settings_icon.setVisibility(View.GONE);
        tv_notification.setTypeface(mTypefaceOpenSansBold);
        tv_settings.setVisibility(View.GONE);
        tv_classifieds.setTypeface(mTypefaceOpenSansRegular);
        tv_discussions.setTypeface(mTypefaceOpenSansRegular);
        tv_entertainment.setTypeface(mTypefaceOpenSansRegular);
        tv_events.setTypeface(mTypefaceOpenSansRegular);
        tv_jobs.setTypeface(mTypefaceOpenSansRegular);
        tv_text_gallery.setTypeface(mTypefaceOpenSansRegular);
        tv_videos.setTypeface(mTypefaceOpenSansRegular);
        tv_top_stories.setTypeface(mTypefaceOpenSansRegular);
        btn_notification_settings_update.setTypeface(mTypefaceOpenSansRegular);
    }
    @OnClick({R.id.tv_notification_arrow_back_icon,R.id.tv_notification_menu_icon})
    public void navigateBack() {
        mParent.onBackPressed();
    }

    @OnClick(R.id.b_notification_settings_update)
    public void navigateHome() {
        Intent dashBoardIntent = new Intent(getActivity(), DashBoardActivity.class);
        startActivity(dashBoardIntent);
    }
}
