package com.xappie.parser;

import android.content.Context;

import com.xappie.models.Model;
import com.xappie.models.SignupSuccessModel;
import com.xappie.models.SignupSuccessOTPModel;

import org.json.JSONObject;

/**
 * Created by Shankar on 8/24/2017.
 */

public class SignUpSuccessOTPParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        SignupSuccessOTPModel mSignupSuccessOTPModel = new SignupSuccessOTPModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("success")) {
                JSONObject successJsonObject = jsonObject.optJSONObject("success");
                mSignupSuccessOTPModel.setMessage(successJsonObject.optString("message"));
                mSignupSuccessOTPModel.setStatus(true);
            } else if (jsonObject.has("failed")) {
                JSONObject failedJsonObject = jsonObject.optJSONObject("failed");
                mSignupSuccessOTPModel.setMessage(failedJsonObject.optString("message"));
                mSignupSuccessOTPModel.setStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mSignupSuccessOTPModel;
    }

}