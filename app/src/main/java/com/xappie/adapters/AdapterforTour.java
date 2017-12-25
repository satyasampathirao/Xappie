package com.xappie.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.AppTourActivity;
import com.xappie.activities.LanguageActivity;
import com.xappie.utils.Utility;

/**
 * Created by shankar on 6/28/2017.
 */

public class AdapterforTour extends PagerAdapter implements OnClickListener {

    private int NumberOfPages = 5;
    private LayoutInflater mLayoutInflater;
    private View mView;
    private Context mContext;
    private TextView mTxtQuickTour;
    private TextView mTxtSkip;

    private Typeface mTypeface_regular;
    private String mFontPath_regular;
    private Typeface mTypeface_medium;
    private String mFontPath_medium;
    private Typeface mTypeface_light;
    private String mFontPath_light;

    public AdapterforTour(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mFontPath_regular = "fonts/OpenSans-Regular.ttf";
        mTypeface_regular = Typeface.createFromAsset(mContext.getAssets(),
                mFontPath_regular);
        mFontPath_medium = "fonts/OpenSans-Bold.ttf";
        mTypeface_medium = Typeface.createFromAsset(mContext.getAssets(),
                mFontPath_medium);
        mFontPath_light = "fonts/OpenSans-Light.ttf";
        mTypeface_light = Typeface.createFromAsset(mContext.getAssets(),
                mFontPath_light);

    }

    @Override
    public int getCount() {
        return NumberOfPages;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(View collection, int position) {

        switch (position) {
            case 0:
                mView = mLayoutInflater.inflate(R.layout.tour_screen_1, null);
                TextView tv_welcome = (TextView) mView.findViewById(R.id.tv_welcome);
                TextView tv_ready = (TextView) mView.findViewById(R.id.tv_ready);
                TextView tv_swipe = (TextView) mView.findViewById(R.id.tv_swipe);
                TextView tv_one_place = (TextView) mView.findViewById(R.id.tv_one_place);
                TextView tv_selected = (TextView) mView.findViewById(R.id.tv_selected);
                TextView tv_its = (TextView) mView.findViewById(R.id.tv_its);
                TextView tv_local = (TextView) mView.findViewById(R.id.tv_local);
                TextView tv_simple = (TextView) mView.findViewById(R.id.tv_simple);
                TextView tv_customizable = (TextView) mView.findViewById(R.id.tv_customizable);
                TextView tv_free = (TextView) mView.findViewById(R.id.tv_free);
                TextView tv_lets_start = (TextView) mView.findViewById(R.id.tv_lets_start);
                tv_welcome.setTypeface(Utility.getOpenSansBold(mContext));
                tv_lets_start.setTypeface(Utility.getOpenSansBold(mContext));
                tv_ready.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_swipe.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_customizable.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_free.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_its.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_local.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_selected.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_simple.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_one_place.setTypeface(Utility.getOpenSansRegular(mContext));

                tv_lets_start.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AppTourActivity.mViewPager.setCurrentItem(1);
                    }
                });

                ((ViewPager) collection).addView(mView, 0);
                break;
            case 1:
                mView = mLayoutInflater.inflate(R.layout.tour_screen_2, null);
                TextView tv_city = (TextView) mView.findViewById(R.id.tv_city);
                TextView tv_location_icon = (TextView) mView.findViewById(R.id.tv_location_icon);
                TextView tv_location = (TextView) mView.findViewById(R.id.tv_location);
                TextView tv_language_icon = (TextView) mView.findViewById(R.id.tv_language_icon);
                TextView tv_language = (TextView) mView.findViewById(R.id.tv_language);
                TextView tv_simply_select = (TextView) mView.findViewById(R.id.tv_simply_select);
                TextView tv_the_customized = (TextView) mView.findViewById(R.id.tv_the_customized);
                TextView tv_you_can_always = (TextView) mView.findViewById(R.id.tv_you_can_always);
                TextView tv_next = (TextView) mView.findViewById(R.id.tv_next);

                tv_city.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_location_icon.setTypeface(Utility.getFontAwesomeWebFont(mContext));
                tv_location.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_language_icon.setTypeface(Utility.getFontAwesomeWebFont(mContext));
                tv_language.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_simply_select.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_the_customized.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_you_can_always.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_next.setTypeface(Utility.getOpenSansBold(mContext));

