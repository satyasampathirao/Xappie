package com.xappie.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity {
    @BindView(R.id.b_check_sign_up)
    Button btn_check_sign_up;
    @BindView(R.id.relativeLayout_sign_up)
    RelativeLayout rl_sign_up;
    @BindView(R.id.linear_sign_up)
    LinearLayout ll_sign_up;
    @BindView(R.id.tv_sign_up_cancel)
    TextView tv_sign_up_cancel;
    @BindView(R.id.tv_sign_up_signUp)
    TextView tv_sign_up_signup;
    @BindView(R.id.tv_sign_up_login)
    TextView tv_sign_up_login;
    @BindView(R.id.relative_sign_up)
    RelativeLayout rl_sign_up_email;
    @BindView(R.id.et_sign_up_email)
    EditText edt_sign_up_email;
    @BindView(R.id.et_sign_up_mobile)
    EditText edt_sign_up_mobile;
    @BindView(R.id.et_sign_up_password)
    EditText edt_sign_up_password;
    @BindView(R.id.tv_sign_up_show)
    TextView tv_sign_up_show;
    @BindView(R.id.checkbox_sign_up)
    CheckBox chb_sign_up;
    @BindView(R.id.relative_check_icon)
    RelativeLayout rl_check_icon;
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
    @BindView(R.id.view_sign_up)
    View view_sign_up;
    @BindView(R.id.tv_or_sign_social)
    TextView tv_or_sign_social;
    @BindView(R.id.relative_sign_up_bottom)
    RelativeLayout rl_sign_up_bottom;
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
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        btn_check_sign_up.setTypeface(Utility.getMaterialIconsRegular(this));
        tv_sign_up_cancel.setTypeface(Utility.getOpenSansRegular(this));
        tv_sign_up_signup.setTypeface(Utility.getOpenSansBold(this));
        tv_sign_up_login.setTypeface(Utility.getOpenSansRegular(this));
        edt_sign_up_email.setTypeface(Utility.getOpenSansRegular(this));
        edt_sign_up_mobile.setTypeface(Utility.getOpenSansRegular(this));
        edt_sign_up_password.setTypeface(Utility.getOpenSansRegular(this));
        tv_sign_up_show.setTypeface(Utility.getOpenSansRegular(this));
        chb_sign_up.setTypeface(Utility.getOpenSansRegular(this));
        tv_by_logging_agree.setTypeface(Utility.getOpenSansRegular(this));
        tv_t_c.setTypeface(Utility.getOpenSansRegular(this));
        tv_and.setTypeface(Utility.getOpenSansRegular(this));
        tv_privacy.setTypeface(Utility.getOpenSansRegular(this));
        tv_or_sign_social.setTypeface(Utility.getOpenSansRegular(this));
    }

    @OnClick(R.id.b_check_sign_up)
    public void navigateSignUpOtp() {
        Intent signUpOtpIntent = new Intent(this, SignUpOtpActivity.class);
        startActivity(signUpOtpIntent);
    }

    @OnClick(R.id.tv_sign_up_login)
    public void navigateLogin() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }

    @OnClick(R.id.tv_sign_up_cancel)
    public void navigateCancel()
    {
        this.onBackPressed();
    }


}
