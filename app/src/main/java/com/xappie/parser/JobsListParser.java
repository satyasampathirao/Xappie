package com.xappie.parser;

import android.content.Context;

import com.google.gson.Gson;
import com.xappie.models.JobsListModel;
import com.xappie.models.JobsModel;
import com.xappie.models.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Santosh on 21-11-2017.
 */

public class JobsListParser implements Parser<Model> {
    @Override
    public Model parse(String json, Context context) {
        JobsListModel jobsListModel = new JobsListModel();
        try {
            JSONArray jsonArray = new JSONArray(json);
            ArrayList<JobsModel> jobsModelArrayList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                JobsModel jobsModel = new Gson().fromJson(jsonObject.toString(), JobsModel.class);
                jobsModelArrayList.add(jobsModel);
            }
            jobsListModel.setJobsModels(jobsModelArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobsListModel;
    }

}
