package com.xappie.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.activities.VideoViewActivity;
import com.xappie.adapters.AdsPagerAdapter;
import com.xappie.adapters.HomeViewPagerAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.interfaces.IHomeCustomization;
import com.xappie.models.ClassifiedsListModel;
import com.xappie.models.EntertainmentListModel;
import com.xappie.models.EntertainmentModel;
import com.xappie.models.EventsListModel;
import com.xappie.models.GalleryItemModel;
import com.xappie.models.HomePageBannerListModel;
import com.xappie.models.HomePageContentModel;
import com.xappie.models.HomePageEventsAdsBannersModel;
import com.xappie.models.JobsListModel;
import com.xappie.models.LanguageListModel;
import com.xappie.models.LanguageModel;
import com.xappie.models.Model;
import com.xappie.models.NewsModel;
import com.xappie.models.StateModel;
import com.xappie.models.StatesListModel;
import com.xappie.models.TopStoriesListModel;
import com.xappie.models.VideosModel;
import com.xappie.parser.ClassifiedsParser;
import com.xappie.parser.EntertainmentParser;
import com.xappie.parser.EventsListParser;
import com.xappie.parser.HomePageAdsEventsBannerParser;
import com.xappie.parser.HomePageBannerParser;
import com.xappie.parser.HomePageContentParser;
import com.xappie.parser.JobsListParser;
import com.xappie.parser.LanguageParser;
import com.xappie.parser.StatesParser;
import com.xappie.parser.TopStoriesParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements IAsyncCaller, IHomeCustomization {

    public static final String TAG = HomeFragment.class.getSimpleName();
    private DashBoardActivity mParent;
    private Toolbar mToolbar;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private View rootView;
    private CoordinatorLayout.LayoutParams mParams;

    @BindView(R.id.card_pager)
    ViewPager card_pager;

    /**
     * Gallery View Ids
     */
    @BindView(R.id.ll_gallery_total_layout)
    LinearLayout ll_gallery_total_layout;
    @BindView(R.id.layout_gallery)
    LinearLayout layout_gallery;

    @BindView(R.id.tv_gallery)
    TextView tv_gallery;
    @BindView(R.id.tv_gallery_more)
    TextView tv_gallery_more;

    /**
     * Videos View Ids
     */
    @BindView(R.id.ll_videos_total_layout)
    LinearLayout ll_videos_total_layout;
    @BindView(R.id.layout_videos)
    LinearLayout layout_videos;

    @BindView(R.id.tv_videos)
    TextView tv_videos;
    @BindView(R.id.tv_videos_more)
    TextView tv_videos_more;

    /**
     * ADS View Ids
     */

    @BindView(R.id.tv_ads)
    TextView tv_ads;

    @BindView(R.id.ads_pager)
    ViewPager ads_pager;


    /**
     * Tops stories View Ids
     */

    @BindView(R.id.rl_top_stories_heading)
    RelativeLayout rl_top_stories_heading;
    @BindView(R.id.hs_top_stories)
    HorizontalScrollView hs_top_stories;

    @BindView(R.id.tv_top_stories)
    TextView tv_top_stories;

    @BindView(R.id.tv_top_stories_more)
    TextView tv_top_stories_more;

    @BindView(R.id.ll_languages_layout_top_stories)
    LinearLayout ll_languages_layout_top_stories;
    @BindView(R.id.ll_top_stories)
    LinearLayout ll_top_stories;


    /**
     * Entertainment View Ids
     */

    @BindView(R.id.rl_entertainment_heading)
    RelativeLayout rl_entertainment_heading;
    @BindView(R.id.hs_entertainment)
    HorizontalScrollView hs_entertainment;

    @BindView(R.id.tv_entertainment)
    TextView tv_entertainment;

    @BindView(R.id.tv_entertainment_more)
    TextView tv_entertainment_more;

    @BindView(R.id.ll_languages_layout_entertainment)
    LinearLayout ll_languages_layout_entertainment;
    @BindView(R.id.ll_entertainment)
    LinearLayout ll_entertainment;


    /**
     * Discussions View Ids
     */

    @BindView(R.id.rl_discussions_heading)
    RelativeLayout rl_discussions_heading;
    @BindView(R.id.hs_discussions)
    HorizontalScrollView hs_discussions;

    @BindView(R.id.tv_discussions)
    TextView tv_discussions;

    @BindView(R.id.tv_discussions_more)
    TextView tv_discussions_more;

    @BindView(R.id.ll_languages_layout_discussions)
    LinearLayout ll_languages_layout_discussions;
    @BindView(R.id.ll_discussions)
    LinearLayout ll_discussions;

    /**
     * Events View Ids
     */

    @BindView(R.id.rl_events_heading)
    RelativeLayout rl_events_heading;
    @BindView(R.id.hs_events)
    HorizontalScrollView hs_events;
    @BindView(R.id.hs_events_inner_layout)
    HorizontalScrollView hs_events_inner_layout;

    @BindView(R.id.tv_events)
    TextView tv_events;

    @BindView(R.id.tv_events_more)
    TextView tv_events_more;

    @BindView(R.id.ll_languages_layout_events)
    LinearLayout ll_languages_layout_events;
    @BindView(R.id.ll_events)
    LinearLayout ll_events;


    @BindView(R.id.ll_no_data_event)
    LinearLayout ll_no_data_event;
    @BindView(R.id.tv_no_data_event)
    TextView tv_no_data_event;

    /**
     * Classifieds View Ids
     */

    @BindView(R.id.rl_classifieds_heading)
    RelativeLayout rl_classifieds_heading;
    @BindView(R.id.hs_classifieds_inner_layout)
    HorizontalScrollView hs_classifieds_inner_layout;
    @BindView(R.id.hs_classifieds)
    HorizontalScrollView hs_classifieds;

    @BindView(R.id.tv_classifieds)
    TextView tv_classifieds;

    @BindView(R.id.tv_classifieds_more)
    TextView tv_classifieds_more;

    @BindView(R.id.ll_languages_layout_classifieds)
    LinearLayout ll_languages_layout_classifieds;
    @BindView(R.id.ll_classifieds)
    LinearLayout ll_classifieds;


    @BindView(R.id.ll_no_data_classified)
    LinearLayout ll_no_data_classified;
    @BindView(R.id.tv_no_data_classified)
    TextView tv_no_data_classified;

    /**
     * Jobs View Ids
     */

    @BindView(R.id.rl_jobs)
    RelativeLayout rl_jobs;
    @BindView(R.id.hs_jobs)
    HorizontalScrollView hs_jobs;

    @BindView(R.id.tv_jobs)
    TextView tv_jobs;

    @BindView(R.id.tv_jobs_more)
    TextView tv_jobs_more;

    @BindView(R.id.ll_languages_layout_jobs)
    LinearLayout ll_languages_layout_jobs;
    @BindView(R.id.ll_jobs)
    LinearLayout ll_jobs;

    @BindView(R.id.ll_no_data_jobs)
    LinearLayout ll_no_data_jobs;
    @BindView(R.id.tv_no_data_jobs)
    TextView tv_no_data_jobs;


    private LanguageListModel mLanguageListModel;
    private HomePageBannerListModel mHomePageBannerListModel;
    private LanguageModel languageModel;
    private HomePageContentModel mHomePageContentModel;
    private HomePageEventsAdsBannersModel mHomePageEventsAdsBannersModel;
    private TopStoriesListModel mTopStoriesListModel;
    private EntertainmentListModel mEntertainmentListModel;
    private StatesListModel mStatesListModel;
    private StateModel stateModel;
    private EventsListModel eventsListModel;
    private JobsListModel jobsListModel;
    private ClassifiedsListModel classifiedsListModel;

    private static IHomeCustomization iHomeCustomization;

    public static IHomeCustomization getInstance() {
        return iHomeCustomization;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        mFrameLayout = (FrameLayout) getActivity().findViewById(R.id.content_frame);
        mParams = (CoordinatorLayout.LayoutParams) mFrameLayout.getLayoutParams();
        iHomeCustomization = this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (appBarLayout != null) {
            appBarLayout.setVisibility(View.VISIBLE);
            mParams.setBehavior(new AppBarLayout.ScrollingViewBehavior());
        }
        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    private void initUI() {
        setTypeface();
        getLanguagesData();
        //setAdsData();
        //getHomePageLocData();
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

    /**
     * This method is used to get the Languages data from the server
     */
    private void getLanguagesData() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            LanguageParser languageParser = new LanguageParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_LANGUAGES, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, languageParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to get data of the home page
     */
    private void getHomePageData() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            linkedHashMap.put("language", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_LANGUAGE_ID));
            linkedHashMap.put(Constants.PAGE_NO, "1");
            linkedHashMap.put(Constants.PAGE_SIZE, "7");
            linkedHashMap.put("modules", Utility.getSharedPrefStringData(mParent, Constants.HOME_PAGE_CONTENTS));
            HomePageContentParser videosParser = new HomePageContentParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_HOME_CONTENT, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, videosParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to get data of the events
     */
    private void getHomePageLocData() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            linkedHashMap.put(Constants.PAGE_NO, "1");
            linkedHashMap.put(Constants.PAGE_SIZE, "7");
            linkedHashMap.put("modules", "ads,banners," + Utility.getSharedPrefStringData(mParent, Constants.HOME_PAGE_EVENTS_CONTENTS) + Utility.getSharedPrefStringData(mParent, Constants.HOME_PAGE_JOBS_CONTENTS));
            linkedHashMap.put("country", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_ID));
            linkedHashMap.put("state", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID));
            linkedHashMap.put("city", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID));
            HomePageAdsEventsBannerParser homePageAdsEventsBannerParser = new HomePageAdsEventsBannerParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_HOME_CONTENT_LOC, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, homePageAdsEventsBannerParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to get home banner data
     */
    private void getHomeBannerData() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            linkedHashMap.put("language", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_LANGUAGE_ID));
            linkedHashMap.put("country", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_ID));
            linkedHashMap.put("state", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID));
            linkedHashMap.put("city", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID));
            HomePageBannerParser homePageBannerParser = new HomePageBannerParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_HOME_BANNER, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, homePageBannerParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setClassifiedsData() {

        /*ll_languages_layout_classifieds.removeAllViews();
        for (int i = 0; i < getEventsData().size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.language_item, null);
            TextView tv_language_name = (TextView) ll.findViewById(R.id.tv_language_name);
            View view = (View) ll.findViewById(R.id.view);
            tv_language_name.setText(getEventsData().get(i));
            tv_language_name.setTypeface(Utility.getOpenSansBold(mParent));
            if (i == 0) {
                view.setVisibility(View.VISIBLE);
                tv_language_name.setTextColor(Utility.getColor(mParent, R.color.text_language_color));
            } else {
                view.setVisibility(View.GONE);
            }
            ll_languages_layout_classifieds.addView(ll);
        }*/
        ll_classifieds.removeAllViews();
        LinearLayout linearLayout = new LinearLayout(mParent);
        hs_classifieds.setVisibility(View.GONE);
        rl_classifieds_heading.setVisibility(View.VISIBLE);
        hs_classifieds_inner_layout.setVisibility(View.VISIBLE);
        ll_classifieds.setVisibility(View.VISIBLE);

        if (mHomePageEventsAdsBannersModel != null && mHomePageEventsAdsBannersModel.getClassifiedsModel().size() > 0) {
            for (int i = 0; i < mHomePageEventsAdsBannersModel.getClassifiedsModel().size(); i++) {
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                RelativeLayout ll = (RelativeLayout) mParent.getLayoutInflater().inflate(R.layout.classfields_item, null);
                LinearLayout view = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.view, null);
                ImageView img_gallery_image = (ImageView) ll.findViewById(R.id.img_gallery_image);
                TextView tv_title = (TextView) ll.findViewById(R.id.tv_title);
                TextView tv_classified_name = (TextView) ll.findViewById(R.id.tv_classified_name);
                TextView tv_calendar_icon = (TextView) ll.findViewById(R.id.tv_calendar_icon);
                TextView tv_time = (TextView) ll.findViewById(R.id.tv_time);
                TextView tv_price_icon = (TextView) ll.findViewById(R.id.tv_price_icon);
                TextView tv_posted_by = (TextView) ll.findViewById(R.id.tv_posted_by);
                TextView tv_sub_classified_name = (TextView) ll.findViewById(R.id.tv_sub_classified_name);

                if (!Utility.isValueNullOrEmpty(mHomePageEventsAdsBannersModel.getClassifiedsModel().get(i).getImage())) {
                    Utility.universalImageLoaderPicLoading(img_gallery_image,
                            mHomePageEventsAdsBannersModel.getClassifiedsModel().get(i).getImage(), null, R.drawable.xappie_place_);
                } else {
                    Utility.universalImageLoaderPicLoading(img_gallery_image,
                            "", null, R.drawable.xappie_place_);
                }
                tv_classified_name.setTypeface(Utility.getOpenSansRegular(mParent));
                tv_time.setTypeface(Utility.getOpenSansRegular(mParent));

                tv_classified_name.setText(mHomePageEventsAdsBannersModel.getClassifiedsModel().get(i).getCategory());
                tv_title.setText(mHomePageEventsAdsBannersModel.getClassifiedsModel().get(i).getTitle());
                tv_calendar_icon.setTypeface(Utility.getFontAwesomeWebFont(mParent));
                tv_price_icon.setTypeface(Utility.getMaterialIconsRegular(mParent));
                tv_posted_by.setText(mHomePageEventsAdsBannersModel.getClassifiedsModel().get(i).getPrice());
                tv_sub_classified_name.setText(mHomePageEventsAdsBannersModel.getClassifiedsModel().get(i).getSub_category());
                tv_title.setTypeface(Utility.getOpenSansRegular(mParent));
                tv_sub_classified_name.setTypeface(Utility.getOpenSansRegular(mParent));
                tv_posted_by.setTypeface(Utility.getOpenSansRegular(mParent));


                if (!Utility.isValueNullOrEmpty(mHomePageEventsAdsBannersModel.getBannersModels().get(i).getRecordedDate())) {
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    PrettyTime prettyTime = new PrettyTime();
                    Date date;
                    String outputDateStr = "";
                    try {
                        date = inputFormat.parse(mHomePageEventsAdsBannersModel.getClassifiedsModel().get(i).getRecordedDate());
                        outputDateStr = prettyTime.format(date);
                        tv_time.setText(outputDateStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }


                ll.setId(i);
                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = view.getId();
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.CLASSIFIEDS_ID, mHomePageEventsAdsBannersModel.getClassifiedsModel().get(position).getId());
                        Utility.navigateDashBoardFragment(new ClassifiedsDetailFragment(), ClassifiedsDetailFragment.TAG, bundle, mParent);
                    }
                });
                linearLayout.addView(view);
                linearLayout.addView(ll);

                if (i == 2 || i == mHomePageEventsAdsBannersModel.getClassifiedsModel().size() - 1)
                    ll_classifieds.addView(linearLayout);
            }
        } else {
            rl_classifieds_heading.setVisibility(View.GONE);
            hs_classifieds_inner_layout.setVisibility(View.GONE);
            hs_classifieds.setVisibility(View.GONE);
        }
    }


    /**
     * Sets Jobs data
     */
    private void setJobsData() {

       /* ll_languages_layout_jobs.removeAllViews();
        for (int i = 0; i < getEventsData().size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.language_item, null);
            TextView tv_language_name = (TextView) ll.findViewById(R.id.tv_language_name);
            View view = (View) ll.findViewById(R.id.view);
            tv_language_name.setText(getEventsData().get(i));
            tv_language_name.setTypeface(Utility.getOpenSansBold(mParent));

            if (i == 0) {
                view.setVisibility(View.VISIBLE);
                tv_language_name.setTextColor(Utility.getColor(mParent, R.color.text_language_color));
            } else {
                view.setVisibility(View.GONE);
            }

            ll_languages_layout_jobs.addView(ll);
        } */


        ll_jobs.removeAllViews();

        for (int i = 0; i < getNewsModels().size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.news_item, null);
            ImageView img_news_item = (ImageView) ll.findViewById(R.id.img_news_item);
            TextView tv_title = (TextView) ll.findViewById(R.id.tv_title);
            TextView tv_time = (TextView) ll.findViewById(R.id.tv_time);

            tv_title.setText(getNewsModels().get(i).getTitle());
            tv_title.setTypeface(Utility.getOpenSansBold(mParent));

            tv_time.setText(getNewsModels().get(i).getTime());
            tv_time.setTypeface(Utility.getOpenSansRegular(mParent));

            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utility.navigateDashBoardFragment(new JobsViewFragment(), JobsViewFragment.TAG, null, mParent);
                }
            });

            ll_jobs.addView(ll);
        }
    }

    /**
     * Sets Discussions data
     */
    private void setDiscussionsData() {
        ll_languages_layout_discussions.removeAllViews();
        for (int i = 0; i < getDiscussionsData().size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.language_item, null);
            TextView tv_language_name = (TextView) ll.findViewById(R.id.tv_language_name);
            View view = (View) ll.findViewById(R.id.view);
            tv_language_name.setText(getDiscussionsData().get(i));
            tv_language_name.setTypeface(Utility.getOpenSansBold(mParent));
            if (i == 0) {
                view.setVisibility(View.VISIBLE);
                tv_language_name.setTextColor(Utility.getColor(mParent, R.color.text_language_color));
            } else {
                view.setVisibility(View.GONE);
            }
            ll_languages_layout_discussions.addView(ll);
        }


        ll_discussions.removeAllViews();
        for (int i = 0; i < getNewsModels().size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.news_item, null);
            ImageView img_news_item = (ImageView) ll.findViewById(R.id.img_news_item);
            TextView tv_title = (TextView) ll.findViewById(R.id.tv_title);
            TextView tv_time = (TextView) ll.findViewById(R.id.tv_time);

            tv_title.setText(getNewsModels().get(i).getTitle());
            tv_title.setTypeface(Utility.getOpenSansBold(mParent));

            tv_time.setText(getNewsModels().get(i).getTime());
            tv_time.setTypeface(Utility.getOpenSansRegular(mParent));

            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            ll_discussions.addView(ll);
        }
    }

    /**
     * This method is used to set font
     */
    private void setTypeface() {
        tv_gallery.setTypeface(Utility.getOpenSansBold(mParent));
        tv_gallery_more.setTypeface(Utility.getOpenSansBold(mParent));

        tv_videos.setTypeface(Utility.getOpenSansBold(mParent));
        tv_videos_more.setTypeface(Utility.getOpenSansBold(mParent));

        tv_ads.setTypeface(Utility.getOpenSansBold(mParent));

        tv_top_stories.setTypeface(Utility.getOpenSansBold(mParent));
        tv_top_stories.setText(tv_top_stories.getText().toString().toUpperCase());
        tv_top_stories_more.setTypeface(Utility.getOpenSansBold(mParent));


        tv_entertainment.setTypeface(Utility.getOpenSansBold(mParent));
        tv_entertainment.setText(tv_entertainment.getText().toString().toUpperCase());
        tv_entertainment_more.setTypeface(Utility.getOpenSansBold(mParent));

        tv_discussions.setTypeface(Utility.getOpenSansBold(mParent));
        tv_discussions.setText(tv_discussions.getText().toString().toUpperCase());
        tv_discussions_more.setTypeface(Utility.getOpenSansBold(mParent));

        tv_events.setTypeface(Utility.getOpenSansBold(mParent));
        tv_events.setText(tv_events.getText().toString().toUpperCase());
        tv_events_more.setTypeface(Utility.getOpenSansBold(mParent));

        tv_classifieds.setTypeface(Utility.getOpenSansBold(mParent));
        tv_classifieds.setText(tv_classifieds.getText().toString().toUpperCase());
        tv_classifieds_more.setTypeface(Utility.getOpenSansBold(mParent));

        tv_jobs.setTypeface(Utility.getOpenSansBold(mParent));
        tv_jobs.setText(tv_jobs.getText().toString().toUpperCase());
        tv_jobs_more.setTypeface(Utility.getOpenSansBold(mParent));
    }

    private void setAdsData() {
        if (mHomePageEventsAdsBannersModel != null && mHomePageEventsAdsBannersModel.getAdsModels() != null && mHomePageEventsAdsBannersModel.getAdsModels().size() > 0) {
            ads_pager.setAdapter(new AdsPagerAdapter(mParent, mHomePageEventsAdsBannersModel.getAdsModels()));
            final Handler handler = new Handler();
            final Runnable update = new Runnable() {
                public void run() {
                    if (ads_page_position == mHomePageEventsAdsBannersModel.getAdsModels().size()) {
                        ads_page_position = 0;
                    } else {
                        ads_page_position = ads_page_position + 2;
                    }
                    ads_pager.setCurrentItem(ads_page_position, true);
                }
            };
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            }, 100, 3000);
            tv_ads.setVisibility(View.VISIBLE);
            ads_pager.setVisibility(View.VISIBLE);
        } else {
            tv_ads.setVisibility(View.GONE);
            ads_pager.setVisibility(View.GONE);
        }
        /*layout_ads.removeAllViews();
        if (mHomePageEventsAdsBannersModel != null && mHomePageEventsAdsBannersModel.getAdsModels() != null && mHomePageEventsAdsBannersModel.getAdsModels().size() > 0) {
            for (int i = 0; i < mHomePageEventsAdsBannersModel.getAdsModels().size(); i++) {
                LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.ads_item, null);
                ImageView img_ad_image = (ImageView) ll.findViewById(R.id.img_ad_image);
                if (!Utility.isValueNullOrEmpty(mHomePageEventsAdsBannersModel.getAdsModels().get(i).getImage())) {
                    Utility.universalImageLoaderPicLoading(img_ad_image,
                            mHomePageEventsAdsBannersModel.getAdsModels().get(i).getImage(), null, R.drawable.xappie_place_holder);
                } else {
                    Utility.universalImageLoaderPicLoading(img_ad_image,
                            "", null, R.drawable.xappie_place_holder);
                }
                ll.setId(i);
                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = view.getId();
                        showFitDialog(mHomePageEventsAdsBannersModel.getAdsModels().get(pos).getImage(), mParent);
                    }
                });
                layout_ads.addView(ll);
            }
            layout_ads.setVisibility(View.VISIBLE);
        } else
            layout_ads.setVisibility(View.GONE);*/

    }

    private ArrayList<String> getDiscussionsData() {
        ArrayList<String> mLanguagesData = new ArrayList<>();
        mLanguagesData.add("DOCTORS");
        mLanguagesData.add("LAWYERS");
        mLanguagesData.add("STUDENTS");
        mLanguagesData.add("KIDS");
        mLanguagesData.add("ROOMMATES");
        return mLanguagesData;
    }

    private ArrayList<String> getEventsData() {
        ArrayList<String> mLanguagesData = new ArrayList<>();
        mLanguagesData.add("BAY AREA");
        mLanguagesData.add("SAN JOSE");
        mLanguagesData.add("SEATTLE");
        mLanguagesData.add("NEW JERSEY");
        mLanguagesData.add("DC");
        return mLanguagesData;
    }

    private ArrayList<NewsModel> getNewsModels() {
        ArrayList<NewsModel> newsModels = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            NewsModel newsModel = new NewsModel();
            newsModel.setTitle("PM Modi arrives in France on last leg of 4-nation tour");
            newsModel.setTime("30 MINUTES AGO");
            newsModel.setId(R.drawable.news_image);
            newsModels.add(newsModel);
        }
        return newsModels;
    }

    @OnClick(R.id.tv_gallery_more)
    public void navigateToGallery() {
        Utility.navigateDashBoardFragment(new GalleryFragment(), GalleryFragment.TAG, null, mParent);
    }

    @OnClick(R.id.tv_videos_more)
    public void navigateToVideos() {
        Utility.navigateDashBoardFragment(new VideosFragment(), VideosFragment.TAG, null, mParent);
    }

    @OnClick(R.id.tv_top_stories_more)
    public void navigateToTopStories() {
        Utility.navigateDashBoardFragment(new TopStoriesFragment(), TopStoriesFragment.TAG, null, mParent);
    }

    @OnClick(R.id.tv_entertainment_more)
    public void navigateToEntertainment() {
        Utility.navigateDashBoardFragment(new EntertainmentFragment(), EntertainmentFragment.TAG, null, mParent);
    }

    @OnClick(R.id.tv_discussions_more)
    public void navigateToDiscussions() {
        Utility.navigateDashBoardFragment(new DiscussionsFragment(), DiscussionsFragment.TAG, null, mParent);
    }

    @OnClick(R.id.tv_events_more)
    public void navigateToEvents() {
        Utility.navigateDashBoardFragment(new EventsFragment(), EventsFragment.TAG, null, mParent);
    }

    @OnClick(R.id.tv_classifieds_more)
    public void navigateToClassifieds() {
        Utility.navigateDashBoardFragment(new ClassifiedsFragment(), ClassifiedsFragment.TAG, null, mParent);
    }

    @OnClick(R.id.tv_jobs_more)
    public void navigateToJobs() {
        Utility.navigateDashBoardFragment(new JobsFragment(), JobsFragment.TAG, null, mParent);
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof LanguageListModel) {
                mLanguageListModel = (LanguageListModel) model;
                if (mLanguageListModel.getLanguageModels().size() > 0) {
                    for (int i = 0; i < mLanguageListModel.getLanguageModels().size(); i++) {
                        if (Utility.getSharedPrefStringData(mParent, Constants.SELECTED_LANGUAGE_ID)
                                .equalsIgnoreCase(mLanguageListModel.getLanguageModels().get(i).getId())) {
                            languageModel = mLanguageListModel.getLanguageModels().get(i);
                        }
                    }
                    getHomeBannerData();
                }
            } else if (model instanceof StatesListModel) {
                mStatesListModel = (StatesListModel) model;
                if (mStatesListModel.getStateModels().size() > 0) {
                    for (int i = 0; i < mStatesListModel.getStateModels().size(); i++) {
                        if (Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID)
                                .equalsIgnoreCase(mStatesListModel.getStateModels().get(i).getId())) {
                            stateModel = mStatesListModel.getStateModels().get(i);
                        }
                    }
                    getHomePageLocData();
                }
            } else if (model instanceof HomePageBannerListModel) {
                mHomePageBannerListModel = (HomePageBannerListModel) model;
                if (mHomePageBannerListModel != null && mHomePageBannerListModel.getHomePageBannerModels() != null
                        && mHomePageBannerListModel.getHomePageBannerModels().size() > 0) {
                    setDataToBanner();
                    card_pager.setVisibility(View.VISIBLE);
                } else {
                    card_pager.setVisibility(View.GONE);
                    getHomePageData();
                }
            } else if (model instanceof HomePageContentModel) {
                mHomePageContentModel = (HomePageContentModel) model;
                setDataToTheScreen();
            } else if (model instanceof HomePageEventsAdsBannersModel) {
                mHomePageEventsAdsBannersModel = (HomePageEventsAdsBannersModel) model;
                setAdsData();
                setDataToEvents();
                setDataToClassifieds();
                setDataToJobs();
            } else if (model instanceof TopStoriesListModel) {
                mTopStoriesListModel = (TopStoriesListModel) model;
                if (mTopStoriesListModel.getEntertainmentModels().size() > 0) {
                    if (mHomePageContentModel != null) {
                        mHomePageContentModel.setTopStoriesModels(mTopStoriesListModel.getEntertainmentModels());
                        setTopStoriesData(mHomePageContentModel.getTopStoriesModels());
                    }
                }
            } else if (model instanceof EntertainmentListModel) {
                mEntertainmentListModel = (EntertainmentListModel) model;
                if (mEntertainmentListModel.getEntertainmentModels().size() > 0) {
                    if (mHomePageContentModel != null) {
                        mHomePageContentModel.setEntertainmentModels(mEntertainmentListModel.getEntertainmentModels());
                        setEntertainmentData(mHomePageContentModel.getEntertainmentModels());
                    }
                }
            } else if (model instanceof EventsListModel) {
                eventsListModel = (EventsListModel) model;
                if (eventsListModel.getEventsModels().size() > 0) {
                    ll_no_data_event.setVisibility(View.GONE);
                    ll_events.setVisibility(View.VISIBLE);
                    if (mHomePageEventsAdsBannersModel != null) {
                        mHomePageEventsAdsBannersModel.setEventsModels(eventsListModel.getEventsModels());
                        setDataToEvents();
                    }
                } else {
                    ll_no_data_event.setVisibility(View.VISIBLE);
                    ll_events.setVisibility(View.GONE);
                }
            } else if (model instanceof ClassifiedsListModel) {
                classifiedsListModel = (ClassifiedsListModel) model;
                if (classifiedsListModel.getClassifiedsModels().size() > 0) {
                    ll_no_data_classified.setVisibility(View.GONE);
                    ll_classifieds.setVisibility(View.VISIBLE);
                    if (mHomePageEventsAdsBannersModel != null) {
                        mHomePageEventsAdsBannersModel.setClassifiedsModel(classifiedsListModel.getClassifiedsModels());
                        setDataToClassifieds();
                    }
                } else {
                    ll_no_data_classified.setVisibility(View.VISIBLE);
                    ll_classifieds.setVisibility(View.GONE);
                }
            } else if (model instanceof JobsListModel) {
                jobsListModel = (JobsListModel) model;
                if (jobsListModel.getJobsModels().size() > 0) {
                    ll_no_data_jobs.setVisibility(View.GONE);
                    ll_jobs.setVisibility(View.VISIBLE);
                    if (mHomePageEventsAdsBannersModel != null) {
                        mHomePageEventsAdsBannersModel.setJobsModels(jobsListModel.getJobsModels());
                        setDataToJobs();
                    }
                } else {
                    ll_no_data_jobs.setVisibility(View.VISIBLE);
                    ll_jobs.setVisibility(View.GONE);
                }
            }
        }
    }

    private void setDataToEvents() {
        if (mHomePageEventsAdsBannersModel != null
                && mHomePageEventsAdsBannersModel.getEventsModels() != null
                && mHomePageEventsAdsBannersModel.getEventsModels().size() > 0) {
            setEventsFilters();
            ll_events.removeAllViews();
            if (mHomePageEventsAdsBannersModel.getEventsModels().size() > 0) {
                rl_events_heading.setVisibility(View.VISIBLE);
                hs_events.setVisibility(View.VISIBLE);
                hs_events_inner_layout.setVisibility(View.VISIBLE);

                for (int i = 0; i < mHomePageEventsAdsBannersModel.getEventsModels().size() && i < 7; i++) {

                    RelativeLayout ll = (RelativeLayout) mParent.getLayoutInflater().inflate(R.layout.events_item, null);
                    LinearLayout view = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.view, null);
                    ImageView img_gallery_image = (ImageView) ll.findViewById(R.id.img_gallery_image);
                    TextView tv_title = (TextView) ll.findViewById(R.id.tv_event_name);
                    TextView tv_time = (TextView) ll.findViewById(R.id.tv_time);
                    TextView tv_posted = (TextView) ll.findViewById(R.id.tv_event_adress);

                    if (!Utility.isValueNullOrEmpty(mHomePageEventsAdsBannersModel.getEventsModels().get(i).getImage())) {
                        Utility.universalImageLoaderPicLoading(img_gallery_image,
                                mHomePageEventsAdsBannersModel.getEventsModels().get(i).getImage(), null, R.drawable.xappie_place_);
                    } else {
                        Utility.universalImageLoaderPicLoading(img_gallery_image,
                                "", null, R.drawable.xappie_place_);
                    }

                    tv_title.setText(mHomePageEventsAdsBannersModel.getEventsModels().get(i).getName());
                    tv_title.setTypeface(Utility.getOpenSansRegular(mParent));

                    tv_time.setText(Utility.readDateFormat(mHomePageEventsAdsBannersModel.getEventsModels().get(i).getStart_time()).toUpperCase());
                    tv_time.setTypeface(Utility.getOpenSansRegular(mParent));
                    tv_posted.setTypeface(Utility.getOpenSansRegular(mParent));
                    tv_posted.setText(mHomePageEventsAdsBannersModel.getEventsModels().get(i).getLocality());

                    ll.setId(i);
                    ll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int position = view.getId();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.EVENT_ID, mHomePageEventsAdsBannersModel.getEventsModels().get(position).getId());
                            Utility.navigateDashBoardFragment(new EventDetailViewFragment(), EventDetailViewFragment.TAG, bundle,
                                    mParent);
                        }
                    });
                    ll_events.addView(view);
                    ll_events.addView(ll);
                }
            }
            rl_events_heading.setVisibility(View.VISIBLE);
            hs_events.setVisibility(View.VISIBLE);
            hs_events_inner_layout.setVisibility(View.VISIBLE);
            ll_events.setVisibility(View.VISIBLE);
        } else {
            rl_events_heading.setVisibility(View.GONE);
            hs_events.setVisibility(View.GONE);
            hs_events_inner_layout.setVisibility(View.GONE);
            ll_events.setVisibility(View.GONE);
        }
    }


    private void setDataToClassifieds() {
        if (mHomePageEventsAdsBannersModel != null
                && mHomePageEventsAdsBannersModel.getClassifiedsModel() != null
                && mHomePageEventsAdsBannersModel.getClassifiedsModel().size() > 0) {
            setClassifiedFilters();
            ll_classifieds.removeAllViews();
            if (mHomePageEventsAdsBannersModel.getClassifiedsModel().size() > 0) {
                rl_classifieds_heading.setVisibility(View.VISIBLE);
                hs_classifieds.setVisibility(View.VISIBLE);
                hs_classifieds_inner_layout.setVisibility(View.VISIBLE);

                for (int i = 0; i < mHomePageEventsAdsBannersModel.getClassifiedsModel().size() && i < 7; i++) {

                    RelativeLayout ll = (RelativeLayout) mParent.getLayoutInflater().inflate(R.layout.classfields_item, null);
                    LinearLayout view = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.view, null);
                    ImageView img_gallery_image = ll.findViewById(R.id.img_gallery_image);
                    TextView tv_title = ll.findViewById(R.id.tv_title);
                    TextView tv_classified_name = ll.findViewById(R.id.tv_classified_name);
                    TextView tv_calendar_icon = ll.findViewById(R.id.tv_calendar_icon);
                    TextView tv_time = ll.findViewById(R.id.tv_time);
                    TextView tv_price_icon = ll.findViewById(R.id.tv_price_icon);
                    TextView tv_posted_by = ll.findViewById(R.id.tv_posted_by);
                    TextView tv_sub_classified_name = ll.findViewById(R.id.tv_sub_classified_name);

                    if (!Utility.isValueNullOrEmpty(mHomePageEventsAdsBannersModel.getClassifiedsModel().get(i).getImage())) {
                        Utility.universalImageLoaderPicLoading(img_gallery_image,
                                mHomePageEventsAdsBannersModel.getClassifiedsModel().get(i).getImage(), null, R.drawable.xappie_place_);
                    } else {
                        Utility.universalImageLoaderPicLoading(img_gallery_image,
                                "", null, R.drawable.xappie_place_);
                    }
                    tv_classified_name.setTypeface(Utility.getOpenSansRegular(mParent));
                    tv_time.setTypeface(Utility.getOpenSansRegular(mParent));

                    tv_classified_name.setText(mHomePageEventsAdsBannersModel.getClassifiedsModel().get(i).getCategory());
                    tv_title.setText(mHomePageEventsAdsBannersModel.getClassifiedsModel().get(i).getTitle());
                    tv_calendar_icon.setTypeface(Utility.getFontAwesomeWebFont(mParent));
                    tv_price_icon.setTypeface(Utility.getMaterialIconsRegular(mParent));
                    tv_posted_by.setText(mHomePageEventsAdsBannersModel.getClassifiedsModel().get(i).getPrice());
                    tv_sub_classified_name.setText(mHomePageEventsAdsBannersModel.getClassifiedsModel().get(i).getSub_category());
                    tv_title.setTypeface(Utility.getOpenSansRegular(mParent));
                    tv_sub_classified_name.setTypeface(Utility.getOpenSansRegular(mParent));
                    tv_posted_by.setTypeface(Utility.getOpenSansRegular(mParent));


                    if (!Utility.isValueNullOrEmpty(mHomePageEventsAdsBannersModel.getClassifiedsModel().get(i).getRecordedDate())) {
                        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        PrettyTime prettyTime = new PrettyTime();
                        Date date;
                        String outputDateStr = "";
                        try {
                            date = inputFormat.parse(mHomePageEventsAdsBannersModel.getClassifiedsModel().get(i).getRecordedDate());
                            outputDateStr = prettyTime.format(date);
                            tv_time.setText(outputDateStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }


                    ll.setId(i);
                    ll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int position = view.getId();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.CLASSIFIEDS_ID, mHomePageEventsAdsBannersModel.getClassifiedsModel().get(position).getId());
                            Utility.navigateDashBoardFragment(new ClassifiedsDetailFragment(), ClassifiedsDetailFragment.TAG, bundle, mParent);
                        }
                    });
                    ll_classifieds.addView(view);
                    ll_classifieds.addView(ll);
                }
            }
            rl_classifieds_heading.setVisibility(View.VISIBLE);
            hs_classifieds.setVisibility(View.VISIBLE);
            hs_classifieds_inner_layout.setVisibility(View.VISIBLE);
            ll_classifieds.setVisibility(View.VISIBLE);
        } else {
            rl_classifieds_heading.setVisibility(View.GONE);
            hs_classifieds.setVisibility(View.GONE);
            hs_classifieds_inner_layout.setVisibility(View.GONE);
            ll_classifieds.setVisibility(View.GONE);
        }
    }

    private void setDataToJobs() {
        if (mHomePageEventsAdsBannersModel != null
                && mHomePageEventsAdsBannersModel.getJobsModels() != null
                && mHomePageEventsAdsBannersModel.getJobsModels().size() > 0) {
            setJobsFilters();
            ll_jobs.removeAllViews();
            if (mHomePageEventsAdsBannersModel.getJobsModels().size() > 0) {
                rl_jobs.setVisibility(View.VISIBLE);
                hs_jobs.setVisibility(View.VISIBLE);
                for (int i = 0; i < mHomePageEventsAdsBannersModel.getJobsModels().size() && i < 5; i++) {
                    LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.find_jobs_list_item, null);
                    ImageView img_news_item = (ImageView) ll.findViewById(R.id.img_logo);
                    TextView tv_title = (TextView) ll.findViewById(R.id.tv_title);
                    TextView tv_positions = (TextView) ll.findViewById(R.id.tv_positions);
                    TextView tv_posted = (TextView) ll.findViewById(R.id.tv_company_name);

                    if (!Utility.isValueNullOrEmpty(mHomePageEventsAdsBannersModel.getJobsModels().get(i).getCompany_logo())) {
                        Utility.universalImageLoaderPicLoading(img_news_item,
                                mHomePageEventsAdsBannersModel.getJobsModels().get(i).getCompany_logo(), null, R.drawable.xappie_place_holder);
                    } else {
                        Utility.universalImageLoaderPicLoading(img_news_item,
                                "", null, R.drawable.xappie_place_holder);
                    }

                    tv_title.setText(mHomePageEventsAdsBannersModel.getJobsModels().get(i).getTitle());
                    tv_title.setTypeface(Utility.getOpenSansRegular(mParent));

                    tv_positions.setText(mHomePageEventsAdsBannersModel.getJobsModels().get(i).getLocality());
                    tv_posted.setTypeface(Utility.getOpenSansRegular(mParent));
                    tv_positions.setTypeface(Utility.getOpenSansRegular(mParent));
                    tv_posted.setText(mHomePageEventsAdsBannersModel.getJobsModels().get(i).getCompany());

                    ll.setId(i);
                    ll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int position = view.getId();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.JOBS_ID, String.valueOf(mHomePageEventsAdsBannersModel.getJobsModels().get(position).getId()));
                            Utility.navigateDashBoardFragment(new JobsViewFragment(), JobsViewFragment.TAG, bundle,
                                    mParent);
                        }
                    });

                    ll_jobs.addView(ll);
                }
            }
            rl_jobs.setVisibility(View.VISIBLE);
            hs_jobs.setVisibility(View.VISIBLE);
            ll_jobs.setVisibility(View.VISIBLE);
        } else {
            rl_jobs.setVisibility(View.GONE);
            hs_jobs.setVisibility(View.GONE);
            ll_jobs.setVisibility(View.GONE);
        }
    }

    /**
     * This method is used to set the data to the screen
     */
    int page_position = 0;
    int ads_page_position = 0;

    private void setDataToBanner() {
        getHomePageData();
        card_pager.setAdapter(new HomeViewPagerAdapter(mParent, mHomePageBannerListModel.getHomePageBannerModels()));

        // addBottomDots(0);
        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == mHomePageBannerListModel.getHomePageBannerModels().size()) {
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

    /**
     * This method is used to set the data to the screen
     */
    private void setDataToTheScreen() {
        /*if (mHomePageContentModel.getVideosModels() != null &&
                mHomePageContentModel.getVideosModels().size() > 0) {
            ll_gallery_total_layout.setVisibility(View.VISIBLE);
        } else {
            ll_gallery_total_layout.setVisibility(View.GONE);
        }*/
        ll_gallery_total_layout.setVisibility(View.GONE);

        rl_discussions_heading.setVisibility(View.GONE);
        hs_discussions.setVisibility(View.GONE);

        /*rl_classifieds_heading.setVisibility(View.GONE);
        hs_classifieds_inner_layout.setVisibility(View.GONE);
        hs_classifieds.setVisibility(View.GONE);*/

       /* rl_jobs.setVisibility(View.GONE);
        hs_jobs.setVisibility(View.GONE); */

        if (mHomePageContentModel.getVideosModels() != null &&
                mHomePageContentModel.getVideosModels().size() > 0) {
            ll_videos_total_layout.setVisibility(View.VISIBLE);
            setVideosData(mHomePageContentModel.getVideosModels());
        } else {
            ll_videos_total_layout.setVisibility(View.GONE);
        }
        if (mHomePageContentModel.getTopStoriesModels() != null &&
                mHomePageContentModel.getTopStoriesModels().size() > 0) {
            rl_top_stories_heading.setVisibility(View.VISIBLE);
            hs_top_stories.setVisibility(View.VISIBLE);
            ll_top_stories.setVisibility(View.VISIBLE);
            setTopStoriesLanguages();
            setTopStoriesData(mHomePageContentModel.getTopStoriesModels());
        } else {
            rl_top_stories_heading.setVisibility(View.GONE);
            hs_top_stories.setVisibility(View.GONE);
            ll_top_stories.setVisibility(View.GONE);
        }
        if (mHomePageContentModel.getEntertainmentModels() != null &&
                mHomePageContentModel.getEntertainmentModels().size() > 0) {
            rl_entertainment_heading.setVisibility(View.VISIBLE);
            hs_entertainment.setVisibility(View.VISIBLE);
            ll_entertainment.setVisibility(View.VISIBLE);
            setEntertainmentLanguages();
            setEntertainmentData(mHomePageContentModel.getEntertainmentModels());
        } else {
            rl_entertainment_heading.setVisibility(View.GONE);
            hs_entertainment.setVisibility(View.GONE);
            ll_entertainment.setVisibility(View.GONE);
        }
        if (mHomePageContentModel.getGalleryItemModels() != null &&
                mHomePageContentModel.getGalleryItemModels().size() > 0) {
            ll_gallery_total_layout.setVisibility(View.VISIBLE);
            setGalleryData(mHomePageContentModel.getGalleryItemModels());
        } else {
            ll_gallery_total_layout.setVisibility(View.GONE);
        }
    }

    /**
     * This method is used to set the languages
     */
    private void setTopStoriesLanguages() {
        ll_languages_layout_top_stories.removeAllViews();
        for (int i = 0; i < mLanguageListModel.getLanguageModels().size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.language_item, null);
            TextView tv_language_name = (TextView) ll.findViewById(R.id.tv_language_name);
            View view = ll.findViewById(R.id.view);
            tv_language_name.setText(mLanguageListModel.getLanguageModels().get(i).getName());
            tv_language_name.setTypeface(Utility.getOpenSansRegular(mParent));

            tv_language_name.setId(i);
            tv_language_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = v.getId();
                    languageModel = mLanguageListModel.getLanguageModels().get(pos);
                    setTopStoriesLanguages();
                    getTopStoriesData(languageModel.getId(), "" + 1);
                }
            });

            if (languageModel != null && mLanguageListModel.getLanguageModels().get(i).getId() == languageModel.getId()) {
                view.setVisibility(View.VISIBLE);
                tv_language_name.setTextColor(Utility.getColor(mParent, R.color.view_color));
            } else {
                view.setVisibility(View.GONE);
            }

            ll_languages_layout_top_stories.addView(ll);
        }
    }

    /**
     * This method is used to get the all the events data from the server
     */
    private void getEventsData(String pageNo) {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            //linkedHashMap.put("type", "Public");
            linkedHashMap.put("country", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_ID));
            linkedHashMap.put("state", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID));
            linkedHashMap.put("city", stateModel.getId());
            linkedHashMap.put(Constants.PAGE_NO, pageNo);
            linkedHashMap.put(Constants.PAGE_SIZE, "7");
            EventsListParser eventsListParser = new EventsListParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_EVENTS, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, eventsListParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getClassifiedsData(String pageNo) {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            //linkedHashMap.put("type", "Public");
            linkedHashMap.put("country", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_ID));
            linkedHashMap.put("state", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID));
            linkedHashMap.put("city", stateModel.getId());
            linkedHashMap.put(Constants.PAGE_NO, pageNo);
            linkedHashMap.put(Constants.PAGE_SIZE, "7");
            ClassifiedsParser eventsListParser = new ClassifiedsParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_CLASSIFIEDS, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, eventsListParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getJobsData(String pageNo) {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            //linkedHashMap.put("type", "Public");
            linkedHashMap.put("country", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_ID));
            linkedHashMap.put("state", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID));
            linkedHashMap.put("city", stateModel.getId());
            linkedHashMap.put(Constants.PAGE_NO, pageNo);
            linkedHashMap.put(Constants.PAGE_SIZE, "7");
            JobsListParser eventsListParser = new JobsListParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_JOBS, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, eventsListParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to set the languages
     */
    private void setEventsFilters() {
        ll_languages_layout_events.removeAllViews();
        for (int i = 0; i < mStatesListModel.getStateModels().size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.language_item, null);
            TextView tv_type = (TextView) ll.findViewById(R.id.tv_language_name);
            View view = (View) ll.findViewById(R.id.view);
            tv_type.setText(mStatesListModel.getStateModels().get(i).getName());
            tv_type.setTextColor(Utility.getColor(mParent, R.color.black));
            tv_type.setTypeface(Utility.getOpenSansRegular(mParent));

            tv_type.setId(i);
            tv_type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = v.getId();
                    stateModel = mStatesListModel.getStateModels().get(pos);
                    setEventsFilters();
                    getEventsData("" + 1);
                }
            });

            if (mStatesListModel != null && mStatesListModel.getStateModels().get(i).getId() == stateModel.getId()) {
                view.setVisibility(View.VISIBLE);
                tv_type.setTextColor(Utility.getColor(mParent, R.color.view_color));

            } else {
                tv_type.setTextColor(Utility.getColor(mParent, R.color.black));
                view.setVisibility(View.GONE);
            }

            ll_languages_layout_events.addView(ll);
        }
    }


    private void setClassifiedFilters() {
        ll_languages_layout_classifieds.removeAllViews();
        for (int i = 0; i < mStatesListModel.getStateModels().size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.language_item, null);
            TextView tv_type = (TextView) ll.findViewById(R.id.tv_language_name);
            View view = (View) ll.findViewById(R.id.view);
            tv_type.setText(mStatesListModel.getStateModels().get(i).getName());
            tv_type.setTextColor(Utility.getColor(mParent, R.color.black));
            tv_type.setTypeface(Utility.getOpenSansRegular(mParent));

            tv_type.setId(i);
            tv_type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = v.getId();
                    stateModel = mStatesListModel.getStateModels().get(pos);
                    setClassifiedFilters();
                    getClassifiedsData("" + 1);
                }
            });

            if (mStatesListModel != null && mStatesListModel.getStateModels().get(i).getId() == stateModel.getId()) {
                view.setVisibility(View.VISIBLE);
                tv_type.setTextColor(Utility.getColor(mParent, R.color.view_color));

            } else {
                tv_type.setTextColor(Utility.getColor(mParent, R.color.black));
                view.setVisibility(View.GONE);
            }

            ll_languages_layout_classifieds.addView(ll);
        }
    }

    private void setJobsFilters() {
        ll_languages_layout_jobs.removeAllViews();
        for (int i = 0; i < mStatesListModel.getStateModels().size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.language_item, null);
            TextView tv_type = (TextView) ll.findViewById(R.id.tv_language_name);
            View view = (View) ll.findViewById(R.id.view);
            tv_type.setText(mStatesListModel.getStateModels().get(i).getName());
            tv_type.setTextColor(Utility.getColor(mParent, R.color.black));
            tv_type.setTypeface(Utility.getOpenSansRegular(mParent));

            tv_type.setId(i);
            tv_type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = v.getId();
                    stateModel = mStatesListModel.getStateModels().get(pos);
                    setJobsFilters();
                    getJobsData("" + 1);
                }
            });

            if (mStatesListModel != null && mStatesListModel.getStateModels().get(i).getId() == stateModel.getId()) {
                view.setVisibility(View.VISIBLE);
                tv_type.setTextColor(Utility.getColor(mParent, R.color.view_color));
            } else {
                tv_type.setTextColor(Utility.getColor(mParent, R.color.black));
                view.setVisibility(View.GONE);
            }

            ll_languages_layout_jobs.addView(ll);
        }
    }

    /**
     * This method is used to set entertainment languages
     */
    private void setEntertainmentLanguages() {
        ll_languages_layout_entertainment.removeAllViews();
        for (int i = 0; i < mLanguageListModel.getLanguageModels().size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.language_item, null);
            TextView tv_language_name = (TextView) ll.findViewById(R.id.tv_language_name);
            View view = ll.findViewById(R.id.view);
            tv_language_name.setText(mLanguageListModel.getLanguageModels().get(i).getName());
            tv_language_name.setTypeface(Utility.getOpenSansRegular(mParent));

            tv_language_name.setId(i);
            tv_language_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = v.getId();
                    languageModel = mLanguageListModel.getLanguageModels().get(pos);
                    setEntertainmentLanguages();
                    getEntertainmentData(languageModel.getId(), "" + 1);
                }
            });

            if (languageModel != null && mLanguageListModel.getLanguageModels().get(i).getId() == languageModel.getId()) {
                view.setVisibility(View.VISIBLE);
                tv_language_name.setTextColor(Utility.getColor(mParent, R.color.view_color));
            } else {
                view.setVisibility(View.GONE);
            }

            ll_languages_layout_entertainment.addView(ll);
        }
    }

    /**
     * This method is used to get data of the top stories
     */
    private void getTopStoriesData(String id, String pageNo) {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            linkedHashMap.put("language", id);
            linkedHashMap.put(Constants.PAGE_NO, pageNo);
            linkedHashMap.put(Constants.PAGE_SIZE, Constants.PAGE_SIZE_SEVEN);
            TopStoriesParser topStoriesParser = new TopStoriesParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_STORIES, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, topStoriesParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to get data of the top stories
     */
    private void getEntertainmentData(String id, String pageNo) {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            linkedHashMap.put("language", id);
            linkedHashMap.put(Constants.PAGE_NO, pageNo);
            linkedHashMap.put(Constants.PAGE_SIZE, Constants.PAGE_SIZE_SEVEN);
            EntertainmentParser entertainmentParser = new EntertainmentParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_ENTERTAINMENTS, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, entertainmentParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to set the data of the top stories
     */
    private void setTopStoriesData(ArrayList<EntertainmentModel> topStoriesModels) {
        ll_top_stories.removeAllViews();
        for (int i = 0; i < topStoriesModels.size() && i < 5; i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.news_item, null);
            ImageView img_news_item = (ImageView) ll.findViewById(R.id.img_news_item);
            TextView tv_title = (TextView) ll.findViewById(R.id.tv_title);
            TextView tv_time = (TextView) ll.findViewById(R.id.tv_time);
            TextView tv_posted_by = (TextView) ll.findViewById(R.id.tv_posted_by);

            tv_title.setText(topStoriesModels.get(i).getTitle());
            tv_posted_by.setText(topStoriesModels.get(i).getSource());
            tv_title.setTypeface(Utility.getOpenSansRegular(mParent));
            tv_posted_by.setTypeface(Utility.getOpenSansRegular(mParent));

            if (!Utility.isValueNullOrEmpty(topStoriesModels.get(i).getRecordedDate())) {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                PrettyTime prettyTime = new PrettyTime();
                Date date;
                String outputDateStr = "";
                try {
                    date = inputFormat.parse(topStoriesModels.get(i).getRecordedDate());
                    outputDateStr = prettyTime.format(date);
                    tv_time.setText(outputDateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            tv_time.setTypeface(Utility.getOpenSansRegular(mParent));

            if (!Utility.isValueNullOrEmpty(topStoriesModels.get(i).getProfile_image())) {
                Utility.universalImageLoaderPicLoading(img_news_item,
                        topStoriesModels.get(i).getProfile_image(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(img_news_item,
                        "", null, R.drawable.xappie_place_holder);
            }

            ll.setId(i);
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.SELECTED_DETAIL_VIEW_ID, mHomePageContentModel.getTopStoriesModels().get(view.getId()).getId());
                    bundle.putString(Constants.SELECTED_DETAIL_VIEW_FROM, TAG);
                    bundle.putSerializable(Constants.SELECTED_MORE_TOPICS_LIST, mHomePageContentModel.getTopStoriesModels());
                    Utility.navigateDashBoardFragment(new GalleryDetailViewFragment(), GalleryDetailViewFragment.TAG, bundle,
                            mParent);
                }
            });

            ll_top_stories.addView(ll);
        }
    }

    /**
     * Sets Entertainment data
     */
    private void setEntertainmentData(ArrayList<EntertainmentModel> topStoriesModels) {
        ll_entertainment.removeAllViews();
        for (int i = 0; i < mHomePageContentModel.getEntertainmentModels().size() && i < 5; i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.news_item, null);
            ImageView img_news_item = (ImageView) ll.findViewById(R.id.img_news_item);
            TextView tv_title = (TextView) ll.findViewById(R.id.tv_title);
            TextView tv_time = (TextView) ll.findViewById(R.id.tv_time);
            TextView tv_posted_by = (TextView) ll.findViewById(R.id.tv_posted_by);

            tv_title.setText(mHomePageContentModel.getEntertainmentModels().get(i).getTitle());
            tv_posted_by.setText(mHomePageContentModel.getEntertainmentModels().get(i).getSource());
            tv_title.setTypeface(Utility.getOpenSansRegular(mParent));

            if (!Utility.isValueNullOrEmpty(topStoriesModels.get(i).getRecordedDate())) {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                PrettyTime prettyTime = new PrettyTime();
                Date date;
                String outputDateStr = "";
                try {
                    date = inputFormat.parse(topStoriesModels.get(i).getRecordedDate());
                    outputDateStr = prettyTime.format(date);
                    tv_time.setText(outputDateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            tv_time.setTypeface(Utility.getOpenSansRegular(mParent));
            tv_posted_by.setTypeface(Utility.getOpenSansRegular(mParent));

            if (!Utility.isValueNullOrEmpty(topStoriesModels.get(i).getProfile_image())) {
                Utility.universalImageLoaderPicLoading(img_news_item,
                        topStoriesModels.get(i).getProfile_image(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(img_news_item,
                        "", null, R.drawable.xappie_place_holder);
            }

            ll.setId(i);
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.SELECTED_DETAIL_VIEW_ID, mHomePageContentModel.getEntertainmentModels().get(view.getId()).getId());
                    bundle.putString(Constants.SELECTED_DETAIL_VIEW_FROM, TAG);
                    bundle.putSerializable(Constants.SELECTED_MORE_TOPICS_LIST, mHomePageContentModel.getEntertainmentModels());
                    Utility.navigateDashBoardFragment(new GalleryDetailViewFragment(), GalleryDetailViewFragment.TAG, bundle,
                            mParent);
                }
            });

            ll_entertainment.addView(ll);
        }
    }

    /**
     * This method is used to set the data of the videos
     */
    private void setVideosData(final ArrayList<VideosModel> videosData) {
        layout_videos.removeAllViews();
        for (int i = 0; i < videosData.size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.videos_item, null);
            ImageView img_video_image = (ImageView) ll.findViewById(R.id.img_video_image);
            TextView tv_title = (TextView) ll.findViewById(R.id.tv_title);
            tv_title.setTypeface(Utility.getOpenSansRegular(mParent));

            tv_title.setText(videosData.get(i).getTitle());
            if (!Utility.isValueNullOrEmpty(videosData.get(i).getThumb_nail()))
                Utility.universalImageLoaderPicLoading(img_video_image,
                        videosData.get(i).getThumb_nail(), null, R.drawable.xappie_place_holder);
            else {
                Utility.universalImageLoaderPicLoading(img_video_image,
                        "", null, R.drawable.xappie_place_holder);
            }

            img_video_image.setId(i);
            img_video_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = view.getId();
                    VideosModel videosModel = videosData.get(position);
                    Intent intent = new Intent(mParent, VideoViewActivity.class);
                    String mVideoId = (videosModel.getUrl() != null && videosModel.getUrl().matches(Constants.pattern))
                            ? videosModel.getUrl().substring(videosModel.getUrl().length() - 11,
                            videosModel.getUrl().length())
                            : "";

                    if (!mVideoId.isEmpty()) {
                        intent.putExtra("videoId", mVideoId);
                        mParent.startActivity(intent);
                    } else {
                        Utility.showToastMessage(mParent, "Url Not Valid");
                    }
                }
            });
            layout_videos.addView(ll);
        }
    }

    /**
     * This method is used to set the gallery data
     */
    private void setGalleryData(final ArrayList<GalleryItemModel> galleryData) {
        layout_gallery.removeAllViews();
        for (int i = 0; i < galleryData.size(); i++) {
            RelativeLayout ll = (RelativeLayout) mParent.getLayoutInflater().inflate(R.layout.gallery_item, null);
            ImageView img_gallery_image = (ImageView) ll.findViewById(R.id.img_gallery_image);
            TextView tv_title = (TextView) ll.findViewById(R.id.tv_title);
            tv_title.setTypeface(Utility.getOpenSansRegular(mParent));
            tv_title.setText(galleryData.get(i).getTitle());

            if (!Utility.isValueNullOrEmpty(galleryData.get(i).getProfile_image()))
                Utility.universalImageLoaderPicLoading(img_gallery_image,
                        galleryData.get(i).getProfile_image(), null, R.drawable.xappie_place_holder);
            else {
                Utility.universalImageLoaderPicLoading(img_gallery_image,
                        "", null, R.drawable.xappie_place_holder);
            }

            img_gallery_image.setId(i);
            img_gallery_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = view.getId();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.GALLERY_ID, galleryData.get(pos).getId());
                    Utility.navigateDashBoardFragment(new GalleryImageViewFragment(), GalleryImageViewFragment.TAG, bundle, mParent);
                }
            });
            layout_gallery.addView(ll);
        }
    }

    @Override
    public void updateData() {
        getHomePageData();
        getHomePageLocData();
    }
}
