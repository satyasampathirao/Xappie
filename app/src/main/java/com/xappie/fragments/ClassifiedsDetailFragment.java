package com.xappie.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.ClassifiedsDetailViewPagerAdapter;
import com.xappie.adapters.HomeViewPagerAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.ClassifiedsDetailModel;
import com.xappie.models.Model;
import com.xappie.models.StateModel;
import com.xappie.parser.ClassifiedsDetailParser;
import com.xappie.parser.StatesParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;

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


    @BindView(R.id.tv_date)
    TextView tv_date;

    @BindView(R.id.tv_post_title)
    TextView tv_post_title;
    @BindView(R.id.tv_topic_details)
    TextView tv_topic_details;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.card_pager)
    ViewPager card_pager;
    @BindView(R.id.ll_dots)
    LinearLayout ll_dots;


    @BindView(R.id.tv_person_name)
    TextView tv_person_name;

    @BindView(R.id.tv_phone)
    TextView tv_phone;

    @BindView(R.id.tv_email)
    TextView tv_email;

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
    @BindView(R.id.email_icon)
    TextView email_icon;

    @BindView(R.id.phone_icon)
    TextView phone_icon;

    @BindView(R.id.tv_web_site)
    TextView tv_website;
    @BindView(R.id.tv_web_site_icon)
    TextView tv_web_site_icon;


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
    @BindView(R.id.tv_locality)
    TextView tv_locality;

    @BindView(R.id.tv_close_icon)
    TextView tv_close_icon;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;
    private Typeface mTypefaceMaterialIcon;

    private ClassifiedsDetailModel classifiedsDetailModel;
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
        getClassifiedsData("" + 1);
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
        mTypefaceMaterialIcon = Utility.getMaterialIconsRegular(mParent);

        tv_notification_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(Utility.getResourcesString(mParent, R.string.classifieds));
        tv_title.setTypeface(mTypefaceOpenSansRegular);

        tv_location_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notifications_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);


        tv_date.setTypeface(mTypefaceOpenSansRegular);
        tv_post_title.setTypeface(mTypefaceOpenSansRegular);
        tv_topic_details.setTypeface(mTypefaceOpenSansRegular);
        tv_price.setTypeface(mTypefaceOpenSansRegular);

        tv_person_name.setTypeface(mTypefaceOpenSansRegular);

        tv_phone.setTypeface(mTypefaceOpenSansRegular);
        tv_email.setTypeface(mTypefaceOpenSansRegular);

        tv_address.setTypeface(mTypefaceOpenSansRegular);

        tv_person_icon.setTypeface(mTypefaceMaterialIcon);
        tv_email_icon.setTypeface(mTypefaceMaterialIcon);
        tv_price_icon.setTypeface(mTypefaceMaterialIcon);
        tv_phone_icon.setTypeface(mTypefaceMaterialIcon);
        tv_web_site_icon.setTypeface(mTypefaceMaterialIcon);
        tv_address_icon.setTypeface(mTypefaceMaterialIcon);
        email_icon.setTypeface(mTypefaceMaterialIcon);
        phone_icon.setTypeface(mTypefaceMaterialIcon);
        tv_close_icon.setTypeface(mTypefaceMaterialIcon);
        tv_locality.setTypeface(mTypefaceOpenSansRegular);


        tv_website.setTypeface(mTypefaceOpenSansRegular);
    }

    @OnClick({R.id.tv_notification_arrow_back_icon,
            R.id.tv_notification_menu_icon, R.id.tv_close_icon})
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

    @OnClick(R.id.phone_icon)
    public void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(mParent, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    mParent,
                    new String[]{Manifest.permission.CALL_PHONE},
                    Integer.parseInt("123"));
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + classifiedsDetailModel.getMobile()));
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
                    APIConstants.GET_CLASSIFIED_DETAILS + "/" + mId, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, classifiedsParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof ClassifiedsDetailModel) {
                classifiedsDetailModel = (ClassifiedsDetailModel) model;
                setClasifiedsData();
            } /*else if (model instanceof StatesListModel) {
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
            }*/

        }
    }

    private void setClasifiedsData() {

        if (!Utility.isValueNullOrEmpty(classifiedsDetailModel.getRecordedDate())) {
            tv_date.setText("Posted On" + Utility.displayDateFormat(classifiedsDetailModel.getRecordedDate()));
        } else {
            tv_date.setVisibility(View.GONE);
        }
        if (!Utility.isValueNullOrEmpty(classifiedsDetailModel.getTitle())) {
            tv_post_title.setText(classifiedsDetailModel.getTitle());
        } else {
            tv_post_title.setVisibility(View.GONE);
        }
        if (!Utility.isValueNullOrEmpty(classifiedsDetailModel.getLocality())) {
            tv_locality.setText(classifiedsDetailModel.getLocality());
        } else {
            tv_locality.setVisibility(View.GONE);
        }
        if (!Utility.isValueNullOrEmpty(classifiedsDetailModel.getPrice())) {
            tv_price.setText(classifiedsDetailModel.getPrice());
        } else {
            ll_price.setVisibility(View.GONE);
        }
        if (!Utility.isValueNullOrEmpty(classifiedsDetailModel.getDescription())) {
            tv_topic_details.setText(classifiedsDetailModel.getDescription());
        } else {
            tv_topic_details.setVisibility(View.GONE);
        }
        if (!Utility.isValueNullOrEmpty(classifiedsDetailModel.getAddress())) {
            tv_address.setText(classifiedsDetailModel.getAddress());
        } else {
            ll_address.setVisibility(View.GONE);
        }
        if (!Utility.isValueNullOrEmpty(classifiedsDetailModel.getName())) {
            tv_person_name.setText(classifiedsDetailModel.getName());
        } else {
            ll_name.setVisibility(View.GONE);
        }
        if (!Utility.isValueNullOrEmpty(classifiedsDetailModel.getEmail())) {
            tv_email.setText(classifiedsDetailModel.getEmail());
        } else {
            ll_email_field.setVisibility(View.GONE);
        }
        if (!Utility.isValueNullOrEmpty(classifiedsDetailModel.getMobile())) {
            tv_phone.setText(classifiedsDetailModel.getMobile());
        } else {
            ll_phone_field.setVisibility(View.GONE);
        }
        if (!Utility.isValueNullOrEmpty(classifiedsDetailModel.getWebsite())) {
            tv_website.setText(classifiedsDetailModel.getWebsite());
        } else {
            ll_website.setVisibility(View.GONE);
        }

        email_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{classifiedsDetailModel.getEmail()});


                //need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });

        setViewpagerData();

    }

    int page_position = 0;

    private void setViewpagerData() {
        card_pager.setAdapter(new ClassifiedsDetailViewPagerAdapter(mParent, classifiedsDetailModel.getImages()));

        addBottomDots(0);
        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == classifiedsDetailModel.getImages().size()) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                card_pager.setCurrentItem(page_position, true);
            }
        };

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 100, 4000);
    }

    private void addBottomDots(int currentPage) {
        TextView[] dots = new TextView[classifiedsDetailModel.getImages().size()];
        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(mParent);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(45);
            dots[i].setTextColor(Color.parseColor("#AD343E"));
            ll_dots.addView(dots[i]);
        }
        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#FFFFFF"));
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

