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
import com.xappie.models.AddClassifiedModel;
import com.xappie.models.ClassifiedUpdateModel;
import com.xappie.models.ClassifiedsModel;
import com.xappie.models.Model;
import com.xappie.parser.ClassifiedSuccessParser;
import com.xappie.parser.ClassifiedUpdateParser;
import com.xappie.parser.ClassifiedsDetailParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.io.File;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shankar on 11/21/2017.
 */

public class ClassifiedsAddFragment extends Fragment implements IAsyncCaller, IUpdateSelectedFile {

    public static final String TAG = ClassifiedsAddFragment.class.getSimpleName();
    private DashBoardActivity mParent;

    @BindView(R.id.edt_title)
    EditText edt_title;

    @BindView(R.id.edt_description)
    EditText edt_description;

    @BindView(R.id.edt_cost)
    EditText edt_cost;
    @BindView(R.id.tv_contact_details)
    TextView tv_contact_details;

    @BindView(R.id.edt_name)
    EditText edt_name;
    @BindView(R.id.edt_mobile)
    EditText edt_mobile;
    @BindView(R.id.edt_email)
    EditText edt_email;
    @BindView(R.id.edt_address)
    EditText edt_address;
    @BindView(R.id.edt_upload_image)
    EditText edt_upload_image;

    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.btn_upload)
    Button btn_upload;
    public static File mYourFile;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceOpenSansBold;
    private AddClassifiedModel addClassifiedModel;
    private ClassifiedsModel classifiedsModel;
    private ClassifiedUpdateModel classifiedUpdateModel;
    private String mID;

    private String mCat_Id;

    private static IUpdateSelectedFile iUpdateSelectedFile;

    public static IUpdateSelectedFile getInstance() {
        return iUpdateSelectedFile;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        iUpdateSelectedFile = this;

        if (getArguments() != null && getArguments().containsKey(Constants.CLASSIFIEDS_CATEGORY_ID)) {
            mCat_Id = getArguments().getString(Constants.CLASSIFIEDS_CATEGORY_ID);
        }

        if (getArguments() != null && getArguments().containsKey(Constants.CLASSIFIEDS_ID)) {
            mID = getArguments().getString(Constants.CLASSIFIEDS_ID);
        } else {
            mID = "";
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_classifieds_add, container, false);
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

        edt_title.setTypeface(mTypefaceOpenSansRegular);
        edt_description.setTypeface(mTypefaceOpenSansRegular);
        edt_cost.setTypeface(mTypefaceOpenSansRegular);

        tv_contact_details.setTypeface(mTypefaceOpenSansRegular);

        edt_name.setTypeface(mTypefaceOpenSansRegular);
        edt_mobile.setTypeface(mTypefaceOpenSansRegular);
        edt_email.setTypeface(mTypefaceOpenSansRegular);
        edt_address.setTypeface(mTypefaceOpenSansRegular);
        edt_upload_image.setTypeface(mTypefaceOpenSansRegular);
        btn_submit.setTypeface(mTypefaceOpenSansRegular);
        btn_upload.setTypeface(mTypefaceOpenSansRegular);

        if (!Utility.isValueNullOrEmpty(mID)) {
            btn_submit.setText("Update");
            getClassifiedDetails();
        }
    }

    private void getClassifiedDetails() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            ClassifiedsDetailParser classifiedsDetailParser = new ClassifiedsDetailParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_CLASSIFIED_DETAILS + "/" + mID, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, classifiedsDetailParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setPreData() {
        if (!Utility.isValueNullOrEmpty(mID) && classifiedsModel != null) {
            edt_title.setText(classifiedsModel.getTitle());
            edt_description.setText(classifiedsModel.getDescription());
            edt_cost.setText(classifiedsModel.getPrice());
            edt_upload_image.setText(classifiedsModel.getImage());
            edt_name.setText(classifiedsModel.getName());
            edt_mobile.setText(classifiedsModel.getMobile());
            edt_email.setText(classifiedsModel.getEmail());
            edt_address.setText(classifiedsModel.getAddress());
        }
    }

    @OnClick(R.id.btn_upload)
    void eventDialog() {
        showEventDialog();
    }

    private void showEventDialog() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mParent.startActivityForResult(Intent.createChooser(intent, "Select Classified image"), Constants.FROM_POST_FORUM_ADD_CLASSIFIEDS_ID);
    }

    @OnClick(R.id.btn_submit)
    void submitDetails() {
        if (isValidFields()) {
            if (!Utility.isValueNullOrEmpty(mID)) {
                LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
                paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
                paramMap.put("id", mID);
                paramMap.put("cat_id", classifiedsModel.getCat_id());
                paramMap.put("title", edt_title.getText().toString());
                paramMap.put("description", edt_description.getText().toString());
                paramMap.put("price", edt_cost.getText().toString());
                paramMap.put("name", edt_name.getText().toString());
                paramMap.put("mobile", edt_mobile.getText().toString());
                paramMap.put("email", edt_email.getText().toString());
                paramMap.put("address", edt_address.getText().toString());
                paramMap.put("country", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_ID));
                paramMap.put("state", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID));
                paramMap.put("city", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID));
                if (mYourFile != null) {
                    paramMap.put("photo", Utility.convertFileToByteArray(mYourFile));
                    paramMap.put("photo_name", edt_upload_image.getText().toString());
                }
                ClassifiedUpdateParser classifiedUpdateParser = new ClassifiedUpdateParser();
                ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(mParent, Utility.getResourcesString(mParent,
                        R.string.please_wait), true,
                        APIConstants.UPDATE_CLASSIFIED, paramMap,
                        APIConstants.REQUEST_TYPE.POST, this, classifiedUpdateParser);
                Utility.execute(serverIntractorAsync);
            } else {
                LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
                paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
                paramMap.put("cat_id", mCat_Id);
                paramMap.put("title", edt_title.getText().toString());
                paramMap.put("description", edt_description.getText().toString());
                paramMap.put("price", edt_cost.getText().toString());
                paramMap.put("name", edt_name.getText().toString());
                paramMap.put("mobile", edt_mobile.getText().toString());
                paramMap.put("email", edt_email.getText().toString());
                paramMap.put("address", edt_address.getText().toString());
                paramMap.put("country", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_ID));
                paramMap.put("state", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID));
                paramMap.put("city", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID));
                if (mYourFile != null) {
                    paramMap.put("photo", Utility.convertFileToByteArray(mYourFile));
                    paramMap.put("photo_name", edt_upload_image.getText().toString());
                }
                ClassifiedSuccessParser classifiedSuccessParser = new ClassifiedSuccessParser();
                ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(mParent, Utility.getResourcesString(mParent,
                        R.string.please_wait), true,
                        APIConstants.ADD_CLASSIFIED, paramMap,
                        APIConstants.REQUEST_TYPE.POST, this, classifiedSuccessParser);
                Utility.execute(serverIntractorAsync);

            }
        }
    }

    private boolean isValidFields() {
        boolean isValid = true;
        if (Utility.isValueNullOrEmpty(edt_title.getText().toString())) {
            Utility.setSnackBar(mParent, edt_title, "Please enter title of the classified");
            edt_title.requestFocus();
            isValid = false;
        } else if (edt_description.getText().toString().length() < 4) {
            Utility.setSnackBar(mParent, edt_description, "Please enter description");
            edt_description.requestFocus();
            isValid = false;
        } else if (Utility.isValueNullOrEmpty(edt_cost.getText().toString())) {
            Utility.setSnackBar(mParent, edt_cost, "If cost is not there mention it as Zero");
            edt_cost.requestFocus();
            isValid = false;
        } else if (Utility.isValueNullOrEmpty(edt_upload_image.getText().toString())) {
            Utility.setSnackBar(mParent, edt_upload_image, "Please choose classified image");
            edt_upload_image.requestFocus();
            isValid = false;
        } else if (Utility.isValueNullOrEmpty(edt_name.getText().toString())) {
            Utility.setSnackBar(mParent, edt_name, "Please Enter Name");
            edt_name.requestFocus();
            isValid = false;
        } else if (Utility.isValueNullOrEmpty(edt_mobile.getText().toString())) {
            Utility.setSnackBar(mParent, edt_mobile, "Please Enter Mobile No");
            edt_mobile.requestFocus();
            isValid = false;
        } else if (Utility.isValueNullOrEmpty(edt_email.getText().toString())) {
            Utility.setSnackBar(mParent, edt_email, "Please Enter Email");
            edt_email.requestFocus();
            isValid = false;
        } else if (Utility.isValueNullOrEmpty(edt_address.getText().toString())) {
            Utility.setSnackBar(mParent, edt_address, "Please Enter Address");
            edt_address.requestFocus();
            isValid = false;
        }
        return isValid;
    }


    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof AddClassifiedModel) {
                addClassifiedModel = (AddClassifiedModel) model;
                Utility.showToastMessage(mParent, addClassifiedModel.getMsg());
                clearData();
            } else if (model instanceof ClassifiedsModel) {
                classifiedsModel = (ClassifiedsModel) model;
                setPreData();
            } else if (model instanceof ClassifiedUpdateModel) {
                classifiedUpdateModel = (ClassifiedUpdateModel) model;
                Utility.showToastMessage(mParent, classifiedUpdateModel.getMessage());
                mParent.onBackPressed();
            }
        }

    }

    private void clearData() {
        edt_title.setText("");
        edt_description.setText("");
        edt_upload_image.setText("");
        mYourFile = null;
        edt_cost.setText("");
        edt_name.setText("");
        edt_email.setText("");
        edt_mobile.setText("");
        edt_address.setText("");
    }

    @Override
    public void updateFile(String path) {
        mYourFile = new File(path);
        edt_upload_image.setText(mYourFile.getName());
    }
}
