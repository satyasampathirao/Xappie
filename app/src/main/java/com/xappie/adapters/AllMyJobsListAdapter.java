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
import com.xappie.models.JobsModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Ravi on 04-Aug-17.
 */

public class AllMyJobsListAdapter extends BaseAdapter {

    private DashBoardActivity mDashBoardActivity;
    private LayoutInflater mLayoutInflater;
    private ArrayList<JobsModel> jobsModels;
    private Typeface mOpenSansBoldTypeface;
    private Typeface mOpenSansRegularTypeface;

    public AllMyJobsListAdapter(DashBoardActivity mDashBoardActivity, ArrayList<JobsModel> jobsModels) {
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

        AllMyJobsListAdapter.ClassifiedsListHolder mClassifiedsListHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.find_jobs_list_item,
                    null);
            mClassifiedsListHolder = new AllMyJobsListAdapter.ClassifiedsListHolder();
            mClassifiedsListHolder.img_logo = convertView.findViewById(R.id.img_logo);
            mClassifiedsListHolder.tv_title = convertView.findViewById(R.id.tv_title);
            mClassifiedsListHolder.tv_positions = convertView.findViewById(R.id.tv_positions);
            mClassifiedsListHolder.tv_company_name = convertView.findViewById(R.id.tv_company_name);

            mClassifiedsListHolder.tv_title.setTypeface(mOpenSansBoldTypeface);
            mClassifiedsListHolder.tv_positions.setTypeface(mOpenSansRegularTypeface);
            mClassifiedsListHolder.tv_company_name.setTypeface(mOpenSansRegularTypeface);

            convertView.setTag(mClassifiedsListHolder);
        } else {
            mClassifiedsListHolder = (AllMyJobsListAdapter.ClassifiedsListHolder) convertView.getTag();
        }

        JobsModel jobsModel = jobsModels.get(position);
        // mClassifiedsListHolder.tv_title.setText(entertainmentModel.getTitle());
        mClassifiedsListHolder.tv_title.setText(jobsModel.getTitle());
        mClassifiedsListHolder.tv_positions.setText(jobsModel.getLocality());
        mClassifiedsListHolder.tv_company_name.setText(jobsModel.getCompany().toUpperCase());

        if (!Utility.isValueNullOrEmpty(jobsModel.getCompany_logo())) {
            Utility.universalImageLoaderPicLoading(mClassifiedsListHolder.img_logo,
                    jobsModel.getCompany_logo(), null, R.drawable.xappie_place_holder);
        } else {
            Utility.universalImageLoaderPicLoading(mClassifiedsListHolder.img_logo,
                    "", null, R.drawable.xappie_place_holder);
        }


        return convertView;
    }

    private class ClassifiedsListHolder {
        TextView tv_title;
        TextView tv_positions;
        TextView tv_company_name;
        ImageView img_logo;
    }
}
