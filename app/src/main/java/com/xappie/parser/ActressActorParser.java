package com.xappie.parser;

import android.content.Context;

import com.google.gson.Gson;
import com.xappie.models.ActressActorsListModel;
import com.xappie.models.GallerySubItemModel;
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
                    GallerySubModel gallerySubModel = new Gson().fromJson(actorsJsonObject.toString(), GallerySubModel.class);
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
                    GallerySubModel gallerySubModel = new Gson().fromJson(actressJsonObject.toString(), GallerySubModel.class);
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
                    GallerySubModel gallerySubModel = new Gson().fromJson(moviesJsonObject.toString(), GallerySubModel.class);
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
                    GallerySubModel gallerySubModel = new Gson().fromJson(eventsJsonObject.toString(), GallerySubModel.class);
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