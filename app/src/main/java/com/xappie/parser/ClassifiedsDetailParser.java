package com.xappie.parser;

import android.content.Context;

import com.google.gson.Gson;
import com.xappie.models.ClassifiedsModel;
import com.xappie.models.Model;

/**
 * Created by Santosh on 20-11-2017.
 * Edited by Shankar on  23-11-2017.
 */

public class ClassifiedsDetailParser implements Parser<Model> {

    @Override
    public Model parse(String json, Context context) {
        ClassifiedsModel classifiedsModel = new Gson().fromJson(json.toString(), ClassifiedsModel.class);
        return classifiedsModel;
    }
}
