package com.xappie.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 8/29/2017.
 */

public class EntertainmentListModel extends Model {
    private ArrayList<EntertainmentModel> entertainmentModels;

    public ArrayList<EntertainmentModel> getEntertainmentModels() {
        return entertainmentModels;
    }

    public void setEntertainmentModels(ArrayList<EntertainmentModel> entertainmentModels) {
        this.entertainmentModels = entertainmentModels;
    }
}
