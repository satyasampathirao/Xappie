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

public class LoginActivity extends BaseActivity {
    @BindView(R.id.b_check)
    Button b_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initUI();
    }
     private void initUI()
     {
         b_check.setTypeface(Utility.getMaterialIconsRegular(this));
     }

     @OnClick(R.id.b_log_sign_up)

     public void sign_up ()
     {
         Intent i = new Intent(this,SignUpActivity.class);
         startActivity(i);
     }

     @OnClick(R.id.tv_trouble_getting)
    public void trouble()
     {
         Intent i = new Intent(this,ForgotPasswordActivity.class);
         startActivity(i);
     }
}
