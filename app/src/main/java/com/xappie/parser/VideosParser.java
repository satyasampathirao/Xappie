package com.xappie.parser;

import android.content.Context;

import com.xappie.models.Model;
import com.xappie.models.VideosListModel;
import com.xappie.models.VideosModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 8/24/2017.
 */

public class VideosParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        VideosListModel mVideosListModel = new VideosListModel();
        try {
            JSONArray jsonArray = new JSONArray(s);
            ArrayList<VideosModel> videosModels = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                VideosModel videosModel = new VideosModel();
                videosModel.setId(jsonObject.optString("id"));
                videosModel.setTitle(jsonObject.optString("title"));
                videosModel.setLanguage(jsonObject.optString("language"));
                videosModel.setThumb_nail(jsonObject.optString("thumb_nail"));
                videosModel.setStatus(jsonObject.optString("status"));
                videosModel.setUrl(jsonObject.optString("url"));
                videosModels.add(videosModel);
            }
            mVideosListModel.setVideosModels(videosModels);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mVideosListModel;
    }

}