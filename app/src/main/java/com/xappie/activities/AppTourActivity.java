package com.xappie.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.adapters.AdapterforTour;
import com.xappie.customviews.CirclePageIndicatorForTour;
import com.xappie.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppTourActivity extends Activity {
    public static ViewPager mViewPager;
    private AdapterforTour mAdapterforTour;
    private CirclePageIndicatorForTour cri;

    @BindView(R.id.tv_close_icon)
    TextView tv_close_icon;

    @BindView(R.id.tv_xappie_tour)
    TextView tv_xappie_tour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_tour_activity);
        Utility.setTranslateStatusBar(this);
        ButterKnife.bind(this);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        cri = (CirclePageIndicatorForTour) findViewById(R.id.indicator);
        mViewPager.setOffscreenPageLimit(5);
        mAdapterforTour = new AdapterforTour(this);
        mViewPager.setAdapter(mAdapterforTour);
        cri.setViewPager(mViewPager);
        tv_close_icon.setTypeface(Utility.getMaterialIconsRegular(this));
        tv_xappie_tour.setTypeface(Utility.getOpenSansRegular(this));
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(startMain);
        finish();
    }

    @OnClick(R.id.tv_close_icon)
    void navigateToHomeActivity() {
        Intent intent = new Intent(AppTourActivity.this,
                LanguageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        AppTourActivity.this.startActivity(intent);
        AppTourActivity.this.finish();
    }
}
