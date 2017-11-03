package com.xappie.fragments;


import android.content.Intent;
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
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.interfaces.IUpdateSelectedFile;
import com.xappie.models.Model;
import com.xappie.parser.LoginParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.io.File;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shankar on 7/28/2017.
 */
public class AddNewEventFragment extends Fragment implements IAsyncCaller, IUpdateSelectedFile {

    public static final String TAG = AddNewEventFragment.class.getSimpleName();
    private DashBoardActivity mParent;

    @BindView(R.id.tv_about_event)
    TextView tv_about_event;
    @BindView(R.id.tv_when_event_taking_place)
    TextView tv_when_event_taking_place;
    @BindView(R.id.tv_where_is_the_event_taking_place)
    TextView tv_where_is_the_event_taking_place;

    @BindView(R.id.edt_name_of_the_event)
    EditText edt_name_of_the_event;
    @BindView(R.id.edt_tag_line)
    EditText edt_tag_line;
    @BindView(R.id.edt_description)
    EditText edt_description;
    @BindView(R.id.edt_upload_image)
    EditText edt_upload_image;
    @BindView(R.id.edt_cost)
    EditText edt_cost;
    @BindView(R.id.edt_dress_code)
    EditText edt_dress_code;
    @BindView(R.id.et_type_of_event)
    EditText et_type_of_event;

    @BindView(R.id.edt_start_time)
    EditText edt_start_time;
    @BindView(R.id.edt_end_time)
    EditText edt_end_time;

    @BindView(R.id.edt_name_of_the_location)
    EditText edt_name_of_the_location;
    @BindView(R.id.edt_address)
    EditText edt_address;


    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.btn_upload)
    Button btn_upload;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceOpenSansBold;
    public static File mYourFile;


    private static IUpdateSelectedFile iUpdateSelectedFile;

    public static IUpdateSelectedFile getInstance() {
        return iUpdateSelectedFile;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        iUpdateSelectedFile = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_new_event, container, false);
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

        tv_about_event.setTypeface(mTypefaceOpenSansBold);
        tv_when_event_taking_place.setTypeface(mTypefaceOpenSansBold);
        tv_where_is_the_event_taking_place.setTypeface(mTypefaceOpenSansBold);

        edt_name_of_the_event.setTypeface(mTypefaceOpenSansRegular);
        edt_tag_line.setTypeface(mTypefaceOpenSansRegular);
        edt_description.setTypeface(mTypefaceOpenSansRegular);
        edt_upload_image.setTypeface(mTypefaceOpenSansRegular);
        edt_cost.setTypeface(mTypefaceOpenSansRegular);
        edt_dress_code.setTypeface(mTypefaceOpenSansRegular);
        et_type_of_event.setTypeface(mTypefaceOpenSansRegular);

        edt_start_time.setTypeface(mTypefaceOpenSansRegular);
        edt_end_time.setTypeface(mTypefaceOpenSansRegular);

        edt_name_of_the_location.setTypeface(mTypefaceOpenSansRegular);
        edt_address.setTypeface(mTypefaceOpenSansRegular);

        btn_submit.setTypeface(mTypefaceOpenSansRegular);
        btn_upload.setTypeface(mTypefaceOpenSansRegular);
    }

    @OnClick(R.id.btn_submit)
    void submitDetails() {
        if (isValidFields()) {
            LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
            paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            paramMap.put("event_name", edt_name_of_the_event.getText().toString());
            paramMap.put("tag_line", edt_tag_line.getText().toString());
            paramMap.put("description", edt_description.getText().toString());
            paramMap.put("cost", edt_cost.getText().toString());
            paramMap.put("dress_code", edt_dress_code.getText().toString());
            paramMap.put("start_time", edt_start_time.getText().toString());
            paramMap.put("end_time", edt_end_time.getText().toString());
            paramMap.put("location", edt_name_of_the_location.getText().toString());
            paramMap.put("address", edt_address.getText().toString());
            paramMap.put("country", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_ID));
            paramMap.put("state", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID));
            paramMap.put("city", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID));
            paramMap.put("photo", Utility.convertFileToByteArray(mYourFile));
            paramMap.put("photo_name", edt_upload_image.getText().toString());
            LoginParser mLoginParser = new LoginParser();
            ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(mParent, Utility.getResourcesString(mParent,
                    R.string.please_wait), true,
                    APIConstants.ADD_EVENT, paramMap,
                    APIConstants.REQUEST_TYPE.POST, this, mLoginParser);
            Utility.execute(serverIntractorAsync);
        }
    }

    /**
     * This method is used to upload
     */
    @OnClick(R.id.btn_upload)
    void eventDialog() {
        showEventDialog();
    }

    /**
     * This method is used to show the edit dialog
     */
    private void showEventDialog() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select event image"), Constants.FROM_POST_FORUM_ADD_EVENT_ID);
    }

    private boolean isValidFields() {
        boolean isValid = true;
        if (Utility.isValueNullOrEmpty(edt_name_of_the_event.getText().toString())) {
            Utility.setSnackBar(mParent, edt_name_of_the_event, "Please enter name of the event");
            edt_name_of_the_event.requestFocus();
            isValid = false;
        }/* else if (edt_tag_line.getText().toString().length() < 4) {
            Utility.setSnackBar(mParent, edt_tag_line, "Please enter tag line");
            edt_tag_line.requestFocus();
            isValid = false;
        } */ else if (edt_description.getText().toString().length() < 4) {
            Utility.setSnackBar(mParent, edt_description, "Please enter description");
            edt_description.requestFocus();
            isValid = false;
        } else if (Utility.isValueNullOrEmpty(edt_upload_image.getText().toString())) {
            Utility.setSnackBar(mParent, edt_upload_image, "Please choose event image");
            edt_upload_image.requestFocus();
            isValid = false;
        } else if (Utility.isValueNullOrEmpty(edt_cost.getText().toString())) {
            Utility.setSnackBar(mParent, edt_cost, "If cost is not there mention it as Zero");
            edt_cost.requestFocus();
            isValid = false;
        } /*else if (Utility.isValueNullOrEmpty(edt_dress_code.getText().toString())) {
            Utility.setSnackBar(mParent, edt_dress_code, "Please enter dress code");
            edt_dress_code.requestFocus();
            isValid = false;
        } */ else if (Utility.isValueNullOrEmpty(edt_start_time.getText().toString())) {
            Utility.setSnackBar(mParent, edt_start_time, "Please enter start time");
            edt_start_time.requestFocus();
            isValid = false;
        } else if (Utility.isValueNullOrEmpty(edt_end_time.getText().toString())) {
            Utility.setSnackBar(mParent, edt_end_time, "Please enter end time");
            edt_end_time.requestFocus();
            isValid = false;
        } else if (Utility.isValueNullOrEmpty(edt_name_of_the_location.getText().toString())) {
            Utility.setSnackBar(mParent, edt_name_of_the_location, "Please enter name of the location");
            edt_name_of_the_location.requestFocus();
            isValid = false;
        } else if (Utility.isValueNullOrEmpty(edt_address.getText().toString())) {
            Utility.setSnackBar(mParent, edt_address, "Please enter address");
            edt_address.requestFocus();
            isValid = false;
        }
        return isValid;
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model.isStatus()) {
                /*if (model instanceof CategoryModel) {

                }*/
            }
        }
    }

    @Override
    public void updateFile(String path) {
        mYourFile = new File(path);
        edt_upload_image.setText(mYourFile.getName());
    }
}
