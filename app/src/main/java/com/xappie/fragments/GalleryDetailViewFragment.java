package com.xappie.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.ScrollView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.EntertainmentModel;
import com.xappie.models.EntertainmentTopStoriesDetailModel;
import com.xappie.models.Model;
import com.xappie.models.RelatedTopicsModel;
import com.xappie.parser.EntertainmentTopStoriesDetailParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shankar 26/07/2017
 */
public class GalleryDetailViewFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = GalleryDetailViewFragment.class.getSimpleName();
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

    @BindView(R.id.img_banner)
    ImageView img_banner;

    @BindView(R.id.scroll)
    ScrollView scroll;
    @BindView(R.id.tv_header_title)
    TextView tv_header_title;
    @BindView(R.id.tv_written_by)
    TextView tv_written_by;
    @BindView(R.id.view_dot)
    View view_dot;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_description)
    TextView tv_description;


    @BindView(R.id.ll_next_layout)
    LinearLayout ll_next_layout;
    @BindView(R.id.tv_next_news)
    TextView tv_next_news;
    @BindView(R.id.tv_next_news_header_title)
    TextView tv_next_news_header_title;
    @BindView(R.id.tv_next_icon)
    TextView tv_next_icon;

    @BindView(R.id.tv_more_topics)
    TextView tv_more_topics;
    @BindView(R.id.tv_more)
    TextView tv_more;
    @BindView(R.id.ll_related_topics)
    LinearLayout ll_related_topics;

    @BindView(R.id.b_read_more)
    TextView b_read_more;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;
    private Typeface mTypefaceMaterialIcons;
    private Typeface mTypefaceOpenSansBold;
    private Typeface mTypefaceOpenSansLight;

    private String mSelectedId = "";
    private ArrayList<EntertainmentModel> allEntertainmentModels;
    private ArrayList<EntertainmentModel> entertainmentModels;
    private EntertainmentTopStoriesDetailModel entertainmentTopStoriesDetailModel;

    private int mPosition = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        mFrameLayout = (FrameLayout) getActivity().findViewById(R.id.content_frame);
        mParams = (CoordinatorLayout.LayoutParams) mFrameLayout.getLayoutParams();
        Utility.sendGoogleAnalytics(mParent, TAG);
        Bundle bundle = getArguments();
        if (bundle.containsKey(Constants.SELECTED_DETAIL_VIEW_ID)) {
            mSelectedId = bundle.getString(Constants.SELECTED_DETAIL_VIEW_ID);
            mPosition = bundle.getInt(Constants.SELECTED_DETAIL_VIEW_POSITION);
            allEntertainmentModels = (ArrayList<EntertainmentModel>) bundle.getSerializable(Constants.SELECTED_MORE_TOPICS_LIST);
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
        View rootView = inflater.inflate(R.layout.fragment_gallery_detail_view, container, false);
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
        entertainmentModels = Utility.getMoreTopicsList(clickedItemPosition(mSelectedId), allEntertainmentModels);
    }

    /**
     * This method is used for sets the typeface screen
     */
    private void setTypeFace() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefaceOpenSansLight = Utility.getOpenSansLight(mParent);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);
        mTypefaceMaterialIcons = Utility.getMaterialIconsRegular(mParent);
        mTypefaceOpenSansBold = Utility.getOpenSansBold(mParent);

        tv_notification_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_title.setVisibility(View.INVISIBLE);

        tv_location_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notifications_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);


        tv_header_title.setTypeface(mTypefaceOpenSansRegular);

        tv_written_by.setTypeface(mTypefaceOpenSansRegular);
        tv_time.setTypeface(mTypefaceOpenSansRegular);

        tv_description.setTypeface(mTypefaceOpenSansLight);
        tv_next_news.setTypeface(mTypefaceOpenSansRegular);

        tv_next_news_header_title.setTypeface(mTypefaceOpenSansRegular);
        tv_next_icon.setTypeface(mTypefaceMaterialIcons);

        tv_more_topics.setTypeface(mTypefaceOpenSansRegular);
        tv_more.setTypeface(mTypefaceOpenSansRegular);
        b_read_more.setTypeface(mTypefaceOpenSansRegular);
        getDetailViewData();
    }

    /**
     * This method is used to get the detail view data
     */
    private void getDetailViewData() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            EntertainmentTopStoriesDetailParser mEntertainmentTopStoriesDetailParser = new EntertainmentTopStoriesDetailParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_NEWS_DETAILS + mSelectedId, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, mEntertainmentTopStoriesDetailParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used for back from the fragment
     */
    @OnClick(R.id.ll_next_layout)
    void navigateNextLayout() {
        mSelectedId = entertainmentTopStoriesDetailModel.getmNextDetailModel().getId();
        getDetailViewData();
    }

    /**
     * This method is used for back from the fragment
     */
    @OnClick({R.id.tv_notification_arrow_back_icon,
            R.id.tv_notification_menu_icon, R.id.tv_more})
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

    private int clickedItemPosition(String id) {
        int clickedPosition = -1;
        for (int i = 0; i < allEntertainmentModels.size(); i++) {
            if (allEntertainmentModels.get(i).getId().equalsIgnoreCase(id)) {
                clickedPosition = i;
                break;
            }
        }
        return clickedPosition;
    }

    /**
     * Set Related Data
     */
    private void setRelatedTopics() {
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 8f);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 4f);
        lp2.setMargins(15, 15, 15, 15);

        ll_related_topics.removeAllViews();

        int rowCount = 0;
        LinearLayout linearLayout = null;

        if (entertainmentModels != null && entertainmentModels.size() > 0) {
            for (int j = 0; j < entertainmentModels.size(); j++) {

                if (rowCount == 0) {
                    linearLayout = new LinearLayout(mParent);
                    linearLayout.setLayoutParams(lp1);
                }

                @SuppressLint("InflateParams")
                LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.releated_topic_item, null);
                TextView tv_related_title = (TextView) ll.findViewById(R.id.tv_related_title);
                ImageView img_topic = (ImageView) ll.findViewById(R.id.img_topic);

                tv_related_title.setText(entertainmentModels.get(j).getTitle());
                tv_related_title.setTypeface(Utility.getOpenSansRegular(mParent));

                if (!Utility.isValueNullOrEmpty(entertainmentModels.get(j).getProfile_image())) {
                    Utility.universalImageLoaderPicLoading(img_topic,
                            entertainmentModels.get(j).getProfile_image(), null, R.drawable.xappie_place_holder);
                } else {
                    Utility.universalImageLoaderPicLoading(img_topic,
                            "", null, R.drawable.xappie_place_holder);
                }

                ll.setId(j);
                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = v.getId();
                        mSelectedId = entertainmentModels.get(pos).getId();
                        getDetailViewData();
                        entertainmentModels = Utility.getMoreTopicsList(clickedItemPosition(mSelectedId), allEntertainmentModels);
                        setRelatedTopics();
                    }
                });

                ll.setLayoutParams(lp2);
                linearLayout.addView(ll);

                rowCount += 1;
                if (rowCount == 2 || j == entertainmentModels.size() - 1) {
                    ll_related_topics.addView(linearLayout);
                    rowCount = 0;
                }
            }
        }
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof EntertainmentTopStoriesDetailModel) {
                entertainmentTopStoriesDetailModel = (EntertainmentTopStoriesDetailModel) model;
                setData();
            }
        }
    }

    /**
     * This method is used to set the data
     */
    private void setData() {
        scroll.fullScroll(ScrollView.FOCUS_UP);
        tv_header_title.setText(entertainmentTopStoriesDetailModel.getmCurrentDetailModel().getTitle());
        tv_description.setText(entertainmentTopStoriesDetailModel.getmCurrentDetailModel().getDescription());
        if (!Utility.isValueNullOrEmpty(entertainmentTopStoriesDetailModel.getmCurrentDetailModel().getSource())) {
            tv_written_by.setText(entertainmentTopStoriesDetailModel.getmCurrentDetailModel().getSource());
            tv_written_by.setVisibility(View.VISIBLE);
            view_dot.setVisibility(View.VISIBLE);
        } else {
            tv_written_by.setVisibility(View.GONE);
            view_dot.setVisibility(View.GONE);
        }

        if (!Utility.isValueNullOrEmpty(entertainmentTopStoriesDetailModel.getmCurrentDetailModel().getWeblink())) {
            b_read_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent webIntent = new Intent();
                    webIntent.setAction(Intent.ACTION_VIEW);
                    webIntent.setData(Uri.parse(entertainmentTopStoriesDetailModel.getmCurrentDetailModel().getWeblink()));
                    mParent.startActivity(webIntent);
                }
            });

        } else {
                    b_read_more.setVisibility(View.GONE);
                }


        if (!Utility.isValueNullOrEmpty(entertainmentTopStoriesDetailModel.getmCurrentDetailModel().getRecordedDate())) {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            PrettyTime prettyTime = new PrettyTime();
            Date date;
            String outputDateStr = "";
            try {
                date = inputFormat.parse(entertainmentTopStoriesDetailModel.getmCurrentDetailModel().getRecordedDate());
                outputDateStr = prettyTime.format(date);
                tv_time.setText(outputDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (!Utility.isValueNullOrEmpty(entertainmentTopStoriesDetailModel.getmCurrentDetailModel().getBanner_image()))
            Utility.universalImageLoaderPicLoading(img_banner,
                    entertainmentTopStoriesDetailModel.getmCurrentDetailModel().getBanner_image(),
                    null, R.drawable.xappie_place_);
        else if (!Utility.isValueNullOrEmpty(entertainmentTopStoriesDetailModel.getmCurrentDetailModel().getProfile_image()))
            Utility.universalImageLoaderPicLoading(img_banner,
                    entertainmentTopStoriesDetailModel.getmCurrentDetailModel().getProfile_image(),
                    null, R.drawable.xappie_place_);

        if (entertainmentTopStoriesDetailModel.getmNextDetailModel() != null) {
            tv_next_news_header_title.setText(entertainmentTopStoriesDetailModel.getmNextDetailModel().getTitle());
            ll_next_layout.setVisibility(View.VISIBLE);
        } else
            ll_next_layout.setVisibility(View.GONE);

        setRelatedTopics();
    }
}
