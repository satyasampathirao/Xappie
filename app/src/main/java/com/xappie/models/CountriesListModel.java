package com.xappie.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 8/24/2017.
 */

public class CountriesListModel extends Model {
    private ArrayList<CountriesModel> countriesModels;
    private ArrayList<SpinnerModel> spinnerCountyModels;

    public ArrayList<CountriesModel> getCountriesModels() {
        return countriesModels;
    }

    public void setCountriesModels(ArrayList<CountriesModel> countriesModels) {
        this.countriesModels = countriesModels;
    }

    public ArrayList<SpinnerModel> getSpinnerCountyModels() {
        return spinnerCountyModels;
    }

    public void setSpinnerCountyModels(ArrayList<SpinnerModel> spinnerCountyModels) {
        this.spinnerCountyModels = spinnerCountyModels;
    }
}
