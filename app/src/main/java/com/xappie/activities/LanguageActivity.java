package com.xappie.activities;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.xappie.R;
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
 * Created by Shankar on 8/24/2017.
 */
public class LanguageActivity extends BaseActivity implements IAsyncCaller {

    public static final String TAG = LanguageActivity.class.getSimpleName();

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

    private ArrayList<LanguageModel> languageModels;
    private LanguagesListAdapter languagesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.fragment_language);
        ButterKnife.bind(this);
        initUI();
    }

    /**
     * This method is used for initialization
     */
    private void initUI() {
        setTypeFace();
    }

    /**
     * This method is used to set the font
     */
    private void setTypeFace() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(this);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(this);

        tv_languages.setTypeface(mTypefaceOpenSansRegular);
        tv_languages_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_languages_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_languages_menu_icon.setVisibility(View.INVISIBLE);
        tv_languages_arrow_back_icon.setVisibility(View.GONE);
        tv_languages.setTypeface(mTypefaceFontAwesomeWebFont);

        getLanguagesData();
    }

    /**
     * This method is used to get the Languages data from the server
     */
    private void getLanguagesData() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            LanguageParser lookUpEventTypeParser = new LanguageParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    this, Utility.getResourcesString(this, R.string.please_wait), true,
                    APIConstants.GET_LANGUAGES, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, lookUpEventTypeParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick({R.id.tv_languages_arrow_back_icon, R.id.tv_languages_menu_icon})
    public void navigateBack() {
        this.onBackPressed();
    }

    /**
     * After complete the service call back will be coming in this method
     * It returns the respective model
     */
    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof LanguageListModel) {
                mLanguageListModel = (LanguageListModel) model;
                languageModels = mLanguageListModel.getLanguageModels();
                languagesListAdapter = new LanguagesListAdapter(this, languageModels);
                language_list_item.setAdapter(languagesListAdapter);
            }
        }
    }

    /**
     * This method is used to select preferred language
     */
    @OnItemClick(R.id.language_list_item)
    void onItemClick(int position) {

        for (int i = 0; i < languageModels.size(); i++) {
            LanguageModel languageModel = languageModels.get(i);
            languageModel.setmSelected(false);
            languageModels.set(i, languageModel);
        }

        LanguageModel languageModel = languageModels.get(position);
        languageModel.setmSelected(true);
        languagesListAdapter.notifyDataSetChanged();

        Utility.setSharedPrefStringData(LanguageActivity.this, Constants.SELECTED_LANGUAGE, mLanguageListModel.getLanguageModels().get(position).getName());
        Utility.setSharedPrefStringData(LanguageActivity.this, Constants.SELECTED_LANGUAGE_ID, mLanguageListModel.getLanguageModels().get(position).getId());
        Intent intent = new Intent(LanguageActivity.this, CountriesActivity.class);
        startActivity(intent);
    }
}
