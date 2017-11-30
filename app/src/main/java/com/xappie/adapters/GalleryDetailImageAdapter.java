package com.xappie.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.models.GalleryImageViewModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Shankar on 7/26/2017.
 */

public class GalleryDetailImageAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<GalleryImageViewModel> galleryImageViewModels;


    public GalleryDetailImageAdapter(Context context, ArrayList<GalleryImageViewModel> galleryImageViewModels) {
        this.context = context;
        this.galleryImageViewModels = galleryImageViewModels;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return galleryImageViewModels.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.gallery_view_pager_item, view, false);

        assert imageLayout != null;
        final ImageView image_view = (ImageView) imageLayout
                .findViewById(R.id.img_gallery_image);
        final TextView tv_title = (TextView) imageLayout
                .findViewById(R.id.tv_title);
        final TextView tv_description = (TextView) imageLayout
                .findViewById(R.id.tv_description);
        final TextView tv_source = (TextView) imageLayout
                .findViewById(R.id.tv_source);

        if (!Utility.isValueNullOrEmpty(galleryImageViewModels.get(position).getImage()))
            Utility.universalImageLoaderPicLoading(image_view,
                    galleryImageViewModels.get(position).getImage(), null, R.drawable.gallery_place_holder);
        else {
            Utility.universalImageLoaderPicLoading(image_view,
                    "", null, R.drawable.gallery_place_holder);
        }
        tv_title.setTypeface(Utility.getOpenSansRegular(context));
        tv_title.setText(galleryImageViewModels.get(position).getTitle());
        tv_description.setText(galleryImageViewModels.get(position).getDescription());
        tv_source.setText(galleryImageViewModels.get(position).getSource());
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