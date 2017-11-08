package com.xappie.parser;

import android.content.Context;

import com.xappie.models.EntertainmentModel;
import com.xappie.models.Model;
import com.xappie.models.TopStoriesListModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 8/24/2017.
 */

public class TopStoriesParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        TopStoriesListModel mTopStoriesListModel = new TopStoriesListModel();
        try {
            JSONArray jsonArray = new JSONArray(s);
            ArrayList<EntertainmentModel> entertainmentModels = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                EntertainmentModel entertainmentModel = new EntertainmentModel();
                entertainmentModel.setId(jsonObject.optString("id"));
                entertainmentModel.setTitle(jsonObject.optString("title"));
                entertainmentModel.setType(jsonObject.optString("type"));
                entertainmentModel.setSource(jsonObject.optString("source"));
                entertainmentModel.setDescription(jsonObject.optString("description"));
                entertainmentModel.setLanguage(jsonObject.optString("language"));
                entertainmentModel.setProfile_image(jsonObject.optString("profile_image"));
                entertainmentModel.setStatus(jsonObject.optString("status"));
                entertainmentModel.setNews_status(jsonObject.optString("news_status"));
                entertainmentModel.setRecordedBy(jsonObject.optString("recordedBy"));
                entertainmentModel.setRecordedDate(jsonObject.optString("recordedDate"));
                entertainmentModels.add(entertainmentModel);
            }
            mTopStoriesListModel.setEntertainmentModels(entertainmentModels);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mTopStoriesListModel;
    }

}