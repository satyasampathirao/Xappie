package com.xappie.models;

import java.util.AbstractList;

public class MyAppliedJobsModel extends Model {
    private String id;
    private String title;
    private String category;
    private String country;
    private String state;
    private String city;
    private String company;
    private String positions;
    private String company_logo;
    private String status;
    private String recordedBy;
    private String recordedDate;
    private boolean mHiredLayout;
    private AbstractList<MyAppliedJobsModel> myAppliedJobsModels;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPositions() {
        return positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    public String getCompany_logo() {
        return company_logo;
    }

    public void setCompany_logo(String company_logo) {
        this.company_logo = company_logo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public boolean ismHiredLayout() {
        return mHiredLayout;
    }

    public void setmHiredLayout(boolean mHiredLayout) {
        this.mHiredLayout = mHiredLayout;
    }

    public AbstractList<MyAppliedJobsModel> getMyAppliedJobsModels() {
        return myAppliedJobsModels;
    }

    public void setMyAppliedJobsModels(AbstractList<MyAppliedJobsModel> myAppliedJobsModels) {
        this.myAppliedJobsModels = myAppliedJobsModels;
    }
}
