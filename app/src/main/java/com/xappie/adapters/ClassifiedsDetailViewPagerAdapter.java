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
import com.xappie.models.Images;
import com.xappie.utils.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shankar on 7/21/2017.
 */

public class ClassifiedsDetailViewPagerAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private List<Images> images;
    private Context context;

    public ClassifiedsDetailViewPagerAdapter(Context context, List<Images> images) {
        this.images = images;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.home_pager_item, view, false);

        Images images1 = images.get(position);

        final ImageView img_home_banner = (ImageView) imageLayout.findViewById(R.id.img_home_banner);

        if (!Utility.isValueNullOrEmpty(images1.getImage())) {
            Utility.universalImageLoaderPicLoading(img_home_banner,
                    images1.getImage(), null, R.drawable.xappie_place_);
        } else {
            Utility.universalImageLoaderPicLoading(img_home_banner,
                    "", null, R.drawable.xappie_place_);
        }

        imageLayout.setId(position);
        imageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = view.getId();
                showFitDialog(images.get(pos).getImage(), context);
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
                .placeholder(Utility.getDrawable(context, R.drawable.xappie_place_))
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