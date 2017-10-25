package com.xappie.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xappie.R;
import com.xappie.models.HomePageBannerModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Shankar on 7/21/2017.
 */

public class HomeViewPagerAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private ArrayList<HomePageBannerModel> homePageBannerModels;

    public HomeViewPagerAdapter(Context context, ArrayList<HomePageBannerModel> homePageBannerModels) {
        this.homePageBannerModels = homePageBannerModels;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return homePageBannerModels.size();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.home_pager_item, view, false);

        HomePageBannerModel homePageBannerModel = homePageBannerModels.get(position);

        final ImageView img_home_banner = (ImageView) imageLayout.findViewById(R.id.img_home_banner);

        if (!Utility.isValueNullOrEmpty(homePageBannerModel.getBanner_image())) {
            Utility.universalImageLoaderPicLoading(img_home_banner,
                    homePageBannerModel.getBanner_image(), null, R.drawable.xappie_place_holder);
        } else {
            Utility.universalImageLoaderPicLoading(img_home_banner,
                    "", null, R.drawable.xappie_place_holder);
        }

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