package com.xappie.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.ActressGridAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.ActressActorsListModel;
import com.xappie.models.GallerySubItemModel;
import com.xappie.models.LanguageListModel;
import com.xappie.models.LanguageModel;
import com.xappie.models.Model;
import com.xappie.parser.ActressActorParser;
import com.xappie.parser.LanguageParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shankar 26/07/2017.
 */
public class ActressFragment extends Fragment implements IAsyncCaller, AbsListView.OnScrollListener {

    public static final String TAG = ActressFragment.class.getSimpleName();
    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;
    private View rootView;

    private int aaTotalCount, aaVisibleCount, aaFirstVisibleItem;
    private int mPageNumber = 1;
    private boolean endScroll = false;

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
    @BindView(R.id.grid_view)
    GridView grid_view;
    @BindView(R.id.tv_no_data_found)
    TextView tv_no_data_found;

    @BindView(R.id.ll_a_to_z)
    LinearLayout ll_a_to_z;

    private String mStringTitle = "";
    private String mForGallery = "";
    private ActressGridAdapter actressGridAdapter;
    private LanguageListModel mLanguageListModel;
    private LanguageModel languageModel;
    private String mCurrentLanguage;
    private ArrayList<GallerySubItemModel> actressModels;

    private String mAtoZLetters[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    public static String mSelectedLetter = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        mFrameLayout = (FrameLayout) getActivity().findViewById(R.id.content_frame);
        mParams = (CoordinatorLayout.LayoutParams) mFrameLayout.getLayoutParams();
        Utility.sendGoogleAnalytics(mParent, TAG);
        if (getArguments() != null && getArguments().containsKey(Constants.TITLE)) {
            mStringTitle = getArguments().getString(Constants.TITLE);
            mForGallery = getArguments().getString(Constants.FOR_GALLERY);
            languageModel = (LanguageModel) getArguments().getSerializable(Constants.LANGUAGE_ID);
        }
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
        rootView = inflater.inflate(R.layout.fragment_actress, container, false);
        ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    /**
     * This method is used for initialization
     */
    private void initUI() {
        setTypeFace();
        getLanguagesData();
        setAtoZLetters();
    }

    private void setAtoZLetters() {
        ll_a_to_z.removeAllViews();
        for (int i = 0; i < mAtoZLetters.length; i++) {
            TextView tvLetters = new TextView(mParent);
            tvLetters.setTypeface(Utility.getOpenSansRegular(mParent));
            tvLetters.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
            tvLetters.setPadding(10, 10, 10, 10);
            tvLetters.setGravity(Gravity.CENTER);
            tvLetters.setTextColor(Utility.getColor(mParent, R.color.white));
            tvLetters.setBackgroundColor(Utility.getColor(mParent, R.color.colorPrimary));
            tvLetters.setText(mAtoZLetters[i]);

            if (!mSelectedLetter.equalsIgnoreCase(mAtoZLetters[i])) {
                tvLetters.setBackgroundColor(Utility.getColor(mParent, R.color.colorPrimary));
            } else {
                tvLetters.setBackgroundColor(Utility.getColor(mParent, R.color.colorPrimary));
                tvLetters.setBackground(Utility.getDrawable(mParent, R.drawable.circle_letter_selecter));
            }

            tvLetters.setId(i);
            tvLetters.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utility.isNetworkAvailable(mParent)) {
                        int mPosition = v.getId();
                        if (mSelectedLetter.equalsIgnoreCase(mAtoZLetters[mPosition])) {
                            mSelectedLetter = "";
                            setAtoZLetters();
                        } else {
                            mSelectedLetter = mAtoZLetters[mPosition];
                            setAtoZLetters();
                            actressModels = null;
                            getGalleryData("" + 1, mSelectedLetter);
                        }

                    } else {
                        Utility.showSettingDialog(
                                mParent,
                                Utility.getResourcesString(mParent,
                                        R.string.no_internet_msg),
                                Utility.getResourcesString(mParent,
                                        R.string.no_internet_title),
                                Utility.NO_INTERNET_CONNECTION).show();
                    }
                }
            });
            ll_a_to_z.addView(tvLetters);
        }
    }

    private void setTypeFace() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);

        tv_notification_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(mStringTitle);
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
        actressGridAdapter = new
                ActressGridAdapter(mParent, actressModels);
        grid_view.setAdapter(actressGridAdapter);
        grid_view.setOnScrollListener(this);
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
                    actressModels = null;
                    actressGridAdapter = null;
                    setLanguages();
                    endScroll = false;
                    mCurrentLanguage = languageModel.getId();
                    getGalleryData("" + 1, mSelectedLetter);
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
     * Get the Gallery data
     */
    private void getGalleryData(String pageNo, String mSelectedLetter) {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            linkedHashMap.put("language", mCurrentLanguage);
            linkedHashMap.put("type", mForGallery);
            linkedHashMap.put("start_letter", mSelectedLetter);
            linkedHashMap.put(Constants.PAGE_NO, pageNo);
            linkedHashMap.put(Constants.PAGE_SIZE, Constants.PAGE_SIZE_VALUE);
            ActressActorParser actressActorParser = new ActressActorParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_GALLERY, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, actressActorParser);
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
                        if (languageModel.getId()
                                .equalsIgnoreCase(mLanguageListModel.getLanguageModels().get(i).getId())) {
                            languageModel = mLanguageListModel.getLanguageModels().get(i);
                        }
                    }
                    setLanguages();
                    if (languageModel != null)
                        mCurrentLanguage = languageModel.getId();
                    getGalleryData("" + 1, mSelectedLetter);
                }
            } else if (model instanceof ActressActorsListModel) {
                ActressActorsListModel mActressActorsListModel = (ActressActorsListModel) model;
                if (actressModels == null) {
                    if (mActressActorsListModel.getGallerySubModels() == null) {
                        tv_no_data_found.setVisibility(View.VISIBLE);
                        grid_view.setVisibility(View.GONE);
                    } else {
                        tv_no_data_found.setVisibility(View.GONE);
                        grid_view.setVisibility(View.VISIBLE);
                        if (actressModels == null) {
                            actressModels = new ArrayList<>();
                        }
                        actressModels.addAll(mActressActorsListModel.getGallerySubModels());
                        if (actressGridAdapter == null) {
                            setGridViewData();
                        }
                    }
                } else {
                    grid_view.setVisibility(View.VISIBLE);
                    tv_no_data_found.setVisibility(View.GONE);
                    if (mActressActorsListModel.getGallerySubModels() != null && mActressActorsListModel.getGallerySubModels().size() > 0) {
                        actressModels.addAll(mActressActorsListModel.getGallerySubModels());
                        if (actressGridAdapter == null) {
                            setGridViewData();
                        } else {
                            actressGridAdapter.notifyDataSetChanged();
                        }
                    } else {
                        endScroll = true;
                    }
                }
            }
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
                getGalleryData("" + mPageNumber, mSelectedLetter);
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
            if (grid_view.getAdapter() != null) {
                if (grid_view.getLastVisiblePosition() == grid_view.getAdapter().getCount() - 1 &&
                        grid_view.getChildAt(grid_view.getChildCount() - 1).getBottom() <= grid_view.getHeight()) {
                    Utility.showToastMessage(getActivity(), Utility.getResourcesString(getActivity(),
                            R.string.no_more_data_to_display));
                }
            }
        }
    }
}
