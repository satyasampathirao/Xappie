package com.xappie.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.xappie.R;
import com.xappie.customviews.TouchImageView;
import com.xappie.models.AdsModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Shankar on 7/21/2017.
 */

public class AdsPagerAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private ArrayList<AdsModel> adsModels;
    private Context context;

    public AdsPagerAdapter(Context context, ArrayList<AdsModel> adsModels) {
        this.adsModels = adsModels;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public float getPageWidth(int position) {
        return (super.getPageWidth(position) / 2);
    }

    @Override
    public int getCount() {
        return adsModels.size();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.ads_item, view, false);

        AdsModel adsModel = adsModels.get(position);

        final ImageView img_ad_image = (ImageView) imageLayout.findViewById(R.id.img_ad_image);

        if (!Utility.isValueNullOrEmpty(adsModel.getImage())) {
            Utility.universalImageLoaderPicLoading(img_ad_image,
                    adsModel.getImage(), null, R.drawable.xappie_place_holder);
        } else {
            Utility.universalImageLoaderPicLoading(img_ad_image,
                    "", null, R.drawable.xappie_place_holder);
        }

        imageLayout.setId(position);
        imageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = view.getId();
                showFitDialog(adsModels.get(pos).getImage(), context);
            }
        });

        view.addView(imageLayout, 0);
        return imageLayout;
    }


    /*Image full view*/
    private void showFitDialog(String url, Context context) {
        Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_fitcenter);
        dialog.setCanceledOnTouchOutside(false);
        TouchImageView imageView = (TouchImageView) dialog.findViewById(R.id.imageView);
        Picasso.with(context)
                .load(url)
                .placeholder(Utility.getDrawable(context, R.drawable.xappie_place_holder))
                .into(imageView);
        dialog.show();
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