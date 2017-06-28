package com.xappie.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.AppTourActivity;

/**
 * Created by shankar on 6/28/2017.
 */

public class AdapterforTour extends PagerAdapter implements OnClickListener {

    private int NumberOfPages = 4;
    private LayoutInflater mLayoutInflater;
    private View mView;
    private Context mContext;
    private TextView mTxtQuickTour;
    private TextView mTxtSkip;

    private Typeface mTypeface_regular;
    private String mFontPath_regular;
    private Typeface mTypeface_medium;
    private String mFontPath_medium;
    private Typeface mTypeface_light;
    private String mFontPath_light;

    public AdapterforTour(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mFontPath_regular = "fonts/OpenSans-Regular.ttf";
        mTypeface_regular = Typeface.createFromAsset(mContext.getAssets(),
                mFontPath_regular);
        mFontPath_medium = "fonts/OpenSans-Bold.ttf";
        mTypeface_medium = Typeface.createFromAsset(mContext.getAssets(),
                mFontPath_medium);
        mFontPath_light = "fonts/OpenSans-Light.ttf";
        mTypeface_light = Typeface.createFromAsset(mContext.getAssets(),
                mFontPath_light);

    }

    @Override
    public int getCount() {
        return NumberOfPages;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(View collection, int position) {

        switch (position) {
            case 0:
                mView = mLayoutInflater.inflate(R.layout.tour_screen_1, null);
                ((ViewPager) collection).addView(mView, 0);

                break;
            case 1:
                mView = mLayoutInflater.inflate(R.layout.tour_screen_1, null);
                ((ViewPager) collection).addView(mView, 0);

                break;
            case 2:
                mView = mLayoutInflater.inflate(R.layout.tour_screen_1, null);
                ((ViewPager) collection).addView(mView, 0);

                break;
            case 3:
                mView = mLayoutInflater.inflate(R.layout.tour_screen_1, null);
                ((ViewPager) collection).addView(mView, 0);
                break;

            default:
                break;
        }

        return mView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public void onClick(View v) {
        if (v == mTxtQuickTour) {
            AppTourActivity.mViewPager.setCurrentItem(1);
        }
    }

}
