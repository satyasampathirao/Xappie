package com.xappie.parser;

import android.content.Context;

import com.google.gson.Gson;
import com.xappie.models.JobsModel;
import com.xappie.models.Model;

/**
 * Created by Santosh on 22-11-2017.
 */

public class JobsDetailParser implements Parser<Model> {
    @Override
    public Model parse(String json, Context context) {

        JobsModel jobsModel = new Gson(). fromJson(json.toString(),JobsModel.class);
        return jobsModel;
    }
}
