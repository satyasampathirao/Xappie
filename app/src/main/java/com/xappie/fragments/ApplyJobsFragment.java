package com.xappie.fragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.customviews.CustomProgressDialog;
import com.xappie.interfaces.IUpdateSelectedFile;
import com.xappie.models.ApplyJobsModel;
import com.xappie.models.Model;
import com.xappie.parser.ApplyJobsParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
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
public class ApplyJobsFragment extends Fragment implements View.OnClickListener, IUpdateSelectedFile, IAsyncCaller {

    public static final String TAG = "ApplyJobsFragment";
    private DashBoardActivity mParent;
    private View rootView;
    private EditText et_cover_letter;
    private EditText edt_upload_resume;
    private TextView btn_upload;
    private Button bt_apply;

    private String id = "";
    private String isResume = "";
    private File yourFile;

    private static IUpdateSelectedFile iUpdateSelectedFile;
    private LinearLayout ll_upload_resume;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;
    private Typeface mTypefaceOpenSansBold;

    @BindView(R.id.tv_notification_arrow_back_icon)
    TextView tv_notification_arrow_back_icon;
    @BindView(R.id.tv_notification_menu_icon)
    TextView tv_notification_menu_icon;

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_location_icon)
    TextView tv_location_icon;
    @BindView(R.id.tv_notifications_icon)
    TextView tv_notifications_icon;
    @BindView(R.id.tv_language_icon)
    TextView tv_language_icon;
    private CustomProgressDialog customProgressDialog;

    public static IUpdateSelectedFile getInstance() {
        return iUpdateSelectedFile;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iUpdateSelectedFile = this;
        mParent = (DashBoardActivity) getActivity();
        Bundle bundle = getArguments();
        id = bundle.getString("id");
        isResume = bundle.getString(Constants.IS_RESUME);
        Utility.sendGoogleAnalytics(mParent, TAG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.invalidateOptionsMenu();
        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.apply_job).toUpperCase());
        rootView = inflater.inflate(R.layout.fragment_apply_jobs, container, false);
        ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    private void initUI() {

        customProgressDialog = new CustomProgressDialog(mParent);

        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);
        mTypefaceOpenSansBold = Utility.getOpenSansBold(mParent);

        tv_notification_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(Utility.getResourcesString(mParent, R.string.apply_job));
        tv_title.setTypeface(mTypefaceOpenSansRegular);

        tv_location_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notifications_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        et_cover_letter = rootView.findViewById(R.id.edt_cover_letter);
        edt_upload_resume = rootView.findViewById(R.id.edt_upload_resume);
        ll_upload_resume = rootView.findViewById(R.id.ll_upload_resume);

        if (isResume.equalsIgnoreCase("0")) {
            ll_upload_resume.setVisibility(View.GONE);
        } else {
            ll_upload_resume.setVisibility(View.VISIBLE);
        }

        btn_upload = (TextView) rootView.findViewById(R.id.btn_upload);
        bt_apply = (Button) rootView.findViewById(R.id.btn_apply);
        btn_upload.setTypeface(Utility.getMaterialIconsRegular(mParent));
        btn_upload.setOnClickListener(this);
        bt_apply.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_apply:
                if (isResume.equalsIgnoreCase("0")) {
                    if (isValidFieldsZero())
                        applyJobZero();
                } else {
                    if (isValidFields())
                        applyJob();
                }

                break;
            case R.id.btn_upload:
                pickFile();
                break;
        }
    }

    private boolean isValidFields() {
        boolean isValidated = false;
        if (Utility.isValueNullOrEmpty(et_cover_letter.getText().toString().trim())) {
            Utility.setSnackBar(mParent, et_cover_letter, "Please enter cover letter");
            et_cover_letter.requestFocus();
        } else if (Utility.isValueNullOrEmpty(edt_upload_resume.getText().toString().trim())) {
            Utility.setSnackBar(mParent, edt_upload_resume, "Please select file for upload resume");
            edt_upload_resume.requestFocus();
        } else {
            isValidated = true;
        }
        return isValidated;
    }

    private boolean isValidFieldsZero() {
        boolean isValidated = false;
        if (Utility.isValueNullOrEmpty(et_cover_letter.getText().toString().trim())) {
            Utility.setSnackBar(mParent, et_cover_letter, "Please enter cover letter");
            et_cover_letter.requestFocus();
        } else {
            isValidated = true;
        }
        return isValidated;
    }

    private void applyJob() {

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if (yourFile != null) {
            File file = new File(yourFile.getPath());
            if (file.exists()) {
                final MediaType MEDIA_TYPE = MediaType.parse(Utility.getMimeType(yourFile.getPath()));
                builder.addFormDataPart("resume", file.getName(), RequestBody.create(MEDIA_TYPE, file));
            } else {
                Log.d(TAG, "file not exist ");
            }
        }
        builder.addFormDataPart(Constants.API_KEY, Constants.API_KEY_VALUE);
        builder.addFormDataPart("message", et_cover_letter.getText().toString());
        builder.addFormDataPart("job_id", id);
        builder.addFormDataPart("status", "1");
        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(APIConstants.APPLY_JOB)
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
                final String jsonData = response.body().string();

                mParent.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utility.showLog("jsondata", "" + jsonData);
                        try {
                            JSONObject mJSONObject = new JSONObject(jsonData);
                            Utility.showToastMessage(mParent, mJSONObject.optString("msg"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mParent.onBackPressed();
                    }
                });
            }
        });


       /* LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        paramMap.put("message", et_cover_letter.getText().toString());
        paramMap.put("file", Utility.convertFileToByteArray(yourFile));
        paramMap.put("file_name", yourFile.getName());
        paramMap.put("job_id", id);
        paramMap.put("status", "1");
        ApplyJobsParser applyJobsParser = new ApplyJobsParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(),
                Utility.getResourcesString(getActivity(),
                        R.string.please_wait), true,
                APIConstants.APPLY_JOB,
                paramMap,
                APIConstants.REQUEST_TYPE.POST,
                this, applyJobsParser);
        Utility.execute(serverIntractorAsync);*/
    }

    private void applyJobZero() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        paramMap.put("message", et_cover_letter.getText().toString());
        paramMap.put("job_id", id);
        paramMap.put("status", "1");
        ApplyJobsParser applyJobsParser = new ApplyJobsParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(),
                Utility.getResourcesString(getActivity(),
                        R.string.please_wait), true,
                APIConstants.APPLY_JOB,
                paramMap,
                APIConstants.REQUEST_TYPE.POST,
                this, applyJobsParser);
        Utility.execute(serverIntractorAsync);
    }

    private void pickFile() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mParent.startActivityForResult(Intent.createChooser(intent, "Select Pdf"), Constants.FROM_FILE_ID);
    }

    @Override
    public void updateFile(String path) {
        //File dir = Environment.getExternalStorageDirectory();
        yourFile = new File(path);
        /*File file = new File(path);*/
        Utility.showLog("path", "" + yourFile.getName());
        Utility.showLog("file", "" + yourFile.getAbsolutePath());
        edt_upload_resume.setText(yourFile.getName());
    }


    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model.isStatus()) {
                if (model instanceof ApplyJobsModel) {
                    ApplyJobsModel mApplyJobsModel = (ApplyJobsModel) model;
                    Utility.showToastMessage(getActivity(), mApplyJobsModel.getMessage());
                    getActivity().onBackPressed();
                }
            }
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

