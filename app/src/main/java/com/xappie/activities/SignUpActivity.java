package com.xappie.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.Model;
import com.xappie.models.SignupSuccessModel;
import com.xappie.parser.SignUpSuccessParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity implements IAsyncCaller {

    @BindView(R.id.btn_check)
    Button btn_check;

    @BindView(R.id.tv_sign_up_cancel)
    TextView tv_sign_up_cancel;
    @BindView(R.id.tv_sign_up_signUp)
    TextView tv_sign_up_signup;
    @BindView(R.id.tv_sign_up_login)
    TextView tv_sign_up_login;

    @BindView(R.id.et_sign_up_email)
    EditText et_sign_up_email;
    @BindView(R.id.et_sign_up_mobile)
    EditText et_sign_up_mobile;
    @BindView(R.id.et_sign_up_password)
    EditText et_sign_up_password;
    @BindView(R.id.tv_sign_up_show)
    TextView tv_sign_up_show;
    @BindView(R.id.checkbox_sign_up)
    CheckBox chb_sign_up;

    @BindView(R.id.linear_sign_up_privacy)
    LinearLayout ll_sign_up_privacy;
    @BindView(R.id.tv_by_logging_agree)
    TextView tv_by_logging_agree;
    @BindView(R.id.tv_t_c)
    TextView tv_t_c;
    @BindView(R.id.tv_and)
    TextView tv_and;
    @BindView(R.id.tv_privacy)
    TextView tv_privacy;
    @BindView(R.id.tv_or_sign_social)
    TextView tv_or_sign_social;
    @BindView(R.id.imageButton_facebook)
    ImageButton im_facebook;
    @BindView(R.id.imageButton_google)
    ImageButton im_google;
    @BindView(R.id.imageButton_twitter)
    ImageButton im_twitter;

    private SignupSuccessModel mSignupSuccessModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        btn_check.setTypeface(Utility.getMaterialIconsRegular(this));

        tv_sign_up_cancel.setTypeface(Utility.getOpenSansRegular(this));
        tv_sign_up_signup.setTypeface(Utility.getOpenSansBold(this));
        tv_sign_up_login.setTypeface(Utility.getOpenSansRegular(this));
        et_sign_up_email.setTypeface(Utility.getOpenSansRegular(this));
        et_sign_up_mobile.setTypeface(Utility.getOpenSansRegular(this));
        et_sign_up_password.setTypeface(Utility.getOpenSansRegular(this));
        tv_sign_up_show.setTypeface(Utility.getOpenSansRegular(this));
        chb_sign_up.setTypeface(Utility.getOpenSansRegular(this));
        tv_by_logging_agree.setTypeface(Utility.getOpenSansRegular(this));
        tv_t_c.setTypeface(Utility.getOpenSansRegular(this));
        tv_and.setTypeface(Utility.getOpenSansRegular(this));
        tv_privacy.setTypeface(Utility.getOpenSansRegular(this));
        tv_or_sign_social.setTypeface(Utility.getOpenSansRegular(this));
    }

    /**
     * This method is call the service with validations checks
     */
    @OnClick(R.id.btn_check)
    public void navigateSignUpOtp() {
        if (isValidFields()) {
            LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
            paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            paramMap.put(Constants.AUTH_TYPE, Constants.XAPPIE);
            paramMap.put("email", et_sign_up_email.getText().toString());
            paramMap.put("password", et_sign_up_password.getText().toString());
            SignUpSuccessParser mSignUpSuccessParser = new SignUpSuccessParser();
            ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                    R.string.please_wait), true,
                    APIConstants.SIGN_UP, paramMap,
                    APIConstants.REQUEST_TYPE.POST, this, mSignUpSuccessParser);
            Utility.execute(serverIntractorAsync);
        }
    }

    @OnClick(R.id.tv_sign_up_login)
    public void navigateLogin() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }

    @OnClick(R.id.tv_sign_up_cancel)
    public void navigateCancel() {
        this.onBackPressed();
    }

    private boolean isValidFields() {
        boolean isValid = true;
        if (Utility.isValueNullOrEmpty(et_sign_up_email.getText().toString())) {
            Utility.setSnackBar(this, et_sign_up_email, "Please enter email");
            et_sign_up_email.requestFocus();
            isValid = false;
        } else if (et_sign_up_password.getText().toString().length() < 4) {
            Utility.setSnackBar(this, et_sign_up_password, "Please enter password");
            et_sign_up_password.requestFocus();
            isValid = false;
        } else if (!chb_sign_up.isChecked()) {
            Utility.setSnackBar(this, et_sign_up_password, "Please accept terms & condition");
            et_sign_up_password.requestFocus();
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
                    Intent signUpOtpIntent = new Intent(this, SignUpOtpActivity.class);
                    startActivity(signUpOtpIntent);
                } else {
                    Utility.showToastMessage(SignUpActivity.this, mSignupSuccessModel.getMessage());
                }
            }
        }
    }
}
