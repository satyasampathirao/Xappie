package com.xappie.parser;

import android.content.Context;

import com.xappie.models.EventUpdateModel;
import com.xappie.models.Model;

import org.json.JSONObject;

/**
 * Created by Shankar on 8/24/2017.
 */

public class EventUpdateParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        EventUpdateModel mEventUpdateModel = new EventUpdateModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("status") && jsonObject.getString("status").equalsIgnoreCase("success")) {
                mEventUpdateModel.setMessage(jsonObject.optString("msg"));
                mEventUpdateModel.setStatus(true);
            } else if (jsonObject.has("status")) {
                mEventUpdateModel.setMessage(jsonObject.optString("message"));
                mEventUpdateModel.setStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mEventUpdateModel;
    }

}