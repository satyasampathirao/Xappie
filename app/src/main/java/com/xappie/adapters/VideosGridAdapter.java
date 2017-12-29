package com.xappie.adapters;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.activities.VideoViewActivity;
import com.xappie.models.VideosModel;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Shankar on 26/07/2017
 */

public class VideosGridAdapter extends BaseAdapter {

    private DashBoardActivity mDashBoardActivity;
    private LayoutInflater mLayoutInflater;
    private ArrayList<VideosModel> videosModels;
    private Typeface mOpenSansBoldTypeface;

    public VideosGridAdapter(DashBoardActivity mDashBoardActivity, ArrayList<VideosModel> videosModels) {
        this.mDashBoardActivity = mDashBoardActivity;
        mLayoutInflater = LayoutInflater.from(mDashBoardActivity);
        this.videosModels = videosModels;
        mOpenSansBoldTypeface = Utility.getOpenSansBold(mDashBoardActivity);
    }

    @Override
    public int getCount() {
        return videosModels.size();
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

            mVideosGridHolder.tv_title.setTypeface(Utility.getOpenSansRegular(mDashBoardActivity));

            convertView.setTag(mVideosGridHolder);
        } else {
            mVideosGridHolder = (VideosGridAdapter.VideosGridHolder) convertView.getTag();
        }

        final VideosModel videosModel = videosModels.get(position);
        mVideosGridHolder.tv_title.setText(videosModel.getTitle());
        if (!Utility.isValueNullOrEmpty(videosModel.getThumb_nail()))
            Utility.universalImageLoaderPicLoading(mVideosGridHolder.img_video_image,
                    videosModel.getThumb_nail(), null, R.drawable.xappie_place_holder);
        else {
            Utility.universalImageLoaderPicLoading(mVideosGridHolder.img_video_image,
                    "", null, R.drawable.xappie_place_holder);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mDashBoardActivity, VideoViewActivity.class);
                String mVideoId = (videosModel.getUrl() != null && videosModel.getUrl().matches(Constants.pattern))
                        ? videosModel.getUrl().substring(videosModel.getUrl().length() - 11,
                        videosModel.getUrl().length())
                        : "";

                if (!mVideoId.isEmpty()) {
                    intent.putExtra("videoId", mVideoId);
                    mDashBoardActivity.startActivity(intent);
                } else {
                    Utility.showToastMessage(mDashBoardActivity, "Url Not Valid");
                }
            }
        });


        return convertView;
    }

    private class VideosGridHolder {
        ImageView img_video_image;
        ImageView img_video;
        TextView tv_title;
    }
}
