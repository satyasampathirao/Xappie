package com.xappie.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.xappie.R;
import com.xappie.adapters.AdapterforTour;
import com.xappie.customviews.CirclePageIndicatorForTour;

public class AppTourActivity extends Activity {
	public static ViewPager mViewPager;
	private AdapterforTour mAdapterforTour;
	private CirclePageIndicatorForTour cri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_tour_activity);
		
		mViewPager = (ViewPager) findViewById(R.id.mViewPager);
		cri = (CirclePageIndicatorForTour) findViewById(R.id.indicator);
		mViewPager.setOffscreenPageLimit(5);
		mAdapterforTour = new AdapterforTour(this);
		mViewPager.setAdapter(mAdapterforTour);
		cri.setViewPager(mViewPager);
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
}
