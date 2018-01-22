package com.xappie.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.SpinnerAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.CitiesNewListModel;
import com.xappie.models.DeviceTokenUpdateModel;
import com.xappie.models.LocalityListModel;
import com.xappie.models.Model;
import com.xappie.models.SpinnerModel;
import com.xappie.models.StatesListModel;
import com.xappie.parser.CityParser;
import com.xappie.parser.DeviceTokenUpdateParser;
import com.xappie.parser.LocalityParser;
import com.xappie.parser.StatesParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LocationSelectionFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = LocationSelectionFragment.class.getSimpleName();


    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;

    @BindView(R.id.et_state)
    EditText et_state;
    @BindView(R.id.et_city)
    EditText et_city;
    @BindView(R.id.et_locality)
    EditText et_locality;

    @BindView(R.id.btn_save)
    Button btn_save;
    @BindView(R.id.tv_back)
    TextView tv_back;

    private Typeface mTypefaceOpenSansRegular;
    private StatesListModel mStatesListModel;
    private CitiesNewListModel mCitiesNewListModel;
    private LocalityListModel mLocalityListModel;
    private Intent intent;
    private String mSelectedCountryId;
    private String mSelectedCountryName;

    private Bundle bundle;
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        mFrameLayout = (FrameLayout) getActivity().findViewById(R.id.content_frame);
        mParams = (CoordinatorLayout.LayoutParams) mFrameLayout.getLayoutParams();
        Utility.sendGoogleAnalytics(mParent, TAG);

        bundle = getArguments();
        if (bundle.containsKey(Constants.SELECTED_COUNTRY_ID)) {
            mSelectedCountryId = bundle.getString(Constants.SELECTED_COUNTRY_ID);
            mSelectedCountryName = bundle.getString(Constants.SELECTED_COUNTRY_NAME);
        }
    }

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
        rootView = inflater.inflate(R.layout.activity_location_selection, container, false);
        ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }


    private void initUI() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        tv_back.setTypeface(Utility.getMaterialIconsRegular(mParent));
        btn_save.setTypeface(mTypefaceOpenSansRegular);

        getStateList();
    }

    @OnClick(R.id.btn_save)
    public void navigateToHome() {
        if (isValueFields())
            updateDeviceData();
    }

    /**
     * This method is used to get is valid fields or not
     */
    private boolean isValueFields() {
        boolean isValid = true;
        if (Utility.isValueNullOrEmpty(et_state.getText().toString())) {
            Utility.setSnackBar(mParent, et_state, "Please select state");
            isValid = false;
        } else if (Utility.isValueNullOrEmpty(et_city.getText().toString())) {
            Utility.setSnackBar(mParent, et_city, "Please select city");
            et_city.requestFocus();
            isValid = false;
        }
        return isValid;
    }


    private String getStateId(String s) {
        String mStateId = "";
        for (int i = 0; i < mStatesListModel.getStateModels().size(); i++) {
            if (mStatesListModel.getStateModels().get(i).getName().equals(s)) {
                mStateId = mStatesListModel.getStateModels().get(i).getId();
            }
        }
        return mStateId;
    }

    private String getCityId(String s) {
        String mCityId = "";
        for (int i = 0; i < mCitiesNewListModel.getCityModels().size(); i++) {
            if (mCitiesNewListModel.getCityModels().get(i).getName().equals(s)) {
                mCityId = mCitiesNewListModel.getCityModels().get(i).getId();
            }
        }
        return mCityId;
    }

    private String getLocalityId(String s) {
        String mLocalityId = "";
        for (int i = 0; i < mLocalityListModel.getLocalityModels().size(); i++) {
            if (mLocalityListModel.getLocalityModels().get(i).getName().equals(s)) {
                mLocalityId = mLocalityListModel.getLocalityModels().get(i).getId();
            }
        }
        Utility.showLog("Testing", "" + mLocalityId);
        return mLocalityId;
    }


    @OnClick(R.id.tv_back)
    public void navigateToBack() {
        mParent.onBackPressed();
    }

    @OnClick(R.id.et_state)
    void setStateDataToTheSpinner() {
        if (mStatesListModel != null && mStatesListModel.getStateModels().size() > 0)
            showSpinnerDialog(mParent, "Select State", et_state, mStatesListModel.getSpinnerModels(), 3);
    }

    @OnClick(R.id.et_city)
    void setCityDataToTheSpinner() {
        if (mCitiesNewListModel != null && mCitiesNewListModel.getSpinnerModels().size() > 0)
            showSpinnerDialog(mParent, "Select City", et_city, mCitiesNewListModel.getSpinnerModels(), 4);
    }

    @OnClick(R.id.et_locality)
    void setLocalityDataToTheSpinner() {
        if (mLocalityListModel != null && mLocalityListModel.getSpinnerModels().size() > 0)
            showSpinnerDialog(mParent, "Select Locality", et_locality, mLocalityListModel.getSpinnerModels(), 5);
    }

    public void showSpinnerDialog(final Context context, final String title, final EditText et_spinner,
                                  ArrayList<SpinnerModel> itemsList, final int id) {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);

        /*CUSTOM TITLE*/
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_alert_dialog_title, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_alert_dialog_title);
        RelativeLayout dialog_back_ground = (RelativeLayout) view.findViewById(R.id.dialog_back_ground);
        dialog_back_ground.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        tv_title.setText(title);
        tv_title.setTextColor(context.getResources().getColor(R.color.blackColor));
        builderSingle.setCustomTitle(view);


        final SpinnerAdapter adapter = new SpinnerAdapter(context, itemsList);
        builderSingle.setAdapter(adapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SpinnerModel mData = (SpinnerModel) adapter.getItem(which);
                        if (id == 3) {
                            String text = mData.getTitle();
                            et_spinner.setText(text);
                            et_city.setText("");
                            getCityList();
                        } else if (id == 4) {
                            String text = mData.getTitle();
                            et_spinner.setText(text);
                            et_locality.setText("");
                            getLocalityList();
                        } else if (id == 5) {
                            String text = mData.getTitle();
                            et_spinner.setText(text);
                        }
                    }
                });
        builderSingle.show();
    }

    private void getCityList() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            CityParser statesParser = new CityParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_CITIES + "/" + getStateId(et_state.getText().toString()), linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, statesParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getLocalityList() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            LocalityParser localityParser = new LocalityParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_LOCALITIES + "/" + getCityId(et_city.getText().toString()), linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, localityParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to get the state list
     */
    private void getStateList() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            StatesParser statesParser = new StatesParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_STATES + "/" + mSelectedCountryId, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, statesParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof StatesListModel) {
                mStatesListModel = (StatesListModel) model;
            } else if (model instanceof CitiesNewListModel) {
                mCitiesNewListModel = (CitiesNewListModel) model;
            } else if (model instanceof LocalityListModel) {
                mLocalityListModel = (LocalityListModel) model;
                if (mLocalityListModel.getLocalityModels().size() == 0) {
                    Utility.showToastMessage(mParent,
                            Utility.getResourcesString(mParent, R.string.no_localities_found));
                }
            } else if (model instanceof DeviceTokenUpdateModel) {

                Utility.setSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_ID, mSelectedCountryId);
                Utility.setSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_NAME, mSelectedCountryName);
                Utility.setSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID, getStateId(et_state.getText().toString()));
                Utility.setSharedPrefStringData(mParent, Constants.SELECTED_STATE_NAME, et_state.getText().toString());
                Utility.setSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID, getCityId(et_city.getText().toString()));
                Utility.setSharedPrefStringData(mParent, Constants.SELECTED_CITY_NAME, et_city.getText().toString());


                Utility.setSharedPrefStringData(mParent, Constants.SELECTED_LOCALITY_ID, getLocalityId(et_locality.getText().toString()));
                Utility.setSharedPrefStringData(mParent, Constants.SELECTED_LOCALITY_NAME, et_locality.getText().toString());

                String s = Utility.getSharedPrefStringData(mParent, Constants.HOME_PAGE_CONTENTS);
                if (Utility.isValueNullOrEmpty(s)) {
                    Utility.setSharedPrefStringData(mParent, Constants.HOME_PAGE_CONTENTS, Constants.HOME_PAGE_CONTENTS_DATA);
                    Utility.setSharedPrefStringData(mParent, Constants.HOME_PAGE_EVENTS_CONTENTS, Constants.EVENTS_CLASSIFIEDS_JOBS);
                }
                Intent intent = new Intent(mParent, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    }

    private void updateDeviceData() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        paramMap.put("device_type", Constants.DEVICE_TYPE);
        paramMap.put("token", Utility.getSharedPrefStringData(mParent, Constants.KEY_FCM_TOKEN));
        paramMap.put("country", mSelectedCountryId);
        paramMap.put("state", getStateId(et_state.getText().toString()));
        paramMap.put("city", getCityId(et_state.getText().toString()));
        if (!Utility.isValueNullOrEmpty(et_locality.getText().toString()))
            paramMap.put("locality", getLocalityId(et_locality.getText().toString()));
        else
            paramMap.put("locality", "");
        paramMap.put("language", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_LANGUAGE_ID));
        paramMap.put("modules", Constants.HOME_PAGE_CONTENTS_DATA + "," + Constants.EVENTS_CLASSIFIEDS_JOBS);
        paramMap.put("notifications", Constants.HOME_PAGE_CONTENTS_DATA + "," + Constants.EVENTS_CLASSIFIEDS_JOBS);

        DeviceTokenUpdateParser mDeviceTokenUpdateParser = new DeviceTokenUpdateParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(mParent, Utility.getResourcesString(mParent,
                R.string.please_wait), true,
                APIConstants.UPDATE_DEVICE_PREFERENCE, paramMap,
                APIConstants.REQUEST_TYPE.POST, this, mDeviceTokenUpdateParser);
        Utility.execute(serverIntractorAsync);
    }

}


