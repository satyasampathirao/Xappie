package com.xappie.parser;

import android.content.Context;

import com.xappie.models.JobPostingModel;
import com.xappie.models.Model;

import org.json.JSONObject;

/**
 * Created by Shankar on 11/27/2017.
 */

public class JobPostingParser implements Parser<Model> {
    @Override
    public Model parse(String response, Context context) {
        JobPostingModel mJobPostingModel = new JobPostingModel();
        try {
            JSONObject mJSONObject = new JSONObject(response);
            if (mJSONObject.has("failed")) {
                JSONObject mJSONObjectFailed = mJSONObject.optJSONObject("failed");
                mJobPostingModel.setMessage(mJSONObjectFailed.optString("message"));
                mJobPostingModel.setStatus(true);
            } else if (mJSONObject.has("status")) {
                mJobPostingModel.setMessage(mJSONObject.optString("msg"));
                mJobPostingModel.setStatus(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mJobPostingModel.setStatus(false);
            mJobPostingModel.setMessage("OOPS..! Some problem with the API");
        }

        return mJobPostingModel;
    }
}