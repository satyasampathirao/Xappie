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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.customviews.CircleTransform;
import com.xappie.utils.Constants;
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
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;

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
    @BindView(R.id.img_ravi_image)
    ImageView img_ravi_image;
    @BindView(R.id.b_edit_profile)
    Button btn_edit_profile;
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

        setTypeFace();

    }

    private void setTypeFace() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);
        mTypefaceOpenSansBold = Utility.getOpenSansBold(mParent);

        tv_my_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_my_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_my_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_my_location_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_my_notifications_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_my_profile.setTypeface(mTypefaceOpenSansRegular);
        tv_ravi_profile_name.setTypeface(mTypefaceOpenSansRegular);
        tv_ravi_hyd.setTypeface(mTypefaceOpenSansRegular);
        tv_full_name.setTypeface(mTypefaceOpenSansRegular);
        tv_profile_full_name.setTypeface(mTypefaceOpenSansRegular);
        tv_display_name.setTypeface(mTypefaceOpenSansRegular);
        tv_profile_display_name.setTypeface(mTypefaceOpenSansRegular);
        tv_email.setTypeface(mTypefaceOpenSansRegular);
        tv_ravi_email.setTypeface(mTypefaceOpenSansRegular);
        tv_mobile_no.setTypeface(mTypefaceOpenSansRegular);
        tv_ravi_mobile_nbr.setTypeface(mTypefaceOpenSansRegular);
        btn_edit_profile.setTypeface(mTypefaceOpenSansRegular);

        setBeforeData();

    }

    /**
     * This method is used to set the before data
     */
    private void setBeforeData() {
        tv_ravi_profile_name.setText(Utility.getSharedPrefStringData(mParent, Constants.SIGN_UP_FIRST_NAME));
        tv_profile_full_name.setText(Utility.getSharedPrefStringData(mParent, Constants.SIGN_UP_FIRST_NAME) +
                " " + Utility.getSharedPrefStringData(mParent, Constants.SIGN_UP_LAST_NAME));
        tv_profile_display_name.setText(Utility.getSharedPrefStringData(mParent, Constants.SIGN_UP_DISPLAY_NAME));
        tv_ravi_email.setText(Utility.getSharedPrefStringData(mParent, Constants.SIGN_UP_MAIL_ID));
        tv_ravi_mobile_nbr.setText(Utility.getSharedPrefStringData(mParent, Constants.SIGN_UP_MOBILE));
        tv_ravi_hyd.setText(Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_NAME) + "," +
                Utility.getSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_NAME));
        if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(mParent, Constants.SIGN_UP_PHOTO)))
            Picasso.with(mParent).load(Utility.getSharedPrefStringData(mParent, Constants.SIGN_UP_PHOTO))
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(Utility.getDrawable(mParent, R.drawable.avatar_image))
                    .transform(new CircleTransform()).into(img_ravi_image);
    }

    @OnClick(R.id.b_edit_profile)
    public void navigateEditProfile() {
        Utility.navigateDashBoardFragment(new EditProfileFragment(), EditProfileFragment.TAG, null, mParent);
    }

    @OnClick({R.id.tv_my_arrow_back_icon, R.id.tv_my_menu_icon})
    public void navigateBack() {
        mParent.onBackPressed();
    }

    @OnClick(R.id.tv_my_notifications_icon)
    public void navigateToNotification() {
        Utility.navigateDashBoardFragment(new NotificationsFragment(), NotificationsFragment.TAG, null, mParent);
    }

    @OnClick(R.id.tv_my_language_icon)
    public void navigateLanguage() {
        Utility.navigateDashBoardFragment(new LanguageFragment(), LanguageFragment.TAG, null, mParent);
    }

    @OnClick(R.id.tv_my_location_icon)
    public void navigateLocation() {
        Utility.navigateDashBoardFragment(new CountriesFragment(), CountriesFragment.TAG, null, mParent);
    }
}
