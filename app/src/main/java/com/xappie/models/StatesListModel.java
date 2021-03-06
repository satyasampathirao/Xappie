package com.xappie.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 8/24/2017.
 */

public class StatesListModel extends Model {
    private ArrayList<StateModel> stateModels;
    private ArrayList<SpinnerModel> spinnerModels;

    public ArrayList<StateModel> getStateModels() {
        return stateModels;
    }

    public void setStateModels(ArrayList<StateModel> stateModels) {
        this.stateModels = stateModels;
    }

    public ArrayList<SpinnerModel> getSpinnerModels() {
        return spinnerModels;
    }

    public void setSpinnerModels(ArrayList<SpinnerModel> spinnerModels) {
        this.spinnerModels = spinnerModels;
    }
}
