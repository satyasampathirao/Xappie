package com.xappie.fragments;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.AdsPagerAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.customviews.TouchImageView;
import com.xappie.models.AdsListModel;
import com.xappie.models.AdsModel;
import com.xappie.models.GalleryLatestModel;
import com.xappie.models.GalleryModel;
import com.xappie.models.LanguageListModel;
import com.xappie.models.LanguageModel;
import com.xappie.models.Model;
import com.xappie.parser.AdsListParser;
import com.xappie.parser.GalleryLatestParser;
import com.xappie.parser.GalleryParser;
import com.xappie.parser.LanguageParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = GalleryFragment.class.getSimpleName();
    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;
    private View rootView;

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

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceOpenSansBold;
    private Typeface mTypefaceFontAwesomeWebFont;
    private Typeface mTypefaceOpenSansLight;

    private LanguageModel languageModel;
    private LanguageListModel mLanguageListModel;
    private GalleryModel mGalleryModel;
    private AdsListModel mAdsListModel;
    private GalleryLatestModel mGalleryLatestModel;
    private String mCurrentLanguage;

    /**
     * Gallery Languages setup
     */
    @BindView(R.id.ll_languages)
    LinearLayout ll_languages;

    @BindView(R.id.rl_header_layout)
    RelativeLayout rl_header_layout;
    @BindView(R.id.img_gallery_image)
    ImageView img_gallery_image;
    @BindView(R.id.tv_gallery_first_item)
    TextView tv_gallery_first_item;

    @BindView(R.id.rl_header_layout_2)
    RelativeLayout rl_header_layout_2;
    @BindView(R.id.img_gallery_image_2)
    ImageView img_gallery_image_2;
    @BindView(R.id.tv_gallery_first_item_2)
    TextView tv_gallery_first_item_2;

    /**
     * Gallery Individual item
     */
    @BindView(R.id.ll_gallery_item_1)
    LinearLayout ll_gallery_item_1;
    @BindView(R.id.gallery_img_topic_1)
    ImageView gallery_img_topic_1;
    @BindView(R.id.gallery_txt_topic_1)
    TextView gallery_txt_topic_1;

    @BindView(R.id.ll_gallery_item_2)
    LinearLayout ll_gallery_item_2;
    @BindView(R.id.gallery_img_topic_2)
    ImageView gallery_img_topic_2;
    @BindView(R.id.gallery_txt_topic_2)
    TextView gallery_txt_topic_2;

    @BindView(R.id.ll_gallery_item_3)
    LinearLayout ll_gallery_item_3;
    @BindView(R.id.gallery_img_topic_3)
    ImageView gallery_img_topic_3;
    @BindView(R.id.gallery_txt_topic_3)
    TextView gallery_txt_topic_3;

    @BindView(R.id.ll_gallery_item_4)
    LinearLayout ll_gallery_item_4;
    @BindView(R.id.gallery_img_topic_4)
    ImageView gallery_img_topic_4;
    @BindView(R.id.gallery_txt_topic_4)
    TextView gallery_txt_topic_4;

    @BindView(R.id.ll_gallery_item_5)
    LinearLayout ll_gallery_item_5;
    @BindView(R.id.gallery_img_topic_5)
    ImageView gallery_img_topic_5;
    @BindView(R.id.gallery_txt_topic_5)
    TextView gallery_txt_topic_5;

    @BindView(R.id.ll_gallery_item_6)
    LinearLayout ll_gallery_item_6;
    @BindView(R.id.gallery_img_topic_6)
    ImageView gallery_img_topic_6;
    @BindView(R.id.gallery_txt_topic_6)
    TextView gallery_txt_topic_6;

    @BindView(R.id.ll_gallery_item_7)
    LinearLayout ll_gallery_item_7;
    @BindView(R.id.gallery_img_topic_7)
    ImageView gallery_img_topic_7;
    @BindView(R.id.gallery_txt_topic_7)
    TextView gallery_txt_topic_7;

    @BindView(R.id.ll_gallery_item_8)
    LinearLayout ll_gallery_item_8;
    @BindView(R.id.gallery_img_topic_8)
    ImageView gallery_img_topic_8;
    @BindView(R.id.gallery_txt_topic_8)
    TextView gallery_txt_topic_8;

    @BindView(R.id.tv_ads)
    TextView tv_ads;
    @BindView(R.id.ads_pager)
    ViewPager ads_pager;
    private int page_position = 0;

    @BindView(R.id.tv_actress)
    TextView tv_actress;
    @BindView(R.id.tv_actors)
    TextView tv_actors;
    @BindView(R.id.tv_events)
    TextView tv_events;
    @BindView(R.id.tv_movies)
    TextView tv_movies;

    @BindView(R.id.ll_actress_item_1)
    LinearLayout ll_actress_item_1;
    @BindView(R.id.actress_img_topic_1)
    ImageView actress_img_topic_1;
    @BindView(R.id.actress_txt_topic_1)
    TextView actress_txt_topic_1;

    @BindView(R.id.ll_actress_item_2)
    LinearLayout ll_actress_item_2;
    @BindView(R.id.actress_img_topic_2)
    ImageView actress_img_topic_2;
    @BindView(R.id.actress_txt_topic_2)
    TextView actress_txt_topic_2;

    @BindView(R.id.ll_actress_item_3)
    LinearLayout ll_actress_item_3;
    @BindView(R.id.actress_img_topic_3)
    ImageView actress_img_topic_3;
    @BindView(R.id.actress_txt_topic_3)
    TextView actress_txt_topic_3;

    @BindView(R.id.ll_actress_item_4)
    LinearLayout ll_actress_item_4;
    @BindView(R.id.actress_img_topic_4)
    ImageView actress_img_topic_4;
    @BindView(R.id.actress_txt_topic_4)
    TextView actress_txt_topic_4;

    @BindView(R.id.ll_actress_item_5)
    LinearLayout ll_actress_item_5;
    @BindView(R.id.actress_img_topic_5)
    ImageView actress_img_topic_5;
    @BindView(R.id.actress_txt_topic_5)
    TextView actress_txt_topic_5;

    @BindView(R.id.ll_actors_item_1)
    LinearLayout ll_actors_item_1;
    @BindView(R.id.actors_img_topic_1)
    ImageView actors_img_topic_1;
    @BindView(R.id.actors_txt_topic_1)
    TextView actors_txt_topic_1;

    @BindView(R.id.ll_actors_item_2)
    LinearLayout ll_actors_item_2;
    @BindView(R.id.actors_img_topic_2)
    ImageView actors_img_topic_2;
    @BindView(R.id.actors_txt_topic_2)
    TextView actors_txt_topic_2;

    @BindView(R.id.ll_actors_item_3)
    LinearLayout ll_actors_item_3;
    @BindView(R.id.actors_img_topic_3)
    ImageView actors_img_topic_3;
    @BindView(R.id.actors_txt_topic_3)
    TextView actors_txt_topic_3;

    @BindView(R.id.ll_actors_item_4)
    LinearLayout ll_actors_item_4;
    @BindView(R.id.actors_img_topic_4)
    ImageView actors_img_topic_4;
    @BindView(R.id.actors_txt_topic_4)
    TextView actors_txt_topic_4;

    @BindView(R.id.ll_actors_item_5)
    LinearLayout ll_actors_item_5;
    @BindView(R.id.actors_img_topic_5)
    ImageView actors_img_topic_5;
    @BindView(R.id.actors_txt_topic_5)
    TextView actors_txt_topic_5;

    @BindView(R.id.ll_movies_item_1)
    LinearLayout ll_movies_item_1;
    @BindView(R.id.movies_img_topic_1)
    ImageView movies_img_topic_1;
    @BindView(R.id.movies_txt_topic_1)
    TextView movies_txt_topic_1;

    @BindView(R.id.ll_movies_item_2)
    LinearLayout ll_movies_item_2;
    @BindView(R.id.movies_img_topic_2)
    ImageView movies_img_topic_2;
    @BindView(R.id.movies_txt_topic_2)
    TextView movies_txt_topic_2;

    @BindView(R.id.ll_movies_item_3)
    LinearLayout ll_movies_item_3;
    @BindView(R.id.movies_img_topic_3)
    ImageView movies_img_topic_3;
    @BindView(R.id.movies_txt_topic_3)
    TextView movies_txt_topic_3;

    @BindView(R.id.ll_movies_item_4)
    LinearLayout ll_movies_item_4;
    @BindView(R.id.movies_img_topic_4)
    ImageView movies_img_topic_4;
    @BindView(R.id.movies_txt_topic_4)
    TextView movies_txt_topic_4;

    @BindView(R.id.ll_movies_item_5)
    LinearLayout ll_movies_item_5;
    @BindView(R.id.movies_img_topic_5)
    ImageView movies_img_topic_5;
    @BindView(R.id.movies_txt_topic_5)
    TextView movies_txt_topic_5;

    @BindView(R.id.ll_events_item_1)
    LinearLayout ll_events_item_1;
    @BindView(R.id.events_img_topic_1)
    ImageView events_img_topic_1;
    @BindView(R.id.events_txt_topic_1)
    TextView events_txt_topic_1;

    @BindView(R.id.ll_events_item_2)
    LinearLayout ll_events_item_2;
    @BindView(R.id.events_img_topic_2)
    ImageView events_img_topic_2;
    @BindView(R.id.events_txt_topic_2)
    TextView events_txt_topic_2;

    @BindView(R.id.ll_events_item_3)
    LinearLayout ll_events_item_3;
    @BindView(R.id.events_img_topic_3)
    ImageView events_img_topic_3;
    @BindView(R.id.events_txt_topic_3)
    TextView events_txt_topic_3;

    @BindView(R.id.ll_events_item_4)
    LinearLayout ll_events_item_4;
    @BindView(R.id.events_img_topic_4)
    ImageView events_img_topic_4;
    @BindView(R.id.events_txt_topic_4)
    TextView events_txt_topic_4;

    @BindView(R.id.ll_events_item_5)
    LinearLayout ll_events_item_5;
    @BindView(R.id.events_img_topic_5)
    ImageView events_img_topic_5;
    @BindView(R.id.events_txt_topic_5)
    TextView events_txt_topic_5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        mFrameLayout = (FrameLayout) getActivity().findViewById(R.id.content_frame);
        mParams = (CoordinatorLayout.LayoutParams) mFrameLayout.getLayoutParams();
        Utility.sendGoogleAnalytics(mParent, TAG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (appBarLayout != null) {
            mParams.setBehavior(null);
            mFrameLayout.requestLayout();
            appBarLayout.setVisibility(View.GONE);
        }
        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_gallery, container, false);
        ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    private void initUI() {
        setTypeFace();
        getLanguagesData();
        //setAdsData();
        getAdsData();
    }

    /**
     * This method is used to get the ads data
     */
    private void getAdsData() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            linkedHashMap.put("country", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_ID));
            linkedHashMap.put("state", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID));
            linkedHashMap.put("city", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID));
            AdsListParser languageParser = new AdsListParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_ADS, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, languageParser);
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
     * This method is used to set the languages
     */
    private void setLanguages() {
        ll_languages.removeAllViews();
        for (int i = 0; i < mLanguageListModel.getLanguageModels().size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.language_item, null);
            TextView tv_language_name = (TextView) ll.findViewById(R.id.tv_language_name);
            View view = ll.findViewById(R.id.view);
            tv_language_name.setText(mLanguageListModel.getLanguageModels().get(i).getName_native().toUpperCase());
            tv_language_name.setTextColor(Utility.getColor(mParent, R.color.white));
            tv_language_name.setTypeface(Utility.getOpenSansRegular(mParent));

            tv_language_name.setId(i);
            tv_language_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = v.getId();
                    languageModel = mLanguageListModel.getLanguageModels().get(pos);
                    setLanguages();
                    mCurrentLanguage = languageModel.getId();
                    getLatestGalleryData();
                }
            });

            if (languageModel != null && mLanguageListModel.getLanguageModels().get(i).getId() == languageModel.getId()) {
                view.setVisibility(View.VISIBLE);
                tv_language_name.setTextColor(Utility.getColor(mParent, R.color.white));
                view.setBackgroundColor(Utility.getColor(mParent, R.color.white));
            } else {
                view.setVisibility(View.GONE);
            }

            ll_languages.addView(ll);
        }
    }

    /**
     * This method is used for sets the typeface screen
     */
    private void setTypeFace() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefaceOpenSansBold = Utility.getOpenSansBold(mParent);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);
        mTypefaceOpenSansLight = Utility.getOpenSansLight(mParent);

        tv_notification_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_title.setTypeface(mTypefaceOpenSansRegular);

        tv_location_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notifications_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_ads.setTypeface(mTypefaceOpenSansBold);
        tv_actress.setTypeface(mTypefaceOpenSansBold);
        tv_actors.setTypeface(mTypefaceOpenSansBold);
        tv_events.setTypeface(mTypefaceOpenSansBold);
        tv_movies.setTypeface(mTypefaceOpenSansBold);
    }

    /**
     * This method is used for back from the fragment
     */
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

    @OnClick(R.id.actress_more)
    public void navigateActressMore() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TITLE, "Actress");
        bundle.putString(Constants.FOR_GALLERY, "actress");
        bundle.putSerializable(Constants.LANGUAGE_ID, languageModel);
        Utility.navigateDashBoardFragment(new ActressFragment(), ActressFragment.TAG, bundle, mParent);
    }

    @OnClick(R.id.actors_more)
    public void navigateActorsMore() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TITLE, "Actors");
        bundle.putString(Constants.FOR_GALLERY, "actors");
        bundle.putSerializable(Constants.LANGUAGE_ID, languageModel);
        Utility.navigateDashBoardFragment(new ActressFragment(), ActressFragment.TAG, bundle, mParent);
    }

    @OnClick(R.id.movies_more)
    public void navigateMoviesMore() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TITLE, "Movies");
        bundle.putString(Constants.FOR_GALLERY, "movie");
        bundle.putSerializable(Constants.LANGUAGE_ID, languageModel);
        Utility.navigateDashBoardFragment(new ActressFragment(), ActressFragment.TAG, bundle, mParent);
    }

    @OnClick(R.id.events_more)
    public void navigateEventsMore() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TITLE, "Events");
        bundle.putString(Constants.FOR_GALLERY, "events");
        bundle.putSerializable(Constants.LANGUAGE_ID, languageModel);
        Utility.navigateDashBoardFragment(new ActressFragment(), ActressFragment.TAG, bundle, mParent);
    }


    /**
     * After complete the service call back will be coming in this method
     * It returns the respective model
     */
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
                    setLanguages();
                    mCurrentLanguage = languageModel.getId();
                    getLatestGalleryData();
                }
            } else if (model instanceof GalleryLatestModel) {
                mGalleryLatestModel = (GalleryLatestModel) model;
                if (mGalleryLatestModel != null && mGalleryLatestModel.getLatestGalleryList().size() > 0) {
                    setGalleryData();
                }
            } else if (model instanceof GalleryModel) {
                mGalleryModel = (GalleryModel) model;
                if (mGalleryModel != null && mGalleryModel.getActressGalleryList().size() > 0) {
                    setActressData();
                    setActorsData();
                    setEventsData();
                    setMoviesData();
                }
            } else if (model instanceof AdsListModel) {
                mAdsListModel = (AdsListModel) model;
                setAdsData();
            }
        }
    }

    /**
     * This method is used to get the latest gallery data
     */
    private void getLatestGalleryData() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            linkedHashMap.put("language", mCurrentLanguage);
            linkedHashMap.put(Constants.PAGE_NO, "1");
            linkedHashMap.put(Constants.PAGE_SIZE, Constants.PAGE_SIZE_VALUE);
            GalleryLatestParser galleryLatestParser = new GalleryLatestParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_LATEST_GALLERY, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, galleryLatestParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to navigate gallery detail view
     */
    private void navigateGalleryDetailView(String id) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.GALLERY_ID, id);
        Utility.navigateDashBoardFragment(new GalleryImageViewFragment(), GalleryImageViewFragment.TAG, bundle, mParent);
    }

    /**
     * This method is used to set the gallery data
     */
    private void setGalleryData() {

        if (!Utility.isValueNullOrEmpty(mGalleryLatestModel.getLatestGalleryList().get(0).getBanner_image())) {
            Utility.universalImageLoaderPicLoading(img_gallery_image,
                    mGalleryLatestModel.getLatestGalleryList().get(0).getBanner_image(), null, R.drawable.xappie_place_holder);
        } else {
            Utility.universalImageLoaderPicLoading(img_gallery_image,
                    "", null, R.drawable.xappie_place_holder);
        }
        tv_gallery_first_item.setText(mGalleryLatestModel.getLatestGalleryList().get(0).getTitle());
        tv_gallery_first_item.setTypeface(Utility.getOpenSansRegular(mParent));
        rl_header_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateGalleryDetailView(mGalleryLatestModel.getLatestGalleryList().get(0).getId());
            }
        });

        if (mGalleryLatestModel.getLatestGalleryList().size() >= 2) {
            ll_gallery_item_1.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryLatestModel.getLatestGalleryList().get(1).getProfile_image())) {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_1,
                        mGalleryLatestModel.getLatestGalleryList().get(1).getProfile_image(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_1,
                        "", null, R.drawable.xappie_place_holder);
            }
            gallery_txt_topic_1.setText(mGalleryLatestModel.getLatestGalleryList().get(1).getTitle());
            gallery_txt_topic_1.setTypeface(Utility.getOpenSansRegular(mParent));

            ll_gallery_item_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryDetailView(mGalleryLatestModel.getLatestGalleryList().get(1).getId());
                }
            });
        }
        if (mGalleryLatestModel.getLatestGalleryList().size() >= 3) {
            ll_gallery_item_2.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryLatestModel.getLatestGalleryList().get(2).getProfile_image())) {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_2,
                        mGalleryLatestModel.getLatestGalleryList().get(2).getProfile_image(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_2,
                        "", null, R.drawable.xappie_place_holder);
            }
            gallery_txt_topic_2.setText(mGalleryLatestModel.getLatestGalleryList().get(2).getTitle());
            gallery_txt_topic_2.setTypeface(Utility.getOpenSansRegular(mParent));

            ll_gallery_item_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryDetailView(mGalleryLatestModel.getLatestGalleryList().get(2).getId());
                }
            });
        }
        if (mGalleryLatestModel.getLatestGalleryList().size() >= 4) {
            ll_gallery_item_3.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryLatestModel.getLatestGalleryList().get(3).getProfile_image())) {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_3,
                        mGalleryLatestModel.getLatestGalleryList().get(3).getProfile_image(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_3,
                        "", null, R.drawable.xappie_place_holder);
            }
            gallery_txt_topic_3.setText(mGalleryLatestModel.getLatestGalleryList().get(3).getTitle());
            gallery_txt_topic_3.setTypeface(Utility.getOpenSansRegular(mParent));

            ll_gallery_item_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryDetailView(mGalleryLatestModel.getLatestGalleryList().get(3).getId());
                }
            });
        }
        if (mGalleryLatestModel.getLatestGalleryList().size() >= 5) {
            ll_gallery_item_4.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryLatestModel.getLatestGalleryList().get(4).getProfile_image())) {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_4,
                        mGalleryLatestModel.getLatestGalleryList().get(4).getProfile_image(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_4,
                        "", null, R.drawable.xappie_place_holder);
            }
            gallery_txt_topic_4.setText(mGalleryLatestModel.getLatestGalleryList().get(4).getTitle());
            gallery_txt_topic_4.setTypeface(Utility.getOpenSansRegular(mParent));

            ll_gallery_item_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryDetailView(mGalleryLatestModel.getLatestGalleryList().get(4).getId());
                }
            });
        }

        if (mGalleryLatestModel.getLatestGalleryList().size() >= 6) {
            rl_header_layout_2.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryLatestModel.getLatestGalleryList().get(5).getBanner_image())) {
                Utility.universalImageLoaderPicLoading(img_gallery_image_2,
                        mGalleryLatestModel.getLatestGalleryList().get(5).getBanner_image(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(img_gallery_image_2,
                        "", null, R.drawable.xappie_place_holder);
            }
            tv_gallery_first_item_2.setText(mGalleryLatestModel.getLatestGalleryList().get(5).getTitle());
            tv_gallery_first_item_2.setTypeface(Utility.getOpenSansRegular(mParent));

            rl_header_layout_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryDetailView(mGalleryLatestModel.getLatestGalleryList().get(5).getId());
                }
            });
        }

        if (mGalleryLatestModel.getLatestGalleryList().size() >= 7) {
            ll_gallery_item_5.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryLatestModel.getLatestGalleryList().get(6).getProfile_image())) {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_5,
                        mGalleryLatestModel.getLatestGalleryList().get(6).getProfile_image(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_5,
                        "", null, R.drawable.xappie_place_holder);
            }
            gallery_txt_topic_5.setText(mGalleryLatestModel.getLatestGalleryList().get(6).getTitle());
            gallery_txt_topic_5.setTypeface(Utility.getOpenSansRegular(mParent));

            ll_gallery_item_5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryDetailView(mGalleryLatestModel.getLatestGalleryList().get(6).getId());
                }
            });
        }

        if (mGalleryLatestModel.getLatestGalleryList().size() >= 8) {
            ll_gallery_item_6.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryLatestModel.getLatestGalleryList().get(7).getProfile_image())) {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_6,
                        mGalleryLatestModel.getLatestGalleryList().get(7).getProfile_image(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_6,
                        "", null, R.drawable.xappie_place_holder);
            }
            gallery_txt_topic_6.setText(mGalleryLatestModel.getLatestGalleryList().get(7).getTitle());
            gallery_txt_topic_6.setTypeface(Utility.getOpenSansRegular(mParent));

            ll_gallery_item_6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryDetailView(mGalleryLatestModel.getLatestGalleryList().get(7).getId());
                }
            });
        }

        if (mGalleryLatestModel.getLatestGalleryList().size() >= 9) {
            ll_gallery_item_7.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryLatestModel.getLatestGalleryList().get(8).getProfile_image())) {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_7,
                        mGalleryLatestModel.getLatestGalleryList().get(8).getProfile_image(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_7,
                        "", null, R.drawable.xappie_place_holder);
            }
            gallery_txt_topic_7.setText(mGalleryLatestModel.getLatestGalleryList().get(8).getTitle());
            gallery_txt_topic_7.setTypeface(Utility.getOpenSansRegular(mParent));

            ll_gallery_item_7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryDetailView(mGalleryLatestModel.getLatestGalleryList().get(8).getId());
                }
            });
        }
        if (mGalleryLatestModel.getLatestGalleryList().size() >= 10) {
            ll_gallery_item_8.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryLatestModel.getLatestGalleryList().get(9).getProfile_image())) {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_8,
                        mGalleryLatestModel.getLatestGalleryList().get(9).getProfile_image(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_8,
                        "", null, R.drawable.xappie_place_holder);
            }
            gallery_txt_topic_8.setText(mGalleryLatestModel.getLatestGalleryList().get(9).getTitle());
            gallery_txt_topic_8.setTypeface(Utility.getOpenSansRegular(mParent));

            ll_gallery_item_8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryDetailView(mGalleryLatestModel.getLatestGalleryList().get(9).getId());
                }
            });
        }
        getGalleryData();
    }

    /**
     * This method is used to set the actress data
     */
    private void setActressData() {
        if (mGalleryModel.getActressGalleryList().size() >= 1) {
            ll_actress_item_1.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getActressGalleryList().get(0).getImage())) {
                Utility.universalImageLoaderPicLoading(actress_img_topic_1,
                        mGalleryModel.getActressGalleryList().get(0).getImage(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(actress_img_topic_1,
                        "", null, R.drawable.xappie_place_holder);
            }
            actress_txt_topic_1.setText(mGalleryModel.getActressGalleryList().get(0).getName());
            actress_txt_topic_1.setTypeface(mTypefaceOpenSansRegular);
            ll_actress_item_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryCategoryView(mGalleryModel.getActressGalleryList().get(0).getId());
                }
            });
        }
        if (mGalleryModel.getActressGalleryList().size() >= 2) {
            ll_actress_item_2.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getActressGalleryList().get(1).getImage())) {
                Utility.universalImageLoaderPicLoading(actress_img_topic_2,
                        mGalleryModel.getActressGalleryList().get(1).getImage(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(actress_img_topic_2,
                        "", null, R.drawable.xappie_place_holder);
            }
            actress_txt_topic_2.setText(mGalleryModel.getActressGalleryList().get(1).getName());
            actress_txt_topic_2.setTypeface(mTypefaceOpenSansRegular);
            ll_actress_item_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryCategoryView(mGalleryModel.getActressGalleryList().get(1).getId());
                }
            });
        }
        if (mGalleryModel.getActressGalleryList().size() >= 3) {
            ll_actress_item_3.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getActressGalleryList().get(2).getImage())) {
                Utility.universalImageLoaderPicLoading(actress_img_topic_3,
                        mGalleryModel.getActressGalleryList().get(2).getImage(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(actress_img_topic_3,
                        "", null, R.drawable.xappie_place_holder);
            }
            actress_txt_topic_3.setText(mGalleryModel.getActressGalleryList().get(2).getName());
            actress_txt_topic_3.setTypeface(mTypefaceOpenSansRegular);
            ll_actress_item_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryCategoryView(mGalleryModel.getActressGalleryList().get(2).getId());
                }
            });
        }
        if (mGalleryModel.getActressGalleryList().size() >= 4) {
            ll_actress_item_4.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getActressGalleryList().get(3).getImage())) {
                Utility.universalImageLoaderPicLoading(actress_img_topic_4,
                        mGalleryModel.getActressGalleryList().get(3).getImage(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(actress_img_topic_4,
                        "", null, R.drawable.xappie_place_holder);
            }
            actress_txt_topic_4.setText(mGalleryModel.getActressGalleryList().get(3).getName());
            actress_txt_topic_4.setTypeface(mTypefaceOpenSansRegular);
            ll_actress_item_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryCategoryView(mGalleryModel.getActressGalleryList().get(3).getId());
                }
            });
        }

        if (mGalleryModel.getActressGalleryList().size() >= 5) {
            ll_actress_item_5.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getActressGalleryList().get(4).getImage())) {
                Utility.universalImageLoaderPicLoading(actress_img_topic_5,
                        mGalleryModel.getActressGalleryList().get(4).getImage(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(actress_img_topic_5,
                        "", null, R.drawable.xappie_place_holder);
            }
            actress_txt_topic_5.setText(mGalleryModel.getActressGalleryList().get(4).getName());
            actress_txt_topic_5.setTypeface(mTypefaceOpenSansRegular);
            ll_actress_item_5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryCategoryView(mGalleryModel.getActressGalleryList().get(4).getId());
                }
            });
        }
    }


    /**
     * This method is used to navigate gallery detail view
     */
    private void navigateGalleryCategoryView(String id) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.GALLERY_ID, id);
        Utility.navigateDashBoardFragment(new GalleryCategoryFragment(), GalleryCategoryFragment.TAG, bundle, mParent);
    }

    /**
     * This method is used to set the actors data
     */
    private void setActorsData() {
        if (mGalleryModel.getActorsGalleryList().size() >= 1) {
            ll_actors_item_1.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getActorsGalleryList().get(0).getImage())) {
                Utility.universalImageLoaderPicLoading(actors_img_topic_1,
                        mGalleryModel.getActorsGalleryList().get(0).getImage(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(actors_img_topic_1,
                        "", null, R.drawable.xappie_place_holder);
            }
            actors_txt_topic_1.setText(mGalleryModel.getActorsGalleryList().get(0).getName());
            actors_txt_topic_1.setTypeface(mTypefaceOpenSansRegular);
            ll_actors_item_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryCategoryView(mGalleryModel.getActorsGalleryList().get(0).getId());
                }
            });
        }
        if (mGalleryModel.getActorsGalleryList().size() >= 2) {
            ll_actors_item_2.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getActorsGalleryList().get(1).getImage())) {
                Utility.universalImageLoaderPicLoading(actors_img_topic_2,
                        mGalleryModel.getActorsGalleryList().get(1).getImage(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(actors_img_topic_2,
                        "", null, R.drawable.xappie_place_holder);
            }
            actors_txt_topic_2.setText(mGalleryModel.getActorsGalleryList().get(1).getName());
            actors_txt_topic_2.setTypeface(mTypefaceOpenSansRegular);
            ll_actors_item_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryCategoryView(mGalleryModel.getActorsGalleryList().get(1).getId());
                }
            });
        }
        if (mGalleryModel.getActorsGalleryList().size() >= 3) {
            ll_actors_item_3.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getActorsGalleryList().get(2).getImage())) {
                Utility.universalImageLoaderPicLoading(actors_img_topic_3,
                        mGalleryModel.getActorsGalleryList().get(2).getImage(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(actors_img_topic_3,
                        "", null, R.drawable.xappie_place_holder);
            }
            actors_txt_topic_3.setText(mGalleryModel.getActorsGalleryList().get(2).getName());
            actors_txt_topic_3.setTypeface(mTypefaceOpenSansRegular);
            ll_actors_item_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryCategoryView(mGalleryModel.getActorsGalleryList().get(2).getId());
                }
            });
        }
        if (mGalleryModel.getActorsGalleryList().size() >= 4) {
            ll_actors_item_4.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getActorsGalleryList().get(3).getImage())) {
                Utility.universalImageLoaderPicLoading(actors_img_topic_4,
                        mGalleryModel.getActorsGalleryList().get(3).getImage(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(actors_img_topic_4,
                        "", null, R.drawable.xappie_place_holder);
            }
            actors_txt_topic_4.setText(mGalleryModel.getActorsGalleryList().get(3).getName());
            actors_txt_topic_4.setTypeface(mTypefaceOpenSansRegular);
            ll_actors_item_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryCategoryView(mGalleryModel.getActorsGalleryList().get(3).getId());
                }
            });
        }

        if (mGalleryModel.getActorsGalleryList().size() >= 5) {
            ll_actors_item_5.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getActorsGalleryList().get(4).getImage())) {
                Utility.universalImageLoaderPicLoading(actors_img_topic_5,
                        mGalleryModel.getActorsGalleryList().get(4).getImage(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(actors_img_topic_5,
                        "", null, R.drawable.xappie_place_holder);
            }
            actors_txt_topic_5.setText(mGalleryModel.getActorsGalleryList().get(4).getName());
            actors_txt_topic_5.setTypeface(mTypefaceOpenSansRegular);
            ll_actors_item_5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryCategoryView(mGalleryModel.getActorsGalleryList().get(4).getId());
                }
            });
        }
    }

    /**
     * This method is used to set the movies data
     */
    private void setMoviesData() {
        if (mGalleryModel.getMoviesGalleryList().size() >= 1) {
            ll_movies_item_1.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getMoviesGalleryList().get(0).getImage())) {
                Utility.universalImageLoaderPicLoading(movies_img_topic_1,
                        mGalleryModel.getMoviesGalleryList().get(0).getImage(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(movies_img_topic_1,
                        "", null, R.drawable.xappie_place_holder);
            }
            movies_txt_topic_1.setText(mGalleryModel.getMoviesGalleryList().get(0).getName());
            movies_txt_topic_1.setTypeface(mTypefaceOpenSansRegular);
            ll_movies_item_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryCategoryView(mGalleryModel.getMoviesGalleryList().get(0).getId());
                }
            });
        }
        if (mGalleryModel.getMoviesGalleryList().size() >= 2) {
            ll_movies_item_2.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getMoviesGalleryList().get(1).getImage())) {
                Utility.universalImageLoaderPicLoading(movies_img_topic_2,
                        mGalleryModel.getMoviesGalleryList().get(1).getImage(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(movies_img_topic_2,
                        "", null, R.drawable.xappie_place_holder);
            }
            movies_txt_topic_2.setText(mGalleryModel.getMoviesGalleryList().get(1).getName());
            movies_txt_topic_2.setTypeface(mTypefaceOpenSansRegular);
            ll_movies_item_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryCategoryView(mGalleryModel.getMoviesGalleryList().get(1).getId());
                }
            });
        }
        if (mGalleryModel.getMoviesGalleryList().size() >= 3) {
            ll_movies_item_3.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getMoviesGalleryList().get(2).getImage())) {
                Utility.universalImageLoaderPicLoading(movies_img_topic_3,
                        mGalleryModel.getMoviesGalleryList().get(2).getImage(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(movies_img_topic_3,
                        "", null, R.drawable.xappie_place_holder);
            }
            movies_txt_topic_3.setText(mGalleryModel.getMoviesGalleryList().get(2).getName());
            movies_txt_topic_3.setTypeface(mTypefaceOpenSansRegular);
            ll_movies_item_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryCategoryView(mGalleryModel.getMoviesGalleryList().get(2).getId());
                }
            });
        }
        if (mGalleryModel.getMoviesGalleryList().size() >= 4) {
            ll_movies_item_4.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getMoviesGalleryList().get(3).getImage())) {
                Utility.universalImageLoaderPicLoading(movies_img_topic_4,
                        mGalleryModel.getMoviesGalleryList().get(3).getImage(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(movies_img_topic_4,
                        "", null, R.drawable.xappie_place_holder);
            }
            movies_txt_topic_4.setText(mGalleryModel.getMoviesGalleryList().get(3).getName());
            movies_txt_topic_4.setTypeface(mTypefaceOpenSansRegular);
            ll_movies_item_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryCategoryView(mGalleryModel.getMoviesGalleryList().get(3).getId());
                }
            });
        }

        if (mGalleryModel.getMoviesGalleryList().size() >= 5) {
            ll_movies_item_5.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getMoviesGalleryList().get(4).getImage())) {
                Utility.universalImageLoaderPicLoading(movies_img_topic_5,
                        mGalleryModel.getMoviesGalleryList().get(4).getImage(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(movies_img_topic_5,
                        "", null, R.drawable.xappie_place_holder);
            }
            movies_txt_topic_5.setText(mGalleryModel.getMoviesGalleryList().get(4).getName());
            movies_txt_topic_5.setTypeface(mTypefaceOpenSansRegular);
            ll_movies_item_5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryCategoryView(mGalleryModel.getMoviesGalleryList().get(4).getId());
                }
            });
        }
    }

    /**
     * This method is used to set the events data
     */
    private void setEventsData() {
        if (mGalleryModel.getEventsGalleryList().size() >= 1) {
            ll_events_item_1.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getEventsGalleryList().get(0).getImage())) {
                Utility.universalImageLoaderPicLoading(events_img_topic_1,
                        mGalleryModel.getEventsGalleryList().get(0).getImage(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(events_img_topic_1,
                        "", null, R.drawable.xappie_place_holder);
            }
            events_txt_topic_1.setText(mGalleryModel.getEventsGalleryList().get(0).getName());
            events_txt_topic_1.setTypeface(mTypefaceOpenSansRegular);
            ll_events_item_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryCategoryView(mGalleryModel.getEventsGalleryList().get(0).getId());
                }
            });
        }
        if (mGalleryModel.getEventsGalleryList().size() >= 2) {
            ll_events_item_2.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getEventsGalleryList().get(1).getImage())) {
                Utility.universalImageLoaderPicLoading(events_img_topic_2,
                        mGalleryModel.getEventsGalleryList().get(1).getImage(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(events_img_topic_2,
                        "", null, R.drawable.xappie_place_holder);
            }
            events_txt_topic_2.setText(mGalleryModel.getEventsGalleryList().get(1).getName());
            events_txt_topic_2.setTypeface(mTypefaceOpenSansRegular);
            ll_events_item_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryCategoryView(mGalleryModel.getEventsGalleryList().get(1).getId());
                }
            });
        }
        if (mGalleryModel.getEventsGalleryList().size() >= 3) {
            ll_events_item_3.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getEventsGalleryList().get(2).getImage())) {
                Utility.universalImageLoaderPicLoading(events_img_topic_3,
                        mGalleryModel.getEventsGalleryList().get(2).getImage(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(events_img_topic_3,
                        "", null, R.drawable.xappie_place_holder);
            }
            events_txt_topic_3.setText(mGalleryModel.getEventsGalleryList().get(2).getName());
            events_txt_topic_3.setTypeface(mTypefaceOpenSansRegular);
            ll_events_item_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryCategoryView(mGalleryModel.getEventsGalleryList().get(2).getId());
                }
            });
        }
        if (mGalleryModel.getEventsGalleryList().size() >= 4) {
            ll_events_item_4.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getEventsGalleryList().get(3).getImage())) {
                Utility.universalImageLoaderPicLoading(events_img_topic_4,
                        mGalleryModel.getEventsGalleryList().get(3).getImage(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(events_img_topic_4,
                        "", null, R.drawable.xappie_place_holder);
            }
            events_txt_topic_4.setText(mGalleryModel.getEventsGalleryList().get(3).getName());
            events_txt_topic_4.setTypeface(mTypefaceOpenSansRegular);
            ll_events_item_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryCategoryView(mGalleryModel.getEventsGalleryList().get(3).getId());
                }
            });
        }

        if (mGalleryModel.getEventsGalleryList().size() >= 5) {
            ll_events_item_5.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getEventsGalleryList().get(4).getImage())) {
                Utility.universalImageLoaderPicLoading(events_img_topic_5,
                        mGalleryModel.getEventsGalleryList().get(4).getImage(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(events_img_topic_5,
                        "", null, R.drawable.xappie_place_holder);
            }
            events_txt_topic_5.setText(mGalleryModel.getEventsGalleryList().get(4).getName());
            events_txt_topic_5.setTypeface(mTypefaceOpenSansRegular);
            ll_events_item_5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateGalleryCategoryView(mGalleryModel.getEventsGalleryList().get(4).getId());
                }
            });
        }
    }

    /**
     * Get the Gallery data
     */
    private void getGalleryData() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            linkedHashMap.put("language", mCurrentLanguage);
            //linkedHashMap.put("type", "Actress");
            linkedHashMap.put(Constants.PAGE_NO, "1");
            linkedHashMap.put(Constants.PAGE_SIZE, Constants.PAGE_SIZE_VALUE);
            GalleryParser galleryParser = new GalleryParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_GALLERY_CATEGORIES, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, galleryParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used set the ads data
     */
    private void setAdsData() {
        if (mAdsListModel != null && mAdsListModel.getAdsModels() != null && mAdsListModel.getAdsModels().size() > 0) {
            ads_pager.setAdapter(new AdsPagerAdapter(mParent, mAdsListModel.getAdsModels()));
            final Handler handler = new Handler();
            final Runnable update = new Runnable() {
                public void run() {
                    if (page_position == mAdsListModel.getAdsModels().size()) {
                        page_position = 0;
                    } else {
                        page_position = page_position + 2;
                    }
                    ads_pager.setCurrentItem(page_position, true);
                }
            };
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            }, 100, 4000);
            ads_pager.setVisibility(View.VISIBLE);
            tv_ads.setVisibility(View.VISIBLE);
        } else {
            ads_pager.setVisibility(View.GONE);
            tv_ads.setVisibility(View.GONE);
        }

    }

    /**
     * Image full view
     */
    private void showFitDialog(String url, Context context) {
        Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_fitcenter);
        dialog.setCanceledOnTouchOutside(false);
        TouchImageView imageView = (TouchImageView) dialog.findViewById(R.id.imageView);
        Picasso.with(context)
                .load(url)
                .placeholder(Utility.getDrawable(context, R.drawable.xappie_place_holder))
                .into(imageView);
        dialog.show();
    }


    private ArrayList<AdsModel> getAdsSizes() {
        ArrayList<AdsModel> adsModels = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            AdsModel adsModel = new AdsModel();
            adsModel.setSsds(R.drawable.ads);
            adsModels.add(adsModel);
        }
        return adsModels;
    }
}
