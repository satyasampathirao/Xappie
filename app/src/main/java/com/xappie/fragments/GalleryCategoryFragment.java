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
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.GalleryCategoryAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.GalleryCategoryListModel;
import com.xappie.models.GalleryCategoryModel;
import com.xappie.models.Model;
import com.xappie.parser.GalleryCategoryListParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shankar 16/10/2017.
 */
public class GalleryCategoryFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = GalleryCategoryFragment.class.getSimpleName();
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
    @BindView(R.id.grid_view)
    GridView grid_view;
    @BindView(R.id.tv_no_data_found)
    TextView tv_no_data_found;

    private String mGalleryId = "";
    private GalleryCategoryAdapter galleryCategoryAdapter;
    private ArrayList<GalleryCategoryModel> galleryCategoryModels;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        mFrameLayout = (FrameLayout) getActivity().findViewById(R.id.content_frame);
        mParams = (CoordinatorLayout.LayoutParams) mFrameLayout.getLayoutParams();
        if (getArguments() != null && getArguments().containsKey(Constants.GALLERY_ID)) {
            mGalleryId = getArguments().getString(Constants.GALLERY_ID);
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
        rootView = inflater.inflate(R.layout.fragment_gallery_category, container, false);
        ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    /**
     * This method is used for initialization
     */
    private void initUI() {
        setTypeFace();
        getGalleryCategoryData();
    }

    private void setTypeFace() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);

        tv_notification_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_title.setVisibility(View.GONE);
        tv_title.setTypeface(mTypefaceOpenSansRegular);

        tv_location_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notifications_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);
    }


    /**
     * This method is used to set the grid view data
     */
    private void setGridViewData() {
        galleryCategoryAdapter = new
                GalleryCategoryAdapter(mParent, galleryCategoryModels);
        grid_view.setAdapter(galleryCategoryAdapter);
    }

    /**
     * Get the Gallery data
     */
    private void getGalleryCategoryData() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            GalleryCategoryListParser galleryCategoryListParser = new GalleryCategoryListParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_GALLERY_ALBUM + mGalleryId, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, galleryCategoryListParser);
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
            if (model instanceof GalleryCategoryListModel) {
                GalleryCategoryListModel mGalleryCategoryListModel = (GalleryCategoryListModel) model;
                if (galleryCategoryModels == null) {
                    if (mGalleryCategoryListModel.getGalleryCategoryModels() == null) {
                        tv_no_data_found.setVisibility(View.VISIBLE);
                        grid_view.setVisibility(View.GONE);
                    } else {
                        tv_no_data_found.setVisibility(View.GONE);
                        grid_view.setVisibility(View.VISIBLE);
                        if (galleryCategoryModels == null) {
                            galleryCategoryModels = new ArrayList<>();
                        }
                        galleryCategoryModels.addAll(mGalleryCategoryListModel.getGalleryCategoryModels());
                        if (galleryCategoryAdapter == null) {
                            setGridViewData();
                        }
                    }
                } else {
                    grid_view.setVisibility(View.VISIBLE);
                    tv_no_data_found.setVisibility(View.GONE);
                    if (mGalleryCategoryListModel.getGalleryCategoryModels() != null && mGalleryCategoryListModel.getGalleryCategoryModels().size() > 0) {
                        galleryCategoryModels.addAll(mGalleryCategoryListModel.getGalleryCategoryModels());
                        if (galleryCategoryAdapter == null) {
                            setGridViewData();
                        } else {
                            galleryCategoryAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }
    }
}
