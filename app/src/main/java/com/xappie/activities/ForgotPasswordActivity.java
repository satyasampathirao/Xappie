package com.xappie.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xappie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotPasswordActivity extends BaseActivity
{
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI()
    {

    }
}
