package com.xappie.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
public class PostJobFragment extends Fragment
{
    public static final String TAG = PostJobFragment.class.getSimpleName();
    private DashBoardActivity mParent;

    @BindView(R.id.edt_job_title)
    EditText edt_job_title;
    @BindView(R.id.edt_no_of_positions)
    EditText edt_no_of_positions;
    @BindView(R.id.edt_job_role)
    EditText edt_job_role;
    @BindView(R.id.edt_company_name)
    EditText edt_company_name;
    @BindView(R.id.edt_company_logo)
    EditText edt_company_logo;
    @BindView(R.id.btn_upload)
    Button btn_upload;
    @BindView(R.id.edt_website)
    EditText edt_website;
    @BindView(R.id.edt_eligibility)
    EditText edt_eligibility;
    @BindView(R.id.edt_experience)
    EditText edt_experience;
    @BindView(R.id.edt_job_location)
    EditText edt_job_location;
    @BindView(R.id.edt_job_description)
    EditText edt_job_description;
    @BindView(R.id.tv_mention_publish_date)
    TextView tv_mention_publish_date;
    @BindView(R.id.et_job_category)
    EditText edt_job_category;
    @BindView(R.id.tv_resume_required)
    TextView tv_resume_required;
    @BindView(R.id.radio_group_resume)
    RadioGroup radio_group_resume;
    @BindView(R.id.radio_yes_send)
    RadioButton rb_yes_send;
    @BindView(R.id.radio_no_send)
    RadioButton rb_no_send;
    @BindView(R.id.btn_submit)
    Button btn_submit;


    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceOpenSansBold;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
    }


    public PostJobFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_post_job, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefaceOpenSansBold = Utility.getOpenSansBold(mParent);

        edt_job_title.setTypeface(mTypefaceOpenSansRegular);
        edt_company_logo.setTypeface(mTypefaceOpenSansRegular);
        edt_company_name.setTypeface(mTypefaceOpenSansRegular);
        edt_eligibility.setTypeface(mTypefaceOpenSansRegular);
        edt_experience.setTypeface(mTypefaceOpenSansRegular);
        edt_job_category.setTypeface(mTypefaceOpenSansRegular);
        edt_job_description.setTypeface(mTypefaceOpenSansRegular);
        edt_job_location.setTypeface(mTypefaceOpenSansRegular);
        edt_no_of_positions.setTypeface(mTypefaceOpenSansRegular);
        edt_website.setTypeface(mTypefaceOpenSansRegular);
        edt_job_role.setTypeface(mTypefaceOpenSansRegular);
        btn_upload.setTypeface(mTypefaceOpenSansRegular);
        btn_submit.setTypeface(mTypefaceOpenSansRegular);
        rb_no_send.setTypeface(mTypefaceOpenSansRegular);
        rb_yes_send.setTypeface(mTypefaceOpenSansRegular);
        tv_mention_publish_date.setTypeface(mTypefaceOpenSansRegular);
        tv_resume_required.setTypeface(mTypefaceOpenSansRegular);
    }

    @OnClick(R.id.btn_submit)
    public void navigateSubmit()
    {

    }

    @OnClick(R.id.btn_upload)
    public void navigateUpload()
    {

    }
}
