package com.xappie.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobsViewFragment extends Fragment {

    public static final String TAG = JobsViewFragment.class.getSimpleName();
    private DashBoardActivity mParent;

    @BindView(R.id.iv_logo)
    ImageView iv_logo;
    @BindView(R.id.tv_text_header)
    TextView tv_text_header;
    @BindView(R.id.tv_all_over)
    TextView tv_all_over;
    @BindView(R.id.tv_noof_positions)
    TextView tv_noof_positions;
    @BindView(R.id.tv_no)
    TextView tv_no;
    @BindView(R.id.tv_published_date)
    TextView tv_published_date;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_job_role_head)
    TextView tv_job_role_head;
    @BindView(R.id.tv_job_role)
    TextView tv_job_role;
    @BindView(R.id.tv_company_name)
    TextView tv_company_name;
    @BindView(R.id.tv_company_details)
    TextView tv_company_details;
    @BindView(R.id.tv_eligibility)
    TextView tv_eligibility;
    @BindView(R.id.tv_eligibility_details)
    TextView tv_eligibility_details;
    @BindView(R.id.tv_experience)
    TextView tv_experience;
    @BindView(R.id.tv_experience_details)
    TextView tv_experience_details;
    @BindView(R.id.tv_job_description)
    TextView tv_job_description;
    @BindView(R.id.tv_job_description_details)
    TextView tv_job_description_details;
    @BindView(R.id.tv_Stipendiary)
    TextView tv_Stipendiary;
    @BindView(R.id.tv_Stipendiary_police)
    TextView tv_Stipendiary_police;
    @BindView(R.id.tv_Stipendiary_police_constable)
    TextView tv_Stipendiary_police_constable;
    @BindView(R.id.btn_submit_resume)
    Button btn_submit_resume;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceOpenSansBold;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
    }


    public JobsViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_jobs_view, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }
    private void initUI() {
        setTypeFace();
    }
    private void setTypeFace() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefaceOpenSansBold = Utility.getOpenSansBold(mParent);

        tv_eligibility.setTypeface(mTypefaceOpenSansBold);
        tv_all_over.setTypeface(mTypefaceOpenSansRegular);
        tv_company_details.setTypeface(mTypefaceOpenSansRegular);
        tv_company_name.setTypeface(mTypefaceOpenSansBold);
        tv_date.setTypeface(mTypefaceOpenSansRegular);
        tv_eligibility_details.setTypeface(mTypefaceOpenSansRegular);
        tv_experience.setTypeface(mTypefaceOpenSansBold);
        tv_experience_details.setTypeface(mTypefaceOpenSansRegular);
        tv_job_description.setTypeface(mTypefaceOpenSansBold);
        tv_job_description_details.setTypeface(mTypefaceOpenSansRegular);
        tv_job_role.setTypeface(mTypefaceOpenSansRegular);
        tv_job_role_head.setTypeface(mTypefaceOpenSansBold);
        tv_no.setTypeface(mTypefaceOpenSansRegular);
        tv_noof_positions.setTypeface(mTypefaceOpenSansRegular);
        tv_published_date.setTypeface(mTypefaceOpenSansRegular);
        tv_Stipendiary.setTypeface(mTypefaceOpenSansRegular);
        tv_Stipendiary_police_constable.setTypeface(mTypefaceOpenSansRegular);
        tv_text_header.setTypeface(mTypefaceOpenSansBold);
        tv_Stipendiary_police.setTypeface(mTypefaceOpenSansRegular);
        btn_submit_resume.setTypeface(mTypefaceOpenSansBold);
    }

    @OnClick(R.id.btn_submit_resume)
    public void navigateApplyJobs()
    {
           Utility.navigateAllJobsFragment(new ApplyJobsFragment(),ApplyJobsFragment.TAG,null,mParent);
    }
}
