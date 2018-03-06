package com.xappie.app;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.xappie.R;
import com.xappie.utils.Utility;

/**
 * Created by Shankar on 2/7/2017.
 */

public class XappieApplication extends MultiDexApplication {

    private static XappieApplication instance;
    private static final String TWITTER_KEY = "hUXcPocxvhNKULzPsXH7npqYM";
    private static final String TWITTER_SECRET = "fy89ShuBq1FPseHdtIGwgFNOFrdZJwr1mRiUslcLnqNgmQb9m2";

    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sAnalytics = GoogleAnalytics.getInstance(this);
        initImageLoader(getApplicationContext());
        TwitterConfig config = new TwitterConfig.Builder(this)
                .twitterAuthConfig(new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET))
                .build();
        Twitter.initialize(config);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        MobileAds.initialize(getApplicationContext(), Utility.getResourcesString(this, R.string.banner_ad_unit_id));
        MobileAds.initialize(getApplicationContext(), Utility.getResourcesString(this, R.string.interstitial_ad_unit_id));
        MobileAds.initialize(getApplicationContext(), Utility.getResourcesString(this, R.string.native_ad_unit_id));
    }

    /**
     * This method is used to initialize the Universal image loader
     * When application starts
     *
     * @param context Context of the Application class
     */
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    /**
     * Gets the default {@link Tracker} for this {@link XappieApplication}.
     *
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
        }

        return sTracker;
    }

    public static Context getAppContext() {
        return instance;
    }
}
