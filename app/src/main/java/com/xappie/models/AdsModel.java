package com.xappie.models;

/**
 * Created by Shankar on 7/21/2017.
 */

public class AdsModel {

    private int ssds;
    private String id;
    private String company_details;
    private String country;
    private String state;
    private String city;
    private String image;
    private String recordedBy;
    private String recordedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany_details() {
        return company_details;
    }

    public void setCompany_details(String company_details) {
        this.company_details = company_details;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRecordedBy() {
        return recordedBy;
    }

    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }

    public String getRecordedDate() {
        return recordedDate;
    }

    public void setRecordedDate(String recordedDate) {
        this.recordedDate = recordedDate;
    }

    public int getSsds() {
        return ssds;
    }

    public void setSsds(int ssds) {
        this.ssds = ssds;
    }
}
