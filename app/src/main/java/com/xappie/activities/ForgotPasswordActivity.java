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

public class ForgotPasswordActivity extends BaseActivity implements IAsyncCaller {
    public static final String TAG = ForgotPasswordActivity.class.getSimpleName();
    @BindView(R.id.relativeLayout_forgot_password)
    RelativeLayout rl_forgot_password;
    @BindView(R.id.tv_forgot_password)
    TextView tv_forgot_password;
    @BindView(R.id.tv_we_will_send)
    TextView tv_we_will_send;
    @BindView(R.id.et_sign_up_email)
    EditText edt_sign_up_email;
    @BindView(R.id.b_forgot_cancel)
    Button btn_forgot_cancel;
    @BindView(R.id.b_forgot_reset_password)
    Button btn_forgot_reset_password;

    private SignupLoginSuccessModel mSignupLoginSuccessModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        initUI();
        Utility.sendGoogleAnalytics(this, TAG);
    }

    private void initUI() {
        tv_forgot_password.setTypeface(Utility.getOpenSansBold(this));
        tv_we_will_send.setTypeface(Utility.getOpenSansRegular(this));
        edt_sign_up_email.setTypeface(Utility.getOpenSansRegular(this));
        btn_forgot_cancel.setTypeface(Utility.getOpenSansRegular(this));
        btn_forgot_reset_password.setTypeface(Utility.getOpenSansRegular(this));
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
    @OnClick(R.id.b_forgot_reset_password)
    public void navigateResetPassword() {
        if (isValidFields()) {
            LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
            paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            paramMap.put("email", edt_sign_up_email.getText().toString());
            SignUpLoginSuccessParser mSignUpLoginSuccessParser = new SignUpLoginSuccessParser();
            ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                    R.string.please_wait), true,
                    APIConstants.FORGOT_PASSWORD, paramMap,
                    APIConstants.REQUEST_TYPE.POST, this, mSignUpLoginSuccessParser);
            Utility.execute(serverIntractorAsync);
        }
    }

    private boolean isValidFields() {
        boolean isValid = true;
        if (Utility.isValueNullOrEmpty(edt_sign_up_email.getText().toString())) {
            Utility.setSnackBar(this, edt_sign_up_email, "Please enter email");
            edt_sign_up_email.requestFocus();
            isValid = false;
        } else if (!edt_sign_up_email.getText().toString().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z]+)*(\\.[A-Za-z]{2,4})$")) {
            Utility.setSnackBar(this, edt_sign_up_email, "Please enter valid email");
            edt_sign_up_email.requestFocus();
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
                    Utility.showToastMessage(ForgotPasswordActivity.this, mSignupLoginSuccessModel.getMessage());
                    finish();
                    Intent signUpOtpIntent = new Intent(this, ResetPasswordActivity.class);
                    signUpOtpIntent.putExtra(Constants.EMAIL, edt_sign_up_email.getText().toString());
                    startActivity(signUpOtpIntent);
                } else {
                    Utility.showToastMessage(ForgotPasswordActivity.this, mSignupLoginSuccessModel.getMessage());
                }
            }
        }
    }
}
