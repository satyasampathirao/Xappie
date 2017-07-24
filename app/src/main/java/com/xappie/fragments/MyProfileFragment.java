package com.xappie.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shankar 21/07/2017
 */
public class MyProfileFragment extends Fragment {

    public static final String TAG = MyProfileFragment.class.getSimpleName();
    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;

    @BindView(R.id.tv_my_arrow_back_icon)
    TextView tv_my_arrow_back_icon;
    @BindView(R.id.tv_my_menu_icon)
    TextView tv_my_menu_icon;
    @BindView(R.id.tv_my_profile)
    TextView tv_my_profile;
    @BindView(R.id.tv_my_language_icon)
    TextView tv_my_language_icon;
    @BindView(R.id.tv_my_notifications_icon)
    TextView tv_my_notifications_icon;
    @BindView(R.id.tv_my_location_icon)
    TextView tv_my_location_icon;
   @BindView(R.id.tv_ravi_profile_name)
    TextView tv_ravi_profile_name;
    @BindView(R.id.tv_ravi_hyd)
    TextView tv_ravi_hyd;
    @BindView(R.id.tv_full_name)
    TextView tv_full_name;
    @BindView(R.id.tv_profile_full_name)
    TextView tv_profile_full_name;
    @BindView(R.id.tv_display_name)
    TextView tv_display_name;
    @BindView(R.id.tv_profile_display_name)
    TextView tv_profile_display_name;
    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_ravi_email)
    TextView tv_ravi_email;
    @BindView(R.id.tv_mobile_no)
    TextView tv_mobile_no;
    @BindView(R.id.tv_ravi_mobile_nbr)
    TextView tv_ravi_mobile_nbr;
    @BindView(R.id.b_edit_profile)
    Button btn_edit_profile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (appBarLayout != null)
            appBarLayout.setVisibility(View.GONE);
        View rootView = inflater.inflate(R.layout.fragment_my_profile, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    /**
     * This method is used to initialization
     */
    private void initUI() {

        tv_my_arrow_back_icon.setTypeface(Utility.getMaterialIconsRegular(getActivity()));
        tv_my_menu_icon.setTypeface(Utility.getMaterialIconsRegular(getActivity()));
        tv_my_language_icon.setTypeface(Utility.getMaterialIconsRegular(getActivity()));
        tv_my_location_icon.setTypeface(Utility.getMaterialIconsRegular(getActivity()));
        tv_my_notifications_icon.setTypeface(Utility.getMaterialIconsRegular(getActivity()));
        tv_my_profile.setTypeface(Utility.getOpenSansRegular(getActivity()));
      tv_ravi_profile_name.setTypeface(Utility.getOpenSansRegular(getActivity()));
        tv_ravi_hyd.setTypeface(Utility.getOpenSansRegular(getActivity()));
        tv_full_name.setTypeface(Utility.getOpenSansRegular(getActivity()));
        tv_profile_full_name.setTypeface(Utility.getOpenSansRegular(getActivity()));
        tv_display_name.setTypeface(Utility.getOpenSansRegular(getActivity()));
        tv_profile_display_name.setTypeface(Utility.getOpenSansRegular(getActivity()));
        tv_email.setTypeface(Utility.getOpenSansRegular(getActivity()));
        tv_ravi_email.setTypeface(Utility.getOpenSansRegular(getActivity()));
        tv_mobile_no.setTypeface(Utility.getOpenSansRegular(getActivity()));
        tv_ravi_mobile_nbr.setTypeface(Utility.getOpenSansRegular(getActivity()));
        btn_edit_profile.setTypeface(Utility.getOpenSansRegular(getActivity()));
    }

  @OnClick(R.id.b_edit_profile)
    public void navigateEditProfile()
    {
        Utility.navigateDashBoardFragment(new EditProfileFragment(),EditProfileFragment.TAG,null,mParent);
    }

    @OnClick(R.id.tv_my_arrow_back_icon)
    public void navigateBack()
    {
       mParent.onBackPressed();
    }
    @OnClick(R.id.tv_my_notifications_icon)
    public void navigateToNotification()
    {
        Utility.navigateDashBoardFragment(new NotificationsFragment(),NotificationsFragment.TAG,null,mParent);
    }
}
