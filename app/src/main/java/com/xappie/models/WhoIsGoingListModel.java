package com.xappie.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 11/9/2017.
 */

public class WhoIsGoingListModel extends Model {
    private ArrayList<WhoIsGoingModel> whoIsGoingModels;

    public ArrayList<WhoIsGoingModel> getWhoIsGoingModels() {
        return whoIsGoingModels;
    }

    public void setWhoIsGoingModels(ArrayList<WhoIsGoingModel> whoIsGoingModels) {
        this.whoIsGoingModels = whoIsGoingModels;
    }
}
