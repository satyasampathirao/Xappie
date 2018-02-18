package com.xappie.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrivacyPolicyActivity extends Activity {

    @BindView(R.id.tv_privacy_policy)
    TextView tv_privacy_policy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_privacy_policy);
        ButterKnife.bind(this);
        tv_privacy_policy.setTypeface(Utility.getOpenSansRegular(this));
    }
}
