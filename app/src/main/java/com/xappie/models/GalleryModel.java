package com.xappie.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 7/21/2017.
 */

public class GalleryModel extends Model {
    private ArrayList<GallerySubModel> moviesGalleryList;
    private ArrayList<GallerySubModel> actorsGalleryList;
    private ArrayList<GallerySubModel> actressGalleryList;
    private ArrayList<GallerySubModel> eventsGalleryList;

    public ArrayList<GallerySubModel> getMoviesGalleryList() {
        return moviesGalleryList;
    }

    public void setMoviesGalleryList(ArrayList<GallerySubModel> moviesGalleryList) {
        this.moviesGalleryList = moviesGalleryList;
    }

    public ArrayList<GallerySubModel> getActorsGalleryList() {
        return actorsGalleryList;
    }

    public void setActorsGalleryList(ArrayList<GallerySubModel> actorsGalleryList) {
        this.actorsGalleryList = actorsGalleryList;
    }

    public ArrayList<GallerySubModel> getActressGalleryList() {
        return actressGalleryList;
    }

    public void setActressGalleryList(ArrayList<GallerySubModel> actressGalleryList) {
        this.actressGalleryList = actressGalleryList;
    }

    public ArrayList<GallerySubModel> getEventsGalleryList() {
        return eventsGalleryList;
    }

    public void setEventsGalleryList(ArrayList<GallerySubModel> eventsGalleryList) {
        this.eventsGalleryList = eventsGalleryList;
    }
}
