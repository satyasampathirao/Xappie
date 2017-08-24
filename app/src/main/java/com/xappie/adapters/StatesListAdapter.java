package com.xappie.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.models.StateModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Shankar on 8-24-17.
 */

public class StatesListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mLayoutInflater;
    private Typeface mOpenSansRegularTypeface;
    private ArrayList<StateModel> stateModels;


    public StatesListAdapter(Context context, ArrayList<StateModel> stateModels) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        mOpenSansRegularTypeface = Utility.getOpenSansRegular(context);
        this.stateModels = stateModels;
    }

    @Override
    public int getCount() {
        return stateModels.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        StatesListAdapter.CitiesListHolder mCitiesListHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.custom_cities_item,
                    null);
            mCitiesListHolder = new StatesListAdapter.CitiesListHolder();
            mCitiesListHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_cities_list);

            mCitiesListHolder.tv_title.setTypeface(mOpenSansRegularTypeface);

            convertView.setTag(mCitiesListHolder);
        } else {
            mCitiesListHolder = (StatesListAdapter.CitiesListHolder) convertView.getTag();
        }

        StateModel stateModel = stateModels.get(position);
        mCitiesListHolder.tv_title.setText(stateModel.getName().toUpperCase());

        return convertView;
    }

    private class CitiesListHolder {
        TextView tv_title;
    }
}

