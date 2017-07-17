package com.xappie.activities;

import android.content.Intent;
import android.os.Bundle;

import com.xappie.MainActivity;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;


public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Utility.getSharedPrefBooleanData(this, Constants.IS_TOUR_COMPLETED)) {
            Intent intent = new Intent(this, DashBoardActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, AppTourActivity.class);
            startActivity(intent);
        }
        finish();
    }
}