package com.xappie.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 11/17/2017.
 */

public class AdsListModel extends Model {
    private ArrayList<AdsModel> adsModels;

    public ArrayList<AdsModel> getAdsModels() {
        return adsModels;
    }

    public void setAdsModels(ArrayList<AdsModel> adsModels) {
        this.adsModels = adsModels;
    }
}
