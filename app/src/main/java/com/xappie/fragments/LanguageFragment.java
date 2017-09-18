package com.xappie.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.xappie.activities.CountriesActivity;
import com.xappie.activities.DashBoardActivity;
import com.xappie.activities.LanguageActivity;
import com.xappie.adapters.LanguagesListAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.LanguageListModel;
import com.xappie.models.LanguageModel;
import com.xappie.models.Model;
import com.xappie.parser.LanguageParser;
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
public class LanguageFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = LanguageFragment.class.getSimpleName();

    @BindView(R.id.tv_languages_arrow_back_icon)
    TextView tv_languages_arrow_back_icon;
    @BindView(R.id.tv_languages_menu_icon)
    TextView tv_languages_menu_icon;
    @BindView(R.id.tv_languages)
    TextView tv_languages;
    @BindView(R.id.language_list_item)
    ListView language_list_item;


    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;
    private LanguageListModel mLanguageListModel;


    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        mFrameLayout = (FrameLayout) getActivity().findViewById(R.id.content_frame);
        mParams = (CoordinatorLayout.LayoutParams) mFrameLayout.getLayoutParams();
    }

    public LanguageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (appBarLayout != null) {
            mParams.setBehavior(null);
            mFrameLayout.requestLayout();
            appBarLayout.setVisibility(View.GONE);
        }
        View rootView = inflater.inflate(R.layout.fragment_language, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {
        setTypeFace();
    }

    private void setTypeFace() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);

        tv_languages.setTypeface(mTypefaceOpenSansRegular);
        tv_languages_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_languages_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_languages.setTypeface(mTypefaceFontAwesomeWebFont);

        getLanguagesData();
    }

    private void getLanguagesData() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            LanguageParser lookUpEventTypeParser = new LanguageParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_LANGUAGES, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, lookUpEventTypeParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof LanguageListModel) {
                mLanguageListModel = (LanguageListModel) model;
                language_list_item.setAdapter(new LanguagesListAdapter(mParent, mLanguageListModel.getLanguageModels()));
            }
        }
    }

    /**
     * This method is used to select preferred language
     */
    @OnItemClick(R.id.language_list_item)
    void onItemClick(int position) {
        Utility.setSharedPrefStringData(mParent, Constants.SELECTED_LANGUAGE, mLanguageListModel.getLanguageModels().get(position).getName());
        Utility.setSharedPrefStringData(mParent, Constants.SELECTED_LANGUAGE_ID, mLanguageListModel.getLanguageModels().get(position).getId());
        Intent dashBoardIntent = new Intent(getActivity(), DashBoardActivity.class);
        startActivity(dashBoardIntent);
    }




    @OnClick({R.id.tv_languages_arrow_back_icon, R.id.tv_languages_menu_icon})
    public void navigateBack() {
        mParent.onBackPressed();
    }
}
