package com.xappie.fragments;


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
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.ClassifiedsListAdapter;
import com.xappie.models.EntertainmentModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shankar Pilli on 07/28/2017
 */
public class ClassifiedsListFragment extends Fragment {

    public static final String TAG = ClassifiedsListFragment.class.getSimpleName();
    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;

    /**
     * Classifieds Toolbar
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
     * Classifieds List set up
     */
    @BindView(R.id.list_view)
    ListView list_view;

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
        View rootView = inflater.inflate(R.layout.fragment_classifieds_list, container, false);
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

        tv_notification_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notification_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(Utility.getResourcesString(mParent, R.string.auto_mobiles));
        tv_title.setTypeface(mTypefaceOpenSansRegular);

        tv_location_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_notifications_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_language_icon.setTypeface(mTypefaceFontAwesomeWebFont);

        setGridViewData();
    }

    /*This method is used to set the lsit view data*/
    private void setGridViewData() {
        ClassifiedsListAdapter classifiedsListAdapter = new ClassifiedsListAdapter(mParent, getEntertainData());
        list_view.setAdapter(classifiedsListAdapter);
    }

    private ArrayList<EntertainmentModel> getEntertainData() {
        ArrayList<EntertainmentModel> entertainmentModels = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            EntertainmentModel entertainmentModel = new EntertainmentModel();
            entertainmentModel.setId(R.drawable.video_hint);
            entertainmentModel.setTitle("Rarandoi");
            entertainmentModels.add(entertainmentModel);
        }
        return entertainmentModels;
    }

    /**
     * This method is used for back from the fragment
     */
    @OnClick({R.id.tv_notification_arrow_back_icon,
            R.id.tv_notification_menu_icon})
    void backToTheHome() {
        mParent.onBackPressed();
    }

    /**
     * This method is used navigate post
     */
    @OnClick(R.id.fab)
    void navigateToPost() {
        Utility.navigateDashBoardFragment(new ClassifiedsPostFragment(), ClassifiedsPostFragment.TAG, null, mParent);
    }

    @OnClick(R.id.tv_notifications_icon)
    public void navigateNotification()
    {
        Utility.navigateDashBoardFragment(new NotificationsFragment(),NotificationsFragment.TAG,null,mParent);
    }
    @OnClick(R.id.tv_language_icon)
    public void navigateLanguage()
    {
        Utility.navigateDashBoardFragment(new LanguageFragment(),LanguageFragment.TAG,null,mParent);
    }
    @OnClick(R.id.tv_location_icon)
    public void navigateLocation()
    {
        Utility.navigateDashBoardFragment(new CountriesFragment(),CountriesFragment.TAG,null,mParent);
    }

}

