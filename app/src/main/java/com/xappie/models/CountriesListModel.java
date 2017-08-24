package com.xappie.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 8/24/2017.
 */

public class CountriesListModel extends Model {
    private ArrayList<CountriesModel> countriesModels;

    public ArrayList<CountriesModel> getCountriesModels() {
        return countriesModels;
    }

    public void setCountriesModels(ArrayList<CountriesModel> countriesModels) {
        this.countriesModels = countriesModels;
    }
}
