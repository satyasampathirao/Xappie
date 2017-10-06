package com.xappie.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 7/21/2017.
 */

public class ActressActorsListModel extends Model {
    private ArrayList<GalleryItemModel> galleryItemModels;

    public ArrayList<GalleryItemModel> getGalleryItemModels() {
        return galleryItemModels;
    }

    public void setGalleryItemModels(ArrayList<GalleryItemModel> galleryItemModels) {
        this.galleryItemModels = galleryItemModels;
    }
}
