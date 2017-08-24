package com.xappie.parser;

import android.content.Context;

import com.xappie.models.Model;
import com.xappie.models.StateModel;
import com.xappie.models.StatesListModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 8/24/2017.
 */

public class StatesParser implements Parser<Model> {
    @Override
    public Model parse(String s, Context context) {
        StatesListModel mStatesListModel = new StatesListModel();
        try {
            JSONArray jsonArray = new JSONArray(s);
            ArrayList<StateModel> stateModels = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                StateModel mStateModel = new StateModel();
                mStateModel.setId(jsonObject.optString("id"));
                mStateModel.setName(jsonObject.optString("name"));
                stateModels.add(mStateModel);
            }
            mStatesListModel.setStateModels(stateModels);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mStatesListModel;
    }

}