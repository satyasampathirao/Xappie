package com.xappie.adapters;

import android.content.Context;
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
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.Model;
import com.xappie.models.MyAppliedJobStatusUpdatedModel;
import com.xappie.models.MyAppliedJobsModel;
import com.xappie.parser.MyJobsAppliedStatusParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Ravi on 07-Aug-17.
 */

public class JobsAppliedAdapter extends BaseAdapter implements IAsyncCaller {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<MyAppliedJobsModel> mMyAppliedJobsModelsList;
    private Typeface typefaceLatoRegular;

    private int mCheckedPosition = -1;

    public JobsAppliedAdapter(Context context, ArrayList<MyAppliedJobsModel> mMyAppliedJobsModelsList) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mMyAppliedJobsModelsList = mMyAppliedJobsModelsList;
        typefaceLatoRegular = Utility.getOpenSansRegular(mContext);
    }

    @Override
    public int getCount() {
        return mMyAppliedJobsModelsList.size();
    }

    @Override
    public MyAppliedJobsModel getItem(int position) {
        return mMyAppliedJobsModelsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        JobsAppliedAdapter.JobAppliedAdapterHolder mJobAppliedAdapterHolder = null;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.jobs_applied_list,
                    null);
            mJobAppliedAdapterHolder = new JobsAppliedAdapter.JobAppliedAdapterHolder();

            mJobAppliedAdapterHolder.txt_job_name = convertView.findViewById(R.id.txt_job_name);
            mJobAppliedAdapterHolder.tv_company_name = convertView.findViewById(R.id.tv_company_name);
            mJobAppliedAdapterHolder.tv_positions = convertView.findViewById(R.id.tv_positions);
            mJobAppliedAdapterHolder.tv_positions_number = convertView.findViewById(R.id.tv_positions_number);
            mJobAppliedAdapterHolder.img_status_icon = convertView.findViewById(R.id.img_status_icon);
            mJobAppliedAdapterHolder.img_job_company_logo = convertView.findViewById(R.id.img_job_company_logo);
            mJobAppliedAdapterHolder.rl_relative_layout = convertView.findViewById(R.id.rl_relative_layout);
            // mJobAppliedAdapterHolder.mAdView = (NativeExpressAdView) convertView.findViewById(R.id.adView);

            mJobAppliedAdapterHolder.ll_layout = convertView.findViewById(R.id.ll_layout);
            mJobAppliedAdapterHolder.radio_button_hired = convertView.findViewById(R.id.radio_button_hired);
            mJobAppliedAdapterHolder.radio_button_not_hired = convertView.findViewById(R.id.radio_button_not_hired);
            mJobAppliedAdapterHolder.btn_update = convertView.findViewById(R.id.btn_update);

            mJobAppliedAdapterHolder.txt_job_name.setTypeface(typefaceLatoRegular);
            mJobAppliedAdapterHolder.tv_company_name.setTypeface(typefaceLatoRegular);
            mJobAppliedAdapterHolder.tv_positions.setTypeface(typefaceLatoRegular);
            mJobAppliedAdapterHolder.tv_positions_number.setTypeface(typefaceLatoRegular);
            convertView.setTag(mJobAppliedAdapterHolder);
        } else {
            mJobAppliedAdapterHolder = (JobsAppliedAdapter.JobAppliedAdapterHolder) convertView.getTag();
        }

        MyAppliedJobsModel mMyAppliedJobsModel = getItem(position);


        mJobAppliedAdapterHolder.txt_job_name.setText(Utility.capitalizeFirstLetter(mMyAppliedJobsModel.getTitle()) + "@" + mMyAppliedJobsModel.getCompany());
        mJobAppliedAdapterHolder.tv_company_name.setText(Utility.capitalizeFirstLetter(mMyAppliedJobsModel.getCompany()));
        mJobAppliedAdapterHolder.tv_positions_number.setText(mMyAppliedJobsModel.getPositions());

        if (mMyAppliedJobsModel.ismHiredLayout()) {
            mJobAppliedAdapterHolder.ll_layout.setVisibility(View.VISIBLE);
            mJobAppliedAdapterHolder.rl_relative_layout.setVisibility(View.GONE);
        } else {
            mJobAppliedAdapterHolder.ll_layout.setVisibility(View.GONE);
            mJobAppliedAdapterHolder.rl_relative_layout.setVisibility(View.VISIBLE);
        }

        if (!Utility.isValueNullOrEmpty(mMyAppliedJobsModel.getCompany_logo()))
            Utility.universalImageLoaderPicLoading(mJobAppliedAdapterHolder.img_job_company_logo,
                    mMyAppliedJobsModel.getCompany_logo(), null, R.drawable.xappie_place_holder);

        if (mMyAppliedJobsModel.getStatus().equalsIgnoreCase("2")) {
            mJobAppliedAdapterHolder.img_status_icon.setVisibility(View.GONE);
        } else {
            mJobAppliedAdapterHolder.img_status_icon.setVisibility(View.VISIBLE);
        }

        mJobAppliedAdapterHolder.rl_relative_layout.setId(position);
        mJobAppliedAdapterHolder.rl_relative_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = view.getId();
                if (mCheckedPosition != -1) {
                    MyAppliedJobsModel mMyAppliedJobsModel = mMyAppliedJobsModelsList.get(mCheckedPosition);
                    mMyAppliedJobsModel.setmHiredLayout(false);
                    mMyAppliedJobsModelsList.set(mCheckedPosition, mMyAppliedJobsModel);
                    MyAppliedJobsModel mMyAppliedJobsModel1 = mMyAppliedJobsModelsList.get(position);
                    mMyAppliedJobsModel1.setmHiredLayout(true);
                    mMyAppliedJobsModelsList.set(position, mMyAppliedJobsModel1);
                    mCheckedPosition = position;

                } else {
                    MyAppliedJobsModel mMyAppliedJobsModel = mMyAppliedJobsModelsList.get(position);
                    mMyAppliedJobsModel.setmHiredLayout(true);
                    mMyAppliedJobsModelsList.set(position, mMyAppliedJobsModel);
                    mCheckedPosition = position;
                }
                notifyDataSetChanged();
            }
        });

        mJobAppliedAdapterHolder.btn_update.setId(position);
        final JobAppliedAdapterHolder finalMJobAppliedAdapterHolder = mJobAppliedAdapterHolder;
        mJobAppliedAdapterHolder.btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = view.getId();
                if (finalMJobAppliedAdapterHolder.radio_button_hired.isChecked()) {
                    updateHiredPosition("2", mMyAppliedJobsModelsList.get(position).getId());
                } else if (finalMJobAppliedAdapterHolder.radio_button_not_hired.isChecked()) {
                    updateHiredPosition("3", mMyAppliedJobsModelsList.get(position).getId());
                } else {
                    Utility.showToastMessage(mContext, "Please select one of the radio button");
                }
            }
        });

       /* if (position % 5 == 0 && position != 0) {
            if (Constants.logAddsOnOrOff)
                mJobAppliedAdapterHolder.mAdView.setVisibility(View.VISIBLE);
            // Set its video options.
            mJobAppliedAdapterHolder.mAdView.setVideoOptions(new VideoOptions.Builder()
                    .setStartMuted(true)
                    .build());

            // The VideoController can be used to get lifecycle events and info about an ad's video
            // asset. One will always be returned by getVideoController, even if the ad has no video
            // asset.
            mJobAppliedAdapterHolder.mVideoController = mJobAppliedAdapterHolder.mAdView.getVideoController();
            mJobAppliedAdapterHolder.mVideoController.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    super.onVideoEnd();
                }
            });

            // Set an AdListener for the AdView, so the Activity can take action when an ad has finished
            // loading.
            final JobAppliedAdapter.JobAppliedAdapterHolder finalmJobAppliedAdapterHolder = mJobAppliedAdapterHolder;
            mJobAppliedAdapterHolder.mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    if (finalmJobAppliedAdapterHolder.mVideoController.hasVideoContent()) {
                        Log.d("sdf", "Received an ad that contains a video asset.");
                    } else {
                        Log.d("sdf", "Received an ad that does not contain a video asset.");
                    }
                }
            });

            mJobAppliedAdapterHolder.mAdView.loadAd(new AdRequest.Builder().build());
        } else {
            mJobAppliedAdapterHolder.mAdView.setVisibility(View.GONE);
        }*/


        return convertView;
    }

    private void updateHiredPosition(String status, String id) {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        paramMap.put("job_id", id);
        paramMap.put("status", status);
        MyJobsAppliedStatusParser mMyJobsAppliedStatusParser = new MyJobsAppliedStatusParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(mContext, Utility.getResourcesString(mContext,
                R.string.please_wait), true,
                APIConstants.APPLY_JOB, paramMap,
                APIConstants.REQUEST_TYPE.POST, this, mMyJobsAppliedStatusParser);
        Utility.execute(serverIntractorAsync);
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model.isStatus()) {
                if (model instanceof MyAppliedJobStatusUpdatedModel) {
                    MyAppliedJobStatusUpdatedModel mMyAppliedJobStatusUpdatedModel = (MyAppliedJobStatusUpdatedModel) model;
                    Utility.showToastMessage(mContext, mMyAppliedJobStatusUpdatedModel.getMessage());
                }
            }
        }
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

        //private NativeExpressAdView mAdView;
        //private VideoController mVideoController;

    }
}
