package com.xappie.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 8/24/2017.
 */

public class LocalityListModel extends Model {
    private ArrayList<LocalityModel> localityModels;
    private ArrayList<SpinnerModel> spinnerModels;

    public ArrayList<LocalityModel> getLocalityModels() {
        return localityModels;
    }

    public void setLocalityModels(ArrayList<LocalityModel> localityModels) {
        this.localityModels = localityModels;
    }

    public ArrayList<SpinnerModel> getSpinnerModels() {
        return spinnerModels;
    }

    public void setSpinnerModels(ArrayList<SpinnerModel> spinnerModels) {
        this.spinnerModels = spinnerModels;
    }
}
