package com.xappie.parser;

import android.content.Context;

import com.xappie.models.GalleryModel;
import com.xappie.models.GallerySubModel;
import com.xappie.models.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 10/06/2017.
 */

public class GalleryParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        GalleryModel mGalleryModel = new GalleryModel();
        try {
            JSONObject jsonObject = new JSONObject(s);

            if (jsonObject.has("Actors")) {
                JSONArray actorsJsonArray = jsonObject.optJSONArray("Actors");
                ArrayList<GallerySubModel> actorsGalleryModels = new ArrayList<>();
                for (int i = 0; i < actorsJsonArray.length(); i++) {
                    JSONObject actorsJsonObject = actorsJsonArray.optJSONObject(i);
                    GallerySubModel galleryItemModel = new GallerySubModel();
                    galleryItemModel.setId(actorsJsonObject.optString("id"));
                    galleryItemModel.setName(actorsJsonObject.optString("name"));
                    galleryItemModel.setType(actorsJsonObject.optString("type"));
                    galleryItemModel.setImage(actorsJsonObject.optString("image"));
                    actorsGalleryModels.add(galleryItemModel);
                }
                mGalleryModel.setActorsGalleryList(actorsGalleryModels);
            }

            if (jsonObject.has("Actress")) {
                JSONArray actressJsonArray = jsonObject.optJSONArray("Actress");
                ArrayList<GallerySubModel> actressGalleryModels = new ArrayList<>();
                for (int i = 0; i < actressJsonArray.length(); i++) {
                    JSONObject actressJsonObject = actressJsonArray.optJSONObject(i);
                    GallerySubModel galleryItemModel = new GallerySubModel();
                    galleryItemModel.setId(actressJsonObject.optString("id"));
                    galleryItemModel.setName(actressJsonObject.optString("name"));
                    galleryItemModel.setType(actressJsonObject.optString("type"));
                    galleryItemModel.setImage(actressJsonObject.optString("image"));
                    actressGalleryModels.add(galleryItemModel);
                }
                mGalleryModel.setActressGalleryList(actressGalleryModels);
            }

            if (jsonObject.has("Movie")) {
                JSONArray movieJsonArray = jsonObject.optJSONArray("Movie");
                ArrayList<GallerySubModel> moviesGalleryModels = new ArrayList<>();
                for (int i = 0; i < movieJsonArray.length(); i++) {
                    JSONObject moviesJsonObject = movieJsonArray.optJSONObject(i);
                    GallerySubModel galleryItemModel = new GallerySubModel();
                    galleryItemModel.setId(moviesJsonObject.optString("id"));
                    galleryItemModel.setName(moviesJsonObject.optString("name"));
                    galleryItemModel.setType(moviesJsonObject.optString("type"));
                    galleryItemModel.setImage(moviesJsonObject.optString("image"));
                    moviesGalleryModels.add(galleryItemModel);
                }
                mGalleryModel.setMoviesGalleryList(moviesGalleryModels);
            }

            if (jsonObject.has("Events")) {
                JSONArray eventsJsonArray = jsonObject.optJSONArray("Events");
                ArrayList<GallerySubModel> eventsGalleryModels = new ArrayList<>();
                for (int i = 0; i < eventsJsonArray.length(); i++) {
                    JSONObject eventsJsonObject = eventsJsonArray.optJSONObject(i);
                    GallerySubModel galleryItemModel = new GallerySubModel();
                    galleryItemModel.setId(eventsJsonObject.optString("id"));
                    galleryItemModel.setName(eventsJsonObject.optString("name"));
                    galleryItemModel.setType(eventsJsonObject.optString("type"));
                    galleryItemModel.setImage(eventsJsonObject.optString("image"));
                    eventsGalleryModels.add(galleryItemModel);
                }
                mGalleryModel.setEventsGalleryList(eventsGalleryModels);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mGalleryModel;
    }

}