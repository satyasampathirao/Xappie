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
    private Typeface mOpenSansLightTypeface;

    public FindJobsListAdapter(DashBoardActivity mDashBoardActivity, ArrayList<JobsModel> jobsModels) {
        this.mDashBoardActivity = mDashBoardActivity;
        mLayoutInflater = LayoutInflater.from(mDashBoardActivity);
        this.jobsModels = jobsModels;
        mOpenSansBoldTypeface = Utility.getOpenSansBold(mDashBoardActivity);
        mOpenSansRegularTypeface = Utility.getOpenSansRegular(mDashBoardActivity);
        mOpenSansLightTypeface = Utility.getOpenSansLight(mDashBoardActivity);
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
            mClassifiedsListHolder.tv_positions = (TextView) convertView.findViewById(R.id.tv_positions);
            mClassifiedsListHolder.tv_pos = (TextView) convertView.findViewById(R.id.tv_pos);
            mClassifiedsListHolder.tv_company_name = (TextView) convertView.findViewById(R.id.tv_company_name);
            mClassifiedsListHolder.view = (View) convertView.findViewById(R.id.view_job);
            mClassifiedsListHolder.tv_dots = convertView.findViewById(R.id.tv_dots);

            mClassifiedsListHolder.tv_title.setTypeface(mOpenSansRegularTypeface);
            mClassifiedsListHolder.tv_positions.setTypeface(mOpenSansRegularTypeface);
            mClassifiedsListHolder.tv_company_name.setTypeface(mOpenSansRegularTypeface);
            mClassifiedsListHolder.tv_pos.setTypeface(Utility.getFontAwesomeWebFont(mDashBoardActivity));
            mClassifiedsListHolder.tv_dots.setTypeface(Utility.getMaterialIconsRegular(mDashBoardActivity));
            mClassifiedsListHolder.tv_dots.setVisibility(View.GONE);
            mClassifiedsListHolder.view.setVisibility(View.GONE);

            convertView.setTag(mClassifiedsListHolder);
        } else {
            mClassifiedsListHolder = (FindJobsListAdapter.ClassifiedsListHolder) convertView.getTag();
        }

        final JobsModel jobsModel = jobsModels.get(position);
        // mClassifiedsListHolder.tv_title.setText(entertainmentModel.getTitle());
        mClassifiedsListHolder.tv_title.setText(jobsModel.getTitle());
        if (!Utility.isValueNullOrEmpty(jobsModel.getLocality()))
        {
            mClassifiedsListHolder.tv_positions.setText(jobsModel.getLocality());
        }
        else {
            mClassifiedsListHolder.tv_pos.setVisibility(View.GONE);
            mClassifiedsListHolder.tv_positions.setVisibility(View.GONE);
        }

        mClassifiedsListHolder.tv_company_name.setText(jobsModel.getCompany());

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
                int position = v.getId();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.JOBS_ID, String.valueOf(jobsModels.get(position).getId()));

                Utility.navigateDashBoardFragment(new JobsViewFragment(),JobsViewFragment.TAG, bundle,mDashBoardActivity);
            }
        });


        return convertView;
    }

    private class ClassifiedsListHolder {
        TextView tv_title;
        TextView tv_positions;
        TextView tv_company_name;
        TextView tv_pos;
        ImageView img_logo;
        TextView tv_dots;
        View view;
    }
}

