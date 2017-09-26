package com.xappie.parser;

import android.content.Context;

import com.xappie.models.Model;
import com.xappie.models.SignupSuccessModel;

import org.json.JSONObject;

/**
 * Created by Shankar on 8/24/2017.
 */

public class SignUpSuccessParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        SignupSuccessModel mSignupSuccessModel = new SignupSuccessModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("success")) {
                JSONObject successJsonObject = jsonObject.optJSONObject("success");
                mSignupSuccessModel.setMessage(successJsonObject.optString("message"));
                mSignupSuccessModel.setStatus(true);
                mSignupSuccessModel.setUuid(successJsonObject.optString("uuid"));
            } else if (jsonObject.has("failed")) {
                JSONObject failedJsonObject = jsonObject.optJSONObject("failed");
                mSignupSuccessModel.setMessage(failedJsonObject.optString("message"));
                mSignupSuccessModel.setStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mSignupSuccessModel;
    }

}