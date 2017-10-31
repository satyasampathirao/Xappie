package com.xappie.parser;

import android.content.Context;

import com.google.gson.Gson;
import com.xappie.models.EventsListModel;
import com.xappie.models.EventsModel;
import com.xappie.models.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 8/24/2017.
 */

public class EventsListParser implements Parser<Model> {
    @Override
    public Model parse(String json, Context context) {
        EventsListModel eventsListModel = new EventsListModel();
        try {
            JSONArray jsonArray = new JSONArray(json);
            ArrayList<EventsModel> eventsModelArrayList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                EventsModel eventsModel = new Gson().fromJson(jsonObject.toString(), EventsModel.class);
                eventsModelArrayList.add(eventsModel);
            }
            eventsListModel.setEventsModels(eventsModelArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eventsListModel;
    }

}