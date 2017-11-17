package com.xappie.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Santosh on 17-11-2017.
 */

public class ClassifiedsListModel extends Model implements Serializable {
    private ArrayList<ClassifiedsModel> classifiedsModels;

    public ArrayList<ClassifiedsModel> getClassifiedsModels() {
        return classifiedsModels;
    }

    public void setClassifiedsModels(ArrayList<ClassifiedsModel> classifiedsModels) {
        this.classifiedsModels = classifiedsModels;
    }
}
