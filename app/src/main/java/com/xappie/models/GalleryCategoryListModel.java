package com.xappie.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 10/16/2017.
 */

public class GalleryCategoryListModel extends Model {

    private ArrayList<GalleryCategoryModel> galleryCategoryModels;

    public ArrayList<GalleryCategoryModel> getGalleryCategoryModels() {
        return galleryCategoryModels;
    }

    public void setGalleryCategoryModels(ArrayList<GalleryCategoryModel> galleryCategoryModels) {
        this.galleryCategoryModels = galleryCategoryModels;
    }
}
