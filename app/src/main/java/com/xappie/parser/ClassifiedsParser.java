package com.xappie.parser;

import android.content.Context;

import com.xappie.models.ClassifiedsListModel;
import com.xappie.models.ClassifiedsModel;
import com.xappie.models.EntertainmentListModel;
import com.xappie.models.EntertainmentModel;
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
                ClassifiedsModel classifiedsModel = new ClassifiedsModel();
                classifiedsModel.setId(jsonObject.optString("id"));
                classifiedsModel.setCountry(jsonObject.optString("Country"));
                classifiedsModel.setName(jsonObject.optString("Name"));
                classifiedsModel.setState(jsonObject.optString("State"));
                classifiedsModel.setImage(jsonObject.optString("Image"));
                classifiedsModel.setRecordedBy(jsonObject.optString("recordedBy"));
                classifiedsModel.setRecordedDate(jsonObject.optString("recordedDate"));
                classifiedsModels.add(classifiedsModel);
            }
            mClassifiedsListModel.setClassifiedsModels(classifiedsModels);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mClassifiedsListModel;
    }
    }
