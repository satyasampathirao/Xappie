package com.xappie.fragments;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.AdapterforNotificationList;
import com.xappie.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {

    public static final String TAG = NotificationsFragment.class.getSimpleName();

    private int[] heading = {
            R.string.prabhas_meets,
            R.string.khaidi_russia,
            R.string.khaidi_firstlook,
            R.string.prabhas_meets,
            R.string.khaidi_russia,
            R.string.khaidi_firstlook,
            R.string.prabhas_meets,
            R.string.khaidi_russia,
            R.string.khaidi_firstlook
    };

    private int[] time = {
            R.string._5m,
            R.string._1hr,
            R.string._6hr,
            R.string._1day,
            R.string._3day,
            R.string._1week,
            R.string._1week,
            R.string._1week,
            R.string._1week

    };

    private String[] name =
            {
                    "Chiranjeevi",
                    "Pawan Kalyan",
                    " ",
                    " ",
                    " ",
                    " ",
                    " ",
                    " ",
                    " "

            };

            private String[] dots = {
                     "\u2022",
                    "\u2022",
                    " ",
                    " ",
                    " ",
                    " ",
                    " ",
                    " ",
                    " "

            };


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


    private DashBoardActivity mParent;
    private Toolbar mToolbar;
    private AppBarLayout appBarLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (appBarLayout != null)
            appBarLayout.setVisibility(View.GONE);
        View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
        ListView lv_notication_item = (ListView)rootView.findViewById(R.id.notification_list_item);
  lv_notication_item.setAdapter(new AdapterforNotificationList(mParent,heading,time,name,dots));

        ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    private void initUI()
    {
        tv_notification_arrow_back_icon.setTypeface(Utility.getMaterialIconsRegular(getActivity()));
        tv_notification_menu_icon.setTypeface(Utility.getMaterialIconsRegular(getActivity()));
        tv_notification_settings_icon.setTypeface(Utility.getMaterialIconsRegular(getActivity()));
        tv_notification.setTypeface(Utility.getOpenSansRegular(getActivity()));
        tv_settings.setTypeface(Utility.getOpenSansRegular(getActivity()));
    }

    @OnClick(R.id.tv_notification_settings_icon)
    public void navigateNotificationsSettings ()
    {
        Utility.navigateDashBoardFragment(new NotificationsSettingsFragment(),NotificationsSettingsFragment.TAG,null,mParent);
    }
    @OnClick(R.id.tv_settings)
    public void navigateNotificationSettings ()
    {
        Utility.navigateDashBoardFragment(new NotificationsSettingsFragment(),NotificationsSettingsFragment.TAG,null,mParent);
    }



}
