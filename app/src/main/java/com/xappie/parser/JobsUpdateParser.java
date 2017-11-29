package com.xappie.parser;

import android.content.Context;

import com.xappie.models.JobUpdateModel;
import com.xappie.models.Model;

import org.json.JSONObject;

/**
 * Created by Santosh on 29-11-2017.
 */

public class JobsUpdateParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        JobUpdateModel jobUpdateModel = new JobUpdateModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("status") && jsonObject.getString("status").equalsIgnoreCase("success")) {
                jobUpdateModel.setMessage(jsonObject.optString("msg"));
                jobUpdateModel.setStatus(true);
            } else if (jsonObject.has("status")) {
                jobUpdateModel.setMessage(jsonObject.optString("message"));
                jobUpdateModel.setStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobUpdateModel;
    }
}
