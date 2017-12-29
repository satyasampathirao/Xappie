package com.xappie.adapters;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.fragments.GalleryCategoryFragment;
import com.xappie.fragments.GalleryImageViewFragment;
import com.xappie.models.GalleryCategoryModel;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Shankar on 26/07/2017
 */

public class GalleryCategoryAdapter extends BaseAdapter {

    private DashBoardActivity mParent;
    private LayoutInflater mLayoutInflater;
    private ArrayList<GalleryCategoryModel> galleryCategoryModels;
    private Typeface mOpenSansBoldTypeface;

    public GalleryCategoryAdapter(DashBoardActivity mParent, ArrayList<GalleryCategoryModel> galleryCategoryModels) {
        this.mParent = mParent;
        mLayoutInflater = LayoutInflater.from(mParent);
        this.galleryCategoryModels = galleryCategoryModels;
        mOpenSansBoldTypeface = Utility.getOpenSansBold(mParent);
    }

    @Override
    public int getCount() {
        return galleryCategoryModels.size();
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
        GalleryCategoryAdapter.ActressGridHolder mActressGridHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.releated_topic_item,
                    null);
            mActressGridHolder = new GalleryCategoryAdapter.ActressGridHolder();
            mActressGridHolder.img_topic = (ImageView) convertView.findViewById(R.id.img_topic);
            mActressGridHolder.tv_related_title = (TextView) convertView.findViewById(R.id.tv_related_title);

            mActressGridHolder.tv_related_title.setTypeface(Utility.getOpenSansRegular(mParent));

            convertView.setTag(mActressGridHolder);
        } else {
            mActressGridHolder = (GalleryCategoryAdapter.ActressGridHolder) convertView.getTag();
        }

        GalleryCategoryModel galleryCategoryModel = galleryCategoryModels.get(position);
        mActressGridHolder.tv_related_title.setText(galleryCategoryModel.getTitle());

        if (!Utility.isValueNullOrEmpty(galleryCategoryModel.getProfile_image())) {
            Utility.universalImageLoaderPicLoading(mActressGridHolder.img_topic,
                    galleryCategoryModel.getProfile_image(), null, R.drawable.xappie_place_holder);
        } else {
            Utility.universalImageLoaderPicLoading(mActressGridHolder.img_topic,
                    "", null, R.drawable.xappie_place_holder);
        }

        convertView.setId(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = v.getId();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.GALLERY_ID, galleryCategoryModels.get(pos).getId());
                Utility.navigateDashBoardFragment(new GalleryImageViewFragment(), GalleryImageViewFragment.TAG, bundle, mParent);
            }
        });

        return convertView;
    }

    private class ActressGridHolder {
        TextView tv_related_title;
        ImageView img_topic;
    }
}
