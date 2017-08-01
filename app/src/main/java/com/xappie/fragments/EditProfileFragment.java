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
import android.widget.Button;
import android.widget.EditText;
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
 * Created by Shankar 21/07/2017
 */
public class EditProfileFragment extends Fragment {

    public static final String TAG = EditProfileFragment.class.getSimpleName();
    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;

    @BindView(R.id.tv_edit_arrow_back_icon)
    TextView tv_edit_arrow_back_icon;
    @BindView(R.id.tv_edit_menu_icon)
    TextView tv_edit_menu_icon;
    @BindView(R.id.tv_edit_profile)
    TextView tv_edit_profile;
    @BindView(R.id.tv_edit_language_icon)
    TextView tv_edit_language_icon;
    @BindView(R.id.tv_edit_notifications_icon)
    TextView tv_edit_notifications_icon;
    @BindView(R.id.tv_edit_location_icon)
    TextView tv_edit_location_icon;
    @BindView(R.id.tv_edit_profile_full_name)
    TextView tv_edit_profile_full_name;
    @BindView(R.id.edit_text_full_name)
    EditText edt_text_full_name;
    @BindView(R.id.tv_edit_profile_display_name)
    TextView tv_edit_profile_display_name;
    @BindView(R.id.edit_text_display_name)
    EditText edt_text_display_name;
    @BindView(R.id.tv_edit_profile_email)
    TextView tv_edit_profile_email;
    @BindView(R.id.edit_text_email)
    EditText edt_text_email;
    @BindView(R.id.tv_edit_profile_mobile)
    TextView tv_edit_profile_mobile;
    @BindView(R.id.edit_text_mobile)
    EditText edt_text_mobile;
    @BindView(R.id.b_update)
    Button btn_update;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;

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
        View rootView = inflater.inflate(R.layout.fragment_edit_profile, container, false);
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

        setTypeFace();
    }
    private void setTypeFace() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);

        tv_edit_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_edit_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_edit_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_edit_location_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_edit_notifications_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_edit_profile.setTypeface(mTypefaceOpenSansRegular);
        tv_edit_profile_full_name.setTypeface(mTypefaceOpenSansRegular);
        edt_text_full_name.setTypeface(mTypefaceOpenSansRegular);
        tv_edit_profile_display_name.setTypeface(mTypefaceOpenSansRegular);
        edt_text_display_name.setTypeface(mTypefaceOpenSansRegular);
        tv_edit_profile_email.setTypeface(mTypefaceOpenSansRegular);
        edt_text_email.setTypeface(mTypefaceOpenSansRegular);
        tv_edit_profile_mobile.setTypeface(mTypefaceOpenSansRegular);
        edt_text_mobile.setTypeface(mTypefaceOpenSansRegular);
        btn_update.setTypeface(mTypefaceOpenSansRegular);
    }


    @OnClick(R.id.b_update)
    public void navigateMyProfile()
    {
        Utility.navigateDashBoardFragment(new MyProfileFragment(),MyProfileFragment.TAG,null,mParent);
    }

    @OnClick(R.id.tv_edit_arrow_back_icon)
    public void navigateBackMyProfile()
    {
       mParent.onBackPressed();
    }

    @OnClick(R.id.tv_edit_notifications_icon)
    public void navigateNotifications()
    {
        Utility.navigateDashBoardFragment(new NotificationsFragment(),NotificationsFragment.TAG,null,mParent);
    }
}
