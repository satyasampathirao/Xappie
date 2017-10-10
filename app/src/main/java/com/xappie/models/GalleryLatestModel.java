package com.xappie.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 7/21/2017.
 */

public class GalleryLatestModel extends Model {
    private ArrayList<GalleryItemModel> latestGalleryList;

    public ArrayList<GalleryItemModel> getLatestGalleryList() {
        return latestGalleryList;
    }

    public void setLatestGalleryList(ArrayList<GalleryItemModel> latestGalleryList) {
        this.latestGalleryList = latestGalleryList;
    }
}
