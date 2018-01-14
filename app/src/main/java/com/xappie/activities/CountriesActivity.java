package com.xappie.activities;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.xappie.R;
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
public class CountriesActivity extends BaseActivity implements IAsyncCaller {
    public static final String TAG = CountriesActivity.class.getSimpleName();


    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;


    @BindView(R.id.tv_location)
    TextView tv_countries;
    @BindView(R.id.tv_back)
    TextView tv_back;


    @BindView(R.id.country_list_view)
    ListView country_list_item;

    private CountriesListModel mCountriesListModel;
    private ArrayList<CountriesModel> countriesModels;
    private CountriesListAdapter countriesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.fragment_countries);
        ButterKnife.bind(this);
        Utility.sendGoogleAnalytics(this, TAG);
        initUI();
    }

    /**
     * This method is used for initialization
     */
    private void initUI() {
        setTypeFace();
    }

    private void setTypeFace() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(this);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(this);

        tv_countries.setTypeface(mTypefaceOpenSansRegular);
        tv_back.setTypeface(Utility.getMaterialIconsRegular(this));

        getCountriesList();
    }

    /**
     * This method is used to get the countries list from the server
     */
    private void getCountriesList() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            CountriesParser countriesParser = new CountriesParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    this, Utility.getResourcesString(this, R.string.please_wait), true,
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
                countriesListAdapter = new CountriesListAdapter(CountriesActivity.this, countriesModels);
                country_list_item.setAdapter(countriesListAdapter);
            }
        }
    }


    @OnClick(R.id.tv_back)
    public void navigateToBack() {
        onBackPressed();
    }

    /**
     * This method is used to select preferred language
     */
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

        Intent intent = new Intent(CountriesActivity.this, LocationSelectionActivity.class);
        intent.putExtra(Constants.SELECTED_COUNTRY_ID, mCountriesListModel.getCountriesModels().get(position).getId());
        intent.putExtra(Constants.SELECTED_COUNTRY_NAME, mCountriesListModel.getCountriesModels().get(position).getCountry_name());
        startActivity(intent);
    }

}
