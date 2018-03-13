package com.xappie.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.SpinnerAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.customviews.CustomProgressDialog;
import com.xappie.interfaces.IUpdateSelectedFile;
import com.xappie.models.AddClassifiedModel;
import com.xappie.models.ClassifiedUpdateModel;
import com.xappie.models.ClassifiedsDetailModel;
import com.xappie.models.IAmGoingModel;
import com.xappie.models.Images;
import com.xappie.models.LocalityListModel;
import com.xappie.models.LocalityModel;
import com.xappie.models.Model;
import com.xappie.models.SpinnerModel;
import com.xappie.parser.ClassifiedsDetailParser;
import com.xappie.parser.IAmGoingParser;
import com.xappie.parser.LocalityParser;
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

    @BindView(R.id.edt_locality)
    EditText edt_locality;

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
    @BindView(R.id.edt_website)
    EditText edt_website;

    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.btn_upload)
    TextView btn_upload;

    @BindView(R.id.ll_images_layout)
    LinearLayout ll_images_layout;

    public static File mYourFile;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefacematerialRegular;
    private Typeface mTypefaceOpenSansBold;
    private AddClassifiedModel addClassifiedModel;
    private ClassifiedsDetailModel classifiedsModel;
    private ClassifiedUpdateModel classifiedUpdateModel;
    private String mID;

    private String mCat_Id;
    private String mSubId;

    private ArrayList<String> photosNames;
    private ArrayList<File> photosFiles;

    private static IUpdateSelectedFile iUpdateSelectedFile;
    private CustomProgressDialog customProgressDialog;
    private LocalityListModel mLocalityListModel;
    private ArrayList<LocalityModel> localityModels;

    public static IUpdateSelectedFile getInstance() {
        return iUpdateSelectedFile;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        iUpdateSelectedFile = this;
        Utility.sendGoogleAnalytics(mParent, TAG);

        if (getArguments() != null && getArguments().containsKey(Constants.CLASSIFIEDS_CATEGORY_ID)) {
            mCat_Id = getArguments().getString(Constants.CLASSIFIEDS_CATEGORY_ID);
        }
        if (getArguments() != null && getArguments().containsKey(Constants.CLASSIFIEDS_SUB_CATEGORY_ID)) {
            mSubId = getArguments().getString(Constants.CLASSIFIEDS_SUB_CATEGORY_ID);
        }

        if (getArguments() != null && getArguments().containsKey(Constants.CLASSIFIEDS_ID)) {
            mID = getArguments().getString(Constants.CLASSIFIEDS_ID);
        } else {
            mID = "";
        }
        customProgressDialog = new CustomProgressDialog(mParent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_classifieds_add, container, false);
        ButterKnife.bind(this, rootView);
        photosNames = new ArrayList<>();
        photosFiles = new ArrayList<>();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefacematerialRegular = Utility.getMaterialIconsRegular(mParent);
        mTypefaceOpenSansBold = Utility.getOpenSansBold(mParent);

        edt_title.setTypeface(mTypefaceOpenSansRegular);
        edt_description.setTypeface(mTypefaceOpenSansRegular);
        edt_cost.setTypeface(mTypefaceOpenSansRegular);

        tv_contact_details.setTypeface(mTypefaceOpenSansBold);

        edt_name.setTypeface(mTypefaceOpenSansRegular);
        edt_mobile.setTypeface(mTypefaceOpenSansRegular);
        edt_email.setTypeface(mTypefaceOpenSansRegular);
        edt_website.setTypeface(mTypefaceOpenSansRegular);
        edt_address.setTypeface(mTypefaceOpenSansRegular);
        edt_upload_image.setTypeface(mTypefaceOpenSansRegular);
        btn_submit.setTypeface(mTypefaceOpenSansRegular);
        btn_upload.setTypeface(mTypefacematerialRegular);

        if (!Utility.isValueNullOrEmpty(mID)) {
            btn_submit.setText("Update");
            getClassifiedDetails();
        }

        getLocalitiesList();
    }


    /**
     * This method is used to get the localities list from the server
     */
    private void getLocalitiesList() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            LocalityParser localityParser = new LocalityParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_LOCALITIES + "/" + Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID), linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, localityParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
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
            edt_website.setText(classifiedsModel.getWebsite());
            updateEditUi();
        }
    }

    @OnClick(R.id.btn_upload)
    void eventDialog() {
        showPickAlert();
    }

    private void showPickAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mParent);
        alertDialogBuilder.setMessage("Take Photo");
        alertDialogBuilder.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        showEventDialog();
                    }
                });
        alertDialogBuilder.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        captureFile();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void captureFile() {
        /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mParent.startActivityForResult(intent, Constants.FROM_POST_FORUM_ADD_CLASSIFIEDS_CAMERA_ID);*/
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), "picture.jpg");
        mParent.imageUri = FileProvider.getUriForFile(mParent,
                mParent.getApplicationContext().getPackageName() + ".provider", photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mParent.imageUri);
        mParent.startActivityForResult(intent, Constants.FROM_POST_FORUM_ADD_CLASSIFIEDS_CAMERA_ID);
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
                customProgressDialog.showProgress("");
                updateClassifieds();
            } else {
                customProgressDialog.showProgress("");
                addClassifieds();
            }
        }
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public void updateClassifieds() {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        for (int i = 0; i < classifiedsModel.getImages().size(); i++) {
            if (!classifiedsModel.getImages().get(i).getImage().contains("http")) {
                File file = new File(classifiedsModel.getImages().get(i).getImage());
                if (file.exists()) {
                    if (getMimeType(classifiedsModel.getImages().get(i).getImage()) != null) {
                        final MediaType MEDIA_TYPE = MediaType.parse(getMimeType(file.getPath()));
                        builder.addFormDataPart("photo[]", file.getName(), RequestBody.create(MEDIA_TYPE, file));
                    } else {
                        Utility.showToastMessage(mParent, "File has some problem, Remove and select again");
                    }
                } else {
                    Log.d(TAG, "file not exist ");
                }
            }
        }

        builder.addFormDataPart(Constants.API_KEY, Constants.API_KEY_VALUE);
        builder.addFormDataPart("id", mID);
        builder.addFormDataPart("cat_id", classifiedsModel.getCat_id());
        builder.addFormDataPart("sub_cat_id", classifiedsModel.getSub_cat_id());
        builder.addFormDataPart("title", edt_title.getText().toString());
        builder.addFormDataPart("description", edt_description.getText().toString());
        builder.addFormDataPart("price", edt_cost.getText().toString());
        builder.addFormDataPart("name", edt_name.getText().toString());
        builder.addFormDataPart("mobile", edt_mobile.getText().toString());
        builder.addFormDataPart("website", edt_website.getText().toString());
        builder.addFormDataPart("email", edt_email.getText().toString());
        builder.addFormDataPart("address", edt_address.getText().toString());
        builder.addFormDataPart("country", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_ID));
        builder.addFormDataPart("state", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID));
        builder.addFormDataPart("city", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID));
        builder.addFormDataPart("locality", getLocalityId(edt_locality.getText().toString()));

        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(APIConstants.UPDATE_CLASSIFIED)
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
                Utility.showToastMessage(mParent, "Your classified is uploaded successfully and is in pending for Approval");
                mParent.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        clearData();
                        ClassifiedsTabFragment.tv_my_classifieds.performClick();
                    }
                });
            }
        });
    }

    public void addClassifieds() {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        Utility.showLog("size length", "" + photosFiles.size());
        for (int i = 0; i < photosFiles.size(); i++) {
            File file = new File(photosFiles.get(i).getPath());
            if (file.exists()) {
                final MediaType MEDIA_TYPE = MediaType.parse(getMimeType(photosFiles.get(i).getPath()));
                builder.addFormDataPart("photo[]", file.getName(), RequestBody.create(MEDIA_TYPE, file));
            } else {
                Log.d(TAG, "file not exist ");
            }
        }


        builder.addFormDataPart(Constants.API_KEY, Constants.API_KEY_VALUE);
        builder.addFormDataPart("cat_id", mCat_Id);
        builder.addFormDataPart("sub_cat_id", mSubId);
        builder.addFormDataPart("title", edt_title.getText().toString());
        builder.addFormDataPart("description", edt_description.getText().toString());
        builder.addFormDataPart("price", edt_cost.getText().toString());
        builder.addFormDataPart("name", edt_name.getText().toString());
        builder.addFormDataPart("mobile", edt_mobile.getText().toString());
        builder.addFormDataPart("website", edt_website.getText().toString());
        builder.addFormDataPart("email", edt_email.getText().toString());
        builder.addFormDataPart("address", edt_address.getText().toString());
        builder.addFormDataPart("country", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_ID));
        builder.addFormDataPart("state", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID));
        builder.addFormDataPart("city", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID));
        builder.addFormDataPart("locality", getLocalityId(edt_locality.getText().toString()));

        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(APIConstants.ADD_CLASSIFIED)
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
                Utility.showToastMessage(mParent, "Your classified is uploaded successfully and is in pending for Approval");
                String jsonData = response.body().string();
                Utility.showLog("jsondata", "" + jsonData);
                mParent.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        clearData();
                        ClassifiedsTabFragment.tv_my_classifieds.performClick();
                    }
                });
            }
        });
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
        } else if (Utility.isValueNullOrEmpty(edt_locality.getText().toString())) {
            Utility.setSnackBar(mParent, edt_locality, "Please select locality");
            edt_locality.requestFocus();
            isValid = false;
        } else if (Utility.isValueNullOrEmpty(mID) && Utility.isValueNullOrEmpty(edt_upload_image.getText().toString())) {
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
            } else if (model instanceof ClassifiedsDetailModel) {
                classifiedsModel = (ClassifiedsDetailModel) model;
                setPreData();
            } else if (model instanceof ClassifiedUpdateModel) {
                classifiedUpdateModel = (ClassifiedUpdateModel) model;
                Utility.showToastMessage(mParent, classifiedUpdateModel.getMessage());
                mParent.onBackPressed();
            } else if (model instanceof LocalityListModel) {
                mLocalityListModel = (LocalityListModel) model;
                if (mLocalityListModel.getLocalityModels().size() == 0) {
                    Utility.showToastMessage(mParent, Utility.getResourcesString(mParent, R.string.no_localities_found));
                    Intent intent = new Intent(mParent, DashBoardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    localityModels = mLocalityListModel.getLocalityModels();
                }
            } else if (model instanceof IAmGoingModel) {
                Utility.showToastMessage(mParent, "Image deleted");
            }
        }

    }

    private String getLocalityId(String s) {
        String mLocalityId = "";
        for (int i = 0; i < localityModels.size(); i++) {
            if (localityModels.get(i).getName().equals(s)) {
                mLocalityId = localityModels.get(i).getId();
            }
        }
        return mLocalityId;
    }

    @OnClick(R.id.edt_locality)
    void setDataToTheSpinner() {
        if (localityModels != null && localityModels.size() > 0)
            showSpinnerDialog(mParent, "Select Locality", edt_locality, mLocalityListModel.getSpinnerModels(), 1);
    }

    private void showSpinnerDialog(DashBoardActivity mParent, String title, final EditText edt_locality, ArrayList<SpinnerModel> spinnerModels, final int id) {
        android.app.AlertDialog.Builder builderSingle = new android.app.AlertDialog.Builder(mParent);

        /*CUSTOM TITLE*/
        LayoutInflater inflater = (LayoutInflater) mParent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_alert_dialog_title, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_alert_dialog_title);
        RelativeLayout dialog_back_ground = (RelativeLayout) view.findViewById(R.id.dialog_back_ground);
        dialog_back_ground.setBackgroundColor(mParent.getResources().getColor(R.color.colorPrimary));
        tv_title.setText(title);
        tv_title.setTextColor(mParent.getResources().getColor(R.color.blackColor));
        builderSingle.setCustomTitle(view);


        final SpinnerAdapter adapter = new SpinnerAdapter(mParent, spinnerModels);
        builderSingle.setAdapter(adapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SpinnerModel mData = (SpinnerModel) adapter.getItem(which);
                        if (id == 1) {
                            String text = mData.getTitle();
                            edt_locality.setText(text);
                        }
                    }
                });
        builderSingle.show();
    }

    private void clearData() {
        edt_title.setText("");
        edt_description.setText("");
        edt_upload_image.setText("");
        mYourFile = null;
        photosNames = new ArrayList<>();
        photosFiles = new ArrayList<>();
        edt_cost.setText("");
        edt_name.setText("");
        edt_email.setText("");
        edt_mobile.setText("");
        edt_website.setText("");
        edt_address.setText("");
        ll_images_layout.removeAllViews();
    }

    @Override
    public void updateFile(String path) {
        mYourFile = new File(path);
        edt_upload_image.setText(mYourFile.getName());

        if (!Utility.isValueNullOrEmpty(mID)) {
            if (classifiedsModel.getImages().size() < 5) {
                ll_images_layout.setVisibility(View.VISIBLE);
                Images images = new Images();
                images.setImage(path);
                classifiedsModel.getImages().add(images);
                updateEditUi();
            } else
                Utility.showToastMessage(mParent, "You already added Five, Remove and and new one");
        } else {
            if (photosNames.size() < 5) {
                ll_images_layout.setVisibility(View.VISIBLE);
                photosNames.add(mYourFile.getName());
                photosFiles.add(mYourFile);
                updateUi();
            } else
                Utility.showToastMessage(mParent, "You already added Five, Remove and and new one");
        }
    }

    private void updateEditUi() {
        ll_images_layout.removeAllViews();
        for (int i = 0; i < classifiedsModel.getImages().size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.classifieds_images_item, null);
            LinearLayout classifieds_plus_item = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.classifieds_plus_item, null);
            TextView add_icon = classifieds_plus_item.findViewById(R.id.add_icon);

            ImageView img_video_image = ll.findViewById(R.id.img_video_image);
            TextView img_close = ll.findViewById(R.id.img_close);

            img_close.setTypeface(mTypefacematerialRegular);
            add_icon.setTypeface(mTypefacematerialRegular);
            if (classifiedsModel.getImages().get(i).getImage().contains("http")) {
                if (!Utility.isValueNullOrEmpty(classifiedsModel.getImages().get(i).getImage()))
                    Picasso.with(mParent).load(classifiedsModel.getImages().get(i).getImage())
                            .resize(300, 300)
                            .placeholder(Utility.getDrawable(mParent, R.drawable.xappie_place_holder))
                            .into(img_video_image);
                else {
                    Utility.universalImageLoaderPicLoading(img_video_image,
                            "", null, R.drawable.xappie_place_holder);
                }
            } else {
                String decodedImgUri = Uri.fromFile(new File(classifiedsModel.getImages().get(i).getImage())).toString();
                if (!Utility.isValueNullOrEmpty(decodedImgUri))
                    Picasso.with(mParent).load(decodedImgUri)
                            .resize(300, 300)
                            .placeholder(Utility.getDrawable(mParent, R.drawable.xappie_place_holder))
                            .into(img_video_image);
                else {
                    Utility.universalImageLoaderPicLoading(img_video_image,
                            "", null, R.drawable.xappie_place_holder);
                }
            }
            img_close.setId(i);
            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = view.getId();
                    Images images = classifiedsModel.getImages().get(position);
                    classifiedsModel.getImages().remove(position);
                    if (images.getImage().contains("http")) {
                        deleteFromServer(images.getId());
                    }
                    updateEditUi();
                }
            });

            classifieds_plus_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPickAlert();
                }
            });

            ll_images_layout.addView(ll);
            if (photosFiles.size() == 5) {

            } else {
                if (i + 1 == photosFiles.size())
                    ll_images_layout.addView(classifieds_plus_item);
            }
        }
    }

    /**
     * This method is used to delete image from the server
     */
    private void deleteFromServer(String delete_id) {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            IAmGoingParser eventsListParser = new IAmGoingParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.DELETE_CLASSIFIED_IMAGE + delete_id, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, eventsListParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUi() {
        ll_images_layout.removeAllViews();
        for (int i = 0; i < photosFiles.size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.classifieds_images_item, null);
            LinearLayout classifieds_plus_item = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.classifieds_plus_item, null);
            TextView add_icon = classifieds_plus_item.findViewById(R.id.add_icon);

            ImageView img_video_image = ll.findViewById(R.id.img_video_image);
            TextView img_close = ll.findViewById(R.id.img_close);

            img_close.setTypeface(mTypefacematerialRegular);
            add_icon.setTypeface(mTypefacematerialRegular);
            String decodedImgUri = Uri.fromFile(new File(photosFiles.get(i).getPath())).toString();
            if (!Utility.isValueNullOrEmpty(decodedImgUri))
                Picasso.with(mParent).load(decodedImgUri)
                        .resize(300, 300)
                        .placeholder(Utility.getDrawable(mParent, R.drawable.xappie_place_holder))
                        .into(img_video_image);
            else {
                Utility.universalImageLoaderPicLoading(img_video_image,
                        "", null, R.drawable.xappie_place_holder);
            }
            img_close.setId(i);
            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = view.getId();
                    photosNames.remove(position);
                    photosFiles.remove(position);
                    updateUi();
                }
            });

            classifieds_plus_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPickAlert();
                }
            });

            ll_images_layout.addView(ll);
            if (photosFiles.size() == 5) {

            } else {
                if (i + 1 == photosFiles.size())
                    ll_images_layout.addView(classifieds_plus_item);
            }
        }
    }
}
