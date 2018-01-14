package com.xappie.models;

import java.io.Serializable;

/**
 * Created by Shankar on 10/31/2017.
 */

public class EventsModel extends Model implements Serializable {

    private String id;
    private String name;
    private String tag;
    private String description;
    private String image;
    private String cost;
    private String dress_code;
    private String type;
    private String start_time;
    private String end_time;
    private String location;
    private String address;
    private String country;
    private String state;
    private String city;
    //private String status;
    private String recordedBy;
    private String recordedDate;
    private String recordedType;
    private String going;
    private String locality;
    private String facebook;
    private String website;
    private String booking_url;

    private String state_name;
    private String country_name;
    private String city_name;
    private String localityname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDress_code() {
        return dress_code;
    }

    public void setDress_code(String dress_code) {
        this.dress_code = dress_code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    /*public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }*/

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

    public String getRecordedType() {
        return recordedType;
    }

    public void setRecordedType(String recordedType) {
        this.recordedType = recordedType;
    }

    public String getGoing() {
        return going;
    }

    public void setGoing(String going) {
        this.going = going;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBooking_url() {
        return booking_url;
    }

    public void setBooking_url(String booking_url) {
        this.booking_url = booking_url;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getLocalityname() {
        return localityname;
    }

    public void setLocalityname(String localityname) {
        this.localityname = localityname;
    }
}
