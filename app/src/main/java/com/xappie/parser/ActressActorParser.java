package com.xappie.parser;

import android.content.Context;

import com.xappie.models.ActressActorsListModel;
import com.xappie.models.GallerySubModel;
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
                ArrayList<GallerySubModel> actorsGalleryModels = new ArrayList<>();
                for (int i = 0; i < actorsJsonArray.length(); i++) {
                    JSONObject actorsJsonObject = actorsJsonArray.optJSONObject(i);
                    GallerySubModel gallerySubModel = new GallerySubModel();
                    gallerySubModel.setId(actorsJsonObject.optString("id"));
                    gallerySubModel.setName(actorsJsonObject.optString("name"));
                    gallerySubModel.setType(actorsJsonObject.optString("type"));
                    gallerySubModel.setImage(actorsJsonObject.optString("image"));
                    actorsGalleryModels.add(gallerySubModel);
                }
                mGalleryModel.setGallerySubModels(actorsGalleryModels);
                if (actorsGalleryModels.size() > 0) {
                    return mGalleryModel;
                }
            }

            if (jsonObject.has("Actress")) {
                JSONArray actressJsonArray = jsonObject.optJSONArray("Actress");
                ArrayList<GallerySubModel> actressGalleryModels = new ArrayList<>();
                for (int i = 0; i < actressJsonArray.length(); i++) {
                    JSONObject actressJsonObject = actressJsonArray.optJSONObject(i);
                    GallerySubModel gallerySubModel = new GallerySubModel();
                    gallerySubModel.setId(actressJsonObject.optString("id"));
                    gallerySubModel.setName(actressJsonObject.optString("name"));
                    gallerySubModel.setType(actressJsonObject.optString("type"));
                    gallerySubModel.setImage(actressJsonObject.optString("image"));
                    actressGalleryModels.add(gallerySubModel);
                }
                mGalleryModel.setGallerySubModels(actressGalleryModels);
                if (actressGalleryModels.size() > 0) {
                    return mGalleryModel;
                }
            }

            if (jsonObject.has("Movie")) {
                JSONArray movieJsonArray = jsonObject.optJSONArray("Movie");
                ArrayList<GallerySubModel> moviesGalleryModels = new ArrayList<>();
                for (int i = 0; i < movieJsonArray.length(); i++) {
                    JSONObject moviesJsonObject = movieJsonArray.optJSONObject(i);
                    GallerySubModel gallerySubModel = new GallerySubModel();
                    gallerySubModel.setId(moviesJsonObject.optString("id"));
                    gallerySubModel.setName(moviesJsonObject.optString("name"));
                    gallerySubModel.setType(moviesJsonObject.optString("type"));
                    gallerySubModel.setImage(moviesJsonObject.optString("image"));
                    moviesGalleryModels.add(gallerySubModel);
                }
                mGalleryModel.setGallerySubModels(moviesGalleryModels);
                if (moviesGalleryModels.size() > 0) {
                    return mGalleryModel;
                }
            }

            if (jsonObject.has("Events")) {
                JSONArray eventsJsonArray = jsonObject.optJSONArray("Events");
                ArrayList<GallerySubModel> eventsGalleryModels = new ArrayList<>();
                for (int i = 0; i < eventsJsonArray.length(); i++) {
                    JSONObject eventsJsonObject = eventsJsonArray.optJSONObject(i);
                    GallerySubModel gallerySubModel = new GallerySubModel();
                    gallerySubModel.setId(eventsJsonObject.optString("id"));
                    gallerySubModel.setName(eventsJsonObject.optString("name"));
                    gallerySubModel.setType(eventsJsonObject.optString("type"));
                    gallerySubModel.setImage(eventsJsonObject.optString("image"));
                    eventsGalleryModels.add(gallerySubModel);
                }
                mGalleryModel.setGallerySubModels(eventsGalleryModels);
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