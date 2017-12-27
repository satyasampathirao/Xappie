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
import com.xappie.models.StateModel;
import com.xappie.models.StatesListModel;
import com.xappie.parser.StatesParser;
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
public class CitiesActivity extends BaseActivity implements IAsyncCaller {
    public static final String TAG = CitiesActivity.class.getSimpleName();

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;


    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_location)
    TextView tv_location;

    @BindView(R.id.city_list_view)
    ListView city_list_view;

    private StatesListModel mStatesListModel;
    private Intent intent;
    private String mSelectedCountryName;
    private String mSelectedCountryId;
    private String mSelectedStateName;
    private String mSelectedStateId;

    private ArrayList<StateModel> stateModels;
    private StatesListAdapter statesListAdapter;

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
        mSelectedCountryId = intent.getStringExtra(Constants.SELECTED_COUNTRY_ID);
        setTypeFace();
    }

    private void setTypeFace() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(this);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(this);

        tv_back.setTypeface(Utility.getMaterialIconsRegular(this));
        tv_location.setTypeface(mTypefaceOpenSansRegular);

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
                    stateModels = mStatesListModel.getStateModels();
                    statesListAdapter = new StatesListAdapter(CitiesActivity.this, mStatesListModel.getStateModels());
                    city_list_view.setAdapter(statesListAdapter);
                }
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
    @OnItemClick(R.id.city_list_view)
    void onItemClick(int position) {

        for (int i = 0; i < stateModels.size(); i++) {
            StateModel stateModel = stateModels.get(i);
            stateModel.setmSelected(false);
            stateModels.set(i, stateModel);
        }

        StateModel stateModel = stateModels.get(position);
        stateModel.setmSelected(true);
        statesListAdapter.notifyDataSetChanged();

        Utility.setSharedPrefStringData(this, Constants.SELECTED_COUNTRY_ID, mSelectedCountryId);
        Utility.setSharedPrefStringData(this, Constants.SELECTED_COUNTRY_NAME, mSelectedCountryName);
        Utility.setSharedPrefStringData(this, Constants.SELECTED_STATE_ID, mSelectedStateId);
        Utility.setSharedPrefStringData(this, Constants.SELECTED_STATE_NAME, mSelectedStateName);
        Utility.setSharedPrefStringData(this, Constants.SELECTED_CITY_ID, mStatesListModel.getStateModels().get(position).getId());
        Utility.setSharedPrefStringData(this, Constants.SELECTED_CITY_NAME, mStatesListModel.getStateModels().get(position).getName());

        String s = Utility.getSharedPrefStringData(this, Constants.HOME_PAGE_CONTENTS);
        if (Utility.isValueNullOrEmpty(s)) {
            Utility.setSharedPrefStringData(this, Constants.HOME_PAGE_CONTENTS, Constants.HOME_PAGE_CONTENTS_DATA);
            Utility.setSharedPrefStringData(this, Constants.HOME_PAGE_EVENTS_CONTENTS, Constants.EVENTS_CLASSIFIEDS_JOBS);
        }

        Intent intent = new Intent(CitiesActivity.this, DashBoardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
