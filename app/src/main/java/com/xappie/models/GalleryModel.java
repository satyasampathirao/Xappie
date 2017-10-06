package com.xappie.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 7/21/2017.
 */

public class GalleryModel extends Model {
    private ArrayList<GalleryItemModel> moviesGalleryList;
    private ArrayList<GalleryItemModel> actorsGalleryList;
    private ArrayList<GalleryItemModel> actressGalleryList;
    private ArrayList<GalleryItemModel> eventsGalleryList;

    public ArrayList<GalleryItemModel> getMoviesGalleryList() {
        return moviesGalleryList;
    }

    public void setMoviesGalleryList(ArrayList<GalleryItemModel> moviesGalleryList) {
        this.moviesGalleryList = moviesGalleryList;
    }

    public ArrayList<GalleryItemModel> getActorsGalleryList() {
        return actorsGalleryList;
    }

    public void setActorsGalleryList(ArrayList<GalleryItemModel> actorsGalleryList) {
        this.actorsGalleryList = actorsGalleryList;
    }

    public ArrayList<GalleryItemModel> getActressGalleryList() {
        return actressGalleryList;
    }

    public void setActressGalleryList(ArrayList<GalleryItemModel> actressGalleryList) {
        this.actressGalleryList = actressGalleryList;
    }

    public ArrayList<GalleryItemModel> getEventsGalleryList() {
        return eventsGalleryList;
    }

    public void setEventsGalleryList(ArrayList<GalleryItemModel> eventsGalleryList) {
        this.eventsGalleryList = eventsGalleryList;
    }
}
