package com.xappie.parser;

import android.content.Context;

import com.xappie.models.Model;
import com.xappie.models.MyAppliedJobsModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 11/27/2017.
 */

public class MyAppliedJobsParser implements Parser<Model> {
    @Override
    public Model parse(String response, Context context) {
        MyAppliedJobsModel mMyAppliedJobsModel = new MyAppliedJobsModel();
        try {
            JSONArray mJSONArray = new JSONArray(response);
            ArrayList<MyAppliedJobsModel> myAppliedJobsModels = new ArrayList<>();
            for (int i = 0; i < mJSONArray.length(); i++) {
                JSONObject jsonObject = mJSONArray.optJSONObject(i);
                MyAppliedJobsModel mMyAppliedJobsModelItem = new MyAppliedJobsModel();
                mMyAppliedJobsModelItem.setCompany(jsonObject.optString("company"));
                mMyAppliedJobsModelItem.setId(jsonObject.optString("id"));
                mMyAppliedJobsModelItem.setTitle(jsonObject.optString("title"));
                mMyAppliedJobsModelItem.setCategory(jsonObject.optString("category"));
                mMyAppliedJobsModelItem.setCountry(jsonObject.optString("country"));
                mMyAppliedJobsModelItem.setState(jsonObject.optString("state"));
                mMyAppliedJobsModelItem.setCity(jsonObject.optString("city"));
                mMyAppliedJobsModelItem.setCompany(jsonObject.optString("company"));
                mMyAppliedJobsModelItem.setPositions(jsonObject.optString("positions"));
                mMyAppliedJobsModelItem.setCompany_logo(jsonObject.optString("company_logo"));
                mMyAppliedJobsModelItem.setStatus(jsonObject.optString("status"));
                mMyAppliedJobsModelItem.setRecordedBy(jsonObject.optString("recordedBy"));
                mMyAppliedJobsModelItem.setRecordedDate(jsonObject.optString("recordedDate"));
                myAppliedJobsModels.add(mMyAppliedJobsModelItem);
            }
            mMyAppliedJobsModel.setMyAppliedJobsModels(myAppliedJobsModels);
            mMyAppliedJobsModel.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            mMyAppliedJobsModel.setStatus(false);
            mMyAppliedJobsModel.setMessage("OOPS..! Some problem with the API");
        }

        return mMyAppliedJobsModel;
    }
}
