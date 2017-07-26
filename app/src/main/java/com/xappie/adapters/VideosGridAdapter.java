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

public class VideosGridAdapter extends BaseAdapter {

    private DashBoardActivity mDashBoardActivity;
    private LayoutInflater mLayoutInflater;
    private ArrayList<ActressModel> actressModels;
    private Typeface mOpenSansBoldTypeface;

    public VideosGridAdapter(DashBoardActivity mDashBoardActivity, ArrayList<ActressModel> actressModels) {
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
        VideosGridAdapter.VideosGridHolder mVideosGridHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.videos_adapter_item,
                    null);
            mVideosGridHolder = new VideosGridAdapter.VideosGridHolder();
            mVideosGridHolder.img_video_image = (ImageView) convertView.findViewById(R.id.img_video_image);
            mVideosGridHolder.img_video = (ImageView) convertView.findViewById(R.id.img_video);
            mVideosGridHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);

            mVideosGridHolder.tv_title.setTypeface(mOpenSansBoldTypeface);

            convertView.setTag(mVideosGridHolder);
        } else {
            mVideosGridHolder = (VideosGridAdapter.VideosGridHolder) convertView.getTag();
        }

        ActressModel actressModel = actressModels.get(position);
        mVideosGridHolder.tv_title.setText(actressModel.getTitle());

        return convertView;
    }

    private class VideosGridHolder {
        ImageView img_video_image;
        ImageView img_video;
        TextView tv_title;
    }
}
