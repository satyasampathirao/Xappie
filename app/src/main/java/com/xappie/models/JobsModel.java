package com.xappie.models;

import java.io.Serializable;

/**
 * Created by Ravi on 04-Aug-17.
 */

public class JobsModel extends Model implements Serializable {
    private String url;
    private int id;
    private String title;
    private String positions;
    private String positions_nbr;
    private String posted_by;
    private String country;
    private String state;
    private String city;
    private String category;
    private String company_logo;
    private String recordedBy;
    private String recordedDate;
    private String jobs_status;
    private String role;
    private String company;
    private String description;
    private String isResume;

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

    public String getPositions() {
        return positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    public String getPositions_nbr() {
        return positions_nbr;
    }

    public void setPositions_nbr(String positions_nbr) {
        this.positions_nbr = positions_nbr;
    }

    public String getPosted_by() {
        return posted_by;
    }

    public void setPosted_by(String posted_by) {
        this.posted_by = posted_by;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCompany_logo() {
        return company_logo;
    }

    public void setCompany_logo(String company_logo) {
        this.company_logo = company_logo;
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

    public String getJobs_status() {
        return jobs_status;
    }

    public void setJobs_status(String jobs_status) {
        this.jobs_status = jobs_status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsResume() {
        return isResume;
    }

    public void setIsResume(String isResume) {
        this.isResume = isResume;
    }
}
