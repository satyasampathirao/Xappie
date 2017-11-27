package com.xappie.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 11/27/2017.
 */

public class CategoryModel extends Model {
    private String id;
    private String name;
    private ArrayList<SpinnerModel> mCategorySpinnerModels;
    private ArrayList<CategoryModel> categoryModels;

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

    public ArrayList<SpinnerModel> getmCategorySpinnerModels() {
        return mCategorySpinnerModels;
    }

    public void setmCategorySpinnerModels(ArrayList<SpinnerModel> mCategorySpinnerModels) {
        this.mCategorySpinnerModels = mCategorySpinnerModels;
    }

    public ArrayList<CategoryModel> getCategoryModels() {
        return categoryModels;
    }

    public void setCategoryModels(ArrayList<CategoryModel> categoryModels) {
        this.categoryModels = categoryModels;
    }
}
