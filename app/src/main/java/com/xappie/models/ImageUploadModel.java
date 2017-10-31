package com.xappie.models;

/**
 * Created by Shankar on 10/31/2017.
 */

public class ImageUploadModel extends Model {

    private String photo;
    private String message;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
