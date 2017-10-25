package com.xappie.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 10/25/2017.
 */

public class HomePageBannerListModel extends Model {

    private ArrayList<HomePageBannerModel> homePageBannerModels;

    public ArrayList<HomePageBannerModel> getHomePageBannerModels() {
        return homePageBannerModels;
    }

    public void setHomePageBannerModels(ArrayList<HomePageBannerModel> homePageBannerModels) {
        this.homePageBannerModels = homePageBannerModels;
    }
}
