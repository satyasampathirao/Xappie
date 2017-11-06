package com.xappie.parser;

import android.content.Context;

import com.xappie.models.AddEventModel;
import com.xappie.models.Model;

import org.json.JSONObject;

/**
 * Created by Shankar on 8/24/2017.
 */

public class AddEventSuccessParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        AddEventModel mAddEventModel = new AddEventModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("status") && jsonObject.getString("status").equalsIgnoreCase("success")) {
                mAddEventModel.setMessage(jsonObject.optString("msg"));
                mAddEventModel.setStatus(true);
            } else if (jsonObject.has("status")) {
                mAddEventModel.setMessage(jsonObject.optString("message"));
                mAddEventModel.setStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mAddEventModel;
    }

}