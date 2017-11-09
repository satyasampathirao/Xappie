package com.xappie.fragments;


import android.content.Intent;
import android.graphics.Paint;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.activities.LoginActivity;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.AddEventModel;
import com.xappie.models.EventsListModel;
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
    @BindView(R.id.ll_cost)
    LinearLayout ll_cost;

    @BindView(R.id.tv_event_name)
    TextView tv_event_name;
    @BindView(R.id.tv_date_time)
    TextView tv_date_time;
    @BindView(R.id.tv_dress_code)
    TextView tv_dress_code;
    @BindView(R.id.tv_dress_code_value)
    TextView tv_dress_code_value;
    @BindView(R.id.tv_restrictions)
    TextView tv_restrictions;
    @BindView(R.id.tv_address)
    TextView tv_address;

    @BindView(R.id.tv_event_tag_line_text_comes_here)
    TextView tv_event_tag_line_text_comes_here;
    @BindView(R.id.tv_total_cost)
    TextView tv_total_cost;
    @BindView(R.id.tv_ticket_cost)
    TextView tv_ticket_cost;
    @BindView(R.id.tv_details)
    TextView tv_details;
    @BindView(R.id.tv_a_weekly_desi)
    TextView tv_a_weekly_desi;


    @BindView(R.id.btn_i_am_going)
    Button btn_i_am_going;
    @BindView(R.id.btn_may_be)
    Button btn_may_be;
    @BindView(R.id.btn_who_is_going)
    Button btn_who_is_going;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;
    private Typeface mTypefaceOpenSansBold;

    private String mId;
    private EventsModel eventsModel;
    private IAmGoingModel iAmGoingModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        mFrameLayout = (FrameLayout) getActivity().findViewById(R.id.content_frame);
        mParams = (CoordinatorLayout.LayoutParams) mFrameLayout.getLayoutParams();
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

        tv_event_name.setTypeface(mTypefaceOpenSansBold);
        tv_date_time.setTypeface(mTypefaceOpenSansBold);
        tv_dress_code.setPaintFlags(tv_dress_code.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv_dress_code.setTypeface(mTypefaceOpenSansRegular);
        tv_dress_code_value.setTypeface(mTypefaceOpenSansBold);
        tv_restrictions.setTypeface(mTypefaceOpenSansBold);
        tv_address.setTypeface(mTypefaceOpenSansBold);

        tv_event_tag_line_text_comes_here.setTypeface(mTypefaceOpenSansBold);
        tv_total_cost.setTypeface(mTypefaceOpenSansBold);
        tv_ticket_cost.setTypeface(mTypefaceOpenSansRegular);
        tv_details.setTypeface(mTypefaceOpenSansRegular);
        tv_a_weekly_desi.setTypeface(mTypefaceOpenSansRegular);
        tv_a_weekly_desi.setVisibility(View.GONE);

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
            Utility.showToastMessage(mParent, "Login First");
            Intent intent = new Intent(mParent, LoginActivity.class);
            startActivity(intent);
        } else {
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

    /*This method is used to navigate event detail view*/
    @OnClick(R.id.btn_who_is_going)
    void whoIsGoing() {
        Utility.navigateDashBoardFragment(new EventsGoingMaybeGoingFragment(), EventsGoingMaybeGoingFragment.TAG, null, mParent);
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
            }
        }
    }

    private void setEventsData() {
        if (!Utility.isValueNullOrEmpty(eventsModel.getImage())) {
            Utility.universalImageLoaderPicLoading(img_event,
                    eventsModel.getImage(), null, R.drawable.xappie_place_holder);
        } else {
            Utility.universalImageLoaderPicLoading(img_event,
                    "", null, R.drawable.xappie_place_holder);
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
            tv_date_time.setText(eventsModel.getStart_time());
        } else {
            tv_date_time.setVisibility(View.GONE);
        }

        if (!Utility.isValueNullOrEmpty(eventsModel.getDress_code())) {
            tv_dress_code_value.setText(eventsModel.getDress_code());
        } else {
            tv_dress_code_value.setVisibility(View.GONE);
        }
        if (!Utility.isValueNullOrEmpty(eventsModel.getCost())) {
            tv_total_cost.setText(eventsModel.getCost());
        } else {
            ll_cost.setVisibility(View.GONE);
        }

        if (!Utility.isValueNullOrEmpty(eventsModel.getAddress())) {
            tv_address.setText(eventsModel.getAddress());
        } else {
            tv_address.setVisibility(View.GONE);
        }
        if (!Utility.isValueNullOrEmpty(eventsModel.getDescription())) {
            tv_details.setText(eventsModel.getDescription());
        } else {
            tv_details.setVisibility(View.GONE);
        }
    }
}
