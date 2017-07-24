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
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
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
    EditText edt_email;
    @BindView(R.id.et_password)
    EditText edt_password;
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
        tv_log_login.setTypeface(Utility.getOpenSansRegular(this));
        tv_log_sign_up.setTypeface(Utility.getOpenSansRegular(this));
        edt_email.setTypeface(Utility.getOpenSansRegular(this));
        edt_password.setTypeface(Utility.getOpenSansRegular(this));
    }

    @OnClick(R.id.tv_log_sign_up)
    public void navigateSignUp() {
        Intent signUpIntent = new Intent(this, SignUpActivity.class);
        startActivity(signUpIntent);
    }

    @OnClick(R.id.btn_check)
    public void navigateLogin() {
        Utility.setSharedPrefBooleanData(LoginActivity.this, Constants.IS_LOGIN_COMPLETED, true);
        finish();
        Intent signUpIntent = new Intent(this, DashBoardActivity.class);
        startActivity(signUpIntent);
    }

    @OnClick(R.id.tv_trouble_getting)
    public void navigateForgotPassword() {
        Intent forgotPasswordIntent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(forgotPasswordIntent);
    }
    @OnClick(R.id.tv_log_cancel)
    public void navigateCancel()
    {
        this.onBackPressed();
    }

}
