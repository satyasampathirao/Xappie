package com.xappie.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.LoginModel;
import com.xappie.models.Model;
import com.xappie.parser.LanguageParser;
import com.xappie.parser.LoginParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements IAsyncCaller {
    @BindView(R.id.btn_check)
    Button btn_check;

    @BindView(R.id.linear_login)
    LinearLayout ll_login;
    @BindView(R.id.tv_log_cancel)
    TextView tv_log_cancel;
    @BindView(R.id.tv_log_login)
    TextView tv_log_login;
    @BindView(R.id.tv_log_sign_up)
    TextView tv_log_sign_up;
    @BindView(R.id.relative_login)
    RelativeLayout rl_login_email;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.tv_log_show)
    TextView tv_log_show;
    @BindView(R.id.tv_trouble_getting)
    TextView tv_trouble_getting;
    @BindView(R.id.linear_login_privacy)
    LinearLayout ll_login_privacy;
    @BindView(R.id.tv_by_logging_agree)
    TextView tv_by_logging_agree;
    @BindView(R.id.tv_t_c)
    TextView tv_t_c;
    @BindView(R.id.tv_and)
    TextView tv_and;
    @BindView(R.id.tv_privacy)
    TextView tv_privacy;
    @BindView(R.id.view_log_in)
    View view_log_in;
    @BindView(R.id.tv_or_sign_social)
    TextView tv_or_sign_social;
    @BindView(R.id.relative_login_bottom)
    RelativeLayout rl_login_bottom;
    @BindView(R.id.imageButton_facebook)
    ImageButton im_facebook;
    @BindView(R.id.imageButton_google)
    ImageButton im_google;
    @BindView(R.id.imageButton_twitter)
    ImageButton im_twitter;


    private LoginModel mLoginModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        btn_check.setTypeface(Utility.getMaterialIconsRegular(this));
        tv_log_show.setTypeface(Utility.getOpenSansRegular(this));
        tv_trouble_getting.setTypeface(Utility.getOpenSansRegular(this));
        tv_by_logging_agree.setTypeface(Utility.getOpenSansRegular(this));
        tv_t_c.setTypeface(Utility.getOpenSansRegular(this));
        tv_and.setTypeface(Utility.getOpenSansRegular(this));
        tv_privacy.setTypeface(Utility.getOpenSansRegular(this));
        tv_or_sign_social.setTypeface(Utility.getOpenSansRegular(this));
        tv_log_cancel.setTypeface(Utility.getOpenSansRegular(this));
        tv_log_login.setTypeface(Utility.getOpenSansBold(this));
        tv_log_sign_up.setTypeface(Utility.getOpenSansRegular(this));

        et_email.setTypeface(Utility.getOpenSansRegular(this));
        et_password.setTypeface(Utility.getOpenSansRegular(this));
    }

    @OnClick(R.id.tv_log_sign_up)
    public void navigateSignUp() {
        Intent signUpIntent = new Intent(this, SignUpActivity.class);
        startActivity(signUpIntent);
    }

    @OnClick(R.id.btn_check)
    public void navigateLogin() {/*
        Utility.setSharedPrefBooleanData(LoginActivity.this, Constants.IS_LOGIN_COMPLETED, true);
        finish();
        Intent signUpIntent = new Intent(this, DashBoardActivity.class);
        startActivity(signUpIntent);*/
        if (isValidFields()) {
            LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
            paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            paramMap.put(Constants.AUTH_TYPE, Constants.XAPPIE);
            paramMap.put("email", et_email.getText().toString());
            paramMap.put("password", et_password.getText().toString());
            LoginParser mLoginParser = new LoginParser();
            ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                    R.string.please_wait), true,
                    APIConstants.LOGIN, paramMap,
                    APIConstants.REQUEST_TYPE.POST, this, mLoginParser);
            Utility.execute(serverIntractorAsync);

            Utility.showLog("USER NAME", "" + et_email.getText().toString());
            Utility.showLog("PASSWORD ", "" + et_password.getText().toString());
        }

    }

    private boolean isValidFields() {
        boolean isValid = true;
        if (Utility.isValueNullOrEmpty(et_email.getText().toString())) {
            Utility.setSnackBar(this, et_email, "Please enter username");
            et_email.requestFocus();
            isValid = false;
        } else if (et_password.getText().toString().length() < 4) {
            Utility.setSnackBar(this, et_password, "Please enter password");
            et_password.requestFocus();
            isValid = false;
        }
        return isValid;
    }

    @OnClick(R.id.tv_trouble_getting)
    public void navigateForgotPassword() {
        Intent forgotPasswordIntent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(forgotPasswordIntent);
    }

    @OnClick(R.id.tv_log_cancel)
    public void navigateCancel() {
        this.onBackPressed();
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof LoginModel) {
                mLoginModel = (LoginModel) model;
                if (mLoginModel.isStatus()) {
                    Utility.showToastMessage(LoginActivity.this, Utility.capitalizeFirstLetter(mLoginModel.getMessage()));
                    Utility.setSharedPrefBooleanData(LoginActivity.this, Constants.IS_LOGIN_COMPLETED, true);
                    finish();
                    Utility.setSharedPrefStringData(LoginActivity.this, Constants.SIGN_UP_FIRST_NAME, mLoginModel.getFirst_name());
                    Utility.setSharedPrefStringData(LoginActivity.this, Constants.SIGN_UP_LAST_NAME, mLoginModel.getLast_name());
                    Utility.setSharedPrefStringData(LoginActivity.this, Constants.SIGN_UP_MAIL_ID, mLoginModel.getEmail());
                    Utility.setSharedPrefStringData(LoginActivity.this, Constants.SIGN_UP_UUID, mLoginModel.getUuid());
                    Utility.setSharedPrefStringData(LoginActivity.this, Constants.TOKEN, mLoginModel.getCi_session());
                    Intent signUpIntent = new Intent(this, DashBoardActivity.class);
                    startActivity(signUpIntent);
                    //Utility.setSharedPrefStringData(LoginActivity.this, Constants.SIGN_UP_CURRENT_DATE, Utility.getDate());
                } else {
                    Utility.showToastMessage(LoginActivity.this, Utility.capitalizeFirstLetter(mLoginModel.getMessage()));
                }
            }
        }
    }
}
