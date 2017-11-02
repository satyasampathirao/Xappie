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
public class StatesActivity extends BaseActivity implements IAsyncCaller {
    public static final String TAG = StatesActivity.class.getSimpleName();

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
    @BindView(R.id.city_list_view)
    ListView city_list_view;

    private StatesListModel mStatesListModel;
    private Intent intent;
    private String mSelectedCountryId;
    private String mSelectedCountryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.fragment_states);
        ButterKnife.bind(this);
        initUI();
    }

    /**
     * This method is used for initialization
     */
    private void initUI() {
        intent = getIntent();
        mSelectedCountryId = intent.getStringExtra(Constants.SELECTED_COUNTRY_ID);
        mSelectedCountryName = intent.getStringExtra(Constants.SELECTED_COUNTRY_NAME);
        setTypeFace();
    }

    private void setTypeFace() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(this);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(this);

        tv_cities.setTypeface(mTypefaceOpenSansRegular);
        tv_cities.setText(Utility.getResourcesString(this, R.string.states));
        tv_countries_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_countries_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_arrow_right_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_city_name.setTypeface(mTypefaceOpenSansRegular);
        tv_country_name.setTypeface(mTypefaceOpenSansRegular);
        tv_country_name.setText(mSelectedCountryName);

        tv_city_name.setText(Utility.getResourcesString(this, R.string.select_state));

        tv_notification_icon.setVisibility(View.GONE);
        tv_language_icon.setVisibility(View.GONE);
        tv_countries_menu_icon.setVisibility(View.GONE);

        getStatesList();
    }

    /**
     * This method is used to get the States list from the server
     */
    private void getStatesList() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            StatesParser statesParser = new StatesParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    this, Utility.getResourcesString(this, R.string.please_wait), true,
                    APIConstants.GET_STATES + "/" + mSelectedCountryId, linkedHashMap,
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
                    Utility.showToastMessage(StatesActivity.this, Utility.getResourcesString(StatesActivity.this, R.string.no_states_found));
                } else {
                    city_list_view.setAdapter(new StatesListAdapter(StatesActivity.this, mStatesListModel.getStateModels()));
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
        Intent intent = new Intent(StatesActivity.this, CitiesActivity.class);
        intent.putExtra(Constants.SELECTED_COUNTRY_NAME, mSelectedCountryName);
        intent.putExtra(Constants.SELECTED_COUNTRY_ID, mSelectedCountryId);
        intent.putExtra(Constants.SELECTED_STATE_ID, mStatesListModel.getStateModels().get(position).getId());
        intent.putExtra(Constants.SELECTED_STATE_NAME, mStatesListModel.getStateModels().get(position).getName());
        startActivity(intent);
    }

}
