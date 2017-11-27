package com.xappie.parser;

import android.content.Context;

import com.xappie.models.Model;
import com.xappie.models.MyAppliedJobStatusUpdatedModel;

import org.json.JSONObject;

/**
 * Created by Shankar on 11/27/2017.
 */

public class MyJobsAppliedStatusParser implements Parser<Model> {
    @Override
    public Model parse(String response, Context context) {
        MyAppliedJobStatusUpdatedModel mMyAppliedJobStatusUpdatedModel = new MyAppliedJobStatusUpdatedModel();
        try {
            JSONObject mJSONObject = new JSONObject(response);

            if (mJSONObject.has("failed")) {
                JSONObject mFailedJSONObject = mJSONObject.optJSONObject("failed");
                mMyAppliedJobStatusUpdatedModel.setMessage(mFailedJSONObject.optString("message"));
                mMyAppliedJobStatusUpdatedModel.setStatus_msg(mFailedJSONObject.optString("status"));
                mMyAppliedJobStatusUpdatedModel.setStatus(false);
            } else {
                mMyAppliedJobStatusUpdatedModel.setMessage(mJSONObject.optString("msg"));
                mMyAppliedJobStatusUpdatedModel.setStatus_msg(mJSONObject.optString("status"));
                mMyAppliedJobStatusUpdatedModel.setStatus(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mMyAppliedJobStatusUpdatedModel.setStatus(false);
            mMyAppliedJobStatusUpdatedModel.setMessage("OOPS..! Some problem with the API");
        }
        return mMyAppliedJobStatusUpdatedModel;
    }
}
