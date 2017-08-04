package com.xappie.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobsSearchFragment extends Fragment {

    public static final String TAG = JobsSearchFragment.class.getSimpleName();
    private DashBoardActivity mParent;

    @BindView(R.id.et_select_job_category)
    EditText edt_select_job_category;
    @BindView(R.id.edt_company_name)
    EditText edt_company_name;
    @BindView(R.id.edt_city_name)
    EditText edt_city_name;
    @BindView(R.id.btn_search)
    Button btn_search;




    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceOpenSansBold;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
    }



    public JobsSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_jobs_search, container, false);
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

        edt_city_name.setTypeface(mTypefaceOpenSansRegular);
        edt_company_name.setTypeface(mTypefaceOpenSansRegular);
        edt_select_job_category.setTypeface(mTypefaceOpenSansRegular);
        btn_search.setTypeface(mTypefaceOpenSansRegular);

    }

    @OnClick(R.id.btn_search)
    public void navigateFindJobs()
    {
        Utility.navigateAllJobsFragment(new JobsFragment(),JobsFragment.TAG,null,mParent);
    }

}
