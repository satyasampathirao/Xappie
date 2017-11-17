package com.xappie.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.xappie.R;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;


public class SplashActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.splash_activity);

        Handler mSplashHandler = new Handler();
        Runnable action = new Runnable() {
            @Override
            public void run() {
                if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(SplashActivity.this, Constants.SELECTED_COUNTRY_NAME))) {
                    Intent intent = new Intent(SplashActivity.this, DashBoardActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, AppTourActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        };
        mSplashHandler.postDelayed(action, Constants.SPLASH_TIME_OUT);
    }
}