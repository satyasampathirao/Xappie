package com.xappie.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Shankar on 7/21/2017.
 */

public class ActressListModel extends Model implements Serializable {
    private ArrayList<ActressModel> actressModels;

    public ArrayList<ActressModel> getActressModels() {
        return actressModels;
    }

    public void setActressModels(ArrayList<ActressModel> actressModels) {
        this.actressModels = actressModels;
    }
}
