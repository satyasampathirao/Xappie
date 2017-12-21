package com.xappie.adapters;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
import com.xappie.activities.DashBoardActivity;
import com.xappie.customviews.RoundedCornersTransformation;
import com.xappie.fragments.ClassifiedsFragment;
import com.xappie.fragments.ClassifiedsListFragment;
import com.xappie.fragments.ClassifiedsTabFragment;
import com.xappie.fragments.SubClassifiedsFragment;
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

  // @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
          //  mClassifiedsGridHolder.img_gallery_image.setClipToOutline(true);

            convertView.setTag(mClassifiedsGridHolder);
        } else {
            mClassifiedsGridHolder = (ClassifiedsAdapter.ClassifiedsGridHolder) convertView.getTag();
        }

        final ClassifiedsModel classifiedsModel = classifiedsModels.get(position);
        mClassifiedsGridHolder.tv_title.setText(classifiedsModel.getName());
        if (!Utility.isValueNullOrEmpty(classifiedsModel.getImage())) {


          Picasso.with(mDashBoardActivity).load(classifiedsModel.getImage())
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(Utility.getDrawable(mDashBoardActivity, R.color.twenty_percent_red))
                    .transform(new RoundedCornersTransformation(15,15)).into(mClassifiedsGridHolder.img_gallery_image);
           mClassifiedsGridHolder.img_gallery_image.setAlpha(220);


        } else {

            Utility.universalImageLoaderPicLoading(mClassifiedsGridHolder.img_gallery_image,
                    "", null, R.color.twenty_percent_red);

        }

        convertView.setId(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = v.getId();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.CLASSIFIEDS_CATEGORY_ID, classifiedsModels.get(position).getId());
                bundle.putString("Name",classifiedsModel.getName());
                Utility.navigateDashBoardFragment(new SubClassifiedsFragment(), SubClassifiedsFragment.TAG, bundle, mDashBoardActivity);
            }
        });

        return convertView;
    }

    private class ClassifiedsGridHolder {
        TextView tv_title;
        ImageView img_gallery_image;
    }
}
