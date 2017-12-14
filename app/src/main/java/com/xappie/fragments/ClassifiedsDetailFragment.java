package com.xappie.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.ClassifiedsListModel;
import com.xappie.models.ClassifiedsModel;
import com.xappie.models.Model;
import com.xappie.models.StateModel;
import com.xappie.models.StatesListModel;
import com.xappie.parser.ClassifiedsDetailParser;
import com.xappie.parser.ClassifiedsParser;
import com.xappie.parser.StatesParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shankar on 7/28/2017.
 */

public class ClassifiedsDetailFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = ClassifiedsDetailFragment.class.getSimpleName();
    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;

    /**
     * Gallery Toolbar
     */
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

    @BindView(R.id.img_person)
    ImageView img_person;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_date)
    TextView tv_date;

    @BindView(R.id.tv_post_title)
    TextView tv_post_title;
    @BindView(R.id.tv_topic_details)
    TextView tv_topic_details;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.img_uploaded)
    ImageView img_uploaded;
    @BindView(R.id.tv_price_text)
    TextView tv_price_text;
    @BindView(R.id.tv_name_text)
    TextView tv_name_text;
    @BindView(R.id.tv_person_name)
    TextView tv_person_name;
    @BindView(R.id.tv_phone_text)
    TextView tv_phone_text;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_email_text)
    TextView tv_email_text;
    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_address_text)
    TextView tv_address_text;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_price_icon)
    TextView tv_price_icon;
    @BindView(R.id.tv_address_icon)
    TextView tv_address_icon;
    @BindView(R.id.tv_email_icon)
    TextView tv_email_icon;
    @BindView(R.id.tv_phone_icon)
    TextView tv_phone_icon;
    @BindView(R.id.tv_person_icon)
    TextView tv_person_icon;
    @BindView(R.id.ll_email)
    LinearLayout ll_email;
    @BindView(R.id.ll_phone)
    LinearLayout ll_phone;
    @BindView(R.id.email_icon)
    TextView email_icon;
    @BindView(R.id.email_text)
    TextView email_text;
    @BindView(R.id.phone_icon)
    TextView phone_icon;
    @BindView(R.id.phone_text)
    TextView phone_text;
    @BindView(R.id.tv_web_site)
    TextView tv_website;
    @BindView(R.id.tv_web_site_icon)
    TextView tv_web_site_icon;
    @BindView(R.id.tv_web_site_text)
    TextView tv_web_site_text;

    @BindView(R.id.ll_price)
    LinearLayout ll_price;
    @BindView(R.id.ll_Name)
    LinearLayout ll_name;
    @BindView(R.id.ll_phone_field)
    LinearLayout ll_phone_field;
    @BindView(R.id.ll_email_field)
    LinearLayout ll_email_field;
    @BindView(R.id.ll_website)
    LinearLayout ll_website;
    @BindView(R.id.ll_address)
    LinearLayout ll_address;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;

    private ClassifiedsListModel classifiedsListModel;
    private StatesListModel mStatesListModel;
    private ClassifiedsModel classifiedsModel;
    private StateModel stateModel;
    private String mId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        mFrameLayout = (FrameLayout) getActivity().findViewById(R.id.content_frame);
        mParams = (CoordinatorLayout.LayoutParams) mFrameLayout.getLayoutParams();
        if (getArguments() != null && getArguments().containsKey(Constants.CLASSIFIEDS_ID)) {
            mId = getArguments().getString(Constants.CLASSIFIEDS_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (appBarLayout != null) {
            mParams.setBehavior(null);
            mFrameLayout.requestLayout();
            appBarLayout.setVisibility(View.GONE);
        }
        View rootView = inflater.inflate(R.layout.fragment_classifieds_detail, container, false);
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
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);

        tv_notification_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(Utility.getResourcesString(mParent, R.string.auto_mobiles));
        tv_title.setTypeface(mTypefaceOpenSansRegular);

        tv_location_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notifications_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_name.setTypeface(mTypefaceOpenSansRegular);
        tv_date.setTypeface(mTypefaceOpenSansRegular);
        tv_post_title.setTypeface(Utility.getOpenSansBold(mParent));
        tv_topic_details.setTypeface(mTypefaceOpenSansRegular);
        tv_price.setTypeface(Utility.getOpenSansBold(mParent));
        tv_price_text.setTypeface(mTypefaceOpenSansRegular);
        tv_person_name.setTypeface(Utility.getOpenSansBold(mParent));
        tv_name_text.setTypeface(mTypefaceOpenSansRegular);
        tv_phone_text.setTypeface(mTypefaceOpenSansRegular);
        tv_phone.setTypeface(Utility.getOpenSansBold(mParent));
        tv_email.setTypeface(Utility.getOpenSansBold(mParent));
        tv_email_text.setTypeface(mTypefaceOpenSansRegular);
        tv_address.setTypeface(Utility.getOpenSansBold(mParent));
        tv_address_text.setTypeface(mTypefaceOpenSansRegular);
        tv_person_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_email_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_price_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_phone_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_web_site_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_address_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        email_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        phone_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        email_text.setTypeface(mTypefaceOpenSansRegular);
        phone_text.setTypeface(mTypefaceOpenSansRegular);
        tv_web_site_text.setTypeface(mTypefaceOpenSansRegular);
        tv_website.setTypeface(Utility.getOpenSansBold(mParent));
    }
    @OnClick({R.id.tv_notification_arrow_back_icon,
            R.id.tv_notification_menu_icon})
    void backToTheHome() {
        mParent.onBackPressed();
    }

    @OnClick(R.id.tv_notifications_icon)
    public void navigateNotification()
    {
        Utility.navigateDashBoardFragment(new NotificationsFragment(),NotificationsFragment.TAG,null,mParent);
    }
    @OnClick(R.id.tv_language_icon)
    public void navigateLanguage()
    {
        Utility.navigateDashBoardFragment(new LanguageFragment(),LanguageFragment.TAG,null,mParent);
    }
    @OnClick(R.id.tv_location_icon)
    public void navigateLocation()
    {
        Utility.navigateDashBoardFragment(new CountriesFragment(),CountriesFragment.TAG,null,mParent);
    }

    @OnClick(R.id.ll_phone)
    public void onCall()
    {
        int permissionCheck = ContextCompat.checkSelfPermission(mParent, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    mParent,
                    new String[]{Manifest.permission.CALL_PHONE},
                    Integer.parseInt("123"));
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + classifiedsModel.getMobile()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void getClassifiedsData(String pageNo) {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            //linkedHashMap.put("type", "Public");
            linkedHashMap.put("country", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_ID));
            linkedHashMap.put("state", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID));
            linkedHashMap.put(Constants.PAGE_NO, pageNo);
            linkedHashMap.put(Constants.PAGE_SIZE, Constants.PAGE_SIZE_VALUE);
            ClassifiedsDetailParser classifiedsParser = new ClassifiedsDetailParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_CLASSIFIED_DETAILS + "/" + mId , linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, classifiedsParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof ClassifiedsModel) {
                classifiedsModel = (ClassifiedsModel) model;
                setClasifiedsData();
            } else if (model instanceof StatesListModel) {
                mStatesListModel = (StatesListModel) model;
                if (mStatesListModel.getStateModels().size() > 0) {
                    for (int i = 0; i < mStatesListModel.getStateModels().size(); i++) {
                        if (Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID)
                                .equalsIgnoreCase(mStatesListModel.getStateModels().get(i).getId())) {
                            stateModel = mStatesListModel.getStateModels().get(i);
                        }
                    }
                    getClassifiedsData("" + 1);
                }
            }

        }
    }

    private void setClasifiedsData() {

        if (!Utility.isValueNullOrEmpty(classifiedsModel.getName()))
        {
            tv_name.setText(classifiedsModel.getName());
        }
        else {
            tv_name.setVisibility(View.GONE);
        }
       if (!Utility.isValueNullOrEmpty(classifiedsModel.getRecordedDate()))
       {
           tv_date.setText(Utility.readDateFormat(classifiedsModel.getRecordedDate()));
       }
       else {
            tv_date.setVisibility(View.GONE);
       }
       if (!Utility.isValueNullOrEmpty(classifiedsModel.getTitle()))
       {
           tv_post_title.setText(classifiedsModel.getTitle());
       }
        else {
            tv_post_title.setVisibility(View.GONE);
       }
       if (!Utility.isValueNullOrEmpty(classifiedsModel.getPrice()))
       {
           tv_price.setText(classifiedsModel.getPrice());
       }
       else {
            ll_price.setVisibility(View.GONE);
       }
       if (!Utility.isValueNullOrEmpty(classifiedsModel.getDescription()))
       {
           tv_topic_details.setText(classifiedsModel.getDescription());
       }
       else {
            tv_topic_details.setVisibility(View.GONE);
       }
       if (!Utility.isValueNullOrEmpty(classifiedsModel.getAddress()))
       {
           tv_address.setText(classifiedsModel.getAddress());
       }
       else {
            ll_address.setVisibility(View.GONE);
       }
       if (!Utility.isValueNullOrEmpty(classifiedsModel.getName()))
       {
           tv_person_name.setText(classifiedsModel.getName());
       }
       else {
            ll_name.setVisibility(View.GONE);
       }
       if (!Utility.isValueNullOrEmpty(classifiedsModel.getEmail()))
       {
           tv_email.setText(classifiedsModel.getEmail());
       }
       else {
            ll_email_field.setVisibility(View.GONE);
       }
       if (!Utility.isValueNullOrEmpty(classifiedsModel.getMobile()))
       {
           tv_phone.setText(classifiedsModel.getMobile());
       }
       else {
            ll_phone_field.setVisibility(View.GONE);
       }
        if (!Utility.isValueNullOrEmpty(classifiedsModel.getWebsite()))
        {
            tv_website.setText(classifiedsModel.getWebsite());
        }
        else {
            ll_website.setVisibility(View.GONE);
        }

        if (!Utility.isValueNullOrEmpty(classifiedsModel.getImage())) {
            Utility.universalImageLoaderPicLoading(img_uploaded,
                    classifiedsModel.getImage(), null, R.drawable.xappie_place_);
        } else {
            Utility.universalImageLoaderPicLoading(img_uploaded,
                    "", null, R.drawable.xappie_place_);
        }

        ll_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ classifiedsModel.getEmail()});


             //need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });


    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 123:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    onCall();
                } else {
                    Log.d("TAG", "Call Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }
    }

