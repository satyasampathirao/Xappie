package com.xappie.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xappie.R;
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

public class ResetPasswordActivity extends BaseActivity implements IAsyncCaller {
    public static final String TAG = ResetPasswordActivity.class.getSimpleName();

    @BindView(R.id.relativeLayout_reset_password)
    RelativeLayout relativeLayout_reset_password;

    @BindView(R.id.tv_reset_password)
    TextView tv_reset_password;

    @BindView(R.id.et_otp)
    EditText et_otp;
    @BindView(R.id.et_new_password)
    EditText et_new_password;
    @BindView(R.id.et_confirm_password)
    EditText et_confirm_password;

    @BindView(R.id.b_forgot_cancel)
    Button btn_forgot_cancel;
    @BindView(R.id.btn_submit)
    Button btn_submit;

    private SignupLoginSuccessModel mSignupLoginSuccessModel;
    private String mEmail = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);
        initUI();
        Utility.sendGoogleAnalytics(this, TAG);
    }

    private void initUI() {
        tv_reset_password.setTypeface(Utility.getOpenSansBold(this));
        et_otp.setTypeface(Utility.getOpenSansRegular(this));
        et_new_password.setTypeface(Utility.getOpenSansRegular(this));
        et_confirm_password.setTypeface(Utility.getOpenSansRegular(this));

        btn_forgot_cancel.setTypeface(Utility.getOpenSansRegular(this));
        btn_submit.setTypeface(Utility.getOpenSansRegular(this));

        if (getIntent().hasExtra(Constants.EMAIL)) {
            mEmail = getIntent().getStringExtra(Constants.EMAIL);
        }
    }

    /**
     * Cancel and comes back
     */
    @OnClick(R.id.b_forgot_cancel)
    public void navigateBack() {
        this.onBackPressed();
    }


    /**
     * This method is used to hit the server
     */
    @OnClick(R.id.btn_submit)
    public void navigateResetPassword() {
        if (isValidFields()) {
            LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
            paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            paramMap.put("email", mEmail);
            paramMap.put("otp", et_otp.getText().toString());
            paramMap.put("password", et_new_password.getText().toString());
            SignUpLoginSuccessParser mSignUpLoginSuccessParser = new SignUpLoginSuccessParser();
            ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                    R.string.please_wait), true,
                    APIConstants.RESET_PASSWORD, paramMap,
                    APIConstants.REQUEST_TYPE.POST, this, mSignUpLoginSuccessParser);
            Utility.execute(serverIntractorAsync);
        }
    }

    private boolean isValidFields() {
        boolean isValid = true;
        if (Utility.isValueNullOrEmpty(et_otp.getText().toString())) {
            Utility.setSnackBar(this, et_otp, "Please enter otp");
            et_otp.requestFocus();
            isValid = false;
        } else if (Utility.isValueNullOrEmpty(et_new_password.getText().toString())) {
            Utility.setSnackBar(this, et_otp, "Please enter new password");
            et_otp.requestFocus();
            isValid = false;
        } else if (Utility.isValueNullOrEmpty(et_confirm_password.getText().toString())) {
            Utility.setSnackBar(this, et_otp, "Please enter confirm password");
            et_otp.requestFocus();
            isValid = false;
        } else if (!et_new_password.getText().toString().equalsIgnoreCase(et_confirm_password.getText().toString())) {
            Utility.setSnackBar(this, et_otp, "Passwords doesn't match");
            et_otp.requestFocus();
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
                    Utility.showToastMessage(ResetPasswordActivity.this, mSignupLoginSuccessModel.getMessage());
                    finish();
                } else {
                    Utility.showToastMessage(ResetPasswordActivity.this, mSignupLoginSuccessModel.getMessage());
                }
            }
        }
    }
}
