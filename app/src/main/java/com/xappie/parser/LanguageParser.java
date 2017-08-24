package com.xappie.parser;

import android.content.Context;

import com.xappie.models.LanguageListModel;
import com.xappie.models.LanguageModel;
import com.xappie.models.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 8/24/2017.
 */

public class LanguageParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        LanguageListModel mLanguageListModel = new LanguageListModel();
        try {
            JSONArray jsonArray = new JSONArray(s);
            ArrayList<LanguageModel> languageModels = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                LanguageModel languageModel = new LanguageModel();
                languageModel.setId(jsonObject.optString("id"));
                languageModel.setName(jsonObject.optString("name"));
                languageModel.setName_native(jsonObject.optString("name_native"));
                languageModels.add(languageModel);
            }
            mLanguageListModel.setLanguageModels(languageModels);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mLanguageListModel;
    }

}