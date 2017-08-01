package com.xappie.adapters;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.models.CitiesListModel;
import com.xappie.models.LanguagesListModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Ravi on 31-Jul-17.
 */

public class CityListAdapter extends BaseAdapter {

    private DashBoardActivity mDashBoardActivity;
    private LayoutInflater mLayoutInflater;
    private Typeface mOpenSansRegularTypeface;
    private ArrayList<CitiesListModel> citiesListModels;


    public CityListAdapter(DashBoardActivity mDashBoardActivity,ArrayList<CitiesListModel> citiesListModels )
    {
        this.mDashBoardActivity = mDashBoardActivity;
        mLayoutInflater = LayoutInflater.from(mDashBoardActivity);
        mOpenSansRegularTypeface = Utility.getOpenSansRegular(mDashBoardActivity);
        this.citiesListModels = citiesListModels;
    }

    @Override
    public int getCount() {
        return citiesListModels.size();
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

        CityListAdapter.CitiesListHolder mCitiesListHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.custom_cities_item,
                    null);
            mCitiesListHolder = new CityListAdapter.CitiesListHolder();
            mCitiesListHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_cities_list);

            mCitiesListHolder.tv_title.setTypeface(mOpenSansRegularTypeface);

            convertView.setTag(mCitiesListHolder);
        } else {
            mCitiesListHolder = (CityListAdapter.CitiesListHolder) convertView.getTag();
        }

        CitiesListModel citiesListModel = citiesListModels.get(position);
        mCitiesListHolder.tv_title.setText(citiesListModel.getTitle());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }

    private class CitiesListHolder {
        TextView tv_title;

    }
}

