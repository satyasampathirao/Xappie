package com.xappie.parser;

import android.content.Context;

import com.google.gson.Gson;
import com.xappie.models.AdsModel;
import com.xappie.models.BannersModel;
import com.xappie.models.ClassifiedsModel;
import com.xappie.models.EventsModel;
import com.xappie.models.HomePageEventsAdsBannersModel;
import com.xappie.models.JobsModel;
import com.xappie.models.Model;

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

            if (jsonObject.has("classifieds")) {
                JSONArray classifiedsJsonArray = jsonObject.optJSONArray("classifieds");
                ArrayList<ClassifiedsModel> classifiedsModels = new ArrayList<>();
                for (int i = 0; i < classifiedsJsonArray.length(); i++) {
                    JSONObject eventsJsonObject = classifiedsJsonArray.optJSONObject(i);
                    ClassifiedsModel classifiedsModel = new Gson().fromJson(eventsJsonObject.toString(), ClassifiedsModel.class);
                    classifiedsModels.add(classifiedsModel);
                }
                homePageEventsAdsBannersModel.setClassifiedsModel(classifiedsModels);
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

            if (jsonObject.has("jobs")) {
                JSONArray jobsJsonArray = jsonObject.optJSONArray("jobs");
                ArrayList<JobsModel> jobsModels = new ArrayList<>();
                for (int i = 0; i < jobsJsonArray.length(); i++) {
                    JSONObject jobsJsonObject = jobsJsonArray.optJSONObject(i);
                    JobsModel jobsModel = new Gson().fromJson(jobsJsonObject.toString(), JobsModel.class);
                    jobsModels.add(jobsModel);
                }
                homePageEventsAdsBannersModel.setJobsModels(jobsModels);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return homePageEventsAdsBannersModel;
    }

}