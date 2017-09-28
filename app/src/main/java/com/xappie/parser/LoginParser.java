package com.xappie.parser;

import android.content.Context;

import com.xappie.models.LoginModel;
import com.xappie.models.Model;
import com.xappie.models.SignupSuccessOTPModel;

import org.json.JSONObject;

/**
 * Created by Shankar on 8/24/2017.
 */

public class LoginParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        LoginModel mLoginModel = new LoginModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("status") && jsonObject.optString("status").equalsIgnoreCase("success")) {
                mLoginModel.setStatus(true);
                mLoginModel.setCi_session(jsonObject.optString("ci_session"));
                mLoginModel.setMessage(jsonObject.optString("msg"));
                mLoginModel.setUuid(jsonObject.optString("uuid"));
                mLoginModel.setEmail(jsonObject.optString("email"));
                mLoginModel.setFirst_name(jsonObject.optString("first_name"));
                mLoginModel.setLast_name(jsonObject.optString("last_name"));
                mLoginModel.setPhoto(jsonObject.optString("photo"));
                mLoginModel.setCi_session(jsonObject.optString("ci_session"));
            } else if (jsonObject.has("failed")) {
                JSONObject jsonObject1 = jsonObject.optJSONObject("failed");
                mLoginModel.setStatus(false);
                mLoginModel.setMessage(jsonObject1.optString("message"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mLoginModel;
    }
}