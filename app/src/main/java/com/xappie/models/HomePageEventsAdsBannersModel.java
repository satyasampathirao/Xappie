package com.xappie.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 11/9/2017.
 */

public class HomePageEventsAdsBannersModel extends Model {
    private ArrayList<BannersModel> bannersModels;
    private ArrayList<EventsModel> eventsModels;
    private ArrayList<ClassifiedsModel> classifiedsModel;
    private ArrayList<AdsModel> adsModels;

    public ArrayList<BannersModel> getBannersModels() {
        return bannersModels;
    }

    public void setBannersModels(ArrayList<BannersModel> bannersModels) {
        this.bannersModels = bannersModels;
    }

    public ArrayList<EventsModel> getEventsModels() {
        return eventsModels;
    }

    public void setEventsModels(ArrayList<EventsModel> eventsModels) {
        this.eventsModels = eventsModels;
    }

    public ArrayList<AdsModel> getAdsModels() {
        return adsModels;
    }

    public void setAdsModels(ArrayList<AdsModel> adsModels) {
        this.adsModels = adsModels;
    }

    public ArrayList<ClassifiedsModel> getClassifiedsModel() {
        return classifiedsModel;
    }

    public void setClassifiedsModel(ArrayList<ClassifiedsModel> classifiedsModel) {
        this.classifiedsModel = classifiedsModel;
    }
}
