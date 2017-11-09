package com.xappie.parser;

import android.content.Context;

import com.google.gson.Gson;
import com.xappie.models.Model;
import com.xappie.models.WhoIsGoingListModel;
import com.xappie.models.WhoIsGoingModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 8/24/2017.
 */

public class WhoIsGoingListParser implements Parser<Model> {
    @Override
    public Model parse(String json, Context context) {
        WhoIsGoingListModel whoIsGoingListModel = new WhoIsGoingListModel();
        try {
            JSONArray jsonArray = new JSONArray(json);
            ArrayList<WhoIsGoingModel> whoIsGoingModels = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                WhoIsGoingModel whoIsGoingModel = new Gson().fromJson(jsonObject.toString(), WhoIsGoingModel.class);
                whoIsGoingModels.add(whoIsGoingModel);
            }
            whoIsGoingListModel.setWhoIsGoingModels(whoIsGoingModels);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return whoIsGoingListModel;
    }

}