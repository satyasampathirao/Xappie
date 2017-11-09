package com.xappie.models;

/**
 * Created by Shankar on 11/9/2017.
 */

public class EventUpdateModel extends Model {

    private String status;
    private String msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
