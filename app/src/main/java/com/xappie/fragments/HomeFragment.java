package com.xappie.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.HomeViewPagerAdapter;
import com.xappie.models.AdsModel;
import com.xappie.models.GalleryModel;
import com.xappie.models.NewsModel;
import com.xappie.models.VideosModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public static final String TAG = HomeFragment.class.getSimpleName();
    private DashBoardActivity mParent;
    private Toolbar mToolbar;
    private AppBarLayout appBarLayout;

    @BindView(R.id.card_pager)
    ViewPager card_pager;

    /**
     * Gallery View Ids
     */
    @BindView(R.id.layout_gallery)
    LinearLayout layout_gallery;

    @BindView(R.id.tv_gallery)
    TextView tv_gallery;
    @BindView(R.id.tv_gallery_more)
    TextView tv_gallery_more;

    /**
     * Videos View Ids
     */
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

    @BindView(R.id.layout_ads)
    LinearLayout layout_ads;


    /**
     * Tops stories View Ids
     */

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

    @BindView(R.id.tv_entertainment)
    TextView tv_entertainment;

    @BindView(R.id.tv_entertainment_more)
    TextView tv_entertainment_more;

    @BindView(R.id.ll_languages_layout_entertainment)
    LinearLayout ll_languages_layout_entertainment;
    @BindView(R.id.ll_entertainment)
    LinearLayout ll_entertainment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (appBarLayout != null)
            appBarLayout.setVisibility(View.VISIBLE);

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {
        ArrayList<String> mList = new ArrayList<>();
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        card_pager.setAdapter(new HomeViewPagerAdapter(mParent, mList));
        setTypeface();
        setGalleryData();
        setVideosData();
        setAdsData();
        setTopStoriesData();
        setEntertainmentData();
    }

    /**
     * Sets Entertainment data
     */
    private void setEntertainmentData() {
        ll_languages_layout_entertainment.removeAllViews();
        for (int i = 0; i < getLanguagesData().size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.language_item, null);
            TextView tv_language_name = (TextView) ll.findViewById(R.id.tv_language_name);
            View view = (View) ll.findViewById(R.id.view);
            tv_language_name.setText(getLanguagesData().get(i));
            tv_language_name.setTypeface(Utility.getOpenSansBold(mParent));
            if (i == 0) {
                view.setVisibility(View.VISIBLE);
                tv_language_name.setTextColor(Utility.getColor(mParent, R.color.text_language_color));
            } else {
                view.setVisibility(View.GONE);
            }
            ll_languages_layout_entertainment.addView(ll);
        }


        ll_entertainment.removeAllViews();
        for (int i = 0; i < getNewsModels().size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.news_item, null);
            ImageView img_news_item = (ImageView) ll.findViewById(R.id.img_news_item);
            TextView tv_title = (TextView) ll.findViewById(R.id.tv_title);
            TextView tv_time = (TextView) ll.findViewById(R.id.tv_time);

            tv_title.setText(getNewsModels().get(i).getTitle());
            tv_title.setTypeface(Utility.getOpenSansBold(mParent));

            tv_time.setText(getNewsModels().get(i).getTime());
            tv_time.setTypeface(Utility.getOpenSansRegular(mParent));

            ll_entertainment.addView(ll);
        }
    }

    /**
     * Sets Top Stories data
     */
    private void setTopStoriesData() {
        ll_languages_layout_top_stories.removeAllViews();
        for (int i = 0; i < getLanguagesData().size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.language_item, null);
            TextView tv_language_name = (TextView) ll.findViewById(R.id.tv_language_name);
            View view = (View) ll.findViewById(R.id.view);
            tv_language_name.setText(getLanguagesData().get(i));
            tv_language_name.setTypeface(Utility.getOpenSansBold(mParent));
            if (i == 0) {
                view.setVisibility(View.VISIBLE);
                tv_language_name.setTextColor(Utility.getColor(mParent, R.color.text_language_color));
            } else {
                view.setVisibility(View.GONE);
            }
            ll_languages_layout_top_stories.addView(ll);
        }


        ll_top_stories.removeAllViews();
        for (int i = 0; i < getNewsModels().size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.news_item, null);
            ImageView img_news_item = (ImageView) ll.findViewById(R.id.img_news_item);
            TextView tv_title = (TextView) ll.findViewById(R.id.tv_title);
            TextView tv_time = (TextView) ll.findViewById(R.id.tv_time);

            tv_title.setText(getNewsModels().get(i).getTitle());
            tv_title.setTypeface(Utility.getOpenSansBold(mParent));

            tv_time.setText(getNewsModels().get(i).getTime());
            tv_time.setTypeface(Utility.getOpenSansRegular(mParent));

            ll_top_stories.addView(ll);
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
    }

    private void setGalleryData() {
        layout_gallery.removeAllViews();
        for (int i = 0; i < getGallerySizes().size(); i++) {
            RelativeLayout ll = (RelativeLayout) mParent.getLayoutInflater().inflate(R.layout.gallery_item, null);
            ImageView img_gallery_image = (ImageView) ll.findViewById(R.id.img_gallery_image);
            TextView tv_title = (TextView) ll.findViewById(R.id.tv_title);
            tv_title.setTypeface(Utility.getOpenSansBold(mParent));
            tv_title.setText(getGallerySizes().get(i).getTitle());
            img_gallery_image.setImageDrawable(Utility.getDrawable(mParent, getGallerySizes().get(i).getId()));
            layout_gallery.addView(ll);
        }
    }

    private void setVideosData() {
        layout_videos.removeAllViews();
        for (int i = 0; i < getVideosSizes().size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.videos_item, null);
            ImageView img_video_image = (ImageView) ll.findViewById(R.id.img_video_image);
            TextView tv_title = (TextView) ll.findViewById(R.id.tv_title);
            tv_title.setTypeface(Utility.getOpenSansBold(mParent));
            tv_title.setText(getVideosSizes().get(i).getTitle());
            img_video_image.setImageDrawable(Utility.getDrawable(mParent, getVideosSizes().get(i).getId()));
            layout_videos.addView(ll);
        }
    }

    private void setAdsData() {
        layout_ads.removeAllViews();
        for (int i = 0; i < getAdsSizes().size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.ads_item, null);
            ImageView img_ad_image = (ImageView) ll.findViewById(R.id.img_ad_image);
            img_ad_image.setImageDrawable(Utility.getDrawable(mParent, getAdsSizes().get(i).getId()));
            layout_ads.addView(ll);
        }
    }

    private ArrayList<GalleryModel> getGallerySizes() {
        ArrayList<GalleryModel> galleryModels = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            GalleryModel galleryModel = new GalleryModel();
            galleryModel.setTitle("Ileana's hottest holiday picture");
            galleryModel.setId(R.drawable.illiyana);
            galleryModels.add(galleryModel);
        }
        return galleryModels;
    }

    private ArrayList<VideosModel> getVideosSizes() {
        ArrayList<VideosModel> videosModels = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            VideosModel videosModel = new VideosModel();
            videosModel.setTitle("Pawan Kalyan's katamarayudu 50days collections in AP & TG");
            videosModel.setId(R.drawable.video_hint);
            videosModels.add(videosModel);
        }
        return videosModels;
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

    private ArrayList<String> getLanguagesData() {
        ArrayList<String> mLanguagesData = new ArrayList<>();
        mLanguagesData.add("HINDI");
        mLanguagesData.add("ENGLISH");
        mLanguagesData.add("TELUGU");
        mLanguagesData.add("TAMIL");
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
}
