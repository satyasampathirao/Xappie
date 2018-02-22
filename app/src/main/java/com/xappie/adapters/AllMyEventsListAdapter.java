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
import com.xappie.fragments.EventDetailViewFragment;
import com.xappie.models.EventsModel;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Shankar on 28/07/2017
 */

public class AllMyEventsListAdapter extends BaseAdapter {

    private DashBoardActivity mDashBoardActivity;
    private LayoutInflater mLayoutInflater;
    private ArrayList<EventsModel> eventsModels;
    private Typeface mOpenSansBoldTypeface;
    private Typeface mOpenSansRegularTypeface;
    private Typeface mOpenSansLightTypeface;

    public AllMyEventsListAdapter(DashBoardActivity mDashBoardActivity, ArrayList<EventsModel> eventsModels) {
        this.mDashBoardActivity = mDashBoardActivity;
        mLayoutInflater = LayoutInflater.from(mDashBoardActivity);
        this.eventsModels = eventsModels;
        mOpenSansBoldTypeface = Utility.getOpenSansBold(mDashBoardActivity);
        mOpenSansRegularTypeface = Utility.getOpenSansRegular(mDashBoardActivity);
        mOpenSansLightTypeface = Utility.getOpenSansLight(mDashBoardActivity);
    }

    @Override
    public int getCount() {
        return eventsModels.size();
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
        AllMyEventsListAdapter.ClassifiedsListHolder mClassifiedsListHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.all_events_list_item,
                    null);
            mClassifiedsListHolder = new AllMyEventsListAdapter.ClassifiedsListHolder();
            mClassifiedsListHolder.img_logo = (ImageView) convertView.findViewById(R.id.img_logo);
            mClassifiedsListHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            mClassifiedsListHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            mClassifiedsListHolder.tv_location = (TextView) convertView.findViewById(R.id.tv_location);
            mClassifiedsListHolder.tv_dots = (TextView) convertView.findViewById(R.id.tv_dots);

            mClassifiedsListHolder.tv_title.setTypeface(mOpenSansLightTypeface);
            mClassifiedsListHolder.tv_time.setTypeface(mOpenSansLightTypeface);
            mClassifiedsListHolder.tv_location.setTypeface(mOpenSansLightTypeface);
            mClassifiedsListHolder.tv_dots.setTypeface(Utility.getMaterialIconsRegular(mDashBoardActivity));


            convertView.setTag(mClassifiedsListHolder);
        } else {
            mClassifiedsListHolder = (AllMyEventsListAdapter.ClassifiedsListHolder) convertView.getTag();
        }

        EventsModel eventsModel = eventsModels.get(position);
        // mClassifiedsListHolder.tv_title.setText(entertainmentModel.getTitle());
        mClassifiedsListHolder.tv_title.setText(eventsModel.getName());
        if (!Utility.isValueNullOrEmpty(eventsModel.getLocality()))
        {
            mClassifiedsListHolder.tv_location.setText(eventsModel.getLocality());
        }
        else {
            mClassifiedsListHolder.tv_location.setVisibility(View.GONE);
        }

        mClassifiedsListHolder.tv_time.setText(Utility.readDateFormat(eventsModel.getStart_time().substring(0, 10)).toUpperCase() + " "
                + eventsModel.getStart_time().substring(11, 16).toUpperCase());

        if (!Utility.isValueNullOrEmpty(eventsModel.getImage())) {
            Utility.universalImageLoaderPicLoading(mClassifiedsListHolder.img_logo,
                    eventsModel.getImage(), null, R.drawable.xappie_place_holder);
        } else {
            Utility.universalImageLoaderPicLoading(mClassifiedsListHolder.img_logo,
                    "", null, R.drawable.xappie_place_holder);
        }

       /* convertView.setId(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = v.getId();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EVENT_ID, eventsModels.get(position).getId());
                Utility.navigateDashBoardFragment(new EventDetailViewFragment(), EventDetailViewFragment.TAG, bundle, mDashBoardActivity);
            }
        });*/


        return convertView;
    }

    class ClassifiedsListHolder {
        TextView tv_title;
        TextView tv_time;
        TextView tv_location;
        ImageView img_logo;
        TextView tv_dots;
    }
}
