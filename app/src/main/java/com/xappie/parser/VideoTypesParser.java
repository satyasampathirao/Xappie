package com.xappie.parser;

import android.content.Context;

import com.xappie.models.Model;
import com.xappie.models.VideoTypeModel;
import com.xappie.models.VideoTypesListModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 8/24/2017.
 */

public class VideoTypesParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        VideoTypesListModel mVideoTypesListModel = new VideoTypesListModel();
        try {
            JSONArray jsonArray = new JSONArray(s);
            ArrayList<VideoTypeModel> videoTypeModels = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                VideoTypeModel videoTypeModel = new VideoTypeModel();
                videoTypeModel.setId(jsonObject.optString("id"));
                videoTypeModel.setName(jsonObject.optString("name"));
                videoTypeModels.add(videoTypeModel);
            }
            mVideoTypesListModel.setVideoTypeModels(videoTypeModels);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mVideoTypesListModel;
    }

}