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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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
public class AccountSettingFragment extends Fragment {

    public static final String TAG = AccountSettingFragment.class.getSimpleName();
    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;

    @BindView(R.id.tv_account_arrow_back_icon)
    TextView tv_account_arrow_back_icon;
    @BindView(R.id.tv_account_menu_icon)
    TextView tv_account_menu_icon;
    @BindView(R.id.tv_account_settings)
    TextView tv_account_settings;
    @BindView(R.id.tv_account_language_icon)
    TextView tv_account_language_icon;
    @BindView(R.id.tv_account_notifications_icon)
    TextView tv_account_notifications_icon;
    @BindView(R.id.tv_account_location_icon)
    TextView tv_account_location_icon;
    @BindView(R.id.tv_email_id)
    TextView tv_email_id;
    @BindView(R.id.tv_ravi_email_id)
    TextView tv_ravi_email_id;
    @BindView(R.id.tv_old_password)
    TextView tv_old_password;
    @BindView(R.id.edit_text_old_password)
    EditText edt_text_old_password;
    @BindView(R.id.tv_new_password)
    TextView tv_new_password;
    @BindView(R.id.edit_new_password)
    EditText edt_new_password;
    @BindView(R.id.tv_re_enter_new_password)
    TextView tv_re_enter_new_password;
    @BindView(R.id.edit_re_enter_new_password)
    EditText edt_re_enter_new_password;
    @BindView(R.id.b_account_update)
    Button b_account_update;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;
    private Typeface mTypefaceOpenSansBold;

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
        View rootView = inflater.inflate(R.layout.fragment_account_setting, container, false);
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
        mTypefaceOpenSansBold = Utility.getOpenSansBold(mParent);

        tv_account_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_account_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_account_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_account_location_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_account_notifications_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_account_settings.setTypeface(mTypefaceOpenSansBold);
        tv_email_id.setTypeface(mTypefaceOpenSansRegular);
        tv_ravi_email_id.setTypeface(mTypefaceOpenSansRegular);
        tv_old_password.setTypeface(mTypefaceOpenSansRegular);
        edt_text_old_password.setTypeface(mTypefaceOpenSansRegular);
        tv_new_password.setTypeface(mTypefaceOpenSansRegular);
        edt_new_password.setTypeface(mTypefaceOpenSansRegular);
        edt_re_enter_new_password.setTypeface(mTypefaceOpenSansRegular);
        tv_re_enter_new_password.setTypeface(mTypefaceOpenSansRegular);
        b_account_update.setTypeface(mTypefaceOpenSansRegular);
    }

    @OnClick (R.id.b_account_update)
    public void navigateHome()
    {
        Intent navigateDashIntent = new Intent(getActivity(),DashBoardActivity.class);
        startActivity(navigateDashIntent);
    }

    @OnClick ({R.id.tv_account_arrow_back_icon, R.id.tv_account_menu_icon})
    public void navigateBack()
    {
       mParent.onBackPressed();
    }

    @OnClick(R.id.tv_account_notifications_icon)
    public void navigateNotification()
    {
       Utility.navigateDashBoardFragment(new NotificationsFragment(),NotificationsFragment.TAG,null,mParent);
    }
    @OnClick(R.id.tv_account_language_icon)
    public void navigateLanguage()
    {
        Utility.navigateDashBoardFragment(new LanguageFragment(),LanguageFragment.TAG,null,mParent);
    }
    @OnClick(R.id.tv_account_location_icon)
    public void navigateLocation()
    {
        Utility.navigateDashBoardFragment(new CountriesFragment(),CountriesFragment.TAG,null,mParent);
    }
}

