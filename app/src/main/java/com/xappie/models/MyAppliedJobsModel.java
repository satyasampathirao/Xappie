package com.xappie.models;





public class MyAppliedJobsModel  {
    private int id;
    private String title;
    private String position;
    private String role;
    private String company;
    private String location;
    private String category;
    private String isHired;
    private static boolean mHiredLayout;


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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIsHired() {
        return isHired;
    }

    public void setIsHired(String isHired) {
        this.isHired = isHired;
    }
    public static boolean ismHiredLayout() {
        return mHiredLayout;
    }

    public void setmHiredLayout(boolean mHiredLayout) {
        this.mHiredLayout = mHiredLayout;
    }


}
