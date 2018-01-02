package com.xappie.parser;

import android.content.Context;

import com.xappie.models.LocalityListModel;
import com.xappie.models.LocalityModel;
import com.xappie.models.Model;
import com.xappie.models.SpinnerModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 8/24/2017.
 */

public class LocalityParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        LocalityListModel localityListModel = new LocalityListModel();
        try {
            JSONArray jsonArray = new JSONArray(s);
            ArrayList<LocalityModel> localityModels = new ArrayList<>();
            ArrayList<SpinnerModel> spinnerModels = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                LocalityModel mLocalityModel = new LocalityModel();
                SpinnerModel mSpinnerModel = new SpinnerModel();
                mLocalityModel.setId(jsonObject.optString("id"));
                mLocalityModel.setName(jsonObject.optString("name"));
                mSpinnerModel.setTitle(jsonObject.optString("name"));
                localityModels.add(mLocalityModel);
                spinnerModels.add(mSpinnerModel);
            }
            localityListModel.setLocalityModels(localityModels);
            localityListModel.setSpinnerModels(spinnerModels);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return localityListModel;
    }

}