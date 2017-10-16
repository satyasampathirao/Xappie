package com.xappie.models;

/**
 * Created by Shankar on 10/16/2017.
 */

public class GalleryCategoryModel extends Model {

    private String id;
    private String title;
    private String language;
    private String category;
    private String is_banner;
    private String banner_image;
    private String profile_image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIs_banner() {
        return is_banner;
    }

    public void setIs_banner(String is_banner) {
        this.is_banner = is_banner;
    }

    public String getBanner_image() {
        return banner_image;
    }

    public void setBanner_image(String banner_image) {
        this.banner_image = banner_image;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }
}
