package com.xappie.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.Model;
import com.xappie.models.SignupSuccessModel;
import com.xappie.models.SignupSuccessOTPModel;
import com.xappie.parser.SignUpSuccessOTPParser;
import com.xappie.parser.SignUpSuccessParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpOtpActivity extends BaseActivity implements IAsyncCaller {

    public static final String TAG = SignUpOtpActivity.class.getSimpleName();

    @BindView(R.id.btn_check)
    Button btn_check;

    @BindView(R.id.tv_sign_up_otp_cancel)
    TextView tv_sign_up_otp_cancel;
    @BindView(R.id.tv_sign_up_otp_signUp)
    TextView tv_sign_up_otp_signup;
    @BindView(R.id.tv_sign_up_otp_login)
    TextView tv_sign_up_otp_login;

    @BindView(R.id.tv_enter_otp)
    TextView tv_enter_otp;
    @BindView(R.id.et_sign_up_otp_otp)
    EditText et_sign_up_otp_otp;
    @BindView(R.id.tv_resend_otp)
    TextView tv_resend_otp;
    @BindView(R.id.et_sign_in_first_name)
    EditText et_sign_in_first_name;
    @BindView(R.id.et_sign_in_last_name)
    EditText et_sign_in_last_name;
    @BindView(R.id.radio_group_sign_up_otp)
    RadioGroup rg_sign_up_otp;
    @BindView(R.id.radio_button_male)
    RadioButton radio_button_male;
    @BindView(R.id.radio_button_female)
    RadioButton radio_button_female;

    @BindView(R.id.tv_by_logging_agree)
    TextView tv_by_logging_agree;
    @BindView(R.id.tv_t_c)
    TextView tv_t_c;
    @BindView(R.id.tv_privacy)
    TextView tv_privacy;

    private Intent intent;
    private String mMailId;
    private String mPassword;
    private String mMobile;
    private String mUUID;

    private SignupSuccessModel mSignupSuccessModel;
    private SignupSuccessOTPModel mSignupSuccessOTPModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_sign_up_otp);
        ButterKnife.bind(this);
        initUI();
        Utility.sendGoogleAnalytics(this, TAG);
    }

    private void initUI() {
        btn_check.setTypeface(Utility.getMaterialIconsRegular(this));
        tv_sign_up_otp_cancel.setTypeface(Utility.getOpenSansRegular(this));
        tv_sign_up_otp_signup.setTypeface(Utility.getOpenSansBold(this));
        tv_sign_up_otp_login.setTypeface(Utility.getOpenSansRegular(this));
        tv_enter_otp.setTypeface(Utility.getOpenSansRegular(this));

        et_sign_up_otp_otp.setTypeface(Utility.getOpenSansRegular(this));
        tv_resend_otp.setTypeface(Utility.getOpenSansRegular(this));
        et_sign_in_first_name.setTypeface(Utility.getOpenSansRegular(this));
        et_sign_in_last_name.setTypeface(Utility.getOpenSansRegular(this));
        radio_button_male.setTypeface(Utility.getOpenSansRegular(this));
        radio_button_female.setTypeface(Utility.getOpenSansRegular(this));
        tv_by_logging_agree.setTypeface(Utility.getOpenSansRegular(this));
        tv_t_c.setTypeface(Utility.getOpenSansRegular(this));
        tv_privacy.setTypeface(Utility.getOpenSansRegular(this));

        intent = getIntent();
        if (intent.hasExtra(Constants.SIGN_UP_MAIL_ID)) {
            mMailId = intent.getStringExtra(Constants.SIGN_UP_MAIL_ID);
            mPassword = intent.getStringExtra(Constants.SIGN_UP_PASSWORD);
            mMobile = intent.getStringExtra(Constants.SIGN_UP_MOBILE);
            mUUID = intent.getStringExtra(Constants.SIGN_UP_UUID);
        }
        tv_enter_otp.setText(getResources().getString(R.string.enter_one_time, mMailId));
    }

    @OnClick(R.id.tv_sign_up_otp_login)
    public void navigateLogin() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }

    @OnClick(R.id.tv_resend_otp)
    public void callResendOtp() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        paramMap.put(Constants.AUTH_TYPE, Constants.XAPPIE);
        paramMap.put("email", mMailId);
        paramMap.put("password", mPassword);
        paramMap.put("mobile", mMobile);
        SignUpSuccessParser mSignUpSuccessParser = new SignUpSuccessParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                R.string.please_wait), true,
                APIConstants.SIGN_UP, paramMap,
                APIConstants.REQUEST_TYPE.POST, this, mSignUpSuccessParser);
        Utility.execute(serverIntractorAsync);
    }

    @OnClick(R.id.tv_sign_up_otp_cancel)
    public void backToCancel() {
        this.onBackPressed();
    }

    /**
     * This method is used to check otp
     */
    @OnClick(R.id.btn_check)
    public void callOtpCheck() {
        if (isValidFields()) {
            LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
            paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            paramMap.put(Constants.AUTH_TYPE, Constants.XAPPIE);
            paramMap.put("first_name", et_sign_in_first_name.getText().toString());
            paramMap.put("last_name", et_sign_in_last_name.getText().toString());
            paramMap.put("uuid", mUUID);
            paramMap.put("otp", et_sign_up_otp_otp.getText().toString());
            if (radio_button_male.isChecked()) {
                paramMap.put("gender", "male");
            } else {
                paramMap.put("gender", "female");
            }
            SignUpSuccessOTPParser mSignUpSuccessOTPParser = new SignUpSuccessOTPParser();
            ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                    R.string.please_wait), true,
                    APIConstants.SIGN_UP_VERIFY, paramMap,
                    APIConstants.REQUEST_TYPE.POST, this, mSignUpSuccessOTPParser);
            Utility.execute(serverIntractorAsync);
        }
    }


    /**
     * Checks for the validations
     */
    private boolean isValidFields() {
        boolean isValid = true;
        if (Utility.isValueNullOrEmpty(et_sign_up_otp_otp.getText().toString())) {
            Utility.setSnackBar(this, et_sign_up_otp_otp, "Please enter otp");
            et_sign_up_otp_otp.requestFocus();
            isValid = false;
        } else if (et_sign_in_first_name.getText().toString().length() < 4) {
            Utility.setSnackBar(this, et_sign_in_first_name, "Please enter first name");
            et_sign_in_first_name.requestFocus();
            isValid = false;
        } else if (et_sign_in_last_name.getText().toString().length() < 4) {
            Utility.setSnackBar(this, et_sign_in_last_name, "Please enter last name");
            et_sign_in_last_name.requestFocus();
            isValid = false;
        }
        return isValid;
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof SignupSuccessModel) {
                mSignupSuccessModel = (SignupSuccessModel) model;
                if (mSignupSuccessModel.isStatus()) {
                    Utility.showToastMessage(SignUpOtpActivity.this, "Otp code sent to your email id");
                } else {
                    Utility.showToastMessage(SignUpOtpActivity.this, mSignupSuccessModel.getMessage());
                }
            } else if (model instanceof SignupSuccessOTPModel) {
                mSignupSuccessOTPModel = (SignupSuccessOTPModel) model;
                if (mSignupSuccessOTPModel.isStatus()) {
                    Utility.setSharedPrefBooleanData(SignUpOtpActivity.this, Constants.IS_LOGIN_COMPLETED, true);
                    finish();
                    Utility.setSharedPrefStringData(SignUpOtpActivity.this, Constants.SIGN_UP_FIRST_NAME, et_sign_in_first_name.getText().toString());
                    Utility.setSharedPrefStringData(SignUpOtpActivity.this, Constants.SIGN_UP_LAST_NAME, et_sign_in_last_name.getText().toString());
                    Utility.setSharedPrefStringData(SignUpOtpActivity.this, Constants.SIGN_UP_MOBILE, mMobile);
                    Utility.setSharedPrefStringData(SignUpOtpActivity.this, Constants.SIGN_UP_MAIL_ID, mMailId);
                    Utility.setSharedPrefStringData(SignUpOtpActivity.this, Constants.SIGN_UP_CURRENT_DATE, Utility.getDate());
                    Utility.setSharedPrefStringData(SignUpOtpActivity.this, Constants.SIGN_UP_UUID, mUUID);
                    Utility.setSharedPrefStringData(SignUpOtpActivity.this, Constants.TOKEN, "");
                    Intent signUpIntent = new Intent(this, DashBoardActivity.class);
                    startActivity(signUpIntent);
                } else {
                    Utility.showToastMessage(SignUpOtpActivity.this, mSignupSuccessOTPModel.getMessage());
                }
            }
        }
    }

    @OnClick(R.id.tv_privacy)
    void navigatePrivacyPolicy() {
        String url = "http://www.xappie.com/pages/privacy_policy";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    @OnClick(R.id.tv_t_c)
    void navigateTerms()
    {
        String url = "http://www.xappie.com/pages/terms_conditions";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}