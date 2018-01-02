package com.xappie.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.models.LocalityModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Shankar on 01-02-17.
 */

public class LocalityListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mLayoutInflater;
    private Typeface mOpenSansRegularTypeface;
    private ArrayList<LocalityModel> localityModels;
    private Typeface mMaterialTypeface;

    public LocalityListAdapter(Context context, ArrayList<LocalityModel> localityModels) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        mOpenSansRegularTypeface = Utility.getOpenSansRegular(context);
        mMaterialTypeface = Utility.getMaterialIconsRegular(context);
        this.localityModels = localityModels;
    }

    @Override
    public int getCount() {
        return localityModels.size();
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

        LocalityListAdapter.CitiesListHolder mCitiesListHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.custom_cities_item,
                    null);
            mCitiesListHolder = new LocalityListAdapter.CitiesListHolder();
            mCitiesListHolder.tv_title = convertView.findViewById(R.id.tv_cities_list);
            mCitiesListHolder.tv_selected = convertView.findViewById(R.id.tv_selected);
            mCitiesListHolder.tv_selected.setTypeface(mMaterialTypeface);
            mCitiesListHolder.tv_title.setTypeface(mOpenSansRegularTypeface);

            convertView.setTag(mCitiesListHolder);
        } else {
            mCitiesListHolder = (LocalityListAdapter.CitiesListHolder) convertView.getTag();
        }

        LocalityModel localityModel = localityModels.get(position);
        mCitiesListHolder.tv_title.setText(localityModel.getName().toUpperCase());

        if (localityModel.ismSelected()) {
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

