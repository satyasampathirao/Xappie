package com.xappie.parser;

import android.content.Context;

import com.xappie.models.CountriesListModel;
import com.xappie.models.CountriesModel;
import com.xappie.models.LanguageListModel;
import com.xappie.models.LanguageModel;
import com.xappie.models.Model;
import com.xappie.models.SpinnerModel;

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
            ArrayList<SpinnerModel> spinnerModels = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                CountriesModel mCountriesModel = new CountriesModel();
                SpinnerModel mSpinnerModel = new SpinnerModel();
                mCountriesModel.setId(jsonObject.optString("id"));
                mCountriesModel.setCountry_name(jsonObject.optString("country_name"));
                mSpinnerModel.setTitle(jsonObject.optString("country_name"));
                mCountriesModel.setIso_code(jsonObject.optString("iso_code"));
                mCountriesModel.setFlag(jsonObject.optString("flag"));
                countriesModels.add(mCountriesModel);
                spinnerModels.add(mSpinnerModel);
            }
            mCountriesListModel.setCountriesModels(countriesModels);
            mCountriesListModel.setSpinnerCountyModels(spinnerModels);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mCountriesListModel;
    }

}