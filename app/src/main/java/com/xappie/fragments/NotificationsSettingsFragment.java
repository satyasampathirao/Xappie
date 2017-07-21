package com.xappie.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.AdapterforNotificationsSettingsList;
import com.xappie.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsSettingsFragment extends Fragment {

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

    public static final String TAG = NotificationsSettingsFragment.class.getSimpleName();
    private DashBoardActivity mParent;

    ListView lv_notifications_settings;


    private int[] setting_text = {
            R.string.discussions,
            R.string.entertainment,
            R.string.top_stories,
            R.string.gallery,
            R.string.videos,
            R.string.events,
            R.string.classifieds,
            R.string.jobs


    };

    private boolean[] check_state = {
            true,
            false,false,
            true,false,false,false,true

    };

    public NotificationsSettingsFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_notifications_settings, container, false);
        lv_notifications_settings = (ListView) rootView.findViewById(R.id.notification_settings_list_item);
        lv_notifications_settings.setAdapter(new AdapterforNotificationsSettingsList(mParent,setting_text,check_state));

        ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    private void initUI() {
        tv_notification_arrow_back_icon.setTypeface(Utility.getMaterialIconsRegular(getActivity()));
        tv_notification_menu_icon.setTypeface(Utility.getMaterialIconsRegular(getActivity()));
        tv_notification_settings_icon.setVisibility(View.GONE);
        tv_settings.setVisibility(View.GONE);

    }

}
