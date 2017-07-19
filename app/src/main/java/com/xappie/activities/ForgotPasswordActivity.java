package com.xappie.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xappie.R;

public class ForgotPasswordActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_forgot_password);
    }
}
