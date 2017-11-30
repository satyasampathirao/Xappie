package com.xappie.models;

/**
 * Created by Santosh on 29-11-2017.
 */

public class NotificationModel {
    private String category;
    private String title;
    private String time;
    private String source;
    private String image_url;
    private int id;
    private String isopened;



    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsopened() {
        return isopened;
    }

    public void setIsopened(String isopened) {
        this.isopened = isopened;
    }
}
