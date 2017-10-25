package com.xappie.parser;

import android.content.Context;

import com.xappie.models.HomePageBannerListModel;
import com.xappie.models.HomePageBannerModel;
import com.xappie.models.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 8/24/2017.
 */

public class HomePageBannerParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        HomePageBannerListModel homePageBannerListModel = new HomePageBannerListModel();
        try {
            JSONArray jsonArray = new JSONArray(s);
            ArrayList<HomePageBannerModel> homePageBannerModels = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject homePageBannerModelJsonObject = jsonArray.optJSONObject(i);
                HomePageBannerModel homePageBannerModel = new HomePageBannerModel();
                homePageBannerModel.setId(homePageBannerModelJsonObject.optString("id"));
                homePageBannerModel.setBanner_image(homePageBannerModelJsonObject.optString("banner_image"));
                homePageBannerModel.setRecordedDate(homePageBannerModelJsonObject.optString("recordedDate"));
                homePageBannerModels.add(homePageBannerModel);

                homePageBannerListModel.setHomePageBannerModels(homePageBannerModels);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return homePageBannerListModel;
    }

}