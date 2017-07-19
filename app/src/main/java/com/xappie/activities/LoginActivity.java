package com.xappie.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class LoginActivity extends BaseActivity {
    @BindView(R.id.b_check)
    Button btn_check;
    @BindView(R.id.relativeLayout_login)
    RelativeLayout rl_login;
    @BindView(R.id.linear_login)
    LinearLayout ll_login;
    @BindView(R.id.b_log_cancel)
    Button btn_log_cancel;
    @BindView(R.id.b_log_login)
    Button btn_log_login;
    @BindView(R.id.b_log_sign_up)
    Button btn_log_sign_up;
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
    @BindView(R.id.relative_check_icon)
    RelativeLayout rl_check_icon;
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
    }

    @OnClick(R.id.b_log_sign_up)

    public void sign_up() {
        Intent i = new Intent(this, SignUpActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.tv_trouble_getting)
    public void trouble() {
        Intent i = new Intent(this, ForgotPasswordActivity.class);
        startActivity(i);
    }
}
