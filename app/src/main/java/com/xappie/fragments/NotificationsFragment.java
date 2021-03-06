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
import android.widget.ListView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.NotificationListAdapter;
import com.xappie.models.NotificationsListModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shankar 21/07/2017
 */
public class NotificationsFragment extends Fragment {

    public static final String TAG = NotificationsFragment.class.getSimpleName();


    @BindView(R.id.tv_notification)
    TextView tv_notification;
    @BindView(R.id.tv_notification_settings_icon)
    TextView tv_notification_settings_icon;
    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.no_notifications)
    TextView no_notifications;

    @BindView(R.id.notification_list_item)
    ListView notification_list_item;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;
    private Typeface mTypefaceOpenSansBold;

    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;

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
        View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
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
        mTypefaceOpenSansBold = Utility.getOpenSansBold(mParent);


        tv_notification_settings_icon.setTypeface(Utility.getFontAwesomeWebFont(getActivity()));
        tv_notification.setTypeface(mTypefaceOpenSansRegular);
        tv_back.setTypeface(Utility.getMaterialIconsRegular(getActivity()));
        no_notifications.setTypeface(mTypefaceOpenSansRegular);
        notification_list_item.setAdapter(new NotificationListAdapter(mParent, getSampleData()));
        notification_list_item.setVisibility(View.GONE);
    }
    private ArrayList<NotificationsListModel> getSampleData() {
        ArrayList<NotificationsListModel> notificationsListModels = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            NotificationsListModel notificationsListModel = new NotificationsListModel();
            notificationsListModel.setName("Chiranjeevi");
            notificationsListModel.setTime("5 min ago");
            notificationsListModel.setTitle("Prabhas meets Chiru on Khaidi No. 150 sets");
            notificationsListModels.add(notificationsListModel);
        }

        return notificationsListModels;
    }

    @OnClick({R.id.tv_notification_settings_icon})
    public void navigateNotificationsSettings() {
        Utility.navigateDashBoardFragment(new NotificationsSettingsFragment(), NotificationsSettingsFragment.TAG, null, mParent);
    }

    @OnClick(R.id.tv_back)
    public void navigateToBack()
    {
        mParent.onBackPressed();
    }

}
