package com.xappie.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.xappie.R;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.LoginModel;
import com.xappie.models.Model;
import com.xappie.parser.LoginParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.LinkedHashMap;


public class SplashActivity extends BaseActivity implements IAsyncCaller {

    private LoginModel mLoginModel;

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
                    if (!Utility.getSharedPrefBooleanData(SplashActivity.this, Constants.IS_LOGIN_COMPLETED)) {
                        Intent intent = new Intent(SplashActivity.this, DashBoardActivity.class);
                        startActivity(intent);
                        //finish();
                    } else {
                        callLogin();
                    }
                } else {
                    Intent intent = new Intent(SplashActivity.this, AppTourActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        };
        mSplashHandler.postDelayed(action, Constants.SPLASH_TIME_OUT);
    }

    private void callLogin() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        paramMap.put(Constants.AUTH_TYPE, Utility.getSharedPrefStringData(SplashActivity.this, Constants.AUTH_TYPE));
        paramMap.put("email", Utility.getSharedPrefStringData(SplashActivity.this, Constants.SIGN_UP_MAIL_ID));
        paramMap.put(Constants.AUTH_TOKEN, Utility.getSharedPrefStringData(SplashActivity.this, Constants.AUTH_TOKEN));
        paramMap.put("password", Utility.getSharedPrefStringData(SplashActivity.this, Constants.PASSWORD));
        LoginParser mLoginParser = new LoginParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                R.string.please_wait), false,
                APIConstants.LOGIN, paramMap,
                APIConstants.REQUEST_TYPE.POST, this, mLoginParser);
        Utility.execute(serverIntractorAsync);
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof LoginModel) {
                mLoginModel = (LoginModel) model;
                if (mLoginModel.isStatus()) {
                    Utility.setSharedPrefBooleanData(SplashActivity.this, Constants.IS_LOGIN_COMPLETED, true);
                    Utility.setSharedPrefStringData(SplashActivity.this, Constants.SIGN_UP_FIRST_NAME, mLoginModel.getFirst_name());
                    Utility.setSharedPrefStringData(SplashActivity.this, Constants.SIGN_UP_LAST_NAME, mLoginModel.getLast_name());
                    Utility.setSharedPrefStringData(SplashActivity.this, Constants.SIGN_UP_DISPLAY_NAME, mLoginModel.getDisplay_name());
                    Utility.setSharedPrefStringData(SplashActivity.this, Constants.SIGN_UP_MAIL_ID, mLoginModel.getEmail());
                    Utility.setSharedPrefStringData(SplashActivity.this, Constants.SIGN_UP_PHOTO, mLoginModel.getPhoto());
                    Utility.setSharedPrefStringData(SplashActivity.this, Constants.SIGN_UP_UUID, mLoginModel.getUuid());
                    Utility.setSharedPrefStringData(SplashActivity.this, Constants.LOGIN_SESSION_ID, mLoginModel.getCi_session());
                    Utility.setSharedPrefStringData(SplashActivity.this, Constants.JOIN_DATE, Utility.getJoiningDate(mLoginModel.getJoined_date()));
                    Utility.setSharedPrefStringData(SplashActivity.this, Constants.TOKEN, mLoginModel.getCi_session());
                    Intent signUpIntent = new Intent(this, DashBoardActivity.class);
                    startActivity(signUpIntent);
                    //Utility.setSharedPrefStringData(LoginActivity.this, Constants.SIGN_UP_CURRENT_DATE, Utility.getDate());
                } else {
                    Utility.showToastMessage(SplashActivity.this, Utility.capitalizeFirstLetter(mLoginModel.getMessage()));
                }
            }
        }
    }
}