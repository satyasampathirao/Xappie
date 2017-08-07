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
public class ApplyJobsFragment extends Fragment {

    public static final String TAG = ApplyJobsFragment.class.getSimpleName();
    private DashBoardActivity mParent;

    @BindView(R.id.edt_cover_letter)
    EditText edt_cover_letter;
    @BindView(R.id.edt_upload_resume)
    EditText edt_upload_resume;
    @BindView(R.id.btn_upload)
    Button btn_upload;
    @BindView(R.id.btn_apply)
    Button btn_apply;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceOpenSansBold;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
    }

    public ApplyJobsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_apply_jobs, container, false);
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
           private void setTypeFace()
           {
               mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
               mTypefaceOpenSansBold = Utility.getOpenSansBold(mParent);

               edt_cover_letter.setTypeface(mTypefaceOpenSansRegular);
               edt_upload_resume.setTypeface(mTypefaceOpenSansRegular);
               btn_apply.setTypeface(mTypefaceOpenSansRegular);
               btn_upload.setTypeface(mTypefaceOpenSansRegular);


           }

           @OnClick(R.id.btn_apply)
           public void navigateJobsView()
           {
               Utility.navigateAllJobsFragment(new JobsViewFragment(),JobsViewFragment.TAG,null,mParent);
           }

    }
