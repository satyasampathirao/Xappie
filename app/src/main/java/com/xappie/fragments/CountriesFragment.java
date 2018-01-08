package com.xappie.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
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
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.CountriesListModel;
import com.xappie.models.CountriesModel;
import com.xappie.models.Model;
import com.xappie.parser.CountriesParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountriesFragment extends Fragment implements IAsyncCaller {
    public static final String TAG = CountriesFragment.class.getSimpleName();

    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;
    private Typeface mTypefaceOpenSansBold;



    @BindView(R.id.tv_location)
    TextView tv_countries;
    @BindView(R.id.tv_back)
    TextView tv_back;


    @BindView(R.id.country_list_view)
    ListView country_list_item;
    private CountriesListModel mCountriesListModel;
    private ArrayList<CountriesModel> countriesModels;
    private CountriesListAdapter countriesListAdapter;
    private View rootView;

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
        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_countries, container, false);
        ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    private void initUI() {
        setTypeFace();
    }

    private void setTypeFace() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);
        mTypefaceOpenSansBold = Utility.getOpenSansBold(mParent);

        tv_countries.setTypeface(mTypefaceOpenSansRegular);
        tv_back.setTypeface(Utility.getMaterialIconsRegular(mParent));

        getCountriesList();
    }

    private void getCountriesList() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            CountriesParser countriesParser = new CountriesParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_COUNTRIES, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, countriesParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * After complete the service call back will be coming in this method
     * It returns the respective model
     */
    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof CountriesListModel) {
                mCountriesListModel = (CountriesListModel) model;
                countriesModels = mCountriesListModel.getCountriesModels();
                countriesListAdapter = new CountriesListAdapter(mParent, countriesModels);
                country_list_item.setAdapter(countriesListAdapter);
            }
        }
    }

    @OnClick(R.id.tv_back)
    public void navigateToBack() {
        mParent.onBackPressed();
    }


    @OnItemClick(R.id.country_list_view)
    void onItemClick(int position) {

        for (int i = 0; i < countriesModels.size(); i++) {
            CountriesModel countriesListModel = countriesModels.get(i);
            countriesListModel.setmSelected(false);
            countriesModels.set(i, countriesListModel);
        }

        CountriesModel countriesListModel = countriesModels.get(position);
        countriesListModel.setmSelected(true);
        countriesListAdapter.notifyDataSetChanged();

        Bundle bundle = new Bundle();
        bundle.putString(Constants.SELECTED_COUNTRY_ID, mCountriesListModel.getCountriesModels().get(position).getId());
        bundle.putString(Constants.SELECTED_COUNTRY_NAME, mCountriesListModel.getCountriesModels().get(position).getCountry_name());

        Utility.navigateDashBoardFragment(new StatesFragment(), StatesFragment.TAG, bundle, mParent);

    }
}
