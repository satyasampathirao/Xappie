package com.xappie.models;

/**
 * Created by Shankar on 11/27/2017.
 */

public class SpinnerModel {
    private String title;

    public SpinnerModel(String str) {
        setTitle(str);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
