package com.xappie.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ListView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.adapters.LocalityListAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.DeviceTokenUpdateModel;
import com.xappie.models.LocalityListModel;
import com.xappie.models.LocalityModel;
import com.xappie.models.Model;
import com.xappie.parser.LocalityParser;
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
public class LocalitiesActivity extends BaseActivity implements IAsyncCaller {
    public static final String TAG = LocalitiesActivity.class.getSimpleName();

    private Typeface mTypefaceOpenSansRegular;
    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_location)
    TextView tv_location;

    @BindView(R.id.city_list_view)
    ListView city_list_view;

    private LocalityListModel mLocalityListModel;
    private Intent intent;
    private String mSelectedCountryName;
    private String mSelectedCountryId;
    private String mSelectedStateName;
    private String mSelectedStateId;
    private String mSelectedCityName;
    private String mSelectedCityId;

    private ArrayList<LocalityModel> localityModels;
    private LocalityListAdapter localityListAdapter;

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
        mSelectedCityName = intent.getStringExtra(Constants.SELECTED_CITY_NAME);
        mSelectedStateId = intent.getStringExtra(Constants.SELECTED_STATE_ID);
        mSelectedCountryId = intent.getStringExtra(Constants.SELECTED_COUNTRY_ID);
        mSelectedCityId = intent.getStringExtra(Constants.SELECTED_CITY_ID);
        setTypeFace();
    }

    private void setTypeFace() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(this);

        tv_back.setTypeface(Utility.getMaterialIconsRegular(this));
        tv_location.setTypeface(mTypefaceOpenSansRegular);

        getLocalitiesList();
    }

    /**
     * This method is used to get the localities list from the server
     */
    private void getLocalitiesList() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            LocalityParser localityParser = new LocalityParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    this, Utility.getResourcesString(this, R.string.please_wait), true,
                    APIConstants.GET_LOCALITIES + "/" + mSelectedCityId, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, localityParser);
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
            if (model instanceof LocalityListModel) {
                mLocalityListModel = (LocalityListModel) model;
                if (mLocalityListModel.getLocalityModels().size() == 0) {
                    Utility.showToastMessage(LocalitiesActivity.this, Utility.getResourcesString(LocalitiesActivity.this, R.string.no_localities_found));
                    Intent intent = new Intent(LocalitiesActivity.this, DashBoardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } else {
                    localityModels = mLocalityListModel.getLocalityModels();
                    localityListAdapter = new LocalityListAdapter(LocalitiesActivity.this, mLocalityListModel.getLocalityModels());
                    city_list_view.setAdapter(localityListAdapter);
                }
            } else if (model instanceof DeviceTokenUpdateModel) {
                Intent intent = new Intent(LocalitiesActivity.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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

        for (int i = 0; i < localityModels.size(); i++) {
            LocalityModel localityModel = localityModels.get(i);
            localityModel.setmSelected(false);
            localityModels.set(i, localityModel);
        }

        LocalityModel localityModel = localityModels.get(position);
        localityModel.setmSelected(true);
        localityListAdapter.notifyDataSetChanged();

        Utility.setSharedPrefStringData(this, Constants.SELECTED_COUNTRY_ID, mSelectedCountryId);
        Utility.setSharedPrefStringData(this, Constants.SELECTED_COUNTRY_NAME, mSelectedCountryName);
        Utility.setSharedPrefStringData(this, Constants.SELECTED_STATE_ID, mSelectedStateId);
        Utility.setSharedPrefStringData(this, Constants.SELECTED_STATE_NAME, mSelectedStateName);
        Utility.setSharedPrefStringData(this, Constants.SELECTED_CITY_ID, mSelectedCityId);
        Utility.setSharedPrefStringData(this, Constants.SELECTED_CITY_NAME, mSelectedCityName);

        Utility.setSharedPrefStringData(this, Constants.SELECTED_LOCALITY_ID, mLocalityListModel.getLocalityModels().get(position).getId());
        Utility.setSharedPrefStringData(this, Constants.SELECTED_LOCALITY_NAME, mLocalityListModel.getLocalityModels().get(position).getName());

        String s = Utility.getSharedPrefStringData(this, Constants.HOME_PAGE_CONTENTS);
        if (Utility.isValueNullOrEmpty(s)) {
            Utility.setSharedPrefStringData(this, Constants.HOME_PAGE_CONTENTS, Constants.HOME_PAGE_CONTENTS_DATA);
            Utility.setSharedPrefStringData(this, Constants.HOME_PAGE_EVENTS_CONTENTS, Constants.EVENTS_CLASSIFIEDS_JOBS);
        }

        Intent intent = new Intent(LocalitiesActivity.this, DashBoardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }


}
