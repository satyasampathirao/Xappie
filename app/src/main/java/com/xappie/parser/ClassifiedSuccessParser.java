package com.xappie.parser;

import android.content.Context;

import com.xappie.models.AddClassifiedModel;
import com.xappie.models.Model;

import org.json.JSONObject;

/**
 * Created by Santosh on 23-11-2017.
 * Edited by Shankar Pilli 23-11-2017.
 */

public class ClassifiedSuccessParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        AddClassifiedModel addClassifiedModel = new AddClassifiedModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("status") && jsonObject.getString("status").equalsIgnoreCase("success")) {
                addClassifiedModel.setMsg(jsonObject.optString("msg"));
                addClassifiedModel.setStatus(true);
            } else if (jsonObject.has("status")) {
                addClassifiedModel.setMsg(jsonObject.optString("message"));
                addClassifiedModel.setStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return addClassifiedModel;
    }
}
