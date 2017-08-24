package com.xappie.parser;

import android.content.Context;

import com.xappie.models.CountriesListModel;
import com.xappie.models.CountriesModel;
import com.xappie.models.LanguageListModel;
import com.xappie.models.LanguageModel;
import com.xappie.models.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 8/24/2017.
 */

public class CountriesParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        CountriesListModel mCountriesListModel = new CountriesListModel();
        try {
            JSONArray jsonArray = new JSONArray(s);
            ArrayList<CountriesModel> countriesModels = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                CountriesModel mCountriesModel = new CountriesModel();
                mCountriesModel.setId(jsonObject.optString("id"));
                mCountriesModel.setCountry_name(jsonObject.optString("country_name"));
                mCountriesModel.setIso_code(jsonObject.optString("iso_code"));
                countriesModels.add(mCountriesModel);
            }
            mCountriesListModel.setCountriesModels(countriesModels);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mCountriesListModel;
    }

}