package com.xappie.parser;

import android.content.Context;

import com.xappie.models.ImageUploadModel;
import com.xappie.models.Model;

import org.json.JSONObject;

/**
 * Created by Shankar on 8/24/2017.
 */

public class ImageUpdateParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        ImageUploadModel mImageUploadModel = new ImageUploadModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("success")) {
                mImageUploadModel.setStatus(true);
                JSONObject successjsonObject = jsonObject.optJSONObject("success");
                mImageUploadModel.setPhoto(successjsonObject.optString("photo"));
                mImageUploadModel.setMessage(successjsonObject.optString("message"));
            } else if (jsonObject.has("failed")) {
                JSONObject jsonObject1 = jsonObject.optJSONObject("failed");
                mImageUploadModel.setStatus(false);
                mImageUploadModel.setMessage(jsonObject1.optString("message"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mImageUploadModel;
    }
}