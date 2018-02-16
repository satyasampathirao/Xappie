package com.xappie.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.SpinnerAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.customviews.CustomProgressDialog;
import com.xappie.interfaces.IUpdateSelectedFile;
import com.xappie.models.CategoryModel;
import com.xappie.models.JobPostingModel;
import com.xappie.models.JobUpdateModel;
import com.xappie.models.JobsModel;
import com.xappie.models.Model;
import com.xappie.models.SpinnerModel;
import com.xappie.parser.CategoryParser;
import com.xappie.parser.JobPostingParser;
import com.xappie.parser.JobsDetailParser;
import com.xappie.parser.JobsUpdateParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostJobFragment extends Fragment implements IAsyncCaller, IUpdateSelectedFile {
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
    EditText et_job_category;
    @BindView(R.id.tv_resume_required)
    TextView tv_resume_required;

    @BindView(R.id.radio_group_resume)
    RadioGroup radio_group_resume;
    @BindView(R.id.radio_yes_send)
    RadioButton rb_yes_send;
    @BindView(R.id.radio_no_send)
    RadioButton rb_no_send;
    @BindView(R.id.edt_yes_email)
    EditText edt_yes_email;

    @BindView(R.id.btn_submit)
    Button btn_submit;
    private String mID;

    private String mCat_Id;

    private CategoryModel categoryModel;
    private JobPostingModel jobPostingModel;
    private JobUpdateModel jobUpdateModel;
    private JobsModel jobsModel;

    private Typeface mTypefaceOpenSansRegular;

    private File yourFile;
    private static IUpdateSelectedFile iUpdateSelectedFile;
    private CustomProgressDialog customProgressDialog;

    public static IUpdateSelectedFile getInstance() {
        return iUpdateSelectedFile;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        iUpdateSelectedFile = this;
        Utility.sendGoogleAnalytics(mParent, TAG);
        if (getArguments() != null && getArguments().containsKey(Constants.JOBS_CATEGORY_ID)) {
            mCat_Id = getArguments().getString(Constants.JOBS_CATEGORY_ID);
        }

        if (getArguments() != null && getArguments().containsKey(Constants.JOBS_ID)) {
            mID = getArguments().getString(Constants.JOBS_ID);
        } else {
            mID = "";
        }
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
        customProgressDialog = new CustomProgressDialog(mParent);
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);

        edt_job_title.setTypeface(mTypefaceOpenSansRegular);
        edt_company_logo.setTypeface(mTypefaceOpenSansRegular);
        edt_company_name.setTypeface(mTypefaceOpenSansRegular);
        edt_eligibility.setTypeface(mTypefaceOpenSansRegular);
        edt_experience.setTypeface(mTypefaceOpenSansRegular);
        et_job_category.setTypeface(mTypefaceOpenSansRegular);
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

        rb_yes_send.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    edt_yes_email.setVisibility(View.VISIBLE);
                } else {
                    edt_yes_email.setVisibility(View.GONE);
                }
            }
        });

        if (!Utility.isValueNullOrEmpty(mID)) {
            btn_submit.setText("Update");
            getJobsDetails();
        }

        getJobCategoryData();
    }

    private void getJobsDetails()
    {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            JobsDetailParser jobsDetailParser = new JobsDetailParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_JOB_DETAILS + "/" + mID, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, jobsDetailParser);
            Utility.execute(serverJSONAsyncTask);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to get the jobs category
     */
    private void getJobCategoryData() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        CategoryParser mCategoryParser = new CategoryParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(),
                Utility.getResourcesString(getActivity(),
                        R.string.please_wait), true,
                APIConstants.JOB_CATEGORY_URL, paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mCategoryParser);
        Utility.execute(serverIntractorAsync);
    }

    private boolean isValidFields() {
        boolean isValidated = false;
        if (Utility.isValueNullOrEmpty(edt_job_title.getText().toString().trim())) {
            Utility.setSnackBar(mParent, edt_job_title, "Please enter your Job Title");
            edt_job_title.requestFocus();
        } else if (Utility.isValueNullOrEmpty(edt_no_of_positions.getText().toString().trim())) {
            Utility.setSnackBar(mParent, edt_no_of_positions, "Please enter Number of Positions");
            edt_no_of_positions.requestFocus();
        } else if (Utility.isValueNullOrEmpty(edt_job_role.getText().toString().trim())) {
            Utility.setSnackBar(mParent, edt_job_role, "Please enter your Job Role");
            edt_job_role.requestFocus();
        } else if (Utility.isValueNullOrEmpty(edt_company_name.getText().toString().trim())) {
            Utility.setSnackBar(mParent, edt_company_name, "Please enter Company Name");
            edt_company_name.requestFocus();
        } /*else if (Utility.isValueNullOrEmpty(edt_company_logo.getText().toString().trim())) {
            Utility.setSnackBarEnglish(mParent, edt_company_logo, "Please enter Company Logo");
            edt_company_logo.requestFocus();
        }  else if (Utility.isValueNullOrEmpty(edt_website.getText().toString().trim())) {
            Utility.setSnackBarEnglish(mParent, edt_website, "Please Enter Website Name");
            edt_website.requestFocus();
        }else if (Utility.isValueNullOrEmpty(edt_eligibility.getText().toString().trim())) {
            Utility.setSnackBarEnglish(mParent, edt_eligibility, "Please Eligibility");
            edt_eligibility.requestFocus();
        } else if (Utility.isValueNullOrEmpty(edt_experience.getText().toString().trim())) {
            Utility.setSnackBar(mParent, edt_experience, "Please Your Experience");
            edt_experience.requestFocus();
        }*/ else if (Utility.isValueNullOrEmpty(edt_job_location.getText().toString().trim())) {
            Utility.setSnackBar(mParent, edt_job_location, "Please Your Job Location");
            edt_job_location.requestFocus();
        } else if (Utility.isValueNullOrEmpty(edt_job_description.getText().toString().trim())) {
            Utility.setSnackBar(mParent, edt_job_description, "Please Job Description");
            edt_job_description.requestFocus();
        } else if (Utility.isValueNullOrEmpty(et_job_category.getText().toString().trim())) {
            Utility.setSnackBar(mParent, et_job_category, "Please Select Category");
            et_job_category.requestFocus();
        } else if (rb_yes_send.isChecked() && Utility.isValueNullOrEmpty(edt_yes_email.getText().toString().trim())) {
            Utility.setSnackBar(mParent, edt_yes_email, "Please enter email id");
            edt_yes_email.requestFocus();
        } else {
            isValidated = true;
        }
        return isValidated;
    }

   /* @OnClick(R.id.btn_submit)
    public void navigateSubmit() {
        if (isValidFields()) {
            postJobInServer();
        }
    }*/

    @OnClick(R.id.btn_submit)
    void submitDetails() {
        if (isValidFields()) {
            if (!Utility.isValueNullOrEmpty(mID)) {
                customProgressDialog.showProgress("");
                updateJobs();
            } else {
                customProgressDialog.showProgress("");
                addJobs();
            }
        }
    }

    private void updateJobs() {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if (yourFile != null) {
            File file = new File(yourFile.getPath());
            if (file.exists()) {
                final MediaType MEDIA_TYPE = MediaType.parse(Utility.getMimeType(yourFile.getPath()));
                builder.addFormDataPart("logo", file.getName(), RequestBody.create(MEDIA_TYPE, file));
            } else {
                Log.d(TAG, "file not exist ");
            }
        }
        builder.addFormDataPart(Constants.API_KEY, Constants.API_KEY_VALUE);
        builder.addFormDataPart("id", mID);
        builder.addFormDataPart("title", edt_job_title.getText().toString());
        builder.addFormDataPart("position", edt_no_of_positions.getText().toString());
        builder.addFormDataPart("role", edt_job_role.getText().toString());
        builder.addFormDataPart("company_name", edt_company_name.getText().toString());
        //paramMap.put("website", edt_website.getText().toString());
        builder.addFormDataPart("eligible", edt_eligibility.getText().toString());
        //paramMap.put("exp", edt_experience.getText().toString());
        builder.addFormDataPart("description", edt_job_description.getText().toString());
        builder.addFormDataPart("category", getCategoryId(et_job_category.getText().toString()));
        builder.addFormDataPart("address", edt_job_location.getText().toString());

        if (rb_yes_send.isChecked()) {
            builder.addFormDataPart("resume", "1");
            builder.addFormDataPart("email", edt_yes_email.getText().toString());
        } else {
            builder.addFormDataPart("resume", "0");
            builder.addFormDataPart("email", "");
        }
        builder.addFormDataPart("country", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_ID));
        builder.addFormDataPart("state", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID));
        builder.addFormDataPart("city", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID));
        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(APIConstants.UPDATE_JOB)
                .addHeader("Cookie", "ci_session=" + Utility.getSharedPrefStringData(mParent, Constants.LOGIN_SESSION_ID) + ";")
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient.Builder().build();

        Call call = client.newCall(request);


        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                customProgressDialog.dismissProgress();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                customProgressDialog.dismissProgress();
                String jsonData = response.body().string();
                Utility.showLog("jsondata", "" + jsonData);
                Utility.showToastMessage(mParent, "Your event is uploaded successfully and is in pending for Approval");
                mParent.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        clearData();
                        JobsFragment.tv_my_posts.performClick();
                    }
                });
            }
        });
    }

    private void addJobs() {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if (yourFile != null) {
            File file = new File(yourFile.getPath());
            if (file.exists()) {
                final MediaType MEDIA_TYPE = MediaType.parse(Utility.getMimeType(yourFile.getPath()));
                builder.addFormDataPart("logo", file.getName(), RequestBody.create(MEDIA_TYPE, file));
            } else {
                Log.d(TAG, "file not exist ");
            }
        }
        builder.addFormDataPart(Constants.API_KEY, Constants.API_KEY_VALUE);
        builder.addFormDataPart("title", edt_job_title.getText().toString());
        builder.addFormDataPart("position", edt_no_of_positions.getText().toString());
        builder.addFormDataPart("role", edt_job_role.getText().toString());
        builder.addFormDataPart("company_name", edt_company_name.getText().toString());
        //paramMap.put("website", edt_website.getText().toString());
        builder.addFormDataPart("eligible", edt_eligibility.getText().toString());
        //paramMap.put("exp", edt_experience.getText().toString());
        builder.addFormDataPart("description", edt_job_description.getText().toString());
        builder.addFormDataPart("category", getCategoryId(et_job_category.getText().toString()));
        builder.addFormDataPart("address", edt_job_location.getText().toString());

        if (rb_yes_send.isChecked()) {
            builder.addFormDataPart("resume", "1");
            builder.addFormDataPart("email", edt_yes_email.getText().toString());
        } else {
            builder.addFormDataPart("resume", "0");
            builder.addFormDataPart("email", "");
        }
        builder.addFormDataPart("country", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_ID));
        builder.addFormDataPart("state", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID));
        builder.addFormDataPart("city", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID));
        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(APIConstants.POST_JOB_URL)
                .addHeader("Cookie", "ci_session=" + Utility.getSharedPrefStringData(mParent, Constants.LOGIN_SESSION_ID) + ";")
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient.Builder().build();

        Call call = client.newCall(request);


        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                customProgressDialog.dismissProgress();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                customProgressDialog.dismissProgress();
                String jsonData = response.body().string();
                Utility.showLog("jsondata", "" + jsonData);
                Utility.showToastMessage(mParent, "Your event is uploaded successfully and is in pending for Approval");
                mParent.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        clearData();
                        JobsFragment.tv_my_posts.performClick();
                    }
                });
            }
        });
    }

    private void postJobInServer() {
        if (!Utility.isValueNullOrEmpty(mID)) {
            LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
            paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            paramMap.put("id", mID);
            paramMap.put("title", edt_job_title.getText().toString());
            paramMap.put("position", edt_no_of_positions.getText().toString());
            paramMap.put("role", edt_job_role.getText().toString());
            paramMap.put("company_name", edt_company_name.getText().toString());
            paramMap.put("eligible", edt_eligibility.getText().toString());
            paramMap.put("description", edt_job_description.getText().toString());
            paramMap.put("category", getCategoryId(et_job_category.getText().toString()));
            paramMap.put("address", edt_job_location.getText().toString());
            if (rb_yes_send.isChecked()) {
                paramMap.put("resume", "1");
                paramMap.put("email", edt_yes_email.getText().toString());
            } else {
                paramMap.put("resume", "0");
                paramMap.put("email", "");
            }
            if (yourFile != null) {
                paramMap.put("logo_name", edt_company_logo.getText().toString());
                paramMap.put("logo", Utility.convertFileToByteArray(yourFile));
            }
            paramMap.put("country", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_ID));
            paramMap.put("state", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID));
            paramMap.put("city", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID));
            JobsUpdateParser mJobPostingParser = new JobsUpdateParser();
            ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(),
                    Utility.getResourcesString(getActivity(),
                            R.string.please_wait), true,
                    APIConstants.UPDATE_JOB,
                    paramMap,
                    APIConstants.REQUEST_TYPE.POST,
                    this, mJobPostingParser);
            Utility.execute(serverIntractorAsync);

        }
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        paramMap.put("title", edt_job_title.getText().toString());
        paramMap.put("position", edt_no_of_positions.getText().toString());
        paramMap.put("role", edt_job_role.getText().toString());
        paramMap.put("company_name", edt_company_name.getText().toString());
        //paramMap.put("website", edt_website.getText().toString());
        paramMap.put("eligible", edt_eligibility.getText().toString());
        //paramMap.put("exp", edt_experience.getText().toString());
        paramMap.put("description", edt_job_description.getText().toString());
        paramMap.put("category", getCategoryId(et_job_category.getText().toString()));
        paramMap.put("address", edt_job_location.getText().toString());

        if (rb_yes_send.isChecked()) {
            paramMap.put("resume", "1");
            paramMap.put("email", edt_yes_email.getText().toString());
        } else {
            paramMap.put("resume", "0");
            paramMap.put("email", "");
        }
        if (yourFile != null) {
            paramMap.put("logo_name", edt_company_logo.getText().toString());
            paramMap.put("logo", Utility.convertFileToByteArray(yourFile));
        }
        paramMap.put("country", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_ID));
        paramMap.put("state", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID));
        paramMap.put("city", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID));
        JobPostingParser mJobPostingParser = new JobPostingParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(),
                Utility.getResourcesString(getActivity(),
                        R.string.please_wait), true,
                APIConstants.POST_JOB_URL,
                paramMap,
                APIConstants.REQUEST_TYPE.POST,
                this, mJobPostingParser);
        Utility.execute(serverIntractorAsync);
    }

    @OnClick(R.id.btn_upload)
    public void navigateUpload() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mParent.startActivityForResult(Intent.createChooser(intent, "Select Company logo"), Constants.FROM_POSTING_LOGO_FILE_ID);
    }

    private String getCategoryId(String s) {
        String mCategoryId = "";
        for (int i = 0; i < categoryModel.getCategoryModels().size(); i++) {
            if (categoryModel.getCategoryModels().get(i).getName().equals(s)) {
                mCategoryId = categoryModel.getCategoryModels().get(i).getId();
            }
        }
        return mCategoryId;
    }

    @OnClick(R.id.et_job_category)
    void setDataToTheSpinner() {
        if (categoryModel != null)
            showSpinnerDialog(getActivity(), "Job Category", et_job_category, categoryModel.getmCategorySpinnerModels(), 1);
    }

    public void showSpinnerDialog(final Context context, final String title, final EditText et_spinner,
                                  ArrayList<SpinnerModel> itemsList, final int id) {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);

        /*CUSTOM TITLE*/
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_alert_dialog_title, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_alert_dialog_title);
        RelativeLayout dialog_back_ground = (RelativeLayout) view.findViewById(R.id.dialog_back_ground);
        dialog_back_ground.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        tv_title.setText(title);
        tv_title.setTextColor(context.getResources().getColor(R.color.blackColor));
        builderSingle.setCustomTitle(view);


        final SpinnerAdapter adapter = new SpinnerAdapter(context, itemsList);
        builderSingle.setAdapter(adapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SpinnerModel mData = (SpinnerModel) adapter.getItem(which);
                        if (id == 1) {
                            String text = mData.getTitle();
                            et_spinner.setText(text);
                        }
                    }
                });
        builderSingle.show();
    }


    @Override
    public void onComplete(Model model) {
        if (model != null) {
           // if (model.isStatus()) {
                if (model instanceof CategoryModel) {
                    categoryModel = (CategoryModel) model;
                } else if (model instanceof JobPostingModel) {
                    jobPostingModel = (JobPostingModel) model;
                   // Utility.showToastMessage(getActivity(), jobPostingModel.getMessage());
                    Utility.showToastMessage(mParent, "Your jobPost is uploaded successfully and is in pending for Approval");
                    clearData();
                    JobsFragment.tv_my_posts.performClick();
                }
                else if (model instanceof JobsModel) {
                    jobsModel = (JobsModel) model;
                    setPreData();
                } else if (model instanceof JobUpdateModel) {
                    jobUpdateModel = (JobUpdateModel) model;
                  //  Utility.showToastMessage(mParent, jobUpdateModel.getMessage());
                    mParent.onBackPressed();
                }
           // } else {
             //   Utility.showToastMessage(mParent, "OOPS..! Some problem with the API");
           // }
        }
    }

    private void clearData()
    {
        edt_job_title.setText("");
        edt_no_of_positions.setText("");
        edt_job_role.setText("");
        edt_job_location.setText("");
        edt_job_description.setText("");
        edt_company_name.setText("");
        edt_company_logo.setText("");
    }

    private void setPreData()
    {
        if (!Utility.isValueNullOrEmpty(mID) && jobsModel != null) {
            edt_company_logo.setText(jobsModel.getCompany_logo());
            edt_company_name.setText(jobsModel.getCompany());
            edt_job_description.setText(jobsModel.getDescription());
            edt_job_location.setText(jobsModel.getAddress());
            edt_job_role.setText(jobsModel.getRole());
            edt_no_of_positions.setText(jobsModel.getPositions());
            edt_job_title.setText(jobsModel.getTitle());
        }
    }
    @Override
    public void updateFile(String path) {
        yourFile = new File(path);
        edt_company_logo.setText(yourFile.getName());
    }

}