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
import com.xappie.fragments.ClassifiedsTabFragment;
import com.xappie.fragments.SubClassifiedsFragment;
import com.xappie.models.ClassifiedsModel;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Santosh on 07-12-2017.
 */

public class SubClassifiedsAdapter extends BaseAdapter {

    private DashBoardActivity mDashBoardActivity;
    private LayoutInflater mLayoutInflater;
    private ArrayList<ClassifiedsModel> classifiedsModels;
    private Typeface mOpenSansBoldTypeface;
    private String mID;

    public SubClassifiedsAdapter(DashBoardActivity mDashBoardActivity, ArrayList<ClassifiedsModel> classifiedsModels, String mID) {
        this.mDashBoardActivity = mDashBoardActivity;
        mLayoutInflater = LayoutInflater.from(mDashBoardActivity);
        this.classifiedsModels = classifiedsModels;
        this.mID = mID;
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
        SubClassifiedsAdapter.ClassifiedsGridHolder mClassifiedsGridHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.sub_classifieds_list_item,
                    null);
            mClassifiedsGridHolder = new SubClassifiedsAdapter.ClassifiedsGridHolder();
            mClassifiedsGridHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_list);

            mClassifiedsGridHolder.tv_title.setTypeface(Utility.getOpenSansRegular(mDashBoardActivity));

            convertView.setTag(mClassifiedsGridHolder);
        } else {
            mClassifiedsGridHolder = (SubClassifiedsAdapter.ClassifiedsGridHolder) convertView.getTag();
        }

        final ClassifiedsModel classifiedsModel = classifiedsModels.get(position);
        mClassifiedsGridHolder.tv_title.setText(classifiedsModel.getName());

        convertView.setId(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = v.getId();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.CLASSIFIEDS_CATEGORY_ID, mID);
                bundle.putString(Constants.CLASSIFIEDS_SUB_CATEGORY_ID, classifiedsModels.get(position).getId());
                bundle.putString("Name", classifiedsModel.getName());
                Utility.navigateDashBoardFragment(new ClassifiedsTabFragment(), ClassifiedsTabFragment.TAG, bundle, mDashBoardActivity);
            }
        });

        return convertView;
    }

    private class ClassifiedsGridHolder {
        TextView tv_title;

    }
}
