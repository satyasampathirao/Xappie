package com.xappie.parser;

import android.content.Context;

import com.google.gson.Gson;
import com.xappie.models.AdsListModel;
import com.xappie.models.AdsModel;
import com.xappie.models.EventsListModel;
import com.xappie.models.EventsModel;
import com.xappie.models.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 8/24/2017.
 */

public class AdsListParser implements Parser<Model> {
    @Override
    public Model parse(String json, Context context) {
        AdsListModel adsListModel = new AdsListModel();
        try {
            JSONArray jsonArray = new JSONArray(json);
            ArrayList<AdsModel> adsModelArrayList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                AdsModel adsModel = new Gson().fromJson(jsonObject.toString(), AdsModel.class);
                adsModelArrayList.add(adsModel);
            }
            adsListModel.setAdsModels(adsModelArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adsListModel;
    }

}