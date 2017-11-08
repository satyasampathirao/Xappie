package com.xappie.parser;

import android.content.Context;

import com.xappie.models.EntertainmentListModel;
import com.xappie.models.EntertainmentModel;
import com.xappie.models.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 8/24/2017.
 */

public class EntertainmentParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        EntertainmentListModel mEntertainmentListModel = new EntertainmentListModel();
        try {
            JSONArray jsonArray = new JSONArray(s);
            ArrayList<EntertainmentModel> entertainmentModels = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                EntertainmentModel entertainmentModel = new EntertainmentModel();
                entertainmentModel.setId(jsonObject.optString("id"));
                entertainmentModel.setTitle(jsonObject.optString("title"));
                entertainmentModel.setType(jsonObject.optString("type"));
                entertainmentModel.setDescription(jsonObject.optString("description"));
                entertainmentModel.setSource(jsonObject.optString("source"));
                entertainmentModel.setLanguage(jsonObject.optString("language"));
                entertainmentModel.setProfile_image(jsonObject.optString("profile_image"));
                entertainmentModel.setStatus(jsonObject.optString("status"));
                entertainmentModel.setNews_status(jsonObject.optString("news_status"));
                entertainmentModel.setRecordedBy(jsonObject.optString("recordedBy"));
                entertainmentModel.setRecordedDate(jsonObject.optString("recordedDate"));
                entertainmentModels.add(entertainmentModel);
            }
            mEntertainmentListModel.setEntertainmentModels(entertainmentModels);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mEntertainmentListModel;
    }

}