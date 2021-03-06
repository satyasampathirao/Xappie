package com.xappie.models;

import java.io.Serializable;

/**
 * Created by Shankar on 8/24/2017.
 */

public class LanguageModel implements Serializable {
    private String id;
    private String name;
    private String name_native;
    private boolean mSelected;

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

    public String getName_native() {
        return name_native;
    }

    public void setName_native(String name_native) {
        this.name_native = name_native;
    }

    public boolean ismSelected() {
        return mSelected;
    }

    public void setmSelected(boolean mSelected) {
        this.mSelected = mSelected;
    }
}
