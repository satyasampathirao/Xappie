package com.xappie.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 10/31/2017.
 */

public class EventsListModel extends Model {
    private ArrayList<EventsModel> eventsModels;

    public ArrayList<EventsModel> getEventsModels() {
        return eventsModels;
    }

    public void setEventsModels(ArrayList<EventsModel> eventsModels) {
        this.eventsModels = eventsModels;
    }
}
