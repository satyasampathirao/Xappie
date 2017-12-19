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
    private Typeface mMaterialTypeface;

    public StatesListAdapter(Context context, ArrayList<StateModel> stateModels) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        mOpenSansRegularTypeface = Utility.getOpenSansRegular(context);
        mMaterialTypeface = Utility.getMaterialIconsRegular(context);
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
            mCitiesListHolder.tv_title = convertView.findViewById(R.id.tv_cities_list);
            mCitiesListHolder.tv_selected = convertView.findViewById(R.id.tv_selected);
            mCitiesListHolder.tv_selected.setTypeface(mMaterialTypeface);
            mCitiesListHolder.tv_title.setTypeface(mOpenSansRegularTypeface);

            convertView.setTag(mCitiesListHolder);
        } else {
            mCitiesListHolder = (StatesListAdapter.CitiesListHolder) convertView.getTag();
        }

        StateModel stateModel = stateModels.get(position);
        mCitiesListHolder.tv_title.setText(stateModel.getName().toUpperCase());

        if (stateModel.ismSelected()) {
            mCitiesListHolder.tv_selected.setVisibility(View.VISIBLE);
        } else
            mCitiesListHolder.tv_selected.setVisibility(View.GONE);

        return convertView;
    }

    private class CitiesListHolder {
        TextView tv_title;
        TextView tv_selected;
    }
}

