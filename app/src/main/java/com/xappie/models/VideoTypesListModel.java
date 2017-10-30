package com.xappie.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 10/30/2017.
 */

public class VideoTypesListModel extends Model {
    private ArrayList<VideoTypeModel> videoTypeModels;

    public ArrayList<VideoTypeModel> getVideoTypeModels() {
        return videoTypeModels;
    }

    public void setVideoTypeModels(ArrayList<VideoTypeModel> videoTypeModels) {
        this.videoTypeModels = videoTypeModels;
    }
}
