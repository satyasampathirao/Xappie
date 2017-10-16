package com.xappie.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 10/16/2017.
 */

public class GalleryImageViewListModel extends Model {

    private ArrayList<GalleryImageViewModel> galleryImageViewModels;

    public ArrayList<GalleryImageViewModel> getGalleryImageViewModels() {
        return galleryImageViewModels;
    }

    public void setGalleryImageViewModels(ArrayList<GalleryImageViewModel> galleryImageViewModels) {
        this.galleryImageViewModels = galleryImageViewModels;
    }
}
