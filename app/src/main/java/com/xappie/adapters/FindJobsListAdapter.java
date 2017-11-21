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
import com.xappie.fragments.JobsViewFragment;
import com.xappie.models.JobsModel;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Ravi on 04-Aug-17.
 */

public class FindJobsListAdapter extends BaseAdapter
{
    private DashBoardActivity mDashBoardActivity;
    private LayoutInflater mLayoutInflater;
    private ArrayList<JobsModel> jobsModels;
    private Typeface mOpenSansBoldTypeface;
    private Typeface mOpenSansRegularTypeface;

    public FindJobsListAdapter(DashBoardActivity mDashBoardActivity, ArrayList<JobsModel> jobsModels) {
        this.mDashBoardActivity = mDashBoardActivity;
        mLayoutInflater = LayoutInflater.from(mDashBoardActivity);
        this.jobsModels = jobsModels;
        mOpenSansBoldTypeface = Utility.getOpenSansBold(mDashBoardActivity);
        mOpenSansRegularTypeface = Utility.getOpenSansRegular(mDashBoardActivity);
    }

    @Override
    public int getCount() {
        return jobsModels.size();
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
        FindJobsListAdapter.ClassifiedsListHolder mClassifiedsListHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.find_jobs_list_item,
                    null);
            mClassifiedsListHolder = new FindJobsListAdapter.ClassifiedsListHolder();
            mClassifiedsListHolder.img_logo = (ImageView) convertView.findViewById(R.id.img_logo);
            mClassifiedsListHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            mClassifiedsListHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_positions);
            mClassifiedsListHolder.tv_positions_nbr = (TextView) convertView.findViewById(R.id.tv_positions_nbr);
            mClassifiedsListHolder.tv_location = (TextView) convertView.findViewById(R.id.tv_company_name);

            mClassifiedsListHolder.tv_title.setTypeface(mOpenSansBoldTypeface);
            mClassifiedsListHolder.tv_time.setTypeface(mOpenSansRegularTypeface);
            mClassifiedsListHolder.tv_positions_nbr.setTypeface(mOpenSansRegularTypeface);
            mClassifiedsListHolder.tv_location.setTypeface(mOpenSansRegularTypeface);

            convertView.setTag(mClassifiedsListHolder);
        } else {
            mClassifiedsListHolder = (FindJobsListAdapter.ClassifiedsListHolder) convertView.getTag();
        }

        final JobsModel jobsModel = jobsModels.get(position);
        // mClassifiedsListHolder.tv_title.setText(entertainmentModel.getTitle());
        mClassifiedsListHolder.tv_title.setText(jobsModel.getTitle());
        mClassifiedsListHolder.tv_time.setText(Utility.getResourcesString(mDashBoardActivity, R.string.positions).toUpperCase());
        mClassifiedsListHolder.tv_positions_nbr.setText(jobsModel.getJobs_status());

        if (!Utility.isValueNullOrEmpty(jobsModel.getCompany_logo())) {
            Utility.universalImageLoaderPicLoading(mClassifiedsListHolder.img_logo,
                    jobsModel.getCompany_logo(), null, R.drawable.xappie_place_holder);
        } else {
            Utility.universalImageLoaderPicLoading(mClassifiedsListHolder.img_logo,
                    "", null, R.drawable.xappie_place_holder);
        }


        convertView.setId(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                           Utility.navigateAllJobsFragment(new JobsViewFragment(),JobsViewFragment.TAG, null,mDashBoardActivity);
            }
        });


        return convertView;
    }

    private class ClassifiedsListHolder {
        TextView tv_title;
        TextView tv_time;
        TextView tv_location;
        TextView tv_positions_nbr;
        ImageView img_logo;
    }
}

