package com.xappie.models;

/**
 * Created by Ravi on 04-Aug-17.
 */

public class JobsModel
{
    private String url;
    private int id;
    private String title;
    private String positions;
    private String posted_by;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return positions;
    }

    public void setTime(String positions) {
        this.positions = positions;
    }

    public String getPosted_by() {
        return posted_by;
    }

    public void setPosted_by(String posted_by) {
        this.posted_by = posted_by;
    }
}
