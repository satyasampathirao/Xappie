package com.xappie.parser;

import android.content.Context;


import com.xappie.models.ApplyJobsModel;
import com.xappie.models.Model;

import org.json.JSONObject;

/**
 * Created by Shankar on 1/15/2017.
 */
public class ApplyJobsParser implements Parser<Model> {

    @Override
    public Model parse(String json, Context context) {
        ApplyJobsModel mApplyJobsModel = new ApplyJobsModel();
        try {
            JSONObject mJSONObject = new JSONObject(json);

            if (mJSONObject.has("failed")) {
                JSONObject mFailedJSONObject = mJSONObject.optJSONObject("failed");
                mApplyJobsModel.setMessage(mFailedJSONObject.optString("message"));
                //mApplyJobsModel.setStatus_msg(mFailedJSONObject.optString("status"));
                mApplyJobsModel.setStatus(false);
            } else {
                mApplyJobsModel.setMessage(mJSONObject.optString("msg"));
                //mApplyJobsModel.setStatus_msg(mJSONObject.optString("status"));
                mApplyJobsModel.setStatus(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mApplyJobsModel.setStatus(false);
            mApplyJobsModel.setMessage("OOPS..! Some problem with the API");
        }
        return mApplyJobsModel;
    }
}
