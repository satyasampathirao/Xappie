package com.xappie.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xappie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.xappie.customviews.CirclePageIndicatorForTour;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public static final String TAG = HomeFragment.class.getSimpleName();


    @BindView(R.id.card_pager)
    ViewPager card_pager;

    @BindView(R.id.indicator)
    CirclePageIndicatorForTour indicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

}
