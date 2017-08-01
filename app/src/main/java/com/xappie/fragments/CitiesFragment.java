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
import android.widget.ListView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.CityListAdapter;
import com.xappie.models.CitiesListModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CitiesFragment extends Fragment {

    public static final String TAG = CitiesFragment.class.getSimpleName();
    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;


    @BindView(R.id.tv_countries_arrow_back_icon)
    TextView tv_countries_arrow_back_icon;
    @BindView(R.id.tv_countries_menu_icon)
    TextView tv_countries_menu_icon;
    @BindView(R.id.tv_countries)
    TextView tv_countries;
    @BindView(R.id.tv_notifications_icon)
    TextView tv_notification_icon;
    @BindView(R.id.tv_language_icon)
    TextView tv_language_icon;
    @BindView(R.id.tv_country_name)
    TextView tv_country_name;
    @BindView(R.id.tv_arrow_right_icon)
    TextView tv_arrow_right_icon;
    @BindView(R.id.tv_city_name)
    TextView tv_city_name;
    @BindView(R.id.city_list_view)
    ListView city_list_view;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;


    public CitiesFragment() {
        // Required empty public constructor
    }

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
        View rootView =  inflater.inflate(R.layout.fragment_cities, container, false);
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

        tv_countries.setTypeface(mTypefaceOpenSansRegular);
        tv_countries_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_countries_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_arrow_right_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_city_name.setTypeface(mTypefaceOpenSansRegular);
        tv_country_name.setTypeface(mTypefaceOpenSansRegular);

        city_list_view.setAdapter(new CityListAdapter(mParent,getSampleData()));
    }

    private ArrayList<CitiesListModel> getSampleData()
    {
        ArrayList<CitiesListModel> citiesListModels = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            CitiesListModel citiesListModel = new CitiesListModel();
            citiesListModel.setTitle("WASHINGTON DC");
            citiesListModels.add(citiesListModel);

        }
        return citiesListModels;

    }

    @OnClick(R.id.tv_countries_arrow_back_icon)
    public void navigateToBack()
    {
        mParent.onBackPressed();
    }

}
