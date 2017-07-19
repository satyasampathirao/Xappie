package com.xappie.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpOtpActivity extends BaseActivity {

    @BindView(R.id.b_check_sign_up_otp)
    Button btn_check_sign_up_otp;
    @BindView(R.id.relativeLayout_sign_up_otp)
    RelativeLayout rl_sign_up_otp;
    @BindView(R.id.linear_sign_up_otp)
    LinearLayout ll_sign_up_otp;
    @BindView(R.id.b_sign_up_otp_cancel)
    Button btn_sign_up_otp_cancel;
    @BindView(R.id.b_sign_up_otp_signUp)
    Button btn_sign_up_otp_signup;
    @BindView(R.id.b_sign_up_otp_login)
    Button btn_sign_up_otp_login;
    @BindView(R.id.relative_sign_up_otp_middle)
    RelativeLayout rl_sign_up_otp_otp;
    @BindView(R.id.tv_enter_otp)
    TextView tv_enter_otp;
    @BindView(R.id.et_sign_up_otp_otp)
    EditText edt_sign_up_otp_otp;
    @BindView(R.id.tv_resend_otp)
    TextView tv_resend_otp;
    @BindView(R.id.et_sign_in_first_name)
    EditText edt_sign_in_first_name;
    @BindView(R.id.et_sign_in_last_name)
    EditText edt_sign_in_last_name;
    @BindView(R.id.radio_group_sign_up_otp)
    RadioGroup rg_sign_up_otp;
    @BindView(R.id.radio_button_male)
    RadioButton rb_male;
    @BindView(R.id.radio_button_female)
    RadioButton rb_female;
    @BindView(R.id.relative_check_icon)
    RelativeLayout rl_check_icon;
    @BindView(R.id.linear_sign_up_otp_privacy)
    LinearLayout ll_sign_up_otp_privacy;
    @BindView(R.id.tv_by_logging_agree)
    TextView tv_by_logging_agree;
    @BindView(R.id.tv_t_c)
    TextView tv_t_c;
    @BindView(R.id.tv_and)
    TextView tv_and;
    @BindView(R.id.tv_privacy)
    TextView tv_privacy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_sign_up_otp);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        btn_check_sign_up_otp.setTypeface(Utility.getMaterialIconsRegular(this));
    }
    @OnClick(R.id.b_sign_up_otp_login)
    public void otp_login()
    {
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
    }
}