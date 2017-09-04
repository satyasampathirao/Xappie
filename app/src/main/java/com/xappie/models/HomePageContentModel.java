package com.xappie.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 9/4/2017.
 */

public class HomePageContentModel extends Model {
    private ArrayList<VideosModel> videosModels;
    private ArrayList<EntertainmentModel> entertainmentModels;
    private ArrayList<EntertainmentModel> topStoriesModels;

    public ArrayList<VideosModel> getVideosModels() {
        return videosModels;
    }

    public void setVideosModels(ArrayList<VideosModel> videosModels) {
        this.videosModels = videosModels;
    }

    public ArrayList<EntertainmentModel> getEntertainmentModels() {
        return entertainmentModels;
    }

    public void setEntertainmentModels(ArrayList<EntertainmentModel> entertainmentModels) {
        this.entertainmentModels = entertainmentModels;
    }

    public ArrayList<EntertainmentModel> getTopStoriesModels() {
        return topStoriesModels;
    }

    public void setTopStoriesModels(ArrayList<EntertainmentModel> topStoriesModels) {
        this.topStoriesModels = topStoriesModels;
    }
}
