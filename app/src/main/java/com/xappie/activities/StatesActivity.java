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
public class StatesActivity extends BaseActivity implements IAsyncCaller {
    public static final String TAG = StatesActivity.class.getSimpleName();

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
    private String mSelectedCountryId;
    private String mSelectedCountryName;

    private ArrayList<StateModel> stateModels;
    private StatesListAdapter statesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.fragment_states);
        ButterKnife.bind(this);
        initUI();
        Utility.sendGoogleAnalytics(this, TAG);
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

       tv_back.setTypeface(Utility.getMaterialIconsRegular(this));
       tv_location.setTypeface(mTypefaceOpenSansRegular);

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
                    stateModels = mStatesListModel.getStateModels();
                    statesListAdapter = new StatesListAdapter(StatesActivity.this, mStatesListModel.getStateModels());
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

        Intent intent = new Intent(StatesActivity.this, CitiesActivity.class);
        intent.putExtra(Constants.SELECTED_COUNTRY_NAME, mSelectedCountryName);
        intent.putExtra(Constants.SELECTED_COUNTRY_ID, mSelectedCountryId);
        intent.putExtra(Constants.SELECTED_STATE_ID, mStatesListModel.getStateModels().get(position).getId());
        intent.putExtra(Constants.SELECTED_STATE_NAME, mStatesListModel.getStateModels().get(position).getName());
        startActivity(intent);
    }

}
