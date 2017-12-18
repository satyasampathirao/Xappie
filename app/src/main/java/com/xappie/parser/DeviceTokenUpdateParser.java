package com.xappie.parser;

import android.content.Context;

import com.xappie.models.DeviceTokenUpdateModel;
import com.xappie.models.Model;
import com.xappie.models.SignupSuccessModel;

import org.json.JSONObject;

/**
 * Created by Shankar on 8/24/2017.
 */

public class DeviceTokenUpdateParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        DeviceTokenUpdateModel mDeviceTokenUpdateModel = new DeviceTokenUpdateModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("success")) {
                JSONObject successJsonObject = jsonObject.optJSONObject("success");
                mDeviceTokenUpdateModel.setMessage(successJsonObject.optString("message"));
                mDeviceTokenUpdateModel.setStatus(true);
            } else if (jsonObject.has("failed")) {
                JSONObject failedJsonObject = jsonObject.optJSONObject("failed");
                mDeviceTokenUpdateModel.setMessage(failedJsonObject.optString("message"));
                mDeviceTokenUpdateModel.setStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mDeviceTokenUpdateModel;
    }

}