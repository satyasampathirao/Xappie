package com.xappie.parser;

import android.content.Context;

import com.xappie.models.EntertainmentListModel;
import com.xappie.models.EntertainmentModel;
import com.xappie.models.EntertainmentTopStoriesDetailModel;
import com.xappie.models.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 8/24/2017.
 */

public class EntertainmentTopStoriesDetailParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        EntertainmentTopStoriesDetailModel mEntertainmentTopStoriesDetailModel = new EntertainmentTopStoriesDetailModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            EntertainmentModel entertainmentModel = new EntertainmentModel();
            entertainmentModel.setId(jsonObject.optString("id"));
            entertainmentModel.setTitle(jsonObject.optString("title"));
            entertainmentModel.setType(jsonObject.optString("type"));
            entertainmentModel.setDescription(jsonObject.optString("description"));
            entertainmentModel.setSource(jsonObject.optString("source"));
            entertainmentModel.setLanguage(jsonObject.optString("language"));
            entertainmentModel.setProfile_image(jsonObject.optString("profile_image"));
            entertainmentModel.setBanner_image(jsonObject.optString("banner_image"));
            entertainmentModel.setStatus(jsonObject.optString("status"));
            entertainmentModel.setNews_status(jsonObject.optString("news_status"));
            entertainmentModel.setWeblink(jsonObject.optString("weblink"));
            entertainmentModel.setRecordedBy(jsonObject.optString("recordedBy"));
            entertainmentModel.setRecordedDate(jsonObject.optString("recordedDate"));
            mEntertainmentTopStoriesDetailModel.setmCurrentDetailModel(entertainmentModel);

            JSONObject nextJsonObject = jsonObject.optJSONObject("next");
            EntertainmentModel entertainmentNextModel = new EntertainmentModel();
            entertainmentNextModel.setId(nextJsonObject.optString("id"));
            entertainmentNextModel.setTitle(nextJsonObject.optString("title"));
            entertainmentNextModel.setType(nextJsonObject.optString("type"));
            entertainmentModel.setSource(jsonObject.optString("source"));
            entertainmentNextModel.setDescription(nextJsonObject.optString("description"));
            entertainmentNextModel.setLanguage(nextJsonObject.optString("language"));
            entertainmentNextModel.setProfile_image(nextJsonObject.optString("profile_image"));
            entertainmentNextModel.setStatus(nextJsonObject.optString("status"));
            entertainmentNextModel.setNews_status(nextJsonObject.optString("news_status"));
            entertainmentNextModel.setRecordedBy(nextJsonObject.optString("recordedBy"));
            entertainmentNextModel.setRecordedDate(nextJsonObject.optString("recordedDate"));
            mEntertainmentTopStoriesDetailModel.setmNextDetailModel(entertainmentNextModel);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mEntertainmentTopStoriesDetailModel;
    }

}