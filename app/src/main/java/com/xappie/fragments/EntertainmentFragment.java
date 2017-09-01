package com.xappie.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.EntertainmentAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.EntertainmentListModel;
import com.xappie.models.EntertainmentModel;
import com.xappie.models.LanguageListModel;
import com.xappie.models.LanguageModel;
import com.xappie.models.Model;
import com.xappie.parser.EntertainmentParser;
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
public class EntertainmentFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = EntertainmentFragment.class.getSimpleName();
    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;
    private View rootView;

    /**
     * Gallery Toolbar
     */
    @BindView(R.id.tv_notification_arrow_back_icon)
    TextView tv_notification_arrow_back_icon;
    @BindView(R.id.tv_notification_menu_icon)
    TextView tv_notification_menu_icon;

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_location_icon)
    TextView tv_location_icon;
    @BindView(R.id.tv_notifications_icon)
    TextView tv_notifications_icon;
    @BindView(R.id.tv_language_icon)
    TextView tv_language_icon;
    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;

    /**
     * Gallery Actress setup
     */
    @BindView(R.id.ll_languages)
    LinearLayout ll_languages;
    @BindView(R.id.list_view)
    ListView list_view;


    private LanguageListModel mLanguageListModel;
    private EntertainmentListModel mEntertainmentListModel;
    private LanguageModel languageModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        mFrameLayout = (FrameLayout) getActivity().findViewById(R.id.content_frame);
        mParams = (CoordinatorLayout.LayoutParams) mFrameLayout.getLayoutParams();
    }

    @Override
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
        rootView = inflater.inflate(R.layout.fragment_entertainment, container, false);
        ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    private void initUI() {
        setTypeFace();
        getLanguagesData();
    }

    private void setTypeFace() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);

        tv_notification_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(Utility.getResourcesString(mParent, R.string.entertainment));
        tv_title.setTypeface(mTypefaceOpenSansRegular);

        tv_location_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notifications_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);
    }

    /**
     * This method is used to set the grid view data
     */
    private void setGridViewData() {
        EntertainmentAdapter entertainmentAdapter = new
                EntertainmentAdapter(mParent, mEntertainmentListModel.getEntertainmentModels());
        list_view.setAdapter(entertainmentAdapter);
    }

    /**
     * This method is used to set the languages
     */
    private void setLanguages() {
        ll_languages.removeAllViews();
        for (int i = 0; i < mLanguageListModel.getLanguageModels().size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.language_item, null);
            TextView tv_language_name = (TextView) ll.findViewById(R.id.tv_language_name);
            View view = ll.findViewById(R.id.view);
            tv_language_name.setText(mLanguageListModel.getLanguageModels().get(i).getName_native().toUpperCase());
            tv_language_name.setTextColor(Utility.getColor(mParent, R.color.white));
            tv_language_name.setTypeface(Utility.getOpenSansRegular(mParent));

            tv_language_name.setId(i);
            tv_language_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = v.getId();
                    languageModel = mLanguageListModel.getLanguageModels().get(pos);
                    setLanguages();
                    getEntertainmentData(languageModel.getId(), "" + 1);
                }
            });

            if (languageModel != null && mLanguageListModel.getLanguageModels().get(i).getId() == languageModel.getId()) {
                view.setVisibility(View.VISIBLE);
                tv_language_name.setTextColor(Utility.getColor(mParent, R.color.white));
                view.setBackgroundColor(Utility.getColor(mParent, R.color.white));
            } else {
                view.setVisibility(View.GONE);
            }

            ll_languages.addView(ll);
        }
    }

    /**
     * This method is used to get the Languages data from the server
     */
    private void getLanguagesData() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            LanguageParser languageParser = new LanguageParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_LANGUAGES, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, languageParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used for back from the fragment
     */
    @OnClick({R.id.tv_notification_arrow_back_icon,
            R.id.tv_notification_menu_icon})
    void backToTheHome() {
        mParent.onBackPressed();
    }

    @OnClick(R.id.tv_notifications_icon)
    public void navigateNotification() {
        Utility.navigateDashBoardFragment(new NotificationsFragment(), NotificationsFragment.TAG, null, mParent);
    }

    @OnClick(R.id.tv_language_icon)
    public void navigateLanguage() {
        Utility.navigateDashBoardFragment(new LanguageFragment(), LanguageFragment.TAG, null, mParent);
    }

    @OnClick(R.id.tv_location_icon)
    public void navigateLocation() {
        Utility.navigateDashBoardFragment(new CountriesFragment(), CountriesFragment.TAG, null, mParent);
    }

    @OnItemClick(R.id.list_view)
    void navigateData(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.SELECTED_DETAIL_VIEW_ID, mEntertainmentListModel.getEntertainmentModels().get(position).getId());
        bundle.putString(Constants.SELECTED_DETAIL_VIEW_FROM, TAG);
        bundle.putSerializable(Constants.SELECTED_MORE_TOPICS_LIST, getMoreTopicsList(position));
        Utility.navigateDashBoardFragment(new GalleryDetailViewFragment(), GalleryDetailViewFragment.TAG, bundle,
                mParent);
    }

    /**
     * This method is used to get data from the more topics with some conditions
     */
    private ArrayList<EntertainmentModel> getMoreTopicsList(int position) {
        ArrayList<EntertainmentModel> entertainmentModels = new ArrayList<>();
        if (position == 0) {
            for (int i = 1; i < mEntertainmentListModel.getEntertainmentModels().size(); i++) {
                entertainmentModels.add(mEntertainmentListModel.getEntertainmentModels().get(i));
                if (entertainmentModels.size() == 4)
                    break;
            }
        } else if (position == 1) {
            for (int i = 0; i < mEntertainmentListModel.getEntertainmentModels().size(); i++) {
                if (i != 1)
                    entertainmentModels.add(mEntertainmentListModel.getEntertainmentModels().get(i));
                if (entertainmentModels.size() == 4)
                    break;
            }
        } else if (position == 2) {
            for (int i = 0; i < mEntertainmentListModel.getEntertainmentModels().size(); i++) {
                if (i != 2)
                    entertainmentModels.add(mEntertainmentListModel.getEntertainmentModels().get(i));
                if (entertainmentModels.size() == 4)
                    break;
            }
        } else if (position == 3) {
            for (int i = 0; i < mEntertainmentListModel.getEntertainmentModels().size(); i++) {
                if (i != 3)
                    entertainmentModels.add(mEntertainmentListModel.getEntertainmentModels().get(i));
                if (entertainmentModels.size() == 4)
                    break;
            }
        } else {
            for (int i = 0; i < mEntertainmentListModel.getEntertainmentModels().size(); i++) {
                entertainmentModels.add(mEntertainmentListModel.getEntertainmentModels().get(i));
                if (entertainmentModels.size() == 4)
                    break;
            }
        }
        return entertainmentModels;
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
                if (mLanguageListModel.getLanguageModels().size() > 0) {
                    languageModel = mLanguageListModel.getLanguageModels().get(0);
                    setLanguages();
                    getEntertainmentData(mLanguageListModel.getLanguageModels().get(0).getId(), "" + 1);
                }
            } else if (model instanceof EntertainmentListModel) {
                mEntertainmentListModel = (EntertainmentListModel) model;
                if (mEntertainmentListModel.getEntertainmentModels().size() > 0) {
                    setGridViewData();
                }
            }
        }
    }

    /**
     * This method is used to get data of the Entertainments
     */
    private void getEntertainmentData(String id, String pageNo) {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            linkedHashMap.put("language", id);
            linkedHashMap.put(Constants.PAGE_NO, pageNo);
            linkedHashMap.put(Constants.PAGE_SIZE, Constants.PAGE_SIZE_VALUE);
            EntertainmentParser entertainmentParser = new EntertainmentParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_ENTERTAINMENTS, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, entertainmentParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
