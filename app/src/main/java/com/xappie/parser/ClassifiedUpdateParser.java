package com.xappie.parser;

import android.content.Context;

import com.xappie.models.ClassifiedUpdateModel;
import com.xappie.models.Model;

import org.json.JSONObject;

/**
 * Created by Santosh on 23-11-2017.
 * Edited by Shankar on  23-11-2017.
 */

public class ClassifiedUpdateParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        ClassifiedUpdateModel classifiedUpdateModel = new ClassifiedUpdateModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("status") && jsonObject.getString("status").equalsIgnoreCase("success")) {
                classifiedUpdateModel.setMessage(jsonObject.optString("msg"));
                classifiedUpdateModel.setStatus(true);
            } else if (jsonObject.has("status")) {
                classifiedUpdateModel.setMessage(jsonObject.optString("message"));
                classifiedUpdateModel.setStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classifiedUpdateModel;
    }
}
