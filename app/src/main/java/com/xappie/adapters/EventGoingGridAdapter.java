package com.xappie.adapters;

import android.graphics.Typeface;
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
import com.xappie.customviews.CircleTransform;
import com.xappie.models.WhoIsGoingModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Shankar on 26/07/2017
 */

public class EventGoingGridAdapter extends BaseAdapter {

    private DashBoardActivity mDashBoardActivity;
    private LayoutInflater mLayoutInflater;
    private ArrayList<WhoIsGoingModel> whoIsGoingModels;
    private Typeface mOpenSansBoldTypeface;

    public EventGoingGridAdapter(DashBoardActivity mDashBoardActivity, ArrayList<WhoIsGoingModel> whoIsGoingModels) {
        this.mDashBoardActivity = mDashBoardActivity;
        mLayoutInflater = LayoutInflater.from(mDashBoardActivity);
        this.whoIsGoingModels = whoIsGoingModels;
        mOpenSansBoldTypeface = Utility.getOpenSansBold(mDashBoardActivity);
    }

    @Override
    public int getCount() {
        return whoIsGoingModels.size();
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
        EventGoingGridAdapter.EventGoingGridHolder mEventGoingGridHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.going_maygoing_grid_item,
                    null);
            mEventGoingGridHolder = new EventGoingGridAdapter.EventGoingGridHolder();
            mEventGoingGridHolder.img_topic = (ImageView) convertView.findViewById(R.id.img_topic);
            mEventGoingGridHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);

            mEventGoingGridHolder.tv_name.setTypeface(mOpenSansBoldTypeface);

            convertView.setTag(mEventGoingGridHolder);
        } else {
            mEventGoingGridHolder = (EventGoingGridAdapter.EventGoingGridHolder) convertView.getTag();
        }

        WhoIsGoingModel eventGoingModel = whoIsGoingModels.get(position);
        mEventGoingGridHolder.tv_name.setText(eventGoingModel.getFirst_name());
        if (!Utility.isValueNullOrEmpty(eventGoingModel.getPhoto()))
            Picasso.with(mDashBoardActivity).load(eventGoingModel.getPhoto())
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(Utility.getDrawable(mDashBoardActivity, R.drawable.avatar_image))
                    .transform(new CircleTransform()).into(mEventGoingGridHolder.img_topic);
        else {

            Picasso.with(mDashBoardActivity).load("asdasda")
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(Utility.getDrawable(mDashBoardActivity, R.drawable.avatar_image))
                    .transform(new CircleTransform()).into(mEventGoingGridHolder.img_topic);
        }

        return convertView;
    }

    private class EventGoingGridHolder {
        TextView tv_name;
        ImageView img_topic;
    }
}
