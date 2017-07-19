package com.xappie.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.xappie.R;
import com.xappie.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity
{
    @BindView(R.id.b_check_sign_up)
    Button b_check_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        initUI();
    }
    private void initUI()
    {
        b_check_sign_up.setTypeface(Utility.getMaterialIconsRegular(this));
    }

    @OnClick(R.id.b_check_sign_up)
    public void sign_up ()
    {
        Intent i = new Intent(this,SignInActivity.class);
        startActivity(i);
    }
}
