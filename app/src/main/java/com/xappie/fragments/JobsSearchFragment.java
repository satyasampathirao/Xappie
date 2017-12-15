package com.xappie.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.SpinnerAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.CategoryModel;
import com.xappie.models.CitiesNewListModel;
import com.xappie.models.CountriesListModel;
import com.xappie.models.Model;
import com.xappie.models.SpinnerModel;
import com.xappie.models.StatesListModel;
import com.xappie.parser.CategoryParser;
import com.xappie.parser.CityParser;
import com.xappie.parser.CountriesParser;
import com.xappie.parser.StatesParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobsSearchFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = JobsSearchFragment.class.getSimpleName();
    private DashBoardActivity mParent;

    @BindView(R.id.et_select_job_category)
    EditText et_select_job_category;

    @BindView(R.id.et_country)
    EditText et_country;
    @BindView(R.id.et_state)
    EditText et_state;
    @BindView(R.id.et_city)
    EditText et_city;

    @BindView(R.id.edt_company_name)
    EditText edt_company_name;
    @BindView(R.id.edt_city_name)
    EditText edt_city_name;
    @BindView(R.id.btn_search)
    Button btn_search;


    private Typeface mTypefaceOpenSansRegular;
    private CategoryModel categoryModel;
    private CountriesListModel mCountriesListModel;
    private StatesListModel mStatesListModel;
    private CitiesNewListModel mCitiesNewListModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_jobs_search, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);

        edt_city_name.setTypeface(mTypefaceOpenSansRegular);
        edt_company_name.setTypeface(mTypefaceOpenSansRegular);
        edt_company_name.setTypeface(mTypefaceOpenSansRegular);
        btn_search.setTypeface(mTypefaceOpenSansRegular);

        getJobCategoryData();
        getCountryData();
    }

    /**
     * This method is used to get the country list
     */
    private void getCountryData() {
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
     * This method is used to get the jobs category
     */
    private void getJobCategoryData() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        CategoryParser mCategoryParser = new CategoryParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(),
                Utility.getResourcesString(getActivity(),
                        R.string.please_wait), true,
                APIConstants.JOB_CATEGORY_URL, paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mCategoryParser);
        Utility.execute(serverIntractorAsync);
    }

    @OnClick(R.id.btn_search)
    public void navigateFindJobs() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.FILTER_CATEGORY, getCategoryId(et_select_job_category.getText().toString()));
        bundle.putString(Constants.FILTER_FROM, Constants.FILTER);
        bundle.putString(Constants.FILTER_CITY, et_city.getText().toString());
        bundle.putString(Constants.FILTER_STATE, et_state.getText().toString());
        bundle.putString(Constants.FILTER_COUNTRY, et_country.getText().toString());
        bundle.putString(Constants.FILTER_SEARCH, edt_company_name.getText().toString());
        Utility.navigateAllJobsFragment(new FindJobsListFragment(), FindJobsListFragment.TAG, bundle, mParent);
    }

    private String getCategoryId(String s) {
        String mCategoryId = "";
        for (int i = 0; i < categoryModel.getCategoryModels().size(); i++) {
            if (categoryModel.getCategoryModels().get(i).getName().equals(s)) {
                mCategoryId = categoryModel.getCategoryModels().get(i).getId();
            }
        }
        return mCategoryId;
    }

    private String getCountryId(String s) {
        String mCountryId = "";
        for (int i = 0; i < mCountriesListModel.getCountriesModels().size(); i++) {
            if (mCountriesListModel.getCountriesModels().get(i).getCountry_name().equals(s)) {
                mCountryId = mCountriesListModel.getCountriesModels().get(i).getId();
            }
        }
        return mCountryId;
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


    @OnClick(R.id.et_select_job_category)
    void setDataToTheSpinner() {
        if (categoryModel != null)
            showSpinnerDialog(mParent, "Job Category", et_select_job_category, categoryModel.getmCategorySpinnerModels(), 1);
    }

    @OnClick(R.id.et_country)
    void setCountryDataToTheSpinner() {
        if (mCountriesListModel != null)
            showSpinnerDialog(mParent, "Select Country", et_country, mCountriesListModel.getSpinnerCountyModels(), 2);
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
                        if (id == 1) {
                            String text = mData.getTitle();
                            et_spinner.setText(text);
                        } else if (id == 2) {
                            String text = mData.getTitle();
                            et_spinner.setText(text);
                            getStateList();
                        } else if (id == 3) {
                            String text = mData.getTitle();
                            et_spinner.setText(text);
                            getCityList();
                        } else if (id == 4) {
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
                    APIConstants.GET_STATES + "/" + getCountryId(et_country.getText().toString()), linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, statesParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof CategoryModel) {
                categoryModel = (CategoryModel) model;
            } else if (model instanceof CountriesListModel) {
                mCountriesListModel = (CountriesListModel) model;
            } else if (model instanceof StatesListModel) {
                mStatesListModel = (StatesListModel) model;
            } else if (model instanceof CitiesNewListModel) {
                mCitiesNewListModel = (CitiesNewListModel) model;
            }
        }
    }
}
