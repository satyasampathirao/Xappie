package com.xappie.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 7/21/2017.
 */

public class ActressActorsListModel extends Model {
    private ArrayList<GallerySubModel> gallerySubModels;

    public ArrayList<GallerySubModel> getGallerySubModels() {
        return gallerySubModels;
    }

    public void setGallerySubModels(ArrayList<GallerySubModel> gallerySubModels) {
        this.gallerySubModels = gallerySubModels;
    }
}
