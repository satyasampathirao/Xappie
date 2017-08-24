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
import com.xappie.adapters.CountriesListAdapter;
import com.xappie.models.CountriesModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountriesFragment extends Fragment {
    public static final String TAG = CountriesFragment.class.getSimpleName();

    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;
    private Typeface mTypefaceOpenSansBold;


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
    @BindView(R.id.tv_select_country)
    TextView tv_select_country;

    @BindView(R.id.country_list_view)
    ListView country_list_item;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        mFrameLayout = (FrameLayout) getActivity().findViewById(R.id.content_frame);
        mParams = (CoordinatorLayout.LayoutParams) mFrameLayout.getLayoutParams();
    }


    public CountriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (appBarLayout != null) {
            mParams.setBehavior(null);
            mFrameLayout.requestLayout();
            appBarLayout.setVisibility(View.GONE);
        }
        View rootView = inflater.inflate(R.layout.fragment_countries, container, false);
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
        mTypefaceOpenSansBold = Utility.getOpenSansBold(mParent);

        tv_countries.setTypeface(mTypefaceOpenSansRegular);
        tv_countries_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_countries_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_select_country.setTypeface(mTypefaceOpenSansRegular);

        country_list_item.setAdapter(new CountriesListAdapter(mParent, getSampleData()));
    }

    private ArrayList<CountriesModel> getSampleData() {

        ArrayList<CountriesModel> countriesListModels = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            CountriesModel countriesModel = new CountriesModel();
            countriesModel.setCountry_name("INDIA");
            countriesListModels.add(countriesModel);
        }
        return countriesListModels;
    }

    @OnClick({R.id.tv_countries_arrow_back_icon, R.id.tv_countries_menu_icon})
    public void navigateToBack() {
        mParent.onBackPressed();
    }

    @OnClick(R.id.tv_notifications_icon)
    public void navigateToNotification() {
        Utility.navigateDashBoardFragment(new NotificationsFragment(), NotificationsFragment.TAG, null, mParent);
    }

    @OnClick(R.id.tv_language_icon)
    public void navigateToLanguage() {
        Utility.navigateDashBoardFragment(new LanguageFragment(), LanguageFragment.TAG, null, mParent);
    }
}
