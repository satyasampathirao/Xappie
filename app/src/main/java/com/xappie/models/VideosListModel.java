package com.xappie.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 8/24/2017.
 */

public class VideosListModel extends Model {
    private ArrayList<VideosModel> videosModels;

    public ArrayList<VideosModel> getVideosModels() {
        return videosModels;
    }

    public void setVideosModels(ArrayList<VideosModel> videosModels) {
        this.videosModels = videosModels;
    }
}
