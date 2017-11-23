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
import com.xappie.fragments.ClassifiedsDetailFragment;
import com.xappie.models.ClassifiedsModel;
import com.xappie.models.EventsModel;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Shankar on 28/07/2017
 */

public class AllMyClassifiedsListAdapter extends BaseAdapter {

    private DashBoardActivity mDashBoardActivity;
    private LayoutInflater mLayoutInflater;
    private ArrayList<ClassifiedsModel> classifiedsModels;
    private Typeface mOpenSansBoldTypeface;
    private Typeface mOpenSansRegularTypeface;

    public AllMyClassifiedsListAdapter(DashBoardActivity mDashBoardActivity, ArrayList<ClassifiedsModel> classifiedsModels) {
        this.mDashBoardActivity = mDashBoardActivity;
        mLayoutInflater = LayoutInflater.from(mDashBoardActivity);
        this.classifiedsModels = classifiedsModels;
        mOpenSansBoldTypeface = Utility.getOpenSansBold(mDashBoardActivity);
        mOpenSansRegularTypeface = Utility.getOpenSansRegular(mDashBoardActivity);
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
        AllMyClassifiedsListAdapter.ClassifiedsListHolder mClassifiedsListHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.entertainment_list_item,
                    null);
            mClassifiedsListHolder = new AllMyClassifiedsListAdapter.ClassifiedsListHolder();
            mClassifiedsListHolder.img_logo = (ImageView) convertView.findViewById(R.id.img_logo);
            mClassifiedsListHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            mClassifiedsListHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            mClassifiedsListHolder.tv_posted_by = (TextView) convertView.findViewById(R.id.tv_posted_by);
            mClassifiedsListHolder.tv_posted_by.setVisibility(View.GONE);
            mClassifiedsListHolder.tv_time.setTextColor(Utility.getColor(mDashBoardActivity, R.color.light_yellow));

            mClassifiedsListHolder.tv_title.setTypeface(mOpenSansBoldTypeface);
            mClassifiedsListHolder.tv_time.setTypeface(mOpenSansRegularTypeface);
            mClassifiedsListHolder.tv_posted_by.setTypeface(mOpenSansRegularTypeface);

            convertView.setTag(mClassifiedsListHolder);
        } else {
            mClassifiedsListHolder = (AllMyClassifiedsListAdapter.ClassifiedsListHolder) convertView.getTag();
        }

        final ClassifiedsModel classifiedsModel = classifiedsModels.get(position);
        mClassifiedsListHolder.tv_title.setText(classifiedsModel.getTitle());
        if (!Utility.isValueNullOrEmpty(classifiedsModel.getImage())) {
            Utility.universalImageLoaderPicLoading(mClassifiedsListHolder.img_logo,
                    classifiedsModel.getImage(), null, R.drawable.xappie_place_holder);
        } else {
            Utility.universalImageLoaderPicLoading(mClassifiedsListHolder.img_logo,
                    "", null, R.drawable.xappie_place_holder);
        }
        mClassifiedsListHolder.tv_time.setText(classifiedsModel.getApprovedBy());


        return convertView;
    }

    private class ClassifiedsListHolder {
        TextView tv_title;
        TextView tv_time;
        TextView tv_posted_by;
        ImageView img_logo;
    }
}
