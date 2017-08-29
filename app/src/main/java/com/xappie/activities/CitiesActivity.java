package com.xappie.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.adapters.StatesListAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.Model;
import com.xappie.models.StatesListModel;
import com.xappie.parser.StatesParser;
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
public class CitiesActivity extends BaseActivity implements IAsyncCaller {
    public static final String TAG = CitiesActivity.class.getSimpleName();

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;

    @BindView(R.id.tv_countries_arrow_back_icon)
    TextView tv_countries_arrow_back_icon;
    @BindView(R.id.tv_countries_menu_icon)
    TextView tv_countries_menu_icon;
    @BindView(R.id.tv_cities)
    TextView tv_cities;
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
    @BindView(R.id.tv_state_name)
    TextView tv_state_name;
    @BindView(R.id.tv_arrow_right_icon_two)
    TextView tv_arrow_right_icon_two;
    @BindView(R.id.city_list_view)
    ListView city_list_view;

    private StatesListModel mStatesListModel;
    private Intent intent;
    private String mSelectedCountryName;
    private String mSelectedCountryId;
    private String mSelectedStateName;
    private String mSelectedStateId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.fragment_cities);
        ButterKnife.bind(this);
        initUI();
    }

    /**
     * This method is used for initialization
     */
    private void initUI() {
        intent = getIntent();
        mSelectedCountryName = intent.getStringExtra(Constants.SELECTED_COUNTRY_NAME);
        mSelectedStateName = intent.getStringExtra(Constants.SELECTED_STATE_NAME);
        mSelectedStateId = intent.getStringExtra(Constants.SELECTED_STATE_ID);
        setTypeFace();
    }

    private void setTypeFace() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(this);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(this);

        tv_cities.setTypeface(mTypefaceOpenSansRegular);
        tv_cities.setText(Utility.getResourcesString(this, R.string.cities));
        tv_countries_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_countries_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_arrow_right_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_arrow_right_icon_two.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_city_name.setTypeface(mTypefaceOpenSansRegular);
        tv_country_name.setTypeface(mTypefaceOpenSansRegular);
        tv_state_name.setTypeface(mTypefaceOpenSansRegular);
        tv_country_name.setText(mSelectedCountryName);
        tv_state_name.setText(mSelectedStateName);

        tv_city_name.setText(Utility.getResourcesString(this, R.string.select_city));

        tv_notification_icon.setVisibility(View.GONE);
        tv_language_icon.setVisibility(View.GONE);

        getCitiesList();
    }

    /**
     * This method is used to get the Cities list from the server
     */
    private void getCitiesList() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            StatesParser statesParser = new StatesParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    this, Utility.getResourcesString(this, R.string.please_wait), true,
                    APIConstants.GET_CITIES + "/" + mSelectedStateId, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, statesParser);
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
            if (model instanceof StatesListModel) {
                mStatesListModel = (StatesListModel) model;
                if (mStatesListModel.getStateModels().size() == 0) {
                    Utility.showToastMessage(CitiesActivity.this, Utility.getResourcesString(CitiesActivity.this, R.string.no_states_found));
                } else {
                    city_list_view.setAdapter(new StatesListAdapter(CitiesActivity.this, mStatesListModel.getStateModels()));
                }
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
    @OnItemClick(R.id.city_list_view)
    void onItemClick(int position) {
        Utility.setSharedPrefStringData(this, Constants.SELECTED_COUNTRY_ID, mSelectedCountryId);
        Utility.setSharedPrefStringData(this, Constants.SELECTED_COUNTRY_NAME, mSelectedCountryName);
        Utility.setSharedPrefStringData(this, Constants.SELECTED_STATE_ID, mSelectedStateId);
        Utility.setSharedPrefStringData(this, Constants.SELECTED_STATE_NAME, mSelectedStateName);
        Utility.setSharedPrefStringData(this, Constants.SELECTED_CITY_ID, mStatesListModel.getStateModels().get(position).getId());
        Utility.setSharedPrefStringData(this, Constants.SELECTED_COUNTRY_ID, mStatesListModel.getStateModels().get(position).getName());

        Intent intent = new Intent(CitiesActivity.this, DashBoardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}