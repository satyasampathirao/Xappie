package com.xappie.parser;

import android.content.Context;

import com.xappie.models.GalleryImageViewListModel;
import com.xappie.models.GalleryImageViewModel;
import com.xappie.models.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 10/06/2017.
 */

public class GalleryImageViewListParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        GalleryImageViewListModel mGalleryImageViewListModel = new GalleryImageViewListModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.optJSONArray("images");
            ArrayList<GalleryImageViewModel> galleryImageViewModels = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject actorsJsonObject = jsonArray.optJSONObject(i);
                GalleryImageViewModel galleryImageViewModel = new GalleryImageViewModel();
                galleryImageViewModel.setId(actorsJsonObject.optString("id"));
                galleryImageViewModel.setGallery(actorsJsonObject.optString("gallery"));
                galleryImageViewModel.setImage(actorsJsonObject.optString("image"));
                galleryImageViewModel.setDescription(actorsJsonObject.optString("description"));
                galleryImageViewModel.setSource(actorsJsonObject.optString("source"));
                galleryImageViewModel.setRecordedBy(actorsJsonObject.optString("recordedBy"));
                galleryImageViewModel.setRecordedDate(actorsJsonObject.optString("recordedDate"));
                galleryImageViewModels.add(galleryImageViewModel);
            }
            mGalleryImageViewListModel.setGalleryImageViewModels(galleryImageViewModels);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mGalleryImageViewListModel;
    }
}