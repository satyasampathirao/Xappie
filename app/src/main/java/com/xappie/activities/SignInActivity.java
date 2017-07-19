package com.xappie.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.xappie.R;
import com.xappie.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInActivity extends BaseActivity {

    @BindView(R.id.b_check_sign_in)
    Button b_check_sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        b_check_sign_in.setTypeface(Utility.getMaterialIconsRegular(this));
    }
}