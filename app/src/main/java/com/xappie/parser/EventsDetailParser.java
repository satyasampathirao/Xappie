package com.xappie.parser;

import android.content.Context;

import com.google.gson.Gson;
import com.xappie.models.EventsModel;
import com.xappie.models.Model;

/**
 * Created by Shankar on 8/24/2017.
 */

public class EventsDetailParser implements Parser<Model> {
    @Override
    public Model parse(String json, Context context) {
        EventsModel eventsModel = new Gson().fromJson(json.toString(), EventsModel.class);
        return eventsModel;
    }
}