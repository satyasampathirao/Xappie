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
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Shankar on 28/07/2017
 */

public class ClassifiedsListAdapter extends BaseAdapter {

    private DashBoardActivity mDashBoardActivity;
    private LayoutInflater mLayoutInflater;
    private ArrayList<ClassifiedsModel> classifiedsModels;
    private Typeface mOpenSansBoldTypeface;
    private Typeface mOpenSansRegularTypeface;
    private Typeface mMaterialIcon;
    private Typeface mFontAwesome;

    public ClassifiedsListAdapter(DashBoardActivity mDashBoardActivity, ArrayList<ClassifiedsModel> classifiedsModels) {
        this.mDashBoardActivity = mDashBoardActivity;
        mLayoutInflater = LayoutInflater.from(mDashBoardActivity);
        this.classifiedsModels = classifiedsModels;
        mOpenSansBoldTypeface = Utility.getOpenSansBold(mDashBoardActivity);
        mOpenSansRegularTypeface = Utility.getOpenSansRegular(mDashBoardActivity);
        mMaterialIcon = Utility.getMaterialIconsRegular(mDashBoardActivity);
        mFontAwesome = Utility.getFontAwesomeWebFont(mDashBoardActivity);
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
        ClassifiedsListHolder mClassifiedsListHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.classified_list_item,
                    null);
            mClassifiedsListHolder = new ClassifiedsListHolder();
            mClassifiedsListHolder.img_logo = (ImageView) convertView.findViewById(R.id.img_logo);
            mClassifiedsListHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            mClassifiedsListHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            mClassifiedsListHolder.tv_posted_by = (TextView) convertView.findViewById(R.id.tv_posted_by);
            mClassifiedsListHolder.tv_calendar_icon = (TextView) convertView.findViewById(R.id.tv_calendar_icon);
            mClassifiedsListHolder.tv_price_icon = (TextView) convertView.findViewById(R.id.tv_price_icon);
            mClassifiedsListHolder.tv_dots = (TextView) convertView.findViewById(R.id.tv_dots);
            mClassifiedsListHolder.tv_dots.setVisibility(View.GONE);

            mClassifiedsListHolder.tv_title.setTypeface(Utility.getOpenSansLight(mDashBoardActivity));
            mClassifiedsListHolder.tv_time.setTypeface(Utility.getOpenSansLight(mDashBoardActivity));
            mClassifiedsListHolder.tv_posted_by.setTypeface(Utility.getOpenSansLight(mDashBoardActivity));
            mClassifiedsListHolder.tv_calendar_icon.setTypeface(mFontAwesome);
            mClassifiedsListHolder.tv_price_icon.setTypeface(mMaterialIcon);

            convertView.setTag(mClassifiedsListHolder);
        } else {
            mClassifiedsListHolder = (ClassifiedsListHolder) convertView.getTag();
        }

        final ClassifiedsModel classifiedsModel = classifiedsModels.get(position);
        mClassifiedsListHolder.tv_title.setText(classifiedsModel.getTitle());
        mClassifiedsListHolder.tv_time.setText(Utility.readDateFormat(classifiedsModel.getRecordedDate()));
        mClassifiedsListHolder.tv_posted_by.setText(classifiedsModel.getPrice());


        if (!Utility.isValueNullOrEmpty(classifiedsModel.getImage())) {
            Utility.universalImageLoaderPicLoading(mClassifiedsListHolder.img_logo,
                    classifiedsModel.getImage(), null, R.drawable.xappie_place_holder);
        } else {
            Utility.universalImageLoaderPicLoading(mClassifiedsListHolder.img_logo,
                    "", null, R.drawable.xappie_place_holder);
        }


        convertView.setId(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = v.getId();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.CLASSIFIEDS_ID, classifiedsModels.get(position).getId());
                Utility.navigateDashBoardFragment(new ClassifiedsDetailFragment(), ClassifiedsDetailFragment.TAG, bundle, mDashBoardActivity);
            }
        });


        return convertView;
    }

    private class ClassifiedsListHolder {
        TextView tv_title;
        TextView tv_time;
        TextView tv_posted_by;
        ImageView img_logo;
        TextView tv_calendar_icon;
        TextView tv_price_icon;
        TextView tv_dots;
    }
}
