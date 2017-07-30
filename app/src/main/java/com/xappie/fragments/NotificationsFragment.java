package com.xappie.fragments;


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

    @BindView(R.id.notification_list_item)
    ListView notification_list_item;

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
        tv_notification_arrow_back_icon.setTypeface(Utility.getMaterialIconsRegular(getActivity()));
        tv_notification_menu_icon.setTypeface(Utility.getMaterialIconsRegular(getActivity()));
        tv_notification_settings_icon.setTypeface(Utility.getMaterialIconsRegular(getActivity()));
        tv_notification.setTypeface(Utility.getOpenSansRegular(getActivity()));
        tv_settings.setTypeface(Utility.getOpenSansRegular(getActivity()));

        notification_list_item.setAdapter(new NotificationListAdapter(mParent, getSampleData()));
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

    @OnClick(R.id.tv_notification_settings_icon)
    public void navigateNotificationsSettings() {
        Utility.navigateDashBoardFragment(new NotificationsSettingsFragment(), NotificationsSettingsFragment.TAG, null, mParent);
    }

    @OnClick(R.id.tv_settings)
    public void navigateNotificationSettings() {
        Utility.navigateDashBoardFragment(new NotificationsSettingsFragment(), NotificationsSettingsFragment.TAG, null, mParent);
    }

    @OnClick(R.id.tv_notification_arrow_back_icon)
    public void navigateToBack()
    {
        mParent.onBackPressed();
    }

}
