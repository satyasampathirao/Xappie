package com.xappie.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.GalleryDetailImageAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.GalleryImageViewListModel;
import com.xappie.models.LanguageListModel;
import com.xappie.models.Model;
import com.xappie.parser.GalleryImageViewListParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shankar 26/07/2017
 */
public class GalleryImageViewFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = GalleryImageViewFragment.class.getSimpleName();
    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;

    /**
     * Gallery Toolbar
     */
    @BindView(R.id.tv_notification_arrow_back_icon)
    TextView tv_notification_arrow_back_icon;
    @BindView(R.id.tv_title_from)
    TextView tv_title_from;

    @BindView(R.id.tv_page_no)
    TextView tv_page_no;
    @BindView(R.id.tv_share_icon)
    TextView tv_share_icon;

    @BindView(R.id.gallery_images_view_pager)
    protected ViewPager gallery_images_view_pager;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceFontAwesomeWebFont;


    private GalleryDetailImageAdapter galleryDetailImageAdapter;
    private String mGalleryId = "";
    private View rootView;
    private GalleryImageViewListModel mGalleryImageViewListModel;

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
        rootView = inflater.inflate(R.layout.fragment_gallery_image_view, container, false);
        ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    private void initUI() {
        setTypeFace();
        getGalleryData();
    }

    /**
     * This method is used to get data of the gallery
     */
    private void getGalleryData() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            GalleryImageViewListParser galleryImageViewListParser = new GalleryImageViewListParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_GALLERY_DETAILS + mGalleryId, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, galleryImageViewListParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used for back from the fragment
     */
    @OnClick({R.id.tv_notification_arrow_back_icon,
            R.id.tv_title_from})
    void backToView() {
        mParent.onBackPressed();
    }

    private void setTypeFace() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);

        tv_notification_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_title_from.setTypeface(mTypefaceOpenSansRegular);

        tv_page_no.setTypeface(mTypefaceOpenSansRegular);
        tv_share_icon.setTypeface(mTypefaceFontAwesomeWebFont);

    }


    /**
     * After complete the service call back will be coming in this method
     * It returns the respective model
     */
    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof GalleryImageViewListModel) {
                mGalleryImageViewListModel = (GalleryImageViewListModel) model;
                if (mGalleryImageViewListModel.getGalleryImageViewModels().size() > 0) {
                    setGalleryData();
                }
            }
        }
    }

    /**
     * This method is used to set the gallery
     */
    private void setGalleryData() {
        galleryDetailImageAdapter = new GalleryDetailImageAdapter(mParent, mGalleryImageViewListModel.getGalleryImageViewModels());
        gallery_images_view_pager.setAdapter(galleryDetailImageAdapter);
    }
}
