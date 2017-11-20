package com.xappie.parser;

import android.content.Context;

import com.google.gson.Gson;
import com.xappie.models.ClassifiedsListModel;
import com.xappie.models.ClassifiedsModel;
import com.xappie.models.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Santosh on 17-11-2017.
 */

public class ClassifiedsParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        ClassifiedsListModel mClassifiedsListModel = new ClassifiedsListModel();
        try {
            JSONArray jsonArray = new JSONArray(s);
            ArrayList<ClassifiedsModel> classifiedsModels = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                ClassifiedsModel classifiedsModel = new Gson().fromJson(jsonObject.toString(), ClassifiedsModel.class);
                classifiedsModels.add(classifiedsModel);
            }
            mClassifiedsListModel.setClassifiedsModels(classifiedsModels);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mClassifiedsListModel;
    }
}
