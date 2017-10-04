package com.xappie.parser;

import android.content.Context;

import com.xappie.models.Model;
import com.xappie.models.SignupLoginSuccessModel;

import org.json.JSONObject;

/**
 * Created by Shankar on 8/24/2017.
 */

public class SignUpLoginSuccessParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        SignupLoginSuccessModel mSignupLoginSuccessModel = new SignupLoginSuccessModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("success")) {
                JSONObject successJsonObject = jsonObject.optJSONObject("success");
                mSignupLoginSuccessModel.setMessage(successJsonObject.optString("message"));
                mSignupLoginSuccessModel.setStatus(true);
            } else if (jsonObject.has("failed")) {
                JSONObject failedJsonObject = jsonObject.optJSONObject("failed");
                mSignupLoginSuccessModel.setMessage(failedJsonObject.optString("message"));
                mSignupLoginSuccessModel.setStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mSignupLoginSuccessModel;
    }

}