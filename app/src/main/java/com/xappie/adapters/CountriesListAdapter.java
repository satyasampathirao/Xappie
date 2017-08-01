package com.xappie.adapters;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.fragments.CitiesFragment;
import com.xappie.models.CountriesListModel;
import com.xappie.models.EntertainmentModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Ravi on 31-Jul-17.
 */

public class CountriesListAdapter extends BaseAdapter {

    private DashBoardActivity mDashBoardActivity;
    private LayoutInflater mLayoutInflater;
    private Typeface mOpenSansRegularTypeface;
    private ArrayList<CountriesListModel> countriesListModels;

    public CountriesListAdapter(DashBoardActivity mDashBoardActivity,ArrayList<CountriesListModel> countriesListModels)
    {
        this.mDashBoardActivity = mDashBoardActivity;
        mLayoutInflater = LayoutInflater.from(mDashBoardActivity);
        mOpenSansRegularTypeface = Utility.getOpenSansRegular(mDashBoardActivity);
        this.countriesListModels = countriesListModels;

    }
    @Override
    public int getCount() {
        return countriesListModels.size();
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

        CountriesListAdapter.CountryHolder mCountryHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.custom_countries_item,
                    null);
            mCountryHolder = new CountriesListAdapter.CountryHolder();
            mCountryHolder.img_logo = (ImageView) convertView.findViewById(R.id.img_logo);
           mCountryHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_country);

            mCountryHolder.tv_title.setTypeface(mOpenSansRegularTypeface);

            convertView.setTag(mCountryHolder);
        } else {
            mCountryHolder = (CountriesListAdapter.CountryHolder) convertView.getTag();
        }

       CountriesListModel countriesListModel = countriesListModels.get(position);
        mCountryHolder.tv_title.setText(countriesListModel.getTitle());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.navigateDashBoardFragment(new CitiesFragment(),CitiesFragment.TAG,null,mDashBoardActivity);
            }
        });


        return convertView;
    }

    private class CountryHolder {
        TextView tv_title;
        ImageView img_logo;
    }
}

