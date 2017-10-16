package com.xappie.parser;

import android.content.Context;

import com.xappie.models.GalleryCategoryListModel;
import com.xappie.models.GalleryCategoryModel;
import com.xappie.models.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 10/06/2017.
 */

public class GalleryCategoryListParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        GalleryCategoryListModel mGalleryCategoryListModel = new GalleryCategoryListModel();
        try {
            JSONArray jsonArray = new JSONArray(s);
            ArrayList<GalleryCategoryModel> galleryCategoryModels = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject actorsJsonObject = jsonArray.optJSONObject(i);
                GalleryCategoryModel galleryCategoryModel = new GalleryCategoryModel();
                galleryCategoryModel.setId(actorsJsonObject.optString("id"));
                galleryCategoryModel.setTitle(actorsJsonObject.optString("title"));
                galleryCategoryModel.setLanguage(actorsJsonObject.optString("language"));
                galleryCategoryModel.setCategory(actorsJsonObject.optString("category"));
                galleryCategoryModel.setIs_banner(actorsJsonObject.optString("is_banner"));
                galleryCategoryModel.setBanner_image(actorsJsonObject.optString("banner_image"));
                galleryCategoryModel.setProfile_image(actorsJsonObject.optString("profile_image"));
                galleryCategoryModels.add(galleryCategoryModel);
            }
            mGalleryCategoryListModel.setGalleryCategoryModels(galleryCategoryModels);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mGalleryCategoryListModel;
    }
}