package com.xappie.parser;

import android.content.Context;

import com.xappie.models.AddEventModel;
import com.xappie.models.IAmGoingModel;
import com.xappie.models.Model;

import org.json.JSONObject;

/**
 * Created by Shankar on 8/24/2017.
 */

public class IAmGoingParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        IAmGoingModel mIAmGoingModel = new IAmGoingModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("status") && jsonObject.getString("status").equalsIgnoreCase("success")) {
                mIAmGoingModel.setMessage(jsonObject.optString("msg"));
                mIAmGoingModel.setStatus(true);
            } else if (jsonObject.has("status")) {
                mIAmGoingModel.setMessage(jsonObject.optString("message"));
                mIAmGoingModel.setStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mIAmGoingModel;
    }

}