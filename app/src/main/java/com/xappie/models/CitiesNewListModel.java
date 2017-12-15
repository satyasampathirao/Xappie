package com.xappie.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 8/24/2017.
 */

public class CitiesNewListModel extends Model {
    private ArrayList<CityModel> cityModels;
    private ArrayList<SpinnerModel> spinnerModels;

    public ArrayList<CityModel> getCityModels() {
        return cityModels;
    }

    public void setCityModels(ArrayList<CityModel> cityModels) {
        this.cityModels = cityModels;
    }

    public ArrayList<SpinnerModel> getSpinnerModels() {
        return spinnerModels;
    }

    public void setSpinnerModels(ArrayList<SpinnerModel> spinnerModels) {
        this.spinnerModels = spinnerModels;
    }
}
