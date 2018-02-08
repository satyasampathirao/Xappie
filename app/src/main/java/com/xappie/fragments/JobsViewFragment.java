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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.JobsModel;
import com.xappie.models.Model;
import com.xappie.models.StateModel;
import com.xappie.models.StatesListModel;
import com.xappie.parser.JobsDetailParser;
import com.xappie.parser.StatesParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobsViewFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = JobsViewFragment.class.getSimpleName();
    private DashBoardActivity mParent;

    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;


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
    @BindView(R.id.tv_date_icon)
    TextView tv_date_icon;

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
    @BindView(R.id.tv_job_description)
    TextView tv_job_description;
    @BindView(R.id.tv_job_description_details)
    TextView tv_job_description_details;
    @BindView(R.id.tv_job_address)
    TextView tv_job_address;
    @BindView(R.id.tv_job_address_details)
    TextView tv_job_address_details;
    @BindView(R.id.btn_submit_resume)
    TextView btn_submit_resume;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;
    private Typeface mTypefaceOpenSansBold;

    @BindView(R.id.tv_notification_arrow_back_icon)
    TextView tv_notification_arrow_back_icon;
    @BindView(R.id.tv_notification_menu_icon)
    TextView tv_notification_menu_icon;

    private String mId;
    private StatesListModel mStatesListModel;
    private StateModel stateModel;
    private JobsModel jobsModel;

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_location_icon)
    TextView tv_location_icon;
    @BindView(R.id.tv_notifications_icon)
    TextView tv_notifications_icon;
    @BindView(R.id.tv_language_icon)
    TextView tv_language_icon;

    @BindView(R.id.tv_loc_icon)
    TextView tv_loc_icon;
    @BindView(R.id.tv_person_icon)
    TextView tv_person_icon;

    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        mFrameLayout = (FrameLayout) getActivity().findViewById(R.id.content_frame);
        mParams = (CoordinatorLayout.LayoutParams) mFrameLayout.getLayoutParams();
        if (getArguments() != null && getArguments().containsKey(Constants.JOBS_ID)) {
            mId = getArguments().getString(Constants.JOBS_ID);
        }
        Utility.sendGoogleAnalytics(mParent, TAG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (appBarLayout != null) {
            mParams.setBehavior(null);
            mFrameLayout.requestLayout();
            appBarLayout.setVisibility(View.GONE);
        }
        if (rootView != null) {
            return rootView;
        }
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_jobs_view, container, false);
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
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);
        mTypefaceOpenSansBold = Utility.getOpenSansBold(mParent);

        tv_notification_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(Utility.getResourcesString(mParent, R.string.jobs));
        tv_title.setTypeface(mTypefaceOpenSansRegular);

        tv_location_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notifications_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);


        setTypeFace();
        stateModel = new StateModel();
        stateModel.setId(Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID));
        getCitiesList();
    }

    private void getCitiesList() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            StatesParser statesParser = new StatesParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_CITIES + "/" + Utility.getSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID), linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, statesParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTypeFace() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefaceOpenSansBold = Utility.getOpenSansBold(mParent);


        tv_all_over.setTypeface(mTypefaceOpenSansRegular);
        tv_company_details.setTypeface(mTypefaceOpenSansRegular);
        tv_company_name.setTypeface(mTypefaceOpenSansRegular);
        tv_date.setTypeface(mTypefaceOpenSansRegular);
        tv_job_address.setTypeface(mTypefaceOpenSansRegular);
        tv_job_address_details.setTypeface(mTypefaceOpenSansRegular);
        tv_job_description.setTypeface(mTypefaceOpenSansRegular);
        tv_job_description_details.setTypeface(mTypefaceOpenSansRegular);
        tv_job_role.setTypeface(mTypefaceOpenSansRegular);
        tv_job_role_head.setTypeface(mTypefaceOpenSansRegular);
        tv_no.setTypeface(mTypefaceOpenSansRegular);
        tv_noof_positions.setTypeface(mTypefaceOpenSansRegular);
        tv_text_header.setTypeface(mTypefaceOpenSansRegular);
        btn_submit_resume.setTypeface(mTypefaceOpenSansRegular);
        tv_date_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_loc_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_person_icon.setTypeface(Utility.getMaterialIconsRegular(mParent));
        getJobsData();
    }

    @OnClick(R.id.btn_submit_resume)
    public void navigateApplyJobs() {
        Bundle bundle = new Bundle();
        bundle.putString("id", "" + jobsModel.getId());
        if (!Utility.isValueNullOrEmpty(jobsModel.getIsResume()))
            bundle.putString(Constants.IS_RESUME, jobsModel.getIsResume());
        else
            bundle.putString(Constants.IS_RESUME, "0");
        Utility.navigateDashBoardFragment(new ApplyJobsFragment(), ApplyJobsFragment.TAG, bundle, mParent);
    }


    private void getJobsData() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            JobsDetailParser jobsDetailParser = new JobsDetailParser();
            ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(mParent, Utility.getResourcesString(mParent, R.string.please_wait), true, APIConstants.GET_JOB_DETAILS + "/" + mId, linkedHashMap, APIConstants.REQUEST_TYPE.GET, this, jobsDetailParser);
            Utility.execute(serverIntractorAsync);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onComplete(Model model) {

        if (model != null) {
            if (model instanceof JobsModel) {
                jobsModel = (JobsModel) model;

                setJobsData();
            } else if (model instanceof StatesListModel) {
                mStatesListModel = (StatesListModel) model;
                if (mStatesListModel.getStateModels().size() > 0) {
                    for (int i = 0; i < mStatesListModel.getStateModels().size(); i++) {
                        if (Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID)
                                .equalsIgnoreCase(mStatesListModel.getStateModels().get(i).getId())) {
                            stateModel = mStatesListModel.getStateModels().get(i);
                        }
                    }
                }

            }
        }
    }

    private void setJobsData() {
        if (!Utility.isValueNullOrEmpty(jobsModel.getTitle())) {
            tv_text_header.setText(jobsModel.getTitle());
        } else {
            tv_text_header.setVisibility(View.GONE);
        }

        if (!Utility.isValueNullOrEmpty(jobsModel.getLocalityname())) {
            tv_all_over.setText(jobsModel.getLocalityname());
        } else {
            tv_all_over.setVisibility(View.GONE);
            tv_loc_icon.setVisibility(View.GONE);
        }
        if (!Utility.isValueNullOrEmpty(jobsModel.getPositions())) {
            tv_no.setText(jobsModel.getPositions());
        } else {
            tv_no.setVisibility(View.GONE);
        }

        if (!Utility.isValueNullOrEmpty(jobsModel.getRecordedDate())) {
            tv_date.setText(Utility.displayDateFormat(jobsModel.getRecordedDate().toUpperCase()));
        } else {
            tv_date.setVisibility(View.GONE);
            tv_date_icon.setVisibility(View.GONE);
        }
        if (!Utility.isValueNullOrEmpty(jobsModel.getRole())) {
            tv_job_role.setText(jobsModel.getRole());
        } else {
            tv_job_role.setVisibility(View.GONE);
        }
        if (!Utility.isValueNullOrEmpty(jobsModel.getCompany())) {
            tv_company_details.setText(jobsModel.getCompany());
        } else {
            tv_company_details.setVisibility(View.GONE);
        }
        if (!Utility.isValueNullOrEmpty(jobsModel.getDescription())) {
            tv_job_description_details.setText(jobsModel.getDescription());
        } else {
            tv_job_description_details.setVisibility(View.GONE);
        }

        if (!Utility.isValueNullOrEmpty(jobsModel.getAddress()))
        {
            tv_job_address_details.setText(jobsModel.getAddress());
        }
        else {
            tv_job_address_details.setVisibility(View.GONE);
            tv_job_address.setVisibility(View.GONE);
        }

        if (!Utility.isValueNullOrEmpty(jobsModel.getCompany_logo())) {
            Utility.universalImageLoaderPicLoading(iv_logo,
                    jobsModel.getCompany_logo(), null, R.drawable.xappie_place_holder);
        } else {
            Utility.universalImageLoaderPicLoading(iv_logo,
                    "", null, R.drawable.xappie_place_holder);
        }



        if (jobsModel.getIsResume().equalsIgnoreCase("1")) {
            btn_submit_resume.setText(Utility.getResourcesString(getActivity(), R.string.submit_resume));
        } else {
            btn_submit_resume.setText(Utility.getResourcesString(getActivity(), R.string.update_status));
        }
    }

    @OnClick({R.id.tv_notification_arrow_back_icon,
            R.id.tv_notification_menu_icon})
    void backToTheHome() {
        mParent.onBackPressed();
    }

    @OnClick(R.id.tv_notifications_icon)
    public void navigateNotification() {
        Utility.navigateDashBoardFragment(new NotificationsFragment(), NotificationsFragment.TAG, null, mParent);
    }

    @OnClick(R.id.tv_language_icon)
    public void navigateLanguage() {
        Utility.navigateDashBoardFragment(new LanguageFragment(), LanguageFragment.TAG, null, mParent);
    }

    @OnClick(R.id.tv_location_icon)
    public void navigateLocation() {
        Utility.navigateDashBoardFragment(new CountriesFragment(), CountriesFragment.TAG, null, mParent);
    }
}