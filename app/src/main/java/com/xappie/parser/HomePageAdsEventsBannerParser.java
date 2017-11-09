package com.xappie.parser;

import android.content.Context;

import com.google.gson.Gson;
import com.xappie.models.AdsModel;
import com.xappie.models.BannersModel;
import com.xappie.models.EntertainmentModel;
import com.xappie.models.EventsModel;
import com.xappie.models.GalleryItemModel;
import com.xappie.models.HomePageContentModel;
import com.xappie.models.HomePageEventsAdsBannersModel;
import com.xappie.models.Model;
import com.xappie.models.VideosModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 8/24/2017.
 */

public class HomePageAdsEventsBannerParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        HomePageEventsAdsBannersModel homePageEventsAdsBannersModel = new HomePageEventsAdsBannersModel();
        try {
            JSONObject jsonObject = new JSONObject(s);

            if (jsonObject.has("banners")) {
                JSONArray bannersJsonArray = jsonObject.optJSONArray("banners");
                ArrayList<BannersModel> bannersModels = new ArrayList<>();
                for (int i = 0; i < bannersJsonArray.length(); i++) {
                    JSONObject bannersJsonObject = bannersJsonArray.optJSONObject(i);
                    BannersModel bannersModel = new Gson().fromJson(bannersJsonObject.toString(), BannersModel.class);
                    bannersModels.add(bannersModel);
                }
                homePageEventsAdsBannersModel.setBannersModels(bannersModels);
            }

            if (jsonObject.has("events")) {
                JSONArray eventsJsonArray = jsonObject.optJSONArray("events");
                ArrayList<EventsModel> eventsModels = new ArrayList<>();
                for (int i = 0; i < eventsJsonArray.length(); i++) {
                    JSONObject eventsJsonObject = eventsJsonArray.optJSONObject(i);
                    EventsModel eventsModel = new Gson().fromJson(eventsJsonObject.toString(), EventsModel.class);
                    eventsModels.add(eventsModel);
                }
                homePageEventsAdsBannersModel.setEventsModels(eventsModels);
            }

            if (jsonObject.has("ads")) {
                JSONArray adsJsonArray = jsonObject.optJSONArray("ads");
                ArrayList<AdsModel> adsModels = new ArrayList<>();
                for (int i = 0; i < adsJsonArray.length(); i++) {
                    JSONObject adsJsonObject = adsJsonArray.optJSONObject(i);
                    AdsModel adsModel = new Gson().fromJson(adsJsonObject.toString(), AdsModel.class);
                    adsModels.add(adsModel);
                }
                homePageEventsAdsBannersModel.setAdsModels(adsModels);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return homePageEventsAdsBannersModel;
    }

}