                tv_next.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AppTourActivity.mViewPager.setCurrentItem(2);
                    }
                });

                ((ViewPager) collection).addView(mView, 0);
                break;
            case 2:
                mView = mLayoutInflater.inflate(R.layout.tour_screen_3, null);
                TextView tv_menu = (TextView) mView.findViewById(R.id.tv_menu);
                TextView tv_home = (TextView) mView.findViewById(R.id.tv_home);
                TextView tv_notification_settings_icon = (TextView) mView.findViewById(R.id.tv_notification_settings_icon);
                TextView tv_menu_section = (TextView) mView.findViewById(R.id.tv_menu_section);
                TextView tv_menu_jobs = (TextView) mView.findViewById(R.id.tv_menu_jobs);
                TextView tv_based_on = (TextView) mView.findViewById(R.id.tv_based_on);
                TextView tv_remain = (TextView) mView.findViewById(R.id.tv_remain);
                TextView tv_home_next = (TextView) mView.findViewById(R.id.tv_home_next);
                tv_menu.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_home.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_menu_jobs.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_menu_section.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_based_on.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_remain.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_home_next.setTypeface(Utility.getOpenSansBold(mContext));
                tv_notification_settings_icon.setTypeface(Utility.getFontAwesomeWebFont(mContext));

                tv_home_next.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AppTourActivity.mViewPager.setCurrentItem(3);
                    }
                });


                ((ViewPager) collection).addView(mView, 0);

                break;
            case 3:
                mView = mLayoutInflater.inflate(R.layout.tour_screen_4, null);
                TextView tv_alerts = (TextView) mView.findViewById(R.id.tv_alerts);
                TextView tv_notification_icon = (TextView) mView.findViewById(R.id.tv_notification_icon);
                TextView tv_menu_pre = (TextView) mView.findViewById(R.id.tv_menu_pre);
                TextView tv_are_you_missing = (TextView) mView.findViewById(R.id.tv_are_you_missing);
                TextView tv_we_send = (TextView) mView.findViewById(R.id.tv_we_send);
                TextView tv_events_hosted = (TextView) mView.findViewById(R.id.tv_events_hosted);
                TextView tv_customize_based = (TextView) mView.findViewById(R.id.tv_customize_based);
                TextView tv_alerts_next = (TextView) mView.findViewById(R.id.tv_alerts_next);
                tv_alerts.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_notification_icon.setTypeface(Utility.getFontAwesomeWebFont(mContext));
                tv_menu_pre.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_are_you_missing.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_we_send.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_events_hosted.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_customize_based.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_alerts_next.setTypeface(Utility.getOpenSansBold(mContext));

                tv_alerts_next.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AppTourActivity.mViewPager.setCurrentItem(4);
                    }
                });

                ((ViewPager) collection).addView(mView, 0);
                break;
            case 4:
                mView = mLayoutInflater.inflate(R.layout.tour_screen_5, null);
                TextView tv_you_got_it = (TextView) mView.findViewById(R.id.tv_you_got_it);
                TextView tv_enjoy_the_services = (TextView) mView.findViewById(R.id.tv_enjoy_the_services);
                TextView tv_services = (TextView) mView.findViewById(R.id.tv_services);
                TextView tv_entertainment_icon = (TextView) mView.findViewById(R.id.tv_entertainment_icon);
                TextView tv_top_stories_icon = (TextView) mView.findViewById(R.id.tv_top_stories_icon);
                TextView tv_gallery_icon = (TextView) mView.findViewById(R.id.tv_gallery_icon);
                TextView tv_videos_icon = (TextView) mView.findViewById(R.id.tv_videos_icon);
                TextView tv_events_icon = (TextView) mView.findViewById(R.id.tv_events_icon);
                TextView tv_jobs_icon = (TextView) mView.findViewById(R.id.tv_jobs_icon);
                TextView tv_classifieds_icon = (TextView) mView.findViewById(R.id.tv_classifieds_icon);
                TextView tv_chit_chat_icon = (TextView) mView.findViewById(R.id.tv_chit_chat_icon);
                TextView tv_communication_icon = (TextView) mView.findViewById(R.id.tv_communication_icon);

                tv_you_got_it.setTypeface(Utility.getOpenSansBold(mContext));
                tv_enjoy_the_services.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_communication_icon.setTypeface(Utility.getFontAwesomeWebFont(mContext));
                tv_chit_chat_icon.setTypeface(Utility.getFontAwesomeWebFont(mContext));
                tv_classifieds_icon.setTypeface(Utility.getFontAwesomeWebFont(mContext));
                tv_jobs_icon.setTypeface(Utility.getFontAwesomeWebFont(mContext));
                tv_events_icon.setTypeface(Utility.getFontAwesomeWebFont(mContext));
                tv_videos_icon.setTypeface(Utility.getFontAwesomeWebFont(mContext));
                tv_gallery_icon.setTypeface(Utility.getFontAwesomeWebFont(mContext));
                tv_top_stories_icon.setTypeface(Utility.getFontAwesomeWebFont(mContext));
                tv_entertainment_icon.setTypeface(Utility.getFontAwesomeWebFont(mContext));
                
                tv_services.setTypeface(Utility.getOpenSansRegular(mContext));
                tv_you_got_it.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        navigateToHomeActivity();
                    }
                });

                ((ViewPager) collection).addView(mView, 0);
                break;
            default:
                break;
        }

        return mView;
    }

    private void navigateToHomeActivity() {
        Intent intent = new Intent(mContext,
                LanguageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
        ((Activity) mContext).finish();
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public void onClick(View v) {
        if (v == mTxtQuickTour) {
            AppTourActivity.mViewPager.setCurrentItem(1);
        }
    }

}
