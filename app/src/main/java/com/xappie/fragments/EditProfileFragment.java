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
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.Model;
import com.xappie.models.SignupLoginSuccessModel;
import com.xappie.parser.SignUpLoginSuccessParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shankar 21/07/2017
 */
public class EditProfileFragment extends Fragment implements IAsyncCaller {

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
    EditText edit_text_full_name;
    @BindView(R.id.tv_edit_profile_last_name)
    TextView tv_edit_profile_last_name;
    @BindView(R.id.edit_text_last_name)
    EditText edit_text_last_name;
    @BindView(R.id.tv_edit_profile_display_name)
    TextView tv_edit_profile_display_name;
    @BindView(R.id.edit_text_display_name)
    EditText edit_text_display_name;
    @BindView(R.id.tv_edit_profile_email)
    TextView tv_edit_profile_email;
    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_edit_profile_mobile)
    TextView tv_edit_profile_mobile;
    @BindView(R.id.edit_text_mobile)
    EditText edit_text_mobile;
    @BindView(R.id.btn_update)
    Button btn_update;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;

    private SignupLoginSuccessModel mSignupLoginSuccessModel;

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
        edit_text_full_name.setTypeface(mTypefaceOpenSansRegular);
        tv_edit_profile_display_name.setTypeface(mTypefaceOpenSansRegular);
        edit_text_display_name.setTypeface(mTypefaceOpenSansRegular);
        tv_edit_profile_email.setTypeface(mTypefaceOpenSansRegular);
        tv_email.setTypeface(mTypefaceOpenSansRegular);
        tv_edit_profile_mobile.setTypeface(mTypefaceOpenSansRegular);
        edit_text_mobile.setTypeface(mTypefaceOpenSansRegular);
        btn_update.setTypeface(mTypefaceOpenSansRegular);

        tv_email.setText(Utility.getSharedPrefStringData(mParent, Constants.SIGN_UP_MAIL_ID));
        edit_text_full_name.setText(Utility.getSharedPrefStringData(mParent, Constants.SIGN_UP_FIRST_NAME));
        edit_text_mobile.setText(Utility.getSharedPrefStringData(mParent, Constants.SIGN_UP_MOBILE));
        if (Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(mParent, Constants.SIGN_UP_DISPLAY_NAME))) {
            edit_text_display_name.setText("");
        } else {
            edit_text_display_name.setText(Utility.getSharedPrefStringData(mParent, Constants.SIGN_UP_DISPLAY_NAME));
        }
        if (Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(mParent, Constants.SIGN_UP_FIRST_NAME))) {
            edit_text_full_name.setText("");
        } else {
            edit_text_full_name.setText(Utility.getSharedPrefStringData(mParent, Constants.SIGN_UP_DISPLAY_NAME));
        }
        if (Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(mParent, Constants.SIGN_UP_LAST_NAME))) {
            edit_text_last_name.setText("");
        } else {
            edit_text_last_name.setText(Utility.getSharedPrefStringData(mParent, Constants.SIGN_UP_LAST_NAME));
        }

    }


    @OnClick(R.id.btn_update)
    public void navigateMyProfile() {
        if (isValidFields()) {
            LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
            paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            paramMap.put("first_name", edit_text_full_name.getText().toString());
            paramMap.put("last_name", edit_text_last_name.getText().toString());
            paramMap.put("display_name", edit_text_display_name.getText().toString());
            paramMap.put("email", tv_email.getText().toString());
            paramMap.put("mobile", edit_text_mobile.getText().toString());
            SignUpLoginSuccessParser mSignUpLoginSuccessParser = new SignUpLoginSuccessParser();
            ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(mParent, Utility.getResourcesString(mParent,
                    R.string.please_wait), true,
                    APIConstants.UPDATE_PROFILE, paramMap,
                    APIConstants.REQUEST_TYPE.POST, this, mSignUpLoginSuccessParser);
            Utility.execute(serverIntractorAsync);
        }
    }

    private boolean isValidFields() {
        boolean isValid = true;
        if (Utility.isValueNullOrEmpty(edit_text_full_name.getText().toString())) {
            Utility.setSnackBar(mParent, edit_text_full_name, "Please enter full name");
            edit_text_full_name.requestFocus();
            isValid = false;
        }
        return isValid;
    }

    @OnClick({R.id.tv_edit_arrow_back_icon, R.id.tv_edit_menu_icon})
    public void navigateBackMyProfile() {
        mParent.onBackPressed();
    }

    @OnClick(R.id.tv_edit_notifications_icon)
    public void navigateNotifications() {
        Utility.navigateDashBoardFragment(new NotificationsFragment(), NotificationsFragment.TAG, null, mParent);
    }

    @OnClick(R.id.tv_edit_language_icon)
    public void navigateLanguage() {
        Utility.navigateDashBoardFragment(new LanguageFragment(), LanguageFragment.TAG, null, mParent);
    }

    @OnClick(R.id.tv_edit_location_icon)
    public void navigateLocation() {
        Utility.navigateDashBoardFragment(new CountriesFragment(), CountriesFragment.TAG, null, mParent);
    }


    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof SignupLoginSuccessModel) {
                mSignupLoginSuccessModel = (SignupLoginSuccessModel) model;
                if (mSignupLoginSuccessModel.isStatus()) {
                    Utility.showToastMessage(mParent, mSignupLoginSuccessModel.getMessage());

                    if (!Utility.isValueNullOrEmpty(edit_text_full_name.getText().toString())) {
                        Utility.setSharedPrefStringData(mParent, Constants.SIGN_UP_FIRST_NAME, edit_text_full_name.getText().toString());
                    }
                    if (!Utility.isValueNullOrEmpty(edit_text_last_name.getText().toString())) {
                        Utility.setSharedPrefStringData(mParent, Constants.SIGN_UP_LAST_NAME, edit_text_last_name.getText().toString());
                    }
                    if (!Utility.isValueNullOrEmpty(edit_text_mobile.getText().toString())) {
                        Utility.setSharedPrefStringData(mParent, Constants.SIGN_UP_MOBILE, edit_text_mobile.getText().toString());
                    }
                    if (!Utility.isValueNullOrEmpty(edit_text_display_name.getText().toString())) {
                        Utility.setSharedPrefStringData(mParent, Constants.SIGN_UP_DISPLAY_NAME, edit_text_display_name.getText().toString());
                    }
                    mParent.onBackPressed();
                } else {
                    Utility.showToastMessage(mParent, mSignupLoginSuccessModel.getMessage());
                }
            }
        }
    }
}
