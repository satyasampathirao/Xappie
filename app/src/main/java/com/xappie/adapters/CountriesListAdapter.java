package com.xappie.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.xappie.R;
import com.xappie.customviews.CircleTransform;
import com.xappie.models.CountriesModel;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Shankar on 31-Jul-17.
 */

public class CountriesListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mLayoutInflater;
    private Typeface mOpenSansRegularTypeface;
    private ArrayList<CountriesModel> countriesModels;

    public CountriesListAdapter(Context context, ArrayList<CountriesModel> countriesModels) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        mOpenSansRegularTypeface = Utility.getOpenSansRegular(context);
        this.countriesModels = countriesModels;
    }

    @Override
    public int getCount() {
        return countriesModels.size();
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

        CountriesModel countriesListModel = countriesModels.get(position);
        mCountryHolder.tv_title.setText(countriesListModel.getCountry_name());

        if (!Utility.isValueNullOrEmpty(countriesListModel.getFlag())) {
            Picasso.with(context).load(countriesListModel.getFlag())
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(Utility.getDrawable(context, R.drawable.xappie_place_holder))
                    .into(mCountryHolder.img_logo);
        } else {
            Utility.universalImageLoaderPicLoading(mCountryHolder.img_logo,
                    "", null, R.drawable.xappie_place_holder);
        }


        return convertView;
    }

    private class CountryHolder {
        TextView tv_title;
        ImageView img_logo;
    }
}

