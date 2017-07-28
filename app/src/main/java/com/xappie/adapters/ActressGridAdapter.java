package com.xappie.adapters;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.models.ActressModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Shankar on 26/07/2017
 */

public class ActressGridAdapter extends BaseAdapter {

    private DashBoardActivity mDashBoardActivity;
    private LayoutInflater mLayoutInflater;
    private ArrayList<ActressModel> actressModels;
    private Typeface mOpenSansBoldTypeface;

    public ActressGridAdapter(DashBoardActivity mDashBoardActivity, ArrayList<ActressModel> actressModels) {
        this.mDashBoardActivity = mDashBoardActivity;
        mLayoutInflater = LayoutInflater.from(mDashBoardActivity);
        this.actressModels = actressModels;
        mOpenSansBoldTypeface = Utility.getOpenSansBold(mDashBoardActivity);
    }

    @Override
    public int getCount() {
        return actressModels.size();
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
        ActressGridAdapter.ActressGridHolder mActressGridHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.releated_topic_item,
                    null);
            mActressGridHolder = new ActressGridAdapter.ActressGridHolder();
            mActressGridHolder.img_topic = (ImageView) convertView.findViewById(R.id.img_topic);
            mActressGridHolder.tv_related_title = (TextView) convertView.findViewById(R.id.tv_related_title);

            mActressGridHolder.tv_related_title.setTypeface(mOpenSansBoldTypeface);

            convertView.setTag(mActressGridHolder);
        } else {
            mActressGridHolder = (ActressGridAdapter.ActressGridHolder) convertView.getTag();
        }

        ActressModel actressModel = actressModels.get(position);
        mActressGridHolder.tv_related_title.setText(actressModel.getTitle());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }

    private class ActressGridHolder {
        TextView tv_related_title;
        ImageView img_topic;
    }
}