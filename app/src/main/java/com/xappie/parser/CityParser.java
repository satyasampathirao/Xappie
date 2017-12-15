package com.xappie.parser;

import android.content.Context;

import com.xappie.models.CitiesNewListModel;
import com.xappie.models.CityModel;
import com.xappie.models.CountriesListModel;
import com.xappie.models.CountriesModel;
import com.xappie.models.Model;
import com.xappie.models.SpinnerModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 8/24/2017.
 */

public class CityParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        CitiesNewListModel citiesNewListModel = new CitiesNewListModel();
        try {
            JSONArray jsonArray = new JSONArray(s);
            ArrayList<CityModel> cityModels = new ArrayList<>();
            ArrayList<SpinnerModel> spinnerModels = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                CityModel mCityModel = new CityModel();
                SpinnerModel mSpinnerModel = new SpinnerModel();
                mCityModel.setId(jsonObject.optString("id"));
                mCityModel.setName(jsonObject.optString("name"));
                mSpinnerModel.setTitle(jsonObject.optString("name"));
                cityModels.add(mCityModel);
                spinnerModels.add(mSpinnerModel);
            }
            citiesNewListModel.setCityModels(cityModels);
            citiesNewListModel.setSpinnerModels(spinnerModels);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return citiesNewListModel;
    }

}