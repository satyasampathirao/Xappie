package com.xappie.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.utils.Utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Shankar on 7/21/2017.
 */

public class HomeViewPagerAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private ArrayList<String> list;

    public HomeViewPagerAdapter(Context context, ArrayList<String> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.home_pager_item, view, false);

        String model = list.get(position);

        final ImageView img_home_banner = (ImageView) imageLayout.findViewById(R.id.img_home_banner);
        Utility.universalImageLoaderPicLoading(img_home_banner, model, null, R.drawable.xappie_place_holder);

        view.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}