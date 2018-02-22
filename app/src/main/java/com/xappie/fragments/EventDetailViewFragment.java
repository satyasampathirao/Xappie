package com.xappie.fragments;


import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.activities.LoginActivity;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.EventsModel;
import com.xappie.models.IAmGoingModel;
import com.xappie.models.Model;
import com.xappie.parser.EventsDetailParser;
import com.xappie.parser.IAmGoingParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shankar on 7/28/2017.
 */
public class EventDetailViewFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = EventDetailViewFragment.class.getSimpleName();
    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;

    /**
     * Event Detail
     */
    @BindView(R.id.tv_notification_arrow_back_icon)
    TextView tv_notification_arrow_back_icon;
    @BindView(R.id.tv_notification_menu_icon)
    TextView tv_notification_menu_icon;

    @BindView(R.id.img_event)
    ImageView img_event;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_location_icon)
    TextView tv_location_icon;
    @BindView(R.id.tv_notifications_icon)
    TextView tv_notifications_icon;
    @BindView(R.id.tv_language_icon)
    TextView tv_language_icon;


    @BindView(R.id.tv_event_name)
    TextView tv_event_name;
    @BindView(R.id.tv_date_time)
    TextView tv_date_time;
    @BindView(R.id.tv_dress_code)
    TextView tv_dress_code;
    @BindView(R.id.tv_dress_code_value)
    TextView tv_dress_code_value;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_date_location_icon)
    TextView tv_date_location_icon;
    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;

    @BindView(R.id.tv_event_tag_line_text_comes_here)
    TextView tv_event_tag_line_text_comes_here;
    @BindView(R.id.tv_total_cost)
    TextView tv_total_cost;

    @BindView(R.id.tv_details)
    TextView tv_details;
    @BindView(R.id.tv_a_weekly_desi)
    TextView tv_a_weekly_desi;
    @BindView(R.id.tv_date_calendar_icon)
    TextView tv_date_calendar_icon;
    @BindView(R.id.tv_end_date_time)
    TextView tv_end_date_time;
    @BindView(R.id.tv_price_icon)
    TextView tv_price_icon;

    @BindView(R.id.tv_web_site)
    TextView tv_web_site;
    @BindView(R.id.tv_website_link)
    TextView tv_website_link;
    @BindView(R.id.tv_facebook_icon)
    TextView tv_facebook_icon;
    @BindView(R.id.tv_fb_url)
    TextView tv_fb_url;
    @BindView(R.id.tv_tickets)
    TextView tv_tickets;
    @BindView(R.id.tv_locality)
    TextView tv_locality;
    @BindView(R.id.btn_i_am_going)
    TextView btn_i_am_going;
    @BindView(R.id.btn_may_be)
    TextView btn_may_be;
    @BindView(R.id.btn_who_is_going)
    TextView btn_who_is_going;


    @BindView(R.id.ll_i_am_going_may_be_going)
    LinearLayout ll_i_am_going_may_be_going;
    @BindView(R.id.ll_reverse_layout)
    LinearLayout ll_reverse_layout;

    @BindView(R.id.tv_going)
    TextView tv_going;
    @BindView(R.id.tv_may)
    TextView tv_may;
    @BindView(R.id.tv_not)
    TextView tv_not;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;
    private Typeface mTypefaceOpenSansBold;

    private String mId;
    private EventsModel eventsModel;
    private IAmGoingModel iAmGoingModel;

    private String mFromNot = "0";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        mFrameLayout = (FrameLayout) getActivity().findViewById(R.id.content_frame);
        mParams = (CoordinatorLayout.LayoutParams) mFrameLayout.getLayoutParams();
        Utility.sendGoogleAnalytics(mParent, TAG);
        if (getArguments() != null && getArguments().containsKey(Constants.EVENT_ID)) {
            mId = getArguments().getString(Constants.EVENT_ID);
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
        View rootView = inflater.inflate(R.layout.fragment_event_detail_view, container, false);
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

    private void setTypeFace() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);
        mTypefaceOpenSansBold = Utility.getOpenSansBold(mParent);

        tv_notification_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(Utility.getResourcesString(mParent, R.string.events));
        tv_title.setTypeface(mTypefaceOpenSansRegular);

        tv_location_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notifications_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_date_location_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_date_calendar_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_price_icon.setTypeface(Utility.getMaterialIconsRegular(mParent));
        tv_facebook_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_event_name.setTypeface(mTypefaceOpenSansRegular);
        tv_date_time.setTypeface(mTypefaceOpenSansRegular);
        tv_dress_code.setTypeface(mTypefaceOpenSansRegular);
        tv_dress_code_value.setTypeface(mTypefaceOpenSansRegular);
        tv_address.setTypeface(mTypefaceOpenSansRegular);
        tv_end_date_time.setTypeface(mTypefaceOpenSansRegular);
        tv_start_time.setTypeface(mTypefaceOpenSansRegular);
        tv_end_time.setTypeface(mTypefaceOpenSansRegular);
        tv_fb_url.setTypeface(mTypefaceOpenSansRegular);
        tv_locality.setTypeface(mTypefaceOpenSansRegular);
        tv_tickets.setTypeface(mTypefaceOpenSansRegular);
        tv_web_site.setTypeface(mTypefaceOpenSansRegular);
        tv_website_link.setTypeface(mTypefaceOpenSansRegular);

        tv_event_tag_line_text_comes_here.setTypeface(mTypefaceOpenSansRegular);
        tv_total_cost.setTypeface(mTypefaceOpenSansRegular);
        tv_details.setTypeface(mTypefaceOpenSansRegular);
        tv_a_weekly_desi.setTypeface(mTypefaceOpenSansRegular);


        btn_who_is_going.setTypeface(mTypefaceOpenSansRegular);
        btn_may_be.setTypeface(mTypefaceOpenSansRegular);
        btn_i_am_going.setTypeface(mTypefaceOpenSansRegular);

        getEventDetails();
    }

    /**
     * This method is used to get event details
     */
    private void getEventDetails() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            EventsDetailParser eventsListParser = new EventsDetailParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_EVENT_DETAILS + "/" + mId, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, eventsListParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*This method is used to navigate event detail view*/
    @OnClick(R.id.btn_i_am_going)
    void submitIAmGoing() {
        if (!Utility.getSharedPrefBooleanData(mParent, Constants.IS_LOGIN_COMPLETED)) {
            Utility.showToastMessage(mParent, "You need to login for updating the status");
            Intent intent = new Intent(mParent, LoginActivity.class);
            startActivity(intent);
        } else {
            mFromNot = "1";
            try {
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
                IAmGoingParser iAmGoingParser = new IAmGoingParser();
                ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                        mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                        APIConstants.EVENT_GOING + "/" + mId + "/1", linkedHashMap,
                        APIConstants.REQUEST_TYPE.GET, this, iAmGoingParser);
                Utility.execute(serverJSONAsyncTask);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is used to navigate event detail view
     */
    @OnClick(R.id.tv_may)
    void submittMay() {
        if (!Utility.getSharedPrefBooleanData(mParent, Constants.IS_LOGIN_COMPLETED)) {
            Utility.showToastMessage(mParent, "You need to login for updating the status");
            Intent intent = new Intent(mParent, LoginActivity.class);
            startActivity(intent);
        } else {
            if (tv_may.getText().toString().equalsIgnoreCase(Utility.getResourcesString(mParent, R.string.may_be_going))) {
                mFromNot = "2";
            } else {
                mFromNot = "1";
            }
            try {
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
                IAmGoingParser iAmGoingParser = new IAmGoingParser();
                ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                        mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                        APIConstants.EVENT_GOING + "/" + mId + "/" + mFromNot, linkedHashMap,
                        APIConstants.REQUEST_TYPE.GET, this, iAmGoingParser);
                Utility.execute(serverJSONAsyncTask);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is used update not going
     */
    @OnClick(R.id.tv_not)
    void submittNot() {
        if (!Utility.getSharedPrefBooleanData(mParent, Constants.IS_LOGIN_COMPLETED)) {
            Utility.showToastMessage(mParent, "You need to login for updating the status");
            Intent intent = new Intent(mParent, LoginActivity.class);
            startActivity(intent);
        } else {
            mFromNot = "0";
            try {
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
                IAmGoingParser iAmGoingParser = new IAmGoingParser();
                ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                        mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                        APIConstants.EVENT_GOING + "/" + mId + "/" + mFromNot, linkedHashMap,
                        APIConstants.REQUEST_TYPE.GET, this, iAmGoingParser);
                Utility.execute(serverJSONAsyncTask);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*This method is used to may be going*/
    @OnClick(R.id.btn_may_be)
    void submitMayBeGoing() {
        if (!Utility.getSharedPrefBooleanData(mParent, Constants.IS_LOGIN_COMPLETED)) {
            Utility.showToastMessage(mParent, "You need to login for updating the status");
            Intent intent = new Intent(mParent, LoginActivity.class);
            startActivity(intent);
        } else {
            mFromNot = "2";
            try {
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
                IAmGoingParser iAmGoingParser = new IAmGoingParser();
                ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                        mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                        APIConstants.EVENT_GOING + "/" + mId + "/2", linkedHashMap,
                        APIConstants.REQUEST_TYPE.GET, this, iAmGoingParser);
                Utility.execute(serverJSONAsyncTask);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*This method is used to navigate event detail view*/
    @OnClick(R.id.btn_who_is_going)
    void whoIsGoing() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.EVENT_ID, mId);
        Utility.navigateDashBoardFragment(new EventsGoingMaybeGoingFragment(), EventsGoingMaybeGoingFragment.TAG, bundle, mParent);
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


    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof EventsModel) {
                eventsModel = (EventsModel) model;
                setEventsData();
            } else if (model instanceof IAmGoingModel) {
                iAmGoingModel = (IAmGoingModel) model;
                Utility.showToastMessage(mParent, iAmGoingModel.getMessage());
                updateNewUI();
            }
        }
    }

    /**
     * This method is used to update new ui
     */
    private void updateNewUI() {
        if (mFromNot.equalsIgnoreCase("1")) {
            ll_i_am_going_may_be_going.setVisibility(View.GONE);
            ll_reverse_layout.setVisibility(View.VISIBLE);

            tv_going.setText(Utility.getResourcesString(mParent, R.string.i_am_going_hint));
            tv_may.setText(Utility.getResourcesString(mParent, R.string.may_be_going));
            tv_not.setText(Utility.getResourcesString(mParent, R.string.not_going));

            tv_going.setTypeface(Utility.getOpenSansBold(mParent));
            tv_may.setTypeface(Utility.getOpenSansRegular(mParent));
            tv_not.setTypeface(Utility.getOpenSansRegular(mParent));

        } else if (mFromNot.equalsIgnoreCase("2")) {
            ll_i_am_going_may_be_going.setVisibility(View.GONE);
            ll_reverse_layout.setVisibility(View.VISIBLE);

            tv_going.setText(Utility.getResourcesString(mParent, R.string.may_be_going));
            tv_may.setText(Utility.getResourcesString(mParent, R.string.i_am_going_hint));
            tv_not.setText(Utility.getResourcesString(mParent, R.string.not_going));

            tv_going.setTypeface(Utility.getOpenSansBold(mParent));
            tv_may.setTypeface(Utility.getOpenSansRegular(mParent));
            tv_not.setTypeface(Utility.getOpenSansRegular(mParent));
        } else {
            ll_i_am_going_may_be_going.setVisibility(View.VISIBLE);
            ll_reverse_layout.setVisibility(View.GONE);
        }
    }

    private void setEventsData() {
        if (!Utility.isValueNullOrEmpty(eventsModel.getImage())) {
            Utility.universalImageLoaderPicLoading(img_event,
                    eventsModel.getImage(), null, R.drawable.xappie_place_);
        } else {
            Utility.universalImageLoaderPicLoading(img_event,
                    "", null, R.drawable.xappie_place_);
        }

        if (!Utility.isValueNullOrEmpty(eventsModel.getName())) {
            tv_event_name.setText(eventsModel.getName());
        } else {
            tv_event_name.setVisibility(View.GONE);
        }

        if (!Utility.isValueNullOrEmpty(eventsModel.getTag())) {
            tv_event_tag_line_text_comes_here.setText(eventsModel.getTag());
        } else {
            tv_event_tag_line_text_comes_here.setVisibility(View.GONE);
        }
        if (!Utility.isValueNullOrEmpty(eventsModel.getStart_time())) {
           // tv_date_time.setText(Utility.readDateFormat(eventsModel.getStart_time().substring(0, 10)).toUpperCase() + " "
               //     + eventsModel.getStart_time().substring(11, 16).toUpperCase());
            tv_date_time.setText(Utility.displayDate(eventsModel.getStart_time().toUpperCase()));
            tv_start_time.setText(Utility.displayTime(eventsModel.getStart_time()));
        } else {
            tv_date_time.setVisibility(View.GONE);
            tv_start_time.setVisibility(View.GONE);
        }
        if (!Utility.isValueNullOrEmpty(eventsModel.getEnd_time()))
        {
            tv_end_date_time.setText(Utility.displayDate(eventsModel.getEnd_time().toUpperCase()));
            tv_end_time.setText(Utility.displayTime(eventsModel.getEnd_time()));
        }
        else {
            tv_end_date_time.setVisibility(View.GONE);
            tv_end_time.setVisibility(View.GONE);
        }

        if (!Utility.isValueNullOrEmpty(eventsModel.getDress_code())) {
            tv_dress_code_value.setText(eventsModel.getDress_code());
        } else {
            tv_dress_code_value.setVisibility(View.GONE);
            tv_dress_code.setVisibility(View.GONE);
        }
        if (!Utility.isValueNullOrEmpty(eventsModel.getCost())) {
            tv_total_cost.setText(eventsModel.getCost());
        } else {
                 tv_total_cost.setVisibility(View.GONE);
                 tv_price_icon.setVisibility(View.GONE);
        }

        if (!Utility.isValueNullOrEmpty(eventsModel.getAddress())) {
            tv_address.setText(eventsModel.getAddress());
        } else {
            tv_address.setVisibility(View.GONE);
            tv_date_location_icon.setVisibility(View.GONE);
        }
        if (!Utility.isValueNullOrEmpty(eventsModel.getDescription())) {
            tv_details.setText(eventsModel.getDescription());
        } else {
            tv_details.setVisibility(View.GONE);
            tv_a_weekly_desi.setVisibility(View.GONE);
        }
        if (!Utility.isValueNullOrEmpty(eventsModel.getWebsite())) {
            tv_website_link.setText(eventsModel.getWebsite());
            tv_website_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent webIntent = new Intent();
                    webIntent.setAction(Intent.ACTION_VIEW);
                    webIntent.setData(Uri.parse(eventsModel.getWebsite()));
                    mParent.startActivity(webIntent);
                }
            });
        } else {
            tv_web_site.setVisibility(View.GONE);
            tv_website_link.setVisibility(View.GONE);
        }

        if (!Utility.isValueNullOrEmpty(eventsModel.getFacebook())) {
            tv_fb_url.setText(eventsModel.getFacebook());
            tv_fb_url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent webIntent = new Intent();
                    webIntent.setAction(Intent.ACTION_VIEW);
                    webIntent.setData(Uri.parse(eventsModel.getFacebook()));
                    mParent.startActivity(webIntent);
                }
            });
        } else {
            tv_fb_url.setVisibility(View.GONE);
            tv_facebook_icon.setVisibility(View.GONE);
        }
        if (!Utility.isValueNullOrEmpty(eventsModel.getLocation())) {
            tv_locality.setText(eventsModel.getLocation());
        } else {
            tv_locality.setVisibility(View.GONE);
        }
        if (!Utility.isValueNullOrEmpty(eventsModel.getBooking_url())) {
            tv_tickets.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent webIntent = new Intent();
                    webIntent.setAction(Intent.ACTION_VIEW);
                    webIntent.setData(Uri.parse(eventsModel.getBooking_url()));
                    mParent.startActivity(webIntent);
                }
            });
        } else {
            tv_tickets.setVisibility(View.GONE);
        }

        if (Utility.isValueNullOrEmpty(eventsModel.getGoing())) {
            ll_i_am_going_may_be_going.setVisibility(View.VISIBLE);
            ll_reverse_layout.setVisibility(View.GONE);
        } else if (eventsModel.getGoing().equalsIgnoreCase("1")) {
            ll_i_am_going_may_be_going.setVisibility(View.GONE);
            ll_reverse_layout.setVisibility(View.VISIBLE);

            tv_going.setText(Utility.getResourcesString(mParent, R.string.i_am_going_hint));
            tv_may.setText(Utility.getResourcesString(mParent, R.string.may_be_going));
            tv_not.setText(Utility.getResourcesString(mParent, R.string.not_going));

            tv_going.setTypeface(Utility.getOpenSansBold(mParent));
            tv_may.setTypeface(Utility.getOpenSansRegular(mParent));
            tv_not.setTypeface(Utility.getOpenSansRegular(mParent));

        } else if (eventsModel.getGoing().equalsIgnoreCase("2")) {
            ll_i_am_going_may_be_going.setVisibility(View.GONE);
            ll_reverse_layout.setVisibility(View.VISIBLE);

            tv_going.setText(Utility.getResourcesString(mParent, R.string.may_be_going));
            tv_may.setText(Utility.getResourcesString(mParent, R.string.i_am_going_hint));
            tv_not.setText(Utility.getResourcesString(mParent, R.string.not_going));

            tv_going.setTypeface(Utility.getOpenSansBold(mParent));
            tv_may.setTypeface(Utility.getOpenSansRegular(mParent));
            tv_not.setTypeface(Utility.getOpenSansRegular(mParent));
        } else {
            ll_i_am_going_may_be_going.setVisibility(View.VISIBLE);
            ll_reverse_layout.setVisibility(View.GONE);
        }
    }
}
