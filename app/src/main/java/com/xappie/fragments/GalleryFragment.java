package com.xappie.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.AdsModel;
import com.xappie.models.GalleryModel;
import com.xappie.models.LanguageListModel;
import com.xappie.models.LanguageModel;
import com.xappie.models.Model;
import com.xappie.parser.GalleryParser;
import com.xappie.parser.LanguageParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.ArrayList;
import java.util.LinkedHashMap;

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
    private Typeface mTypefaceFontAwesomeWebFont;

    private LanguageModel languageModel;
    private LanguageListModel mLanguageListModel;
    private GalleryModel mGalleryModel;
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

    @BindView(R.id.tv_ads)
    TextView tv_ads;
    @BindView(R.id.layout_ads)
    LinearLayout layout_ads;

    @BindView(R.id.tv_actress)
    TextView tv_actress;
    @BindView(R.id.tv_actors)
    TextView tv_actors;
    @BindView(R.id.tv_events)
    TextView tv_events;

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
        rootView = inflater.inflate(R.layout.fragment_gallery, container, false);
        ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    private void initUI() {
        setTypeFace();
        getLanguagesData();
        setAdsData();
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
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);

        tv_notification_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_title.setTypeface(mTypefaceOpenSansRegular);

        tv_location_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notifications_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_ads.setTypeface(mTypefaceOpenSansRegular);
        tv_actress.setTypeface(mTypefaceOpenSansRegular);
        tv_actors.setTypeface(mTypefaceOpenSansRegular);
        tv_events.setTypeface(mTypefaceOpenSansRegular);
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
     * After complete the service call back will be coming in this method
     * It returns the respective model
     */
    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof LanguageListModel) {
                mLanguageListModel = (LanguageListModel) model;
                if (mLanguageListModel.getLanguageModels().size() > 0) {
                    languageModel = mLanguageListModel.getLanguageModels().get(0);
                    setLanguages();
                    mCurrentLanguage = mLanguageListModel.getLanguageModels().get(0).getId();
                    getGalleryData();
                }
            } else if (model instanceof GalleryModel) {
                mGalleryModel = (GalleryModel) model;
                if (mGalleryModel != null && mGalleryModel.getMoviesGalleryList().size() > 0) {
                    setGalleryData();
                }
            }
        }
    }

    /**
     * This method is used to set the gallery data
     */
    private void setGalleryData() {

        if (!Utility.isValueNullOrEmpty(mGalleryModel.getMoviesGalleryList().get(0).getBanner_image())) {
            Utility.universalImageLoaderPicLoading(img_gallery_image,
                    mGalleryModel.getMoviesGalleryList().get(0).getBanner_image(), null, R.drawable.xappie_place_holder);
        } else {
            Utility.universalImageLoaderPicLoading(img_gallery_image,
                    "", null, R.drawable.xappie_place_holder);
        }
        tv_gallery_first_item.setText(mGalleryModel.getMoviesGalleryList().get(0).getTitle());
        tv_gallery_first_item.setTypeface(Utility.getOpenSansRegular(mParent));

        if (mGalleryModel.getMoviesGalleryList().size() >= 2) {
            ll_gallery_item_1.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getMoviesGalleryList().get(1).getBanner_image())) {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_1,
                        mGalleryModel.getMoviesGalleryList().get(1).getBanner_image(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_1,
                        "", null, R.drawable.xappie_place_holder);
            }
            gallery_txt_topic_1.setText(mGalleryModel.getMoviesGalleryList().get(1).getTitle());
            gallery_txt_topic_1.setTypeface(Utility.getOpenSansRegular(mParent));
        }
        if (mGalleryModel.getMoviesGalleryList().size() >= 3) {
            ll_gallery_item_2.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getMoviesGalleryList().get(2).getBanner_image())) {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_2,
                        mGalleryModel.getMoviesGalleryList().get(2).getBanner_image(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_2,
                        "", null, R.drawable.xappie_place_holder);
            }
            gallery_txt_topic_2.setText(mGalleryModel.getMoviesGalleryList().get(2).getTitle());
            gallery_txt_topic_2.setTypeface(Utility.getOpenSansRegular(mParent));
        }
        if (mGalleryModel.getMoviesGalleryList().size() >= 4) {
            ll_gallery_item_3.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getMoviesGalleryList().get(3).getBanner_image())) {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_3,
                        mGalleryModel.getMoviesGalleryList().get(3).getBanner_image(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_3,
                        "", null, R.drawable.xappie_place_holder);
            }
            gallery_txt_topic_3.setText(mGalleryModel.getMoviesGalleryList().get(3).getTitle());
            gallery_txt_topic_3.setTypeface(Utility.getOpenSansRegular(mParent));
        }
        if (mGalleryModel.getMoviesGalleryList().size() >= 5) {
            ll_gallery_item_4.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getMoviesGalleryList().get(4).getBanner_image())) {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_4,
                        mGalleryModel.getMoviesGalleryList().get(4).getBanner_image(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_4,
                        "", null, R.drawable.xappie_place_holder);
            }
            gallery_txt_topic_4.setText(mGalleryModel.getMoviesGalleryList().get(4).getTitle());
            gallery_txt_topic_4.setTypeface(Utility.getOpenSansRegular(mParent));
        }

        if (mGalleryModel.getMoviesGalleryList().size() >= 6) {
            rl_header_layout_2.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getMoviesGalleryList().get(5).getBanner_image())) {
                Utility.universalImageLoaderPicLoading(img_gallery_image_2,
                        mGalleryModel.getMoviesGalleryList().get(5).getBanner_image(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(img_gallery_image_2,
                        "", null, R.drawable.xappie_place_holder);
            }
            tv_gallery_first_item_2.setText(mGalleryModel.getMoviesGalleryList().get(5).getTitle());
            tv_gallery_first_item_2.setTypeface(Utility.getOpenSansRegular(mParent));
        }

        if (mGalleryModel.getMoviesGalleryList().size() >= 7) {
            ll_gallery_item_5.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getMoviesGalleryList().get(6).getBanner_image())) {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_5,
                        mGalleryModel.getMoviesGalleryList().get(6).getBanner_image(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_5,
                        "", null, R.drawable.xappie_place_holder);
            }
            gallery_txt_topic_5.setText(mGalleryModel.getMoviesGalleryList().get(6).getTitle());
            gallery_txt_topic_5.setTypeface(Utility.getOpenSansRegular(mParent));
        }

        if (mGalleryModel.getMoviesGalleryList().size() >= 8) {
            ll_gallery_item_6.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getMoviesGalleryList().get(7).getBanner_image())) {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_6,
                        mGalleryModel.getMoviesGalleryList().get(7).getBanner_image(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_6,
                        "", null, R.drawable.xappie_place_holder);
            }
            gallery_txt_topic_6.setText(mGalleryModel.getMoviesGalleryList().get(7).getTitle());
            gallery_txt_topic_6.setTypeface(Utility.getOpenSansRegular(mParent));
        }

        if (mGalleryModel.getMoviesGalleryList().size() >= 9) {
            ll_gallery_item_7.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getMoviesGalleryList().get(8).getBanner_image())) {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_7,
                        mGalleryModel.getMoviesGalleryList().get(8).getBanner_image(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(gallery_img_topic_7,
                        "", null, R.drawable.xappie_place_holder);
            }
            gallery_txt_topic_7.setText(mGalleryModel.getMoviesGalleryList().get(8).getTitle());
            gallery_txt_topic_7.setTypeface(Utility.getOpenSansRegular(mParent));
        }

        setActressData();
    }

    /**
     * This method is used to set the actress data
     */
    private void setActressData() {
        if (mGalleryModel.getActressGalleryList().size() >= 1) {
            ll_actress_item_1.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getActressGalleryList().get(0).getBanner_image())) {
                Utility.universalImageLoaderPicLoading(actress_img_topic_1,
                        mGalleryModel.getActressGalleryList().get(0).getBanner_image(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(actress_img_topic_1,
                        "", null, R.drawable.xappie_place_holder);
            }
            actress_txt_topic_1.setText(mGalleryModel.getActressGalleryList().get(0).getTitle());
            actress_txt_topic_1.setTypeface(mTypefaceOpenSansRegular);
        }
        if (mGalleryModel.getActressGalleryList().size() >= 2) {
            ll_actress_item_2.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getActressGalleryList().get(1).getBanner_image())) {
                Utility.universalImageLoaderPicLoading(actress_img_topic_2,
                        mGalleryModel.getActressGalleryList().get(1).getBanner_image(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(actress_img_topic_2,
                        "", null, R.drawable.xappie_place_holder);
            }
            actress_txt_topic_2.setText(mGalleryModel.getActressGalleryList().get(1).getTitle());
            actress_txt_topic_2.setTypeface(mTypefaceOpenSansRegular);
        }
        if (mGalleryModel.getActressGalleryList().size() >= 3) {
            ll_actress_item_3.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getActressGalleryList().get(2).getBanner_image())) {
                Utility.universalImageLoaderPicLoading(actress_img_topic_3,
                        mGalleryModel.getActressGalleryList().get(2).getBanner_image(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(actress_img_topic_3,
                        "", null, R.drawable.xappie_place_holder);
            }
            actress_txt_topic_3.setText(mGalleryModel.getActressGalleryList().get(2).getTitle());
            actress_txt_topic_3.setTypeface(mTypefaceOpenSansRegular);
        }
        if (mGalleryModel.getActressGalleryList().size() >= 4) {
            ll_actress_item_4.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getActressGalleryList().get(3).getBanner_image())) {
                Utility.universalImageLoaderPicLoading(actress_img_topic_4,
                        mGalleryModel.getActressGalleryList().get(3).getBanner_image(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(actress_img_topic_4,
                        "", null, R.drawable.xappie_place_holder);
            }
            actress_txt_topic_4.setText(mGalleryModel.getActressGalleryList().get(3).getTitle());
            actress_txt_topic_4.setTypeface(mTypefaceOpenSansRegular);
        }

        if (mGalleryModel.getActressGalleryList().size() >= 5) {
            ll_actress_item_5.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(mGalleryModel.getActressGalleryList().get(4).getBanner_image())) {
                Utility.universalImageLoaderPicLoading(actress_img_topic_5,
                        mGalleryModel.getActressGalleryList().get(4).getBanner_image(), null, R.drawable.xappie_place_holder);
            } else {
                Utility.universalImageLoaderPicLoading(actress_img_topic_5,
                        "", null, R.drawable.xappie_place_holder);
            }
            actress_txt_topic_5.setText(mGalleryModel.getActressGalleryList().get(4).getTitle());
            actress_txt_topic_5.setTypeface(mTypefaceOpenSansRegular);
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
            linkedHashMap.put(Constants.PAGE_NO, "1");
            linkedHashMap.put(Constants.PAGE_SIZE, Constants.PAGE_SIZE_VALUE);
            GalleryParser galleryParser = new GalleryParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_GALLERY, linkedHashMap,
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
        layout_ads.removeAllViews();
        for (int i = 0; i < getAdsSizes().size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.ads_item, null);
            ImageView img_ad_image = (ImageView) ll.findViewById(R.id.img_ad_image);
            img_ad_image.setImageDrawable(Utility.getDrawable(mParent, getAdsSizes().get(i).getId()));
            layout_ads.addView(ll);
        }
    }

    private ArrayList<AdsModel> getAdsSizes() {
        ArrayList<AdsModel> adsModels = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            AdsModel adsModel = new AdsModel();
            adsModel.setId(R.drawable.ads);
            adsModels.add(adsModel);
        }
        return adsModels;
    }
}
