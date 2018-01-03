package com.xappie.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.EntertainmentAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.EntertainmentModel;
import com.xappie.models.LanguageListModel;
import com.xappie.models.LanguageModel;
import com.xappie.models.Model;
import com.xappie.models.TopStoriesListModel;
import com.xappie.parser.LanguageParser;
import com.xappie.parser.TopStoriesParser;
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
 * Created by Shankar 26/07/2017
 */
public class TopStoriesFragment extends Fragment implements IAsyncCaller, AbsListView.OnScrollListener {

    public static final String TAG = TopStoriesFragment.class.getSimpleName();
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
    @BindView(R.id.tv_no_data_found)
    TextView tv_no_data_found;


    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;
    private int aaTotalCount, aaVisibleCount, aaFirstVisibleItem;
    private int mPageNumber = 1;
    private boolean endScroll = false;

    /**
     * Gallery Actress setup
     */
    @BindView(R.id.ll_languages)
    LinearLayout ll_languages;
    @BindView(R.id.list_view)
    ListView list_view;

    private LanguageListModel mLanguageListModel;
    private TopStoriesListModel mTopStoriesListModel;
    private ArrayList<EntertainmentModel> entertainmentModels;
    private EntertainmentAdapter entertainmentAdapter;
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
        tv_title.setText(Utility.getResourcesString(mParent, R.string.top_stories));
        tv_title.setTypeface(mTypefaceOpenSansRegular);

        tv_location_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notifications_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);
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
     * This method is used to set the grid view data
     */
    private void setGridViewData() {
        entertainmentAdapter = new
                EntertainmentAdapter(mParent, entertainmentModels);
        list_view.setAdapter(entertainmentAdapter);
        list_view.setOnScrollListener(this);
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
                    entertainmentModels = null;
                    entertainmentAdapter = null;
                    setLanguages();
                    endScroll = false;
                    getTopStoriesData("" + 1);
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


    @OnItemClick(R.id.list_view)
    void navigateData(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.SELECTED_DETAIL_VIEW_ID, entertainmentModels.get(position).getId());
        bundle.putString(Constants.SELECTED_DETAIL_VIEW_FROM, TAG);
        bundle.putSerializable(Constants.SELECTED_MORE_TOPICS_LIST, entertainmentModels);
        Utility.navigateDashBoardFragment(new GalleryDetailViewFragment(), GalleryDetailViewFragment.TAG, bundle,
                mParent);
    }

    /**
     * This method is used to get data from the more topics with some conditions
     */
    private ArrayList<EntertainmentModel> getMoreTopicsList(int position) {
        ArrayList<EntertainmentModel> entertainmentModels = new ArrayList<>();
        if (position == 0) {
            for (int i = 1; i < mTopStoriesListModel.getEntertainmentModels().size(); i++) {
                entertainmentModels.add(mTopStoriesListModel.getEntertainmentModels().get(i));
                if (entertainmentModels.size() == 4)
                    break;
            }
        } else if (position == 1) {
            for (int i = 0; i < mTopStoriesListModel.getEntertainmentModels().size(); i++) {
                if (i != 1)
                    entertainmentModels.add(mTopStoriesListModel.getEntertainmentModels().get(i));
                if (entertainmentModels.size() == 4)
                    break;
            }
        } else if (position == 2) {
            for (int i = 0; i < mTopStoriesListModel.getEntertainmentModels().size(); i++) {
                if (i != 2)
                    entertainmentModels.add(mTopStoriesListModel.getEntertainmentModels().get(i));
                if (entertainmentModels.size() == 4)
                    break;
            }
        } else if (position == 3) {
            for (int i = 0; i < mTopStoriesListModel.getEntertainmentModels().size(); i++) {
                if (i != 3)
                    entertainmentModels.add(mTopStoriesListModel.getEntertainmentModels().get(i));
                if (entertainmentModels.size() == 4)
                    break;
            }
        } else {
            for (int i = 0; i < mTopStoriesListModel.getEntertainmentModels().size(); i++) {
                entertainmentModels.add(mTopStoriesListModel.getEntertainmentModels().get(i));
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
                    for (int i = 0; i < mLanguageListModel.getLanguageModels().size(); i++) {
                        if (Utility.getSharedPrefStringData(mParent, Constants.SELECTED_LANGUAGE_ID)
                                .equalsIgnoreCase(mLanguageListModel.getLanguageModels().get(i).getId())) {
                            languageModel = mLanguageListModel.getLanguageModels().get(i);
                        }
                    }
                    setLanguages();
                    getTopStoriesData("" + 1);
                }
            } else if (model instanceof TopStoriesListModel) {
                mTopStoriesListModel = (TopStoriesListModel) model;
                if (entertainmentModels == null) {
                    if (mTopStoriesListModel.getEntertainmentModels() == null) {
                        tv_no_data_found.setVisibility(View.VISIBLE);
                        list_view.setVisibility(View.GONE);
                    } else {
                        tv_no_data_found.setVisibility(View.GONE);
                        list_view.setVisibility(View.VISIBLE);
                        if (entertainmentModels == null) {
                            entertainmentModels = new ArrayList<>();
                        }
                        entertainmentModels.addAll(mTopStoriesListModel.getEntertainmentModels());
                        if (entertainmentAdapter == null) {
                            setGridViewData();
                        }
                    }
                } else {
                    list_view.setVisibility(View.VISIBLE);
                    tv_no_data_found.setVisibility(View.GONE);
                    if (mTopStoriesListModel.getEntertainmentModels() != null && mTopStoriesListModel.getEntertainmentModels().size() > 0) {
                        entertainmentModels.addAll(mTopStoriesListModel.getEntertainmentModels());
                        if (entertainmentAdapter == null) {
                            setGridViewData();
                        } else {
                            entertainmentAdapter.notifyDataSetChanged();
                        }
                    } else {
                        endScroll = true;
                    }
                }
            }
        }
    }

    /**
     * This method is used to get data of the top stories
     */
    private void getTopStoriesData(String pageNo) {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            linkedHashMap.put("language", languageModel.getId());
            linkedHashMap.put(Constants.PAGE_NO, pageNo);
            linkedHashMap.put(Constants.PAGE_SIZE, Constants.PAGE_SIZE_VALUE);
            TopStoriesParser topStoriesParser = new TopStoriesParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_STORIES, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, topStoriesParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            isScrollCompleted();
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        aaTotalCount = totalItemCount;
        aaVisibleCount = visibleItemCount;
        aaFirstVisibleItem = firstVisibleItem;
    }

    private void isScrollCompleted() {
        if (aaTotalCount == (aaFirstVisibleItem + aaVisibleCount) && !endScroll) {
            if (Utility.isNetworkAvailable(getActivity())) {
                mPageNumber = mPageNumber + 1;
                getTopStoriesData("" + mPageNumber);
                Utility.showLog("mPageNumber", "mPageNumber : " + mPageNumber);
            } else {
                Utility.showSettingDialog(
                        getActivity(),
                        getActivity().getResources().getString(
                                R.string.no_internet_msg),
                        getActivity().getResources().getString(
                                R.string.no_internet_title),
                        Utility.NO_INTERNET_CONNECTION).show();
            }
        } else {
            if (list_view.getAdapter() != null) {
                if (list_view.getLastVisiblePosition() == list_view.getAdapter().getCount() - 1 &&
                        list_view.getChildAt(list_view.getChildCount() - 1).getBottom() <= list_view.getHeight()) {
                    Utility.showToastMessage(getActivity(), Utility.getResourcesString(getActivity(),
                            R.string.no_more_data_to_display));
                }
            }
        }
    }
}
