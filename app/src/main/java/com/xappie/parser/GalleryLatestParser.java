package com.xappie.parser;

import android.content.Context;

import com.xappie.models.GalleryItemModel;
import com.xappie.models.GalleryLatestModel;
import com.xappie.models.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 10/06/2017.
 */

public class GalleryLatestParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        GalleryLatestModel mGalleryLatestModel = new GalleryLatestModel();
        try {
            JSONArray jsonArray = new JSONArray(s);
            ArrayList<GalleryItemModel> actorsGalleryModels = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject actorsJsonObject = jsonArray.optJSONObject(i);
                GalleryItemModel galleryItemModel = new GalleryItemModel();
                galleryItemModel.setId(actorsJsonObject.optString("id"));
                galleryItemModel.setTitle(actorsJsonObject.optString("title"));
                galleryItemModel.setCategory(actorsJsonObject.optString("category"));
                galleryItemModel.setLanguage(actorsJsonObject.optString("language"));
                galleryItemModel.setProfile_image(actorsJsonObject.optString("profile_image"));
                galleryItemModel.setBanner_image(actorsJsonObject.optString("banner_image"));
                galleryItemModel.setStatus(actorsJsonObject.optString("status"));
                galleryItemModel.setGallery_status(actorsJsonObject.optString("gallery_status"));
                galleryItemModel.setRecordedBy(actorsJsonObject.optString("recordedBy"));
                galleryItemModel.setRecordedDate(actorsJsonObject.optString("recordedDate"));
                galleryItemModel.setProfile_image_resize(actorsJsonObject.optString("profile_image_resize"));
                galleryItemModel.setBanner_image_resize(actorsJsonObject.optString("banner_image_resize"));
                actorsGalleryModels.add(galleryItemModel);
            }
            mGalleryLatestModel.setLatestGalleryList(actorsGalleryModels);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mGalleryLatestModel;
    }
}