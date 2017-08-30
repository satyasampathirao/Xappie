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
import com.xappie.models.EntertainmentModel;
import com.xappie.utils.Utility;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Shankar on 26/07/2017
 */

public class EntertainmentAdapter extends BaseAdapter {

    private DashBoardActivity mDashBoardActivity;
    private LayoutInflater mLayoutInflater;
    private ArrayList<EntertainmentModel> entertainmentModels;
    private Typeface mOpenSansBoldTypeface;
    private Typeface mOpenSansRegularTypeface;

    public EntertainmentAdapter(DashBoardActivity mDashBoardActivity, ArrayList<EntertainmentModel> entertainmentModels) {
        this.mDashBoardActivity = mDashBoardActivity;
        mLayoutInflater = LayoutInflater.from(mDashBoardActivity);
        this.entertainmentModels = entertainmentModels;
        mOpenSansBoldTypeface = Utility.getOpenSansBold(mDashBoardActivity);
        mOpenSansRegularTypeface = Utility.getOpenSansRegular(mDashBoardActivity);
    }

    @Override
    public int getCount() {
        return entertainmentModels.size();
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
        EntertainmentAdapter.EntertainmentHolder mEntertainmentHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.entertainment_list_item,
                    null);
            mEntertainmentHolder = new EntertainmentAdapter.EntertainmentHolder();
            mEntertainmentHolder.img_logo = (ImageView) convertView.findViewById(R.id.img_logo);
            mEntertainmentHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            mEntertainmentHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            mEntertainmentHolder.tv_posted_by = (TextView) convertView.findViewById(R.id.tv_posted_by);

            mEntertainmentHolder.tv_title.setTypeface(mOpenSansBoldTypeface);
            mEntertainmentHolder.tv_time.setTypeface(mOpenSansRegularTypeface);
            mEntertainmentHolder.tv_posted_by.setTypeface(mOpenSansRegularTypeface);

            convertView.setTag(mEntertainmentHolder);
        } else {
            mEntertainmentHolder = (EntertainmentAdapter.EntertainmentHolder) convertView.getTag();
        }

        EntertainmentModel entertainmentModel = entertainmentModels.get(position);
        mEntertainmentHolder.tv_title.setText(entertainmentModel.getTitle());

        if (!Utility.isValueNullOrEmpty(entertainmentModel.getRecordedDate())) {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            PrettyTime prettyTime = new PrettyTime();
            Date date;
            String outputDateStr = "";
            try {
                date = inputFormat.parse(entertainmentModel.getRecordedDate());
                outputDateStr = prettyTime.format(date);
                mEntertainmentHolder.tv_time.setText(outputDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (!Utility.isValueNullOrEmpty(entertainmentModel.getProfile_image()))
            Utility.universalImageLoaderPicLoading(mEntertainmentHolder.img_logo,
                    entertainmentModel.getProfile_image(), null, R.drawable.xappie_place_holder);

        if (!Utility.isValueNullOrEmpty(entertainmentModel.getRecordedBy())) {
            mEntertainmentHolder.tv_posted_by.setText(entertainmentModel.getRecordedBy());
            mEntertainmentHolder.tv_posted_by.setVisibility(View.VISIBLE);
        } else {
            mEntertainmentHolder.tv_posted_by.setVisibility(View.GONE);
        }

        return convertView;
    }

    private class EntertainmentHolder {
        TextView tv_title;
        TextView tv_time;
        TextView tv_posted_by;
        ImageView img_logo;
    }
}
