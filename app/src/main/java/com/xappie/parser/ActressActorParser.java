package com.xappie.parser;

import android.content.Context;

import com.xappie.models.ActressActorsListModel;
import com.xappie.models.GalleryItemModel;
import com.xappie.models.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 10/06/2017.
 */

public class ActressActorParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        ActressActorsListModel mGalleryModel = new ActressActorsListModel();
        try {
            JSONObject jsonObject = new JSONObject(s);

            if (jsonObject.has("Actors")) {
                JSONArray actorsJsonArray = jsonObject.optJSONArray("Actors");
                ArrayList<GalleryItemModel> actorsGalleryModels = new ArrayList<>();
                for (int i = 0; i < actorsJsonArray.length(); i++) {
                    JSONObject actorsJsonObject = actorsJsonArray.optJSONObject(i);
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
                    galleryItemModel.setCategory_id(actorsJsonObject.optString("category_id"));
                    galleryItemModel.setCategory_name(actorsJsonObject.optString("category_name"));
                    galleryItemModel.setRecordedDate(actorsJsonObject.optString("recordedDate"));
                    galleryItemModel.setProfile_image_resize(actorsJsonObject.optString("profile_image_resize"));
                    galleryItemModel.setBanner_image_resize(actorsJsonObject.optString("banner_image_resize"));
                    actorsGalleryModels.add(galleryItemModel);
                }
                mGalleryModel.setGalleryItemModels(actorsGalleryModels);
                if (actorsGalleryModels.size() > 0) {
                    return mGalleryModel;
                }
            }

            if (jsonObject.has("Actress")) {
                JSONArray actressJsonArray = jsonObject.optJSONArray("Actress");
                ArrayList<GalleryItemModel> actressGalleryModels = new ArrayList<>();
                for (int i = 0; i < actressJsonArray.length(); i++) {
                    JSONObject actressJsonObject = actressJsonArray.optJSONObject(i);
                    GalleryItemModel galleryItemModel = new GalleryItemModel();
                    galleryItemModel.setId(actressJsonObject.optString("id"));
                    galleryItemModel.setTitle(actressJsonObject.optString("title"));
                    galleryItemModel.setCategory(actressJsonObject.optString("category"));
                    galleryItemModel.setLanguage(actressJsonObject.optString("language"));
                    galleryItemModel.setProfile_image(actressJsonObject.optString("profile_image"));
                    galleryItemModel.setBanner_image(actressJsonObject.optString("banner_image"));
                    galleryItemModel.setStatus(actressJsonObject.optString("status"));
                    galleryItemModel.setGallery_status(actressJsonObject.optString("gallery_status"));
                    galleryItemModel.setRecordedBy(actressJsonObject.optString("recordedBy"));
                    galleryItemModel.setCategory_id(actressJsonObject.optString("category_id"));
                    galleryItemModel.setCategory_name(actressJsonObject.optString("category_name"));
                    galleryItemModel.setRecordedDate(actressJsonObject.optString("recordedDate"));
                    galleryItemModel.setProfile_image_resize(actressJsonObject.optString("profile_image_resize"));
                    galleryItemModel.setBanner_image_resize(actressJsonObject.optString("banner_image_resize"));
                    actressGalleryModels.add(galleryItemModel);
                }
                mGalleryModel.setGalleryItemModels(actressGalleryModels);
                if (actressGalleryModels.size() > 0) {
                    return mGalleryModel;
                }
            }

            if (jsonObject.has("Movie")) {
                JSONArray movieJsonArray = jsonObject.optJSONArray("Movie");
                ArrayList<GalleryItemModel> moviesGalleryModels = new ArrayList<>();
                for (int i = 0; i < movieJsonArray.length(); i++) {
                    JSONObject moviesJsonObject = movieJsonArray.optJSONObject(i);
                    GalleryItemModel galleryItemModel = new GalleryItemModel();
                    galleryItemModel.setId(moviesJsonObject.optString("id"));
                    galleryItemModel.setTitle(moviesJsonObject.optString("title"));
                    galleryItemModel.setCategory(moviesJsonObject.optString("category"));
                    galleryItemModel.setLanguage(moviesJsonObject.optString("language"));
                    galleryItemModel.setProfile_image(moviesJsonObject.optString("profile_image"));
                    galleryItemModel.setCategory_id(moviesJsonObject.optString("category_id"));
                    galleryItemModel.setBanner_image(moviesJsonObject.optString("banner_image"));
                    galleryItemModel.setStatus(moviesJsonObject.optString("status"));
                    galleryItemModel.setGallery_status(moviesJsonObject.optString("gallery_status"));
                    galleryItemModel.setRecordedBy(moviesJsonObject.optString("recordedBy"));
                    galleryItemModel.setCategory_name(moviesJsonObject.optString("category_name"));
                    galleryItemModel.setRecordedDate(moviesJsonObject.optString("recordedDate"));
                    galleryItemModel.setProfile_image_resize(moviesJsonObject.optString("profile_image_resize"));
                    galleryItemModel.setBanner_image_resize(moviesJsonObject.optString("banner_image_resize"));
                    moviesGalleryModels.add(galleryItemModel);
                }
                mGalleryModel.setGalleryItemModels(moviesGalleryModels);
                if (moviesGalleryModels.size() > 0) {
                    return mGalleryModel;
                }
            }

            if (jsonObject.has("Events")) {
                JSONArray eventsJsonArray = jsonObject.optJSONArray("Events");
                ArrayList<GalleryItemModel> eventsGalleryModels = new ArrayList<>();
                for (int i = 0; i < eventsJsonArray.length(); i++) {
                    JSONObject eventsJsonObject = eventsJsonArray.optJSONObject(i);
                    GalleryItemModel galleryItemModel = new GalleryItemModel();
                    galleryItemModel.setId(eventsJsonObject.optString("id"));
                    galleryItemModel.setTitle(eventsJsonObject.optString("title"));
                    galleryItemModel.setCategory(eventsJsonObject.optString("category"));
                    galleryItemModel.setLanguage(eventsJsonObject.optString("language"));
                    galleryItemModel.setProfile_image(eventsJsonObject.optString("profile_image"));
                    galleryItemModel.setBanner_image(eventsJsonObject.optString("banner_image"));
                    galleryItemModel.setStatus(eventsJsonObject.optString("status"));
                    galleryItemModel.setGallery_status(eventsJsonObject.optString("gallery_status"));
                    galleryItemModel.setRecordedBy(eventsJsonObject.optString("recordedBy"));
                    galleryItemModel.setCategory_name(eventsJsonObject.optString("category_name"));
                    galleryItemModel.setCategory_id(eventsJsonObject.optString("category_id"));
                    galleryItemModel.setRecordedDate(eventsJsonObject.optString("recordedDate"));
                    galleryItemModel.setProfile_image_resize(eventsJsonObject.optString("profile_image_resize"));
                    galleryItemModel.setBanner_image_resize(eventsJsonObject.optString("banner_image_resize"));
                    eventsGalleryModels.add(galleryItemModel);
                }
                mGalleryModel.setGalleryItemModels(eventsGalleryModels);
                if (eventsGalleryModels.size() > 0) {
                    return mGalleryModel;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mGalleryModel;
    }

}