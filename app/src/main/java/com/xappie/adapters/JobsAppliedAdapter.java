package com.xappie.adapters;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.models.JobsModel;
import com.xappie.models.MyAppliedJobsModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Ravi on 07-Aug-17.
 */

public class JobsAppliedAdapter extends BaseAdapter {

    private DashBoardActivity mDashBoardActivity;
    private LayoutInflater mLayoutInflater;
    private ArrayList<MyAppliedJobsModel> myAppliedJobsModels;
    private Typeface mOpenSansBoldTypeface;
    private Typeface mOpenSansRegularTypeface;

    public JobsAppliedAdapter(DashBoardActivity mDashBoardActivity, ArrayList<MyAppliedJobsModel> myAppliedJobsModels) {
        this.mDashBoardActivity = mDashBoardActivity;
        mLayoutInflater = LayoutInflater.from(mDashBoardActivity);
        this.myAppliedJobsModels = myAppliedJobsModels;
        mOpenSansBoldTypeface = Utility.getOpenSansBold(mDashBoardActivity);
        mOpenSansRegularTypeface = Utility.getOpenSansRegular(mDashBoardActivity);
    }

    @Override
    public int getCount() {
        return myAppliedJobsModels.size();
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
        JobsAppliedAdapter.JobAppliedAdapterHolder mJobAppliedAdapterHolder = null;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.jobs_applied_list,
                    null);

            mJobAppliedAdapterHolder = new JobsAppliedAdapter.JobAppliedAdapterHolder();

            mJobAppliedAdapterHolder.txt_job_name = (TextView) convertView.findViewById(R.id.txt_job_name);
            mJobAppliedAdapterHolder.tv_company_name = (TextView) convertView.findViewById(R.id.tv_company_name);
            mJobAppliedAdapterHolder.tv_positions = (TextView) convertView.findViewById(R.id.tv_positions);
            mJobAppliedAdapterHolder.tv_positions_number = (TextView) convertView.findViewById(R.id.tv_positions_number);
            mJobAppliedAdapterHolder.img_status_icon = (ImageView) convertView.findViewById(R.id.img_status_icon);
            mJobAppliedAdapterHolder.img_job_company_logo = (ImageView) convertView.findViewById(R.id.img_job_company_logo);
            mJobAppliedAdapterHolder.rl_relative_layout = (RelativeLayout) convertView.findViewById(R.id.rl_relative_layout);

            mJobAppliedAdapterHolder.ll_layout = (LinearLayout) convertView.findViewById(R.id.ll_layout);
            mJobAppliedAdapterHolder.radio_button_hired = (RadioButton) convertView.findViewById(R.id.radio_button_hired);
            mJobAppliedAdapterHolder.radio_button_not_hired = (RadioButton) convertView.findViewById(R.id.radio_button_not_hired);
            mJobAppliedAdapterHolder.btn_update = (Button) convertView.findViewById(R.id.btn_update);


            mJobAppliedAdapterHolder.btn_update.setTypeface(mOpenSansBoldTypeface);
            mJobAppliedAdapterHolder.txt_job_name.setTypeface(mOpenSansRegularTypeface);
            mJobAppliedAdapterHolder.tv_company_name.setTypeface(mOpenSansRegularTypeface);
            mJobAppliedAdapterHolder.tv_positions.setTypeface(mOpenSansRegularTypeface);
            mJobAppliedAdapterHolder.tv_positions_number.setTypeface(mOpenSansRegularTypeface);
            convertView.setTag(mJobAppliedAdapterHolder);
        }
        else {
            mJobAppliedAdapterHolder = (JobsAppliedAdapter.JobAppliedAdapterHolder) convertView.getTag();
        }

        MyAppliedJobsModel myAppliedJobsModel = myAppliedJobsModels.get(position);

        mJobAppliedAdapterHolder.txt_job_name.setText("Lorem ipsum is simply dummy text of the printing and typesetting industry");
       mJobAppliedAdapterHolder.tv_positions.setText(Utility.getResourcesString(mDashBoardActivity, R.string.positions).toUpperCase());
           mJobAppliedAdapterHolder.tv_company_name.setText("IBM");

        return convertView;
    }

    private class JobAppliedAdapterHolder {
        private TextView txt_job_name;
        private TextView tv_company_name;
        private TextView tv_positions;
        private TextView tv_positions_number;
        private ImageView img_status_icon;
        private ImageView img_job_company_logo;
        private RelativeLayout rl_relative_layout;
        private LinearLayout ll_layout;

        private RadioButton radio_button_hired;
        private RadioButton radio_button_not_hired;
        private Button btn_update;
    }

}
