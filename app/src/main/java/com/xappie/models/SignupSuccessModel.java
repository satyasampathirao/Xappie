package com.xappie.models;

/**
 * Created by Shankar on 7/21/2017.
 */

public class SignupSuccessModel extends Model{
    private String message;
    private String uuid;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
