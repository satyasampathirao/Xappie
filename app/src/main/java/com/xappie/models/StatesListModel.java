package com.xappie.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 8/24/2017.
 */

public class StatesListModel extends Model {
    private ArrayList<StateModel> stateModels;

    public ArrayList<StateModel> getStateModels() {
        return stateModels;
    }

    public void setStateModels(ArrayList<StateModel> stateModels) {
        this.stateModels = stateModels;
    }
}
