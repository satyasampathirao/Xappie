package com.xappie.parser;

import android.content.Context;

import com.xappie.models.EntertainmentModel;
import com.xappie.models.GalleryItemModel;
import com.xappie.models.HomePageContentModel;
import com.xappie.models.Model;
import com.xappie.models.VideosModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 8/24/2017.
 */

public class HomePageContentParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        HomePageContentModel homePageContentModel = new HomePageContentModel();
        try {
            JSONObject jsonObject = new JSONObject(s);

            if (jsonObject.has("videos")) {
                JSONArray videosJsonArray = jsonObject.optJSONArray("videos");
                ArrayList<VideosModel> videosModels = new ArrayList<>();
                for (int i = 0; i < videosJsonArray.length(); i++) {
                    JSONObject videosJsonObject = videosJsonArray.optJSONObject(i);
                    VideosModel videosModel = new VideosModel();
                    videosModel.setId(videosJsonObject.optString("id"));
                    videosModel.setTitle(videosJsonObject.optString("title"));
                    videosModel.setLanguage(videosJsonObject.optString("language"));
                    videosModel.setThumb_nail(videosJsonObject.optString("thumb_nail"));
                    videosModel.setStatus(videosJsonObject.optString("status"));
                    videosModel.setUrl(videosJsonObject.optString("url"));
                    videosModels.add(videosModel);
                }
                homePageContentModel.setVideosModels(videosModels);
            }

            if (jsonObject.has("stories")) {
                JSONArray storiesJsonArray = jsonObject.optJSONArray("stories");
                ArrayList<EntertainmentModel> storiesModels = new ArrayList<>();
                for (int i = 0; i < storiesJsonArray.length(); i++) {
                    JSONObject storiesJsonObject = storiesJsonArray.optJSONObject(i);
                    EntertainmentModel entertainmentModel = new EntertainmentModel();
                    entertainmentModel.setId(storiesJsonObject.optString("id"));
                    entertainmentModel.setTitle(storiesJsonObject.optString("title"));
                    entertainmentModel.setType(storiesJsonObject.optString("type"));
                    entertainmentModel.setSource(storiesJsonObject.optString("source"));
                    entertainmentModel.setLanguage(storiesJsonObject.optString("language"));
                    entertainmentModel.setProfile_image(storiesJsonObject.optString("profile_image"));
                    entertainmentModel.setStatus(storiesJsonObject.optString("status"));
                    entertainmentModel.setNews_status(storiesJsonObject.optString("news_status"));
                    entertainmentModel.setRecordedBy(storiesJsonObject.optString("recordedBy"));
                    entertainmentModel.setRecordedDate(storiesJsonObject.optString("recordedDate"));
                    storiesModels.add(entertainmentModel);
                }
                homePageContentModel.setTopStoriesModels(storiesModels);
            }

            if (jsonObject.has("entertainments")) {
                JSONArray entertainmentsJsonArray = jsonObject.optJSONArray("entertainments");
                ArrayList<EntertainmentModel> entertainmentsModels = new ArrayList<>();
                for (int i = 0; i < entertainmentsJsonArray.length(); i++) {
                    JSONObject entertainmentsJsonObject = entertainmentsJsonArray.optJSONObject(i);
                    EntertainmentModel entertainmentModel = new EntertainmentModel();
                    entertainmentModel.setId(entertainmentsJsonObject.optString("id"));
                    entertainmentModel.setTitle(entertainmentsJsonObject.optString("title"));
                    entertainmentModel.setType(entertainmentsJsonObject.optString("type"));
                    entertainmentModel.setSource(entertainmentsJsonObject.optString("source"));
                    entertainmentModel.setLanguage(entertainmentsJsonObject.optString("language"));
                    entertainmentModel.setProfile_image(entertainmentsJsonObject.optString("profile_image"));
                    entertainmentModel.setStatus(entertainmentsJsonObject.optString("status"));
                    entertainmentModel.setNews_status(entertainmentsJsonObject.optString("news_status"));
                    entertainmentModel.setRecordedBy(entertainmentsJsonObject.optString("recordedBy"));
                    entertainmentModel.setRecordedDate(entertainmentsJsonObject.optString("recordedDate"));
                    entertainmentsModels.add(entertainmentModel);
                }
                homePageContentModel.setEntertainmentModels(entertainmentsModels);
            }
            if (jsonObject.has("galleries")) {
                JSONArray galleriesJsonArray = jsonObject.optJSONArray("galleries");
                ArrayList<GalleryItemModel> galleryItemModels = new ArrayList<>();
                for (int i = 0; i < galleriesJsonArray.length(); i++) {
                    JSONObject galleriesJsonObject = galleriesJsonArray.optJSONObject(i);
                    GalleryItemModel galleryItemModel = new GalleryItemModel();
                    galleryItemModel.setId(galleriesJsonObject.optString("id"));
                    galleryItemModel.setTitle(galleriesJsonObject.optString("title"));
                    galleryItemModel.setCategory(galleriesJsonObject.optString("category"));
                    galleryItemModel.setLanguage(galleriesJsonObject.optString("language"));
                    galleryItemModel.setProfile_image(galleriesJsonObject.optString("profile_image"));
                    galleryItemModel.setBanner_image(galleriesJsonObject.optString("banner_image"));
                    galleryItemModel.setGallery_status(galleriesJsonObject.optString("gallery_status"));
                    galleryItemModel.setRecordedBy(galleriesJsonObject.optString("recordedBy"));
                    galleryItemModel.setRecordedDate(galleriesJsonObject.optString("recordedDate"));
                    galleryItemModels.add(galleryItemModel);
                }
                homePageContentModel.setGalleryItemModels(galleryItemModels);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return homePageContentModel;
    }

}