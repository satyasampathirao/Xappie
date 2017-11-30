package com.xappie.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.VideosGridAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.LanguageListModel;
import com.xappie.models.LanguageModel;
import com.xappie.models.Model;
import com.xappie.models.VideoTypeModel;
import com.xappie.models.VideoTypesListModel;
import com.xappie.models.VideosListModel;
import com.xappie.parser.LanguageParser;
import com.xappie.parser.VideoTypesParser;
import com.xappie.parser.VideosParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shankar 26/07/2017
 */
public class VideosFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = VideosFragment.class.getSimpleName();
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

    /**
     * Gallery Actress setup
     */
    @BindView(R.id.ll_video_types)
    LinearLayout ll_video_types;
    @BindView(R.id.ll_languages)
    LinearLayout ll_languages;
    @BindView(R.id.grid_view)
    GridView grid_view;


    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;
    private LanguageListModel mLanguageListModel;
    private VideosListModel mVideosListModel;
    private VideoTypesListModel mVideoTypesListModel;

    private LanguageModel languageModel;
    private VideoTypeModel videoTypeModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        mFrameLayout = (FrameLayout) getActivity().findViewById(R.id.content_frame);
        mParams = (CoordinatorLayout.LayoutParams) mFrameLayout.getLayoutParams();
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
        rootView = inflater.inflate(R.layout.fragment_videos, container, false);
        ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    private void initUI() {
        setTypeFace();
        getLanguagesData();
    }

    private void setTypeFace() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);

        tv_notification_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(Utility.getResourcesString(mParent, R.string.videos));
        tv_title.setTypeface(mTypefaceOpenSansRegular);

        tv_location_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notifications_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);
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
     * This method is used to set the grid view data
     */
    private void setGridViewData() {
        VideosGridAdapter videosGridAdapter = new VideosGridAdapter(mParent, mVideosListModel.getVideosModels());
        grid_view.setAdapter(videosGridAdapter);
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
                    videoTypeModel = null;
                    getVideoTypes();
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
     * This method is used to set the video types
     */
    private void setVideoTypes() {
        ll_video_types.removeAllViews();
        for (int i = 0; i < mVideoTypesListModel.getVideoTypeModels().size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.videos_type_item, null);
            TextView tv_type = (TextView) ll.findViewById(R.id.tv_type);
            tv_type.setText(mVideoTypesListModel.getVideoTypeModels().get(i).getName());
            tv_type.setTextColor(Utility.getColor(mParent, R.color.white));
            tv_type.setTypeface(Utility.getOpenSansRegular(mParent));

            tv_type.setId(i);
            tv_type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = v.getId();
                    videoTypeModel = mVideoTypesListModel.getVideoTypeModels().get(pos);
                    setVideoTypes();
                    getVideosData(languageModel.getId(), "" + 1, videoTypeModel.getId());
                }
            });

            if (videoTypeModel != null && mVideoTypesListModel.getVideoTypeModels().get(i).getId() == videoTypeModel.getId()) {
                tv_type.setTextColor(Utility.getColor(mParent, R.color.white));
                tv_type.setBackground(Utility.getDrawable(mParent, R.drawable.bg_color_rect));
            } else {
                tv_type.setTextColor(Utility.getColor(mParent, R.color.types_color));
                tv_type.setBackground(null);
            }

            ll_video_types.addView(ll);
        }
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
                    getVideoTypes();
                    //getVideosData(mLanguageListModel.getLanguageModels().get(0).getId(), "" + 1);
                }
            } else if (model instanceof VideoTypesListModel) {
                mVideoTypesListModel = (VideoTypesListModel) model;
                if (mVideoTypesListModel.getVideoTypeModels().size() > 0) {
                    videoTypeModel = mVideoTypesListModel.getVideoTypeModels().get(0);
                    getVideosData(languageModel.getId(), "" + 1, videoTypeModel.getId());
                    setVideoTypes();
                }
            } else if (model instanceof VideosListModel) {
                mVideosListModel = (VideosListModel) model;
                if (mVideosListModel.getVideosModels().size() > 0) {
                    setGridViewData();
                }
            }
        }
    }

    /**
     * This method is used to get data of the videos
     */
    private void getVideosData(String id, String pageNo, String type) {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            linkedHashMap.put("language", id);
            linkedHashMap.put("type", type);
            linkedHashMap.put(Constants.PAGE_NO, pageNo);
            linkedHashMap.put(Constants.PAGE_SIZE, Constants.PAGE_SIZE_VALUE);
            VideosParser videosParser = new VideosParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_VIDEOS, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, videosParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to get data of the videos
     */
    private void getVideoTypes() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            VideoTypesParser videoTypesParser = new VideoTypesParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_VIDEO_CATEGORIES, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, videoTypesParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}