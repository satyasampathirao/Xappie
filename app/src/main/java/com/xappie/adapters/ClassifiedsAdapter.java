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
import com.xappie.fragments.ClassifiedsFragment;
import com.xappie.fragments.ClassifiedsListFragment;
import com.xappie.fragments.ClassifiedsTabFragment;
import com.xappie.models.ClassifiedsModel;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Shankar on 26/07/2017
 */

public class ClassifiedsAdapter extends BaseAdapter {

    private DashBoardActivity mDashBoardActivity;
    private LayoutInflater mLayoutInflater;
    private ArrayList<ClassifiedsModel> classifiedsModels;
    private Typeface mOpenSansBoldTypeface;

    public ClassifiedsAdapter(DashBoardActivity mDashBoardActivity, ArrayList<ClassifiedsModel> classifiedsModels) {
        this.mDashBoardActivity = mDashBoardActivity;
        mLayoutInflater = LayoutInflater.from(mDashBoardActivity);
        this.classifiedsModels = classifiedsModels;
        mOpenSansBoldTypeface = Utility.getOpenSansBold(mDashBoardActivity);
    }

    @Override
    public int getCount() {
        return classifiedsModels.size();
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
        ClassifiedsAdapter.ClassifiedsGridHolder mClassifiedsGridHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.classfields_grid_item,
                    null);
            mClassifiedsGridHolder = new ClassifiedsAdapter.ClassifiedsGridHolder();
            mClassifiedsGridHolder.img_gallery_image = (ImageView) convertView.findViewById(R.id.img_gallery_image);
            mClassifiedsGridHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);

            mClassifiedsGridHolder.tv_title.setTypeface(mOpenSansBoldTypeface);

            convertView.setTag(mClassifiedsGridHolder);
        } else {
            mClassifiedsGridHolder = (ClassifiedsAdapter.ClassifiedsGridHolder) convertView.getTag();
        }

        ClassifiedsModel classifiedsModel = classifiedsModels.get(position);
        mClassifiedsGridHolder.tv_title.setText(classifiedsModel.getName());
        if (!Utility.isValueNullOrEmpty(classifiedsModel.getImage())) {
            Utility.universalImageLoaderPicLoading(mClassifiedsGridHolder.img_gallery_image,
                    classifiedsModel.getImage(), null, R.drawable.xappie_place_holder);
        } else {
            Utility.universalImageLoaderPicLoading(mClassifiedsGridHolder.img_gallery_image,
                    "", null, R.drawable.xappie_place_holder);
        }

        convertView.setId(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = v.getId();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.CLASSIFIEDS_CATEGORY_ID, classifiedsModels.get(position).getId());
                Utility.navigateDashBoardFragment(new ClassifiedsTabFragment(), ClassifiedsTabFragment.TAG, bundle, mDashBoardActivity);
            }
        });

        return convertView;
    }

    private class ClassifiedsGridHolder {
        TextView tv_title;
        ImageView img_gallery_image;
    }
}
