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
import com.xappie.models.Model;
import com.xappie.parser.CountriesParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

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

    private CountriesListModel mCountriesListModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.fragment_countries);
        ButterKnife.bind(this);
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
        tv_countries_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_countries_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_select_country.setTypeface(mTypefaceOpenSansRegular);

        tv_notification_icon.setVisibility(View.GONE);
        tv_language_icon.setVisibility(View.GONE);

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
                country_list_item.setAdapter(new CountriesListAdapter(CountriesActivity.this, mCountriesListModel.getCountriesModels()));
            }
        }
    }


    @OnClick({R.id.tv_countries_arrow_back_icon, R.id.tv_countries_menu_icon})
    public void navigateToBack() {
        onBackPressed();
    }

    /**
     * This method is used to select preferred language
     */
    @OnItemClick(R.id.country_list_view)
    void onItemClick(int position) {
        Intent intent = new Intent(CountriesActivity.this, StatesActivity.class);
        intent.putExtra(Constants.SELECTED_COUNTRY_ID, mCountriesListModel.getCountriesModels().get(position).getId());
        intent.putExtra(Constants.SELECTED_COUNTRY_NAME, mCountriesListModel.getCountriesModels().get(position).getCountry_name());
        startActivity(intent);
    }

}
