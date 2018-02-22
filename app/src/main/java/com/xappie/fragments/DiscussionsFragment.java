package com.xappie.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.DiscussionsAdapter;
import com.xappie.models.DiscussionsModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shankar on 7/28/2017.
 */
public class DiscussionsFragment extends Fragment {

    public static final String TAG = DiscussionsFragment.class.getSimpleName();
    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;

    /**
     * Discussions Discussions
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
    @BindView(R.id.tv_ready)
    TextView tv_ready;
    @BindView(R.id.tv_swipe)
    TextView tv_swipe;
    @BindView(R.id.tv_one_place)
    TextView tv_one_place;
    @BindView(R.id.tv_selected)
    TextView tv_selected;
    @BindView(R.id.tv_topics)
    TextView tv_topics;
    @BindView(R.id.tv_trending)
    TextView tv_trending;
    @BindView(R.id.tv_new)
    TextView tv_new;
    @BindView(R.id.tv_pinned)
    TextView tv_pinned;
    @BindView(R.id.tv_coming_soon)
    TextView tv_coming_soon;
    @BindView(R.id.first_time)
    TextView first_time;
    @BindView(R.id.sports)
    TextView sports;
    @BindView(R.id.place_to_visit)
    TextView place_to_visit;
    @BindView(R.id.crypto_currency)
    TextView crypto_currency;
    @BindView(R.id.residential)
    TextView residential;
    @BindView(R.id.stock_market)
    TextView stock_market;
    @BindView(R.id.schools)
    TextView schools;
    @BindView(R.id.health)
    TextView health;
    @BindView(R.id.immigration)
    TextView immigration;
    @BindView(R.id.kids_talent)
    TextView kids_talent;
    @BindView(R.id.travel_companion)
    TextView travel_companion;
    @BindView(R.id.cuisines)
    TextView cuisines;
    @BindView(R.id.university)
    TextView university;
    @BindView(R.id.real_estate)
    TextView real_estate;
    @BindView(R.id.politics)
    TextView politics;
    @BindView(R.id.movies)
    TextView movies;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;

    /**
     * Gallery Discussions setup
     */
    @BindView(R.id.grid_view)
    GridView grid_view;


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
        View rootView = inflater.inflate(R.layout.fragment_discussions, container, false);
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

        tv_notification_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(Utility.getResourcesString(mParent, R.string.discussions));
        tv_title.setTypeface(mTypefaceOpenSansRegular);

        tv_location_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notifications_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_selected.setTypeface(Utility.getOpenSansItalic(mParent));
        tv_ready.setTypeface(Utility.getOpenSansItalic(mParent));
        tv_swipe.setTypeface(Utility.getOpenSansItalic(mParent));
        tv_one_place.setTypeface(Utility.getOpenSansItalic(mParent));
        tv_coming_soon.setTypeface(mTypefaceOpenSansRegular);
        movies.setTypeface(mTypefaceOpenSansRegular);
        university.setTypeface(mTypefaceOpenSansRegular);
        cuisines.setTypeface(mTypefaceOpenSansRegular);
        travel_companion.setTypeface(mTypefaceOpenSansRegular);
        kids_talent.setTypeface(mTypefaceOpenSansRegular);
        immigration.setTypeface(mTypefaceOpenSansRegular);
        health.setTypeface(mTypefaceOpenSansRegular);
        stock_market.setTypeface(mTypefaceOpenSansRegular);
        residential.setTypeface(mTypefaceOpenSansRegular);
        real_estate.setTypeface(mTypefaceOpenSansRegular);
        crypto_currency.setTypeface(mTypefaceOpenSansRegular);
        place_to_visit.setTypeface(mTypefaceOpenSansRegular);
        sports.setTypeface(mTypefaceOpenSansRegular);
        schools.setTypeface(mTypefaceOpenSansRegular);
        first_time.setTypeface(mTypefaceOpenSansRegular);
        tv_pinned.setTypeface(mTypefaceOpenSansRegular);
        tv_topics.setTypeface(mTypefaceOpenSansRegular);
        tv_trending.setTypeface(mTypefaceOpenSansRegular);
        tv_new.setTypeface(mTypefaceOpenSansRegular);
        politics.setTypeface(mTypefaceOpenSansRegular);

        setGridViewData();
    }

    /**
     * This method is used for back from the fragment
     */
    @OnClick({R.id.tv_notification_arrow_back_icon,
            R.id.tv_notification_menu_icon})
    void backToTheHome() {
        mParent.onBackPressed();
    }

    /**
     * This method is used to set the grid view data
     */
    private void setGridViewData() {
        DiscussionsAdapter discussionsAdapter = new DiscussionsAdapter(mParent, getDiscussionsData());
        grid_view.setAdapter(discussionsAdapter);
    }

    private ArrayList<DiscussionsModel> getDiscussionsData() {
        ArrayList<DiscussionsModel> discussionsModels = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            DiscussionsModel discussionsModel = new DiscussionsModel();
            //discussionsModel.setId(R.drawable.classifieds);
            discussionsModel.setName("Doctors");
            discussionsModels.add(discussionsModel);
        }
        return discussionsModels;
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

}
