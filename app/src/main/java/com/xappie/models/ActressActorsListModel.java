package com.xappie.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 7/21/2017.
 */

public class ActressActorsListModel extends Model {
    private ArrayList<GallerySubItemModel> gallerySubModels;

    public ArrayList<GallerySubItemModel> getGallerySubModels() {
        return gallerySubModels;
    }

    public void setGallerySubModels(ArrayList<GallerySubItemModel> gallerySubModels) {
        this.gallerySubModels = gallerySubModels;
    }
}
