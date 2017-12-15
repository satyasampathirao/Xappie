package com.xappie.parser;

import android.content.Context;

import com.xappie.models.CategoryModel;
import com.xappie.models.Model;
import com.xappie.models.SpinnerModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 11/27/2017.
 */

public class CategoryParser implements Parser<Model> {

    @Override
    public Model parse(String json, Context context) {
        CategoryModel mCategoryModel = new CategoryModel();
        try {
            JSONArray mJSONArray = new JSONArray(json);
            ArrayList<CategoryModel> categoryModels = new ArrayList<>();
            ArrayList<SpinnerModel> categorysSpinnerModels = new ArrayList<>();
            for (int i = 0; i < mJSONArray.length(); i++) {
                JSONObject jsonObject = mJSONArray.optJSONObject(i);
                CategoryModel categoryModelItem = new CategoryModel();
                SpinnerModel spinnerModel = new SpinnerModel();
                categoryModelItem.setName(jsonObject.optString("name"));
                categoryModelItem.setId(jsonObject.optString("id"));
                categoryModels.add(categoryModelItem);
                spinnerModel.setTitle(jsonObject.optString("name"));
                categorysSpinnerModels.add(spinnerModel);
            }
            mCategoryModel.setCategoryModels(categoryModels);
            mCategoryModel.setmCategorySpinnerModels(categorysSpinnerModels);
            mCategoryModel.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            mCategoryModel.setStatus(false);
            mCategoryModel.setMessage("OOPS..! Some problem with the API");
        }

        return mCategoryModel;
    }
}