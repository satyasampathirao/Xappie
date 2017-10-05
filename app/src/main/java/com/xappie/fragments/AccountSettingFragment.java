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
public class AccountSettingFragment extends Fragment implements IAsyncCaller {

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
    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_old_password)
    TextView tv_old_password;
    @BindView(R.id.edit_text_old_password)
    EditText edit_text_old_password;

    @BindView(R.id.tv_new_password)
    TextView tv_new_password;
    @BindView(R.id.edit_new_password)
    EditText edit_new_password;
    @BindView(R.id.tv_re_enter_new_password)
    TextView tv_re_enter_new_password;
    @BindView(R.id.edit_re_enter_new_password)
    EditText edit_re_enter_new_password;
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

        tv_account_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_account_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_account_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_account_location_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_account_notifications_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_account_settings.setTypeface(mTypefaceOpenSansRegular);
        tv_email_id.setTypeface(mTypefaceOpenSansRegular);
        tv_email.setTypeface(mTypefaceOpenSansRegular);
        tv_old_password.setTypeface(mTypefaceOpenSansRegular);
        edit_text_old_password.setTypeface(mTypefaceOpenSansRegular);
        tv_new_password.setTypeface(mTypefaceOpenSansRegular);
        edit_new_password.setTypeface(mTypefaceOpenSansRegular);
        edit_re_enter_new_password.setTypeface(mTypefaceOpenSansRegular);
        tv_re_enter_new_password.setTypeface(mTypefaceOpenSansRegular);
        btn_update.setTypeface(mTypefaceOpenSansRegular);

        tv_email.setText(Utility.getSharedPrefStringData(mParent, Constants.SIGN_UP_MAIL_ID));
    }


    @OnClick({R.id.tv_account_arrow_back_icon, R.id.tv_account_menu_icon})
    public void navigateBack() {
        mParent.onBackPressed();
    }

    @OnClick(R.id.tv_account_notifications_icon)
    public void navigateNotification() {
        Utility.navigateDashBoardFragment(new NotificationsFragment(), NotificationsFragment.TAG, null, mParent);
    }

    @OnClick(R.id.tv_account_language_icon)
    public void navigateLanguage() {
        Utility.navigateDashBoardFragment(new LanguageFragment(), LanguageFragment.TAG, null, mParent);
    }

    @OnClick(R.id.tv_account_location_icon)
    public void navigateLocation() {
        Utility.navigateDashBoardFragment(new CountriesFragment(), CountriesFragment.TAG, null, mParent);
    }

    /**
     * This method is used to hit the server
     */
    @OnClick(R.id.btn_update)
    public void navigateResetPassword() {
        if (isValidFields()) {
            LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
            paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            paramMap.put("old_password", edit_text_old_password.getText().toString());
            paramMap.put("new_password", edit_new_password.getText().toString());
            SignUpLoginSuccessParser mSignUpLoginSuccessParser = new SignUpLoginSuccessParser();
            ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(mParent, Utility.getResourcesString(mParent,
                    R.string.please_wait), true,
                    APIConstants.UPDATE_PASSWORD, paramMap,
                    APIConstants.REQUEST_TYPE.POST, this, mSignUpLoginSuccessParser);
            Utility.execute(serverIntractorAsync);
        }
    }

    private boolean isValidFields() {
        boolean isValid = true;
        if (Utility.isValueNullOrEmpty(edit_text_old_password.getText().toString())) {
            Utility.setSnackBar(mParent, edit_text_old_password, "Please enter old password");
            edit_text_old_password.requestFocus();
            isValid = false;
        } else if (Utility.isValueNullOrEmpty(edit_new_password.getText().toString())) {
            Utility.setSnackBar(mParent, edit_new_password, "Please enter new password");
            edit_new_password.requestFocus();
            isValid = false;
        } else if (Utility.isValueNullOrEmpty(edit_re_enter_new_password.getText().toString())) {
            Utility.setSnackBar(mParent, edit_re_enter_new_password, "Please enter re enter password");
            edit_re_enter_new_password.requestFocus();
            isValid = false;
        } else if (!edit_new_password.getText().toString().equalsIgnoreCase(edit_re_enter_new_password.getText().toString())) {
            Utility.setSnackBar(mParent, edit_re_enter_new_password, "New and re enter passwords doesn't match");
            edit_re_enter_new_password.requestFocus();
            isValid = false;
        }
        return isValid;
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof SignupLoginSuccessModel) {
                mSignupLoginSuccessModel = (SignupLoginSuccessModel) model;
                if (mSignupLoginSuccessModel.isStatus()) {
                    Utility.showToastMessage(mParent, mSignupLoginSuccessModel.getMessage());
                    mParent.finish();
                } else {
                    Utility.showToastMessage(mParent, mSignupLoginSuccessModel.getMessage());
                }
            }
        }
    }
}